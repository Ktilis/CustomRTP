package org.ktilis.customrtp.modules;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Location;
import org.ktilis.customrtp.Config;

public class WorldGuardModule {
    /*
     * false - there is no region or the isWorldGuardModuleEnabled and denyRtpToRegions settings in the config return false
     * true - there is a region
     */
    public static boolean checkRegion(Location location) {
        if (Config.Modules.isWorldGuardModuleEnabled && !Config.Modules.denyRtpToRegions) {
            com.sk89q.worldedit.util.Location loc = BukkitAdapter.adapt(location);
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            ApplicableRegionSet set = query.getApplicableRegions(loc);
            return set.size() > 0;
        } else {
            return false;
        }
    }
}
