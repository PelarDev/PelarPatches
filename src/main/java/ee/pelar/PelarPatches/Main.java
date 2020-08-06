package ee.pelar.PelarPatches;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

    private int enabledPatches = 0;

    @Override
    public void onEnable() {

        enabledPatches = 0;

        loadConfig();
        FileConfiguration config = getConfig();

        log("Enabling patches...");

        if (config.getBoolean("authme-geyser")) {
            enablePatch(
                    "AuthMe-Geyser",
                    new ee.pelar.PelarPatches.AuthMe_Geyser.Events()
            );
        }

        if (config.getBoolean("discordsrv-griefprevention")) {
            enablePatch(
                    "DiscordSRV-GriefPrevention",
                    new ee.pelar.PelarPatches.DiscordSRV_GriefPrevention.Events()
            );
        }

        if (config.getBoolean("discordsrv-chat")) {
            enablePatch(
                    "DiscordSRV-Chat",
                    new ee.pelar.PelarPatches.DiscordSRV_Chat.Events()
            );
        }

        log("Enabled " + enabledPatches + " patch(es)!");
    }

    private void enablePatch(String name, Listener listener) {
        log("Enabling `" + name + "` patch...");
        getServer().getPluginManager().registerEvents(
                listener,
                this
        );
        log("Patch enabled!");
        enabledPatches += 1;
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin has been Disabled!");
    }

    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void log(String msg) {
        msg = "&c[PelarPatches]&r " + msg;
        getLogger().info(ChatColor.translateAlternateColorCodes('&', msg));
    }

}
