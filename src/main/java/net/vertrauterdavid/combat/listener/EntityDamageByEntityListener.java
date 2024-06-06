package net.vertrauterdavid.combat.listener;

import net.vertrauterdavid.combat.Combat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void handle(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getEntity() instanceof Player player)) return;
        if (Combat.getInstance().getWorldGuardUtil() != null && Combat.getInstance().getWorldGuardUtil().isPvpDenied(player)) return;

        Entity damager = event.getDamager();

        if (damager instanceof Player damagerP) {
            Combat.getInstance().setCombat(player);
            Combat.getInstance().setCombat(damagerP);
        }

        if (damager instanceof Projectile projectile) {
            if (projectile.getShooter() instanceof Player shooter) {
                Combat.getInstance().setCombat(player);
                Combat.getInstance().setCombat(shooter);
            }
        }
    }

}
