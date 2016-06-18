package io.github.bougar.notLoseItems;
import java.util.Optional;

import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(id = "notloseitems", name = "Not lose items on death", version = "1.0")
public class NotLoseItems {
	
	@Listener(order=Order.FIRST) //We need capture as soon as possible
    public void onDropItems(DropItemEvent.Destruct event){ 
        boolean damageCause = event.getCause().first(DamageSource.class).isPresent(); 
        Optional<EntitySpawnCause> spawnType = event.getCause().first(EntitySpawnCause.class);
        if (spawnType.isPresent()){
            EntitySpawnCause cause = spawnType.get();
            //Next line check if entity is a player, and his death cause was some type of damage.
            if ( cause.getEntity().getType().equals(EntityTypes.PLAYER) && damageCause){  
                Player player = (Player) cause.getEntity();
                //If player has the keepitems.items permissions, we might cancell dropitems event
                if (player.hasPermission("keepitems.items"))
                    event.setCancelled(true);
            }
        }
    }
}
