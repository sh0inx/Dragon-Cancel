package io.github.sh0inx.dragoncancel.config;

import io.github.sh0inx.dragoncancel.commands.AboutCommand;
import io.github.sh0inx.dragoncancel.commands.CheckCommand;

public class Commands {
    public AboutCommand aboutCommand;
    public CheckCommand checkCommand;

    public Commands() {
        this("dragonCancel", "dragonCancel");
    }

    public Commands(String permissionBase, String commandBase) {

    }
}
