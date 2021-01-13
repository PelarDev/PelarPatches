package ee.pelar.PelarPatches.TP_Protection;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.HashMap;
import java.util.UUID;

public class Events implements Listener {

    private HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
    private int cooldownLenght = 30;

    public Events(int cooldownLenght) {
        this.cooldownLenght = cooldownLenght;
    }


    @EventHandler
    public void onPlayerTp(PlayerTeleportEvent event) {
        if((event.getCause().name().equals("COMMAND")
                || event.getCause().name().equals("PLUGIN")
                || event.getCause().name().equals("UNKNOWN"))
                && event.getPlayer().getGameMode().toString().equals("SURVIVAL")
                && !event.getTo().getWorld().getEnvironment().equals(World.Environment.NORMAL)) {
            System.out.println("Lisasin");
            cooldown.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if((event.getDamager() instanceof Player) && (event.getEntity() instanceof Player)) {
            if(isInCooldown(event.getDamager().getUniqueId()) || isInCooldown(event.getEntity().getUniqueId())) {
                event.setCancelled(true);
                event.getDamager().sendMessage(ChatColor.RED + "TP järgne puutumatus pole veel läbi saanud.");
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if((event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)
                || (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION)
                && (event.getEntity() instanceof Player)) {
            if(isInCooldown(event.getEntity().getUniqueId())) {
                event.setCancelled(true);
            }
        }
    }
    
    public boolean isInCooldown(UUID uuid) {
        if(cooldown.containsKey(uuid)) {
            long timeLeft = (cooldown.get(uuid) + this.cooldownLenght * 1000L) - System.currentTimeMillis();
            return (timeLeft > 0);
        } else {
            return false;
        }
    }
}
