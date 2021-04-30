package ee.pelar.PelarPatches.TP_Protection;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
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
    private int ignoreCooldown = 35;

    public Events(int cooldownLenght, int ignoreCooldown) {
        this.cooldownLenght = cooldownLenght;
        this.ignoreCooldown = ignoreCooldown;
    }


    @EventHandler
    public void onPlayerTp(PlayerTeleportEvent event) {
        if((event.getCause().name().equals("COMMAND")
                || event.getCause().name().equals("PLUGIN")
                || event.getCause().name().equals("UNKNOWN"))
                && event.getPlayer().getGameMode().toString().equals("SURVIVAL")
                && !event.getTo().getWorld().getEnvironment().equals(World.Environment.NORMAL)
                && canGetProtection(event.getPlayer().getUniqueId())) {
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
        if ((event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE || event.getCause() == EntityDamageEvent.DamageCause.MAGIC) && event.getEntity() instanceof Player) {
            Projectile projectile = (Projectile) event.getDamager();
            if (projectile.getShooter() instanceof Player) {
                Player shooterP = (Player) projectile.getShooter();
                Player targetP = (Player) event.getEntity();
                if (isInCooldown(shooterP.getUniqueId())) {
                    event.setCancelled(true);
                    if (event.getDamager() instanceof Arrow) projectile.remove();
                    shooterP.sendMessage(ChatColor.RED + "TP järgne puutumatus pole veel läbi saanud.");
                } else if (isInCooldown(targetP.getUniqueId())) {
                    event.setCancelled(true);
                    if (event.getDamager() instanceof Arrow) projectile.remove();
                    shooterP.sendMessage(ChatColor.RED + "TP järgne puutumatus pole veel läbi saanud.");
                }
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
    
    private boolean isInCooldown(UUID uuid) {
        if(cooldown.containsKey(uuid)) {
            long timeLeft = (cooldown.get(uuid) + this.cooldownLenght * 1000L) - System.currentTimeMillis();
            return (timeLeft > 0);
        } else {
            return false;
        }
    }

    private boolean canGetProtection(UUID uuid) {
        if (cooldown.containsKey(uuid)) {
            long timeLeft = (cooldown.get(uuid) + this.ignoreCooldown * 1000L) - System.currentTimeMillis();
            return (timeLeft < 0);
        } else {
            return true;
        }
    }
}
