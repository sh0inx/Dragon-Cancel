package io.github.sh0inx.dragoncancel.managers;

import io.github.sh0inx.dragoncancel.DragonCancel;
import io.github.sh0inx.dragoncancel.config.PluginConfiguration;
import io.github.sh0inx.dragoncancel.config.Commands;
import io.github.sh0inx.dragoncancel.config.Messages;


public class ConfigManager extends io.github.sh0inx.heart.managers.ConfigManager {

    PluginConfiguration pluginConfiguration;
    Commands commands;
    Messages messages;

    public ConfigManager() {
        super();
        pluginConfiguration = new PluginConfiguration();
        commands = new Commands();
        messages = new Messages();
    }

    @Override
    public PluginConfiguration getPluginConfiguration() {
        return pluginConfiguration;
    }

    @Override
    public Commands getCommands() {
        return commands;
    }

    @Override
    public Messages getMessages() {
        return messages;
    }

    @Override
    public void initializeConfigs() {
        super.initializeConfigs();
        loadConfigs();
        saveConfigs();
    }

    @Override
    public void loadConfigs() {
        super.loadConfigs();
        this.pluginConfiguration = DragonCancel.getInstance().getPersist().load(PluginConfiguration.class);
        this.messages = DragonCancel.getInstance().getPersist().load(Messages.class);
        this.commands = DragonCancel.getInstance().getPersist().load(Commands.class);
    }

    @Override
    public void saveConfigs() {
        super.saveConfigs();
        DragonCancel.getInstance().getPersist().save(pluginConfiguration);
        DragonCancel.getInstance().getPersist().save(messages);
        DragonCancel.getInstance().getPersist().save(commands);
    }

    @Override
    public void reloadConfig() {
        loadConfigs();
    }
}