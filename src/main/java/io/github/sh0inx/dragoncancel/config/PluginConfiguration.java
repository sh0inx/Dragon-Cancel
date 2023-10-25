package io.github.sh0inx.dragoncancel.config;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class PluginConfiguration extends io.github.sh0inx.heart.configs.PluginConfiguration {

    public PluginConfiguration() {
        super();
    }

    public Map<String, Boolean> worlds = new ImmutableMap.Builder<String, Boolean>()
            .put("world_the_end", true)
            .put("example_world", true)
            .build();
}
