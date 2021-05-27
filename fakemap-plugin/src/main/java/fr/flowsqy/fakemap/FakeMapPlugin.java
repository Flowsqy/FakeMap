package fr.flowsqy.fakemap;

import fr.flowsqy.fakemap.reflect.FakeMapImpl;
import org.bukkit.plugin.java.JavaPlugin;

public class FakeMapPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        FakeMap.setHandler(new FakeMapImpl());
    }
}
