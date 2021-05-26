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

    /**
     * Create a packet
     *
     * @return The created packet
     */
    public static MapPacket createPacket() {
        return getInstance().createPacket();
    }

    /**
     * Get the colors data of a map
     *
     * @param view The MapView of the map
     * @return The colors data of the MapView
     */
    public static byte[] getColorsData(MapView view) {
        return getInstance().getColorsData(view);
    }

}
