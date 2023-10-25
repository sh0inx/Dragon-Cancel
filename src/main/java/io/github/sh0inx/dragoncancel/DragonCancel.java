package io.github.sh0inx.dragoncancel;

import io.github.sh0inx.dragoncancel.listeners.EntitySpawnEventListener;
import io.github.sh0inx.dragoncancel.managers.CommandManager;
import io.github.sh0inx.dragoncancel.managers.ConfigManager;

import io.github.sh0inx.heart.Heart;
import io.github.sh0inx.heart.MessageType;
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

@Getter
public class DragonCancel extends Heart {

    private static DragonCancel instance;

    private final int bstatsPluginId = 20134;
    private final String modrinthPluginId = "4U7mK5Ez";

    private CommandManager commandManager;
    private ConfigManager configManager;

    @Override
    public UpdateCheck getUpdateCheck() {
        return new UpdateCheck();
    }

    @Override
    public VersionCheck getVersionCheck() {
        return new VersionCheck();
    }

    @Override
    public ProfileCheck getProfileCheck() {
        return new ProfileCheck();
    }

    public DragonCancel () {
        instance = this;
    }

    @Override
    public void onEnable() {
        instance = this;

        registerListeners();
        initializeManagers();
        super.onEnable();
        consoleMessage(MessageType.ENABLE);

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
    }

    @Override
    public void onDisable() {
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
        return DragonCancel.getInstance().getConfigManager().getPluginConfiguration().worlds;
    }

    private void editLevelData(String world) throws IOException {

        if(!backupWorld(world, "backupWorlds")) {
            getLogger().warning(
                    IridiumColorAPI.process(DragonCancel.getInstance().getConfigManager().getMessages().consoleDecorator));

            getLogger().warning("Not editing \"" + world + "\" because backup failed.");
            getLogger().warning("Restart server with valid config and EXISTING world you want to dragon cancel.");
            getLogger().warning("If you still want to dragon cancel in \"" + world + "\", DO NOT ENTER THE WORLD.");
            getLogger().warning("If \"" + world + "\" already existed, see previous error/stacktrace.");

            getLogger().warning(
                    IridiumColorAPI.process(DragonCancel.getInstance().getConfigManager().getMessages().consoleDecorator));
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

    public void addBstats(int bstatsPluginID) {
        new Metrics(this, bstatsPluginID);
    }

    public static DragonCancel getInstance() {
        return instance;
    }
}
