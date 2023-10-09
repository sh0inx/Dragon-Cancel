package io.github.sh0inx.dragoncancellite;

import com.iridium.iridiumcolorapi.IridiumColorAPI;
import io.github.sh0inx.dragoncancellite.managers.CommandManager;

import de.tr7zw.changeme.nbtapi.NBTFile;
import de.tr7zw.changeme.nbtapi.NBTCompound;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class DragonCancelLite extends JavaPlugin {

    private static DragonCancelLite instance;
    public static FileConfiguration config;
    public static List<String> worldsToCancel;

    @Override
    public void onLoad() {
        loadConfiguration();
    }

    @Override
    public void onEnable() {
        instance = this;
        Bukkit.getPluginManager().registerEvents(new EntitySpawnEventListener(), this);
        this.getCommand("dragonCancel").setExecutor(new CommandManager());

        for(String world : getWorldsToCancel()) {
            try {
                if(world.equalsIgnoreCase("")) {
                    getLogger().warning("config.yml needs world names to continue, restart server with config.yml populated with world names");
                    return;
                }
                editLevelData(world);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadConfiguration() {
        this.saveDefaultConfig();
        config = this.getConfig();
    }

    public List<String> getWorldsToCancel() {
        return worldsToCancel = config.getStringList("worlds");
    }

    public String getSubString(Substring selection) {

        String hexColorBright = "F3C5FF";
        String hexColorDark = "592463";
        String highlightColor = "<SOLID:" + hexColorBright + ">";
        String lowLightColor = "<SOLID:" + hexColorDark + ">";
        String dragonCancel = DragonCancelLite.getInstance().getDescription().getName();
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

        return switch (selection) {
            case HEXCOLORBRIGHT -> hexColorBright;
            case HEXCOLORDARK -> hexColorDark;
            case HIGHLIGHTCOLOR -> highlightColor;
            case LOWLIGHTCOLOR -> lowLightColor;
            case DRAGONCANCEL -> dragonCancel;
            case DRAGON -> dragon;
            case CANCEL -> cancel;
            case TITLEPREFIX -> titleprefix;
            case TITLESUFFIX -> titlesuffix;
            case PREFIXPLUGINNAME -> prefixPluginName;
            case PREFIXINSIGNIA -> prefixInsignia;
            case MESSAGEPREFIX -> messagePrefix;
            case DRAGONCANCELTITLE -> dragonCancelTitle;
            case DRAGONCANCELSUBTITLE -> dragonCancelSubTitle;
            case CONSOLEDECORATOR -> consoleDecorator;
            default -> "[invalid string call \"" + selection + "\"]";
        };
    }

    private void editLevelData(String world) throws IOException {

        if(!backupWorld(world, "backupWorlds")) {
            getLogger().warning(
                    IridiumColorAPI.process(DragonCancelLite.getInstance().getSubString(Substring.CONSOLEDECORATOR)));

            getLogger().warning("Not editing \"" + world + "\" because backup failed.");
            getLogger().warning("Restart server with valid config and EXISTING world you want to dragon cancel.");
            getLogger().warning("If you still want to dragon cancel in \"" + world + "\", DO NOT ENTER THE WORLD.");
            getLogger().warning("If \"" + world + "\" already existed, see previous error/stacktrace.");

            getLogger().warning(
                    IridiumColorAPI.process(DragonCancelLite.getInstance().getSubString(Substring.CONSOLEDECORATOR)));
            return;
        }

        File file = new File(world + File.separator + "level.dat");
        NBTFile worldFile = new NBTFile(file);

        if(worldFile.getCompound("Data").getCompound("DragonFight") == null) {
            getLogger().warning("Cannot load \"DragonFight\" compound because \"DragonFight\" is null.");
            return;
        }

        NBTCompound compound = worldFile.getCompound("Data").getCompound("DragonFight");
        Byte PreviouslyKilled = compound.getByte("PreviouslyKilled");
        Byte DragonKilled = compound.getByte("DragonKilled");
        Byte NeedsStateScanning = compound.getByte("NeedsStateScanning");
        Byte Dragon = compound.getByte("Dragon");

        if(PreviouslyKilled == (byte) 0) {
            compound.setByte("PreviouslyKilled", (byte) 1);
        }
        if(DragonKilled == 0) {
            compound.setByte("DragonKilled", (byte) 1);
        }
        if(NeedsStateScanning == 1) {
            compound.setByte("NeedsStateScanning", (byte) 0);
        }

        if(Dragon != null) {
            compound.removeKey("Dragon");
        }

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

    public static DragonCancelLite getInstance() {
        return instance;
    }






}