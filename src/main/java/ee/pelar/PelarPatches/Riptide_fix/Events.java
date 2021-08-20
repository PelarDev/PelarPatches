package ee.pelar.PelarPatches.Riptide_fix;

import org.bukkit.ChatColor;
import org.bukkit.WeatherType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Events implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getItem() == null || event.getPlayer().getPlayerWeather() == null) return;
        if (event.getItem().containsEnchantment(Enchantment.RIPTIDE) && event.getPlayer().getPlayerWeather() == WeatherType.DOWNFALL) {
            event.getPlayer().resetPlayerWeather();
            event.getPlayer().sendMessage(ChatColor.RED + "Riptide (Tagasivool) lotsu pärast pandi sinu ilmaks serveri ilm");
        }
    }
}
