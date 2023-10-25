package io.github.sh0inx.dragoncancel.config;

import io.github.sh0inx.dragoncancel.DragonCancel;

public class Messages extends io.github.sh0inx.heart.configs.Messages {

    public Messages() {
        super(DragonCancel.getInstance().getDescription().getName());
    }

    @Override
    public String pluginSubTitle() {
        return lowLightColor + "--{ "
                + highlightColor + "&oHere be &nno"
                + highlightColor + "&o dragons.&r"
                + lowLightColor + " }--";
    }
}
