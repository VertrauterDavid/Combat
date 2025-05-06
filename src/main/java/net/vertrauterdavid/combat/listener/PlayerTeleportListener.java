package net.vertrauterdavid.combat.listener;

import net.vertrauterdavid.combat.Combat;
import net.vertrauterdavid.combat.util.MessageUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportListener implements Listener {

    @EventHandler
    public void handle(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        if (!(Combat.getInstance().getConfig().getBoolean("EnderPeal.Enabled"))) return;

        if (Combat.getInstance().isInCombat(player)) {
            if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
                Location from = event.getFrom().clone();
                from.setY(0);
                Location to = event.getTo().clone();
                to.setY(0);

                if (from.distance(to) > Combat.getInstance().getConfig().getLong("EnderPeal.Distance", 0)) {
                    event.setCancelled(true);
                    player.sendMessage(MessageUtil.get("EnderPeal.Format"));
                }
            }
        }
    }

}
