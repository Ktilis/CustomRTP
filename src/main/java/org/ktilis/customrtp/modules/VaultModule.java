package org.ktilis.customrtp.modules;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.ktilis.customrtp.Config;

public class VaultModule {
    private static Economy economy;

    public static void setupEconomy(Economy econ) {
        economy = econ;
    }

    public static double getCost() {
        return Config.Modules.vaultCost;
    }

    /*
    true  - pay is possible
    false - pay is impossible
     */
    public static boolean checkMoney(Player player) {
        return economy.getBalance(player) >= getCost();
    }

    public static boolean wrireOff(Player player) {
        if (!checkMoney(player)) {
            return false;
        }
        economy.withdrawPlayer(player, getCost());
        return true;
    }

    public Economy getEconomy() {
        return economy;
    }
}
