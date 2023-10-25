package io.github.sh0inx.dragoncancel;

import io.github.sh0inx.dragoncancel.config.PluginConfiguration;
import io.github.sh0inx.dragoncancel.listeners.EntitySpawnEventListener;
import io.github.sh0inx.dragoncancel.managers.CommandManager;
import io.github.sh0inx.dragoncancel.managers.ConfigManager;

import io.github.sh0inx.heart.Heart;
import io.github.sh0inx.heart.MessageType;
import io.github.sh0inx.heart.configs.Commands;
import io.github.sh0inx.heart.configs.Messages;
import io.github.sh0inx.heart.managers.versioncheck.ProfileCheck;
import io.github.sh0inx.heart.managers.versioncheck.UpdateCheck;
import io.github.sh0inx.heart.managers.versioncheck.VersionCheck;

import org.bstats.bukkit.Metrics;

import com.iridium.iridiumcolorapi.IridiumColorAPI;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTFile;

import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;

import lombok.Getter;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPluginLoader;

@Getter
public class DragonCancel extends Heart {

    private static DragonCancel instance;

    private final int bstatsPluginId = 20134;
    private final String modrinthPluginId = "4U7mK5Ez";
    final Metrics metrics = new Metrics(DragonCancel.getInstance(), bstatsPluginId);

    private PluginConfiguration pluginConfiguration;
    private Commands commands;
    private Messages messages;

    private ProfileCheck profileCheck;
    private UpdateCheck updateCheck;
    private VersionCheck versionCheck;

    private CommandManager commandManager;
    private ConfigManager configManager;

    public DragonCancel(JavaPluginLoader loader, PluginDescriptionFile description, File dataFolder, File file) {
        super(loader, description, dataFolder, file);
        instance = this;
    }

    public DragonCancel () {
        instance = this;
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onEnable() {
        instance = this;

        registerListeners();
        initializeManagers();
        consoleMessage(MessageType.ENABLE);
        startUpdateCheck();

        for(Map.Entry<String, Boolean> world : getWorldsToCancel().entrySet()) {

            try {
                if(world.getKey().isEmpty()) {
                    getLogger().warning("config.yml needs world names to continue, restart server with config.yml populated with world names");
                    return;
                }
                editLevelData(world.getKey());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        super.onEnable();
    }

    @Override
    public void onDisable() {
        metrics.shutdown();
    }

    @Override
    public void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new EntitySpawnEventListener(), this);
    }

    public void initializeManagers() {
        this.configManager = new ConfigManager();
        this.commandManager = new CommandManager("dragonCancel");
    }

    public Map<String, Boolean> getWorldsToCancel() {
        return DragonCancel.getInstance().pluginConfiguration.worlds;
    }

    public String getSubString(Substring selection) {

        String hexColorBright = "F3C5FF";
        String hexColorDark = "592463";
        String highlightColor = "<SOLID:" + hexColorBright + ">";
        String lowLightColor = "<SOLID:" + hexColorDark + ">";
        String dragonCancel = DragonCancel.getInstance().getDescription().getName();
        String dragon = dragonCancel.substring(0,6);
        String cancel = dragonCancel.substring(6,12);
        String titleprefix = "*+*+*+ ";
        String titlesuffix = " +*+*+*";
        String prefixPluginName = "<GRADIENT:" + hexColorDark + ">" + dragonCancel + "</GRADIENT:" + hexColorBright + ">";
        String prefixInsignia = "&8}";
        String messagePrefix = prefixPluginName + " " + prefixInsignia + " ";
        String dragonCancelTitle =
                "<GRADIENT:" + hexColorDark + ">"
                        + titleprefix
                        + dragon
                        + "</GRADIENT:" + hexColorBright + ">"
                        + "<GRADIENT:" + hexColorBright + ">"
                        + cancel
                        + titlesuffix
                        + "</GRADIENT:" + hexColorDark + ">";
        String dragonCancelSubTitle =
                lowLightColor + "--{ "
                        + highlightColor + "&oHere be &nno"
                        + highlightColor + "&o dragons.&r"
                        + lowLightColor + " }--";
        String consoleDecorator = "*+*+*+**+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*+*";

        String reponse = null;

        switch (selection) {
            case HEXCOLORBRIGHT:  {
                return hexColorBright;
            }
            case HEXCOLORDARK:  {
                return hexColorDark;
            }
            case HIGHLIGHTCOLOR:  {
                return highlightColor;
            }
            case LOWLIGHTCOLOR:  {
                return lowLightColor;
            }
            case DRAGONCANCEL:  {
                return dragonCancel;
            }
            case DRAGON:  {
                return dragon;
            }
            case CANCEL:  {
                return cancel;
            }
            case DRAGONCANCELTITLE: {
                return dragonCancelTitle;
            }
            case DRAGONCANCELSUBTITLE: {
                return dragonCancelSubTitle;
            }
            case TITLEPREFIX:  {
                return titleprefix;
            }
            case TITLESUFFIX:  {
                return titlesuffix;
            }
            case PREFIXPLUGINNAME:  {
                return prefixPluginName;
            }
            case PREFIXINSIGNIA: {
                return prefixInsignia;
            }
            case MESSAGEPREFIX:  {
                return messagePrefix;
            }
            case CONSOLEDECORATOR:  {
                return consoleDecorator;
            }
            default: {
                return "[invalid string call \"" + selection + "\"]";
            }
        }
    }

    private void editLevelData(String world) throws IOException {

        if(!backupWorld(world, "backupWorlds")) {
            getLogger().warning(
                    IridiumColorAPI.process(DragonCancel.getInstance().getSubString(Substring.CONSOLEDECORATOR)));

            getLogger().warning("Not editing \"" + world + "\" because backup failed.");
            getLogger().warning("Restart server with valid config and EXISTING world you want to dragon cancel.");
            getLogger().warning("If you still want to dragon cancel in \"" + world + "\", DO NOT ENTER THE WORLD.");
            getLogger().warning("If \"" + world + "\" already existed, see previous error/stacktrace.");

            getLogger().warning(
                    IridiumColorAPI.process(DragonCancel.getInstance().getSubString(Substring.CONSOLEDECORATOR)));
            return;
        }

        File file = new File(world + File.separator + "level.dat");
        NBTFile worldFile = new NBTFile(file);

        if(worldFile.getCompound("Data").getCompound("DragonFight") == null) {
            getLogger().warning("Cannot load \"DragonFight\" compound because \"DragonFight\" is null.");
            return;
        }

        NBTCompound compound = worldFile.getCompound("Data").getCompound("DragonFight");

        compound.setBoolean("PreviouslyKilled", true);
        compound.setBoolean("DragonKilled", true);
        compound.setBoolean("NeedsStateScanning", false);

        worldFile.save();
    }

    private boolean backupWorld(String world, String backupFolderName) {

        getLogger().info("Attempting to create backup of \"level.dat\" for \"" + world + "\"...");

        File pluginFolder = new File(getDataFolder().getPath());
        File worldFolder = new File(world);
        File levelData = new File(worldFolder + File.separator + "level.dat");

        File backupFolder = new File(pluginFolder.getPath() + File.separator + backupFolderName);
        File backupWorldFolder = new File(backupFolder + File.separator + world);
        File backupLevelData = new File(backupWorldFolder + File.separator + "level.dat");

        if (!backupFolder.exists()) backupFolder.mkdir();

        try {
            if(worldFolder.listFiles() == null) {
                getLogger().warning("No files in world folder found (Has \"" + world + "\" been created?).");
                return false;
            }
            if(!backupWorldFolder.exists()) {
                Files.copy(worldFolder.toPath(), backupWorldFolder.toPath());
            }

            Files.copy(levelData.toPath(), backupLevelData.toPath(), StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException exception) {
            getLogger().warning("Could not copy " + worldFolder.getName() + " to " + backupWorldFolder.getAbsolutePath());
            exception.printStackTrace();
            return false;
        }

        getLogger().info("Backup successful, check " + backupFolder.getPath() + ".");
        return true;
    }

    public static DragonCancel getInstance() {
        return instance;
    }
}
