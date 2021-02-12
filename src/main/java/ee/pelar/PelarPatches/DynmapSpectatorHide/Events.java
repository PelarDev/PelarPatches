package ee.pelar.PelarPatches.DynmapSpectatorHide;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.dynmap.DynmapAPI;

public class Events implements Listener {

    @EventHandler
    public void onGmChange(PlayerGameModeChangeEvent event) {
        if (event.getNewGameMode() == GameMode.SPECTATOR && !event.getPlayer().hasPermission("dynmap.hide")) {
            DynmapAPI dynmap = (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("Dynmap");
            if (dynmap != null) {
                dynmap.setPlayerVisiblity(event.getPlayer(), false);
            }

        } else if (event.getNewGameMode() != GameMode.SPECTATOR && !event.getPlayer().hasPermission("dynmap.hide")) {
            DynmapAPI dynmap = (DynmapAPI) Bukkit.getServer().getPluginManager().getPlugin("Dynmap");
            if (dynmap != null) {
                dynmap.setPlayerVisiblity(event.getPlayer(), true);
            }

        }
    }

}
