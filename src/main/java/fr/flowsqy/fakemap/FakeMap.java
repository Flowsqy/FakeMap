package fr.flowsqy.fakemap;

import fr.flowsqy.fakemap.reflect.FakeMapImpl;
import org.bukkit.map.MapView;

public class FakeMap {

    private static FakeMapHandler instance = null;

    public static FakeMapHandler getInstance() {
        if (instance == null) {
            instance = new FakeMapImpl();
        }
        return instance;
    }

    public static MapPacket createPacket() {
        return getInstance().createPacket();
    }

    public static byte[] getColorsData(MapView view) {
        return getInstance().getColorsData(view);
    }

}
