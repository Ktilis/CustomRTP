package org.ktilis.customrtp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.ktilis.customrtp.modules.LocationCalculationModule;

public class MainCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Use \"/" + label + " reload\" to reload config");
                return true;
            }
            Bukkit.getScheduler().runTask(CustomRTP.getInstance(), () -> rtp(sender, command, label, args, Config.Modules.isVaultModuleEnabled));
            return true;
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("customrtp.reload")) {
                Bukkit.getScheduler().runTask(CustomRTP.getInstance(), () -> {
                    Config.reloadConfig();
                    sender.sendMessage(ChatColor.GREEN + "Config was successfully reloaded!");
                });
            }
            return true;
        } /*else if (args[0].equalsIgnoreCase("acceptVault")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Use \"/" + label + " reload\" to reload config");
                return true;
            }
            Bukkit.getScheduler().runTask(CustomRTP.getInstance(), () -> rtpWithVaultModule(sender, command, label, args));
        }*/
        return true;
    }

    private void rtp(CommandSender sender, Command command, String label, String[] args, boolean sendVaultMessage) {
        /*if (sendVaultMessage) {
            sender.sendMessage(Config.Messages.rtpMessageWithVaultModule);
            TextComponent yesButton = Config.Messages.yesButton;
            yesButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + label + " acceptVault"));
            sender.spigot().sendMessage(yesButton);
            return;
        }*/
        Player p = (Player) sender;
        if (p.getWorld() != Bukkit.getWorld(Config.Rtp.worldName)) return;
        try {
            sender.sendMessage(Config.Messages.rtpMessage);
            if (p.teleport(LocationCalculationModule.calculate())) {
                sender.sendMessage(Config.Messages.successfullyTeleported);
            }
        } catch (Exception e) {
            sender.sendMessage("An error was received while executing the command: " + e.getMessage());
            e.printStackTrace();
            return;
        }
    }

    /*
    private void rtpWithVaultModule(CommandSender sender, Command command, String label, String[] args) {
        if (VaultModule.wrireOff((Player) sender)) {
            rtp(sender, command, label, args, false);
        } else {
            sender.sendMessage(Config.Messages.noMoney);
        }
    }
    */

}
