package org.ktilis.customrtp.modules;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.ktilis.customrtp.Config;

import java.util.concurrent.ThreadLocalRandom;

public class LocationCalculationModule {
    /**
     * @return safe location to teleportation
     */
    public static Location calculate() {
        World world = Bukkit.getWorld(Config.Rtp.worldName);
        while (true) {
            int x = calculateX();
            int z = calculateZ();
            for (int i = Config.Rtp.heightMin; i < Config.Rtp.heightMax - 1; i++) {
                Location one = new Location(world, x, i + 2, z);
                Location two = new Location(world, x, i + 1, z);
                Location three = new Location(world, x, i, z);
                if (
                        one.getBlock().getType() == Material.AIR &&
                                two.getBlock().getType() == Material.AIR &&
                                three.getBlock().getType() != Material.AIR &&
                                three.getBlock().getType() != Material.LAVA &&
                                three.getBlock().getType() != Material.WATER
                ) {
                    Location loc2 = new Location(world, x, i + 1, z);
                    if (!WorldGuardModule.checkRegion(loc2)) return loc2;
                }
            }
        }
    }

    private static int calculateX() {
        return ThreadLocalRandom.current().nextInt(
                Config.Rtp.worldSpawn.getBlockX() - Config.Rtp.radius,
                Config.Rtp.worldSpawn.getBlockX() + Config.Rtp.radius + 1
        );
    }

    private static int calculateZ() {
        return ThreadLocalRandom.current().nextInt(
                Config.Rtp.worldSpawn.getBlockZ() - Config.Rtp.radius,
                Config.Rtp.worldSpawn.getBlockZ() + Config.Rtp.radius + 1
        );
    }
}
