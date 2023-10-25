package io.github.sh0inx.dragoncancel.managers;

import io.github.sh0inx.dragoncancel.DragonCancel;
import io.github.sh0inx.dragoncancel.config.PluginConfiguration;
import io.github.sh0inx.dragoncancel.config.Commands;
import io.github.sh0inx.dragoncancel.config.Messages;


public class ConfigManager extends io.github.sh0inx.heart.managers.ConfigManager {

    PluginConfiguration pluginConfiguration = new PluginConfiguration();
    Commands commands = new Commands();
    Messages messages = new Messages();

    public ConfigManager() {
        super();
    }

    @Override
    public void initializeConfigs() {
        super.initializeConfigs();
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
}