package org.ktilis.customrtp;

import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;

public class Config {
    private static CustomRTP plugin;

    public static void init() {
        plugin = CustomRTP.getInstance();
        reloadConfig();
    }

    public static void reloadConfig() {
        Bukkit.getScheduler().runTask(CustomRTP.getInstance(), () -> {
            plugin.reloadConfig();
            Messages.reloadConfigMessages();
            Modules.reloadConfigModules();
            Rtp.reloadConfigRtp();
        });
    }

    public static class Messages {
        public static String prefix;

        public static String[] rtpMessage;
        public static String[] rtpMessageWithVaultModule;
        public static TextComponent yesButton;
        public static String noMoney;
        public static String successfullyTeleported;

        public static void reloadConfigMessages() {
            String prefixLocal = plugin.getConfig().getString("messages.prefix");
            prefixLocal.replaceAll("&", "\u00a7");
            prefix = prefixLocal + " ";

            String yesButtonLocal = plugin.getConfig().getString("messages.yesButton");
            yesButtonLocal.replaceAll("&", "\u00a7");
            yesButton = new TextComponent(yesButtonLocal);

            ArrayList<String> rtpMessageArrayList = new ArrayList<>();
            for (Object o : plugin.getConfig().getList("messages.rtpMessage")) {
                String s = (String) o;
                s.replaceAll("&", "\u00a7");
                rtpMessageArrayList.add(prefix + s);
            }
            rtpMessage = rtpMessageArrayList.toArray(new String[rtpMessageArrayList.size()]);

            ArrayList<String> rtpMessageWithVaultModuleArrayList = new ArrayList<>();
            for (Object o : plugin.getConfig().getList("messages.rtpMessageVault")) {
                String s = (String) o;
                s.replaceAll("&", "\u00a7");
                s.replaceAll("%rtp_cost%", String.valueOf(plugin.getConfig().getInt("modules.vault.cost")));
                rtpMessageArrayList.add(prefix + s);
            }
            rtpMessageWithVaultModule = rtpMessageWithVaultModuleArrayList.toArray(new String[rtpMessageWithVaultModuleArrayList.size()]);

            String noMoneyLocal = plugin.getConfig().getString("messages.noMoney");
            noMoneyLocal.replaceAll("&", "\u00a7");
            noMoney = prefix + noMoneyLocal;

            String successfullyTeleportedLocal = plugin.getConfig().getString("messages.successfullyTeleported");
            successfullyTeleportedLocal.replaceAll("&", "\u00a7");
            successfullyTeleported = prefix + successfullyTeleportedLocal;
        }

    }

    public static class Modules {
        public static boolean isWorldGuardModuleEnabled = false;
        public static boolean denyRtpToRegions = false;

        public static boolean isVaultModuleEnabled = false;
        public static double vaultCost = 0.00;

        public static void reloadConfigModules() {
            // WorldGuard config
            isWorldGuardModuleEnabled = plugin.getConfig().getBoolean("modules.worldguard.enabled");
            denyRtpToRegions = plugin.getConfig().getBoolean("modules.worldguard.allowRtpToRegions");

            // Vault config
            isVaultModuleEnabled = plugin.getConfig().getBoolean("modules.vault.enabled");
            vaultCost = plugin.getConfig().getDouble("modules.vault.cost");
        }
    }

    public static class Rtp {
        public static int radius;
        public static int heightMin;
        public static int heightMax;
        public static Location worldSpawn;
        public static String worldName;

        public static void reloadConfigRtp() {
            radius = plugin.getConfig().getInt("rtp.location.radius");
            heightMin = plugin.getConfig().getInt("rtp.location.height.min");
            heightMax = plugin.getConfig().getInt("rtp.location.height.max");
            worldName = plugin.getConfig().getString("rtp.world");
            worldSpawn = Bukkit.getWorld(worldName).getSpawnLocation();
        }
    }
}
