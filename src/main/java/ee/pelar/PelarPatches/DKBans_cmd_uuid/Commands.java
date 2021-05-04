package ee.pelar.PelarPatches.DKBans_cmd_uuid;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Commands implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (args.length == 0) return false;
        String name = null, uuid = null;
        if (Bukkit.getPlayer(args[0]) != null) {
            Player player = Bukkit.getPlayer(args[0]);
            name = player.getName();
            uuid = player.getUniqueId().toString();
        }
        if (Bukkit.getOfflinePlayer(args[0]).hasPlayedBefore() && uuid == null) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            name = player.getName();
            uuid = player.getUniqueId().toString();
        }
        if (uuid == null) {
            sender.sendMessage("Player not found!");
            return false;
        }
        BaseComponent comp = new TextComponent("PelarPunish\n");
        comp.setColor(ChatColor.RED);
        comp.setBold(true);
        if (sender.hasPermission("dkbans.warn")) {
            comp.addExtra(getBaseComp(new TextComponent("Warn\n"), "/warn " + name, "/warn "
                    + uuid, ClickEvent.Action.SUGGEST_COMMAND, ChatColor.DARK_RED));
        }
        if (sender.hasPermission("dkbans.ban")) {
            comp.addExtra(getBaseComp(new TextComponent("Ban\n"), "/ban " + name, "/ban "
                    + uuid, ClickEvent.Action.SUGGEST_COMMAND, ChatColor.GOLD));
        }
        if (sender.hasPermission("dkbans.ban.temp.mute")) {
            comp.addExtra(getBaseComp(new TextComponent("Mute\n"), "/tempmute " + name, "/tempmute "
                    + uuid, ClickEvent.Action.SUGGEST_COMMAND, ChatColor.BLUE));
        }
        comp.addExtra(getBaseComp(new TextComponent("UUID"), "Copy UUID of player " + name,
                uuid, ClickEvent.Action.COPY_TO_CLIPBOARD, ChatColor.WHITE));
        sender.spigot().sendMessage(comp);
        //https://www.spigotmc.org/wiki/the-chat-component-api/
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> toReturn = new ArrayList<>();
        if (args.length == 1) Bukkit.getServer().getOnlinePlayers().forEach(player -> toReturn.add(player.getName()));
        return toReturn;
    }

    private BaseComponent getBaseComp(TextComponent txt, String hoverTxt, String clickEventContent, ClickEvent.Action action, ChatColor color) {
        txt.setColor(color);
        txt.setBold(false);
        txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(hoverTxt)));
        txt.setClickEvent(new ClickEvent(action, clickEventContent));
        return txt;
    }
}
