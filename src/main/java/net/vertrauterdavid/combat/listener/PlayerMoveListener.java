package net.vertrauterdavid.combat.listener;

import net.vertrauterdavid.combat.Combat;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void handle(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!(Combat.getInstance().getConfig().getBoolean("EnderPeal.Enabled"))) return;

        if ((Combat.getInstance().getConfig().getBoolean("ignore-op", true) && player.isOp()) || player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        if (Combat.getInstance().isInCombat(player)) {
            player.setGliding(false);
            player.setFlying(false);
            player.setAllowFlight(false);
        }
    }

}
