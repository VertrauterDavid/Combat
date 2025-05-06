package net.vertrauterdavid.combat.util;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;
import net.vertrauterdavid.combat.Combat;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class MessageUtil {

    public static String get(String key) {
        return color(Combat.getInstance().getConfig().getString(key, ""))
                .replaceAll("%prefix%", getPrefix());
    }

    public static String getPrefix() {
        return color(Combat.getInstance().getConfig().getString("Messages.Prefix", ""));
    }

    public static String color(String message) {
        Pattern pattern = Pattern.compile("&#([A-Fa-f0-9]{6})");
        Matcher matcher = pattern.matcher(message);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(buffer, ChatColor.of("#" + matcher.group(1)).toString());
        }
        matcher.appendTail(buffer);
        return ChatColor.translateAlternateColorCodes('&', buffer.toString());
    }

}
