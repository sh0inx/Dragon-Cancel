package io.github.sh0inx.dragoncancellite;

import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnEventListener implements Listener {
    @EventHandler
    public void onEntitySpawnEvent(EntitySpawnEvent event) {

        boolean cancelDragon = false;

        for(String world : DragonCancelLite.getInstance().getWorldsToCancel()) {
            if(event.getLocation().getWorld().getName().equalsIgnoreCase(world)) {
                cancelDragon = true;
            }
        }

        if(cancelDragon && event.getEntity() instanceof EnderDragon) {
            event.setCancelled(true);
        }
    }
}