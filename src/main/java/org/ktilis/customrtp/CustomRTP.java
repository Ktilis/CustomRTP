package org.ktilis.customrtp;

import org.bukkit.plugin.java.JavaPlugin;

public final class CustomRTP extends JavaPlugin {

    private static CustomRTP instance;

    public static CustomRTP getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        Config.init();
        if (getConfig().getBoolean("modules.worldguard.enabled")) {
            if (!findPlugin("WorldGuard")) {
                getLogger().warning(String.format("[%s] - Disabled due to no WorldGuard dependency found!", getDescription().getName()));
                getServer().getPluginManager().disablePlugin(this);
            }
        }
        /*
        if (getConfig().getBoolean("modules.vault.enabled")) {
            if (!findPlugin("Vault")) {
                getLogger().warning(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
                getServer().getPluginManager().disablePlugin(this);
            } else {
                RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
                Economy economy = rsp.getProvider();
                VaultModule.setupEconomy(economy);
            }
        }
        */

        getCommand("rtp").setExecutor(new MainCMD());

        getLogger().info("Enabled.");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled.");
    }

    private boolean findPlugin(String name) {
        return getServer().getPluginManager().getPlugin(name) != null;
    }
}
