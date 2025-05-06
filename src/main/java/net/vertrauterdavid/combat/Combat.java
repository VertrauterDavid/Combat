package net.vertrauterdavid.combat;

import lombok.Getter;
import net.vertrauterdavid.combat.listener.*;
import net.vertrauterdavid.combat.util.MessageUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class Combat extends JavaPlugin {

    @Getter
    private static Combat instance;

    private final HashMap<UUID, Long> combatPlayers = new HashMap<>();
    private WorldGuardUtil worldGuardUtil = null;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        instance = this;

        if (Bukkit.getPluginManager().getPlugin("WorldGuard") != null) {
            worldGuardUtil = new WorldGuardUtil();
        }

        Bukkit.getPluginManager().registerEvents(new EntityDamageByEntityListener(), instance);
        Bukkit.getPluginManager().registerEvents(new PlayerCommandPreprocessListener(), instance);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(), instance);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), instance);
        Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), instance);

        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> Bukkit.getOnlinePlayers().forEach(player -> {
            if (isInCombat(player)) {
                long seconds = (combatPlayers.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000;
                player.sendActionBar(MessageUtil.get("ActionBar.Format").replaceAll("%seconds%", String.valueOf(seconds + 1)));
            }

            if (!isInCombat(player) && combatPlayers.containsKey(player.getUniqueId())) {
                if (!(Combat.getInstance().getConfig().getString("Messages.NoLongerInCombat", "").equalsIgnoreCase(""))) {
                    player.sendMessage(MessageUtil.get("Messages.NoLongerInCombat"));
                }
                combatPlayers.remove(player.getUniqueId());
            }
        }), 20, 20);
    }

    public boolean isInCombat(Player player) {
        if (!(combatPlayers.containsKey(player.getUniqueId()))) return false;
        return combatPlayers.getOrDefault(player.getUniqueId(), 0L) > System.currentTimeMillis();
    }

    public void setCombat(Player player) {
        if ((getConfig().getBoolean("ignore-op", true) && player.isOp()) || player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR) {
            return;
        }

        if (!isInCombat(player) && !(Combat.getInstance().getConfig().getString("Messages.NowInCombat", "").equalsIgnoreCase(""))) {
            player.sendMessage(MessageUtil.get("Messages.NowInCombat"));
        }

        combatPlayers.put(player.getUniqueId(), System.currentTimeMillis() + 1000 * getConfig().getLong("Duration", 0L));
        player.setGliding(false);
        player.setFlying(false);
        player.setAllowFlight(false);
    }

}
