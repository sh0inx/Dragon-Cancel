package io.github.sh0inx.dragoncancel.listeners;

import io.github.sh0inx.dragoncancel.DragonCancel;

import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Map;

public class EntitySpawnEventListener implements Listener {
    @EventHandler
    public void onEntitySpawnEvent(EntitySpawnEvent event) {

        boolean cancelDragon = false;

        for(Map.Entry<String, Boolean> world : DragonCancel.getInstance().getWorldsToCancel().entrySet()) {

            if(!event.getLocation().getWorld().getName().equalsIgnoreCase(world.getKey())) {
                return;
            }

            cancelDragon = world.getValue();
        }

        if(cancelDragon && event.getEntity() instanceof EnderDragon) {
            event.setCancelled(true);
        }
    }
}