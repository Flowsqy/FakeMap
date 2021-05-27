package fr.flowsqy.fakemap;

import fr.flowsqy.fakemap.v1_16_R3.FakeMapImpl;
import org.bukkit.plugin.java.JavaPlugin;

public class FakeMapPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        FakeMap.setHandler(new FakeMapImpl());
    }
}
