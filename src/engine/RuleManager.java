package engine;

import model.AppType;

import java.util.HashSet;
import java.util.Set;

public class RuleManager {

    private Set<AppType> blockedApps = new HashSet<>();

    public void blockApp(AppType app) {
        blockedApps.add(app);
        System.out.println("Blocking app: " + app);
    }

    public boolean isBlocked(AppType app) {
        if (app == null) return false;
        return blockedApps.contains(app);
    }
}