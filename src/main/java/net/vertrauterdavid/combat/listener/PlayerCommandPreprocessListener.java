package net.vertrauterdavid.combat.listener;

import net.vertrauterdavid.combat.Combat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class PlayerCommandPreprocessListener implements Listener {

    @EventHandler
    public void handle(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage().substring(1);

        if (Combat.getInstance().isInCombat(player)) {
            List<?> list = Combat.getInstance().getConfig().getList("Commands.Blocked", new ArrayList<>());
            if (list.contains(command.toLowerCase()) || list.contains(command.split(" ")[0].toUpperCase())) {
                event.setCancelled(true);
                player.sendMessage(Combat.getInstance().getMessage("Messages.Prefix") + Combat.getInstance().getMessage("Commands.Format").replaceAll("%command%", command));
            }
        }
    }

}
