package fr.flowsqy.fakemap;

import org.bukkit.map.MapView;

public interface FakeMapHandler {

    /**
     * Create a packet
     *
     * @return The created packet
     */
    MapPacket createPacket();

    /**
     * Get the colors data of a map
     *
     * @param view The MapView of the map
     * @return The colors data of the MapView
     */
    byte[] getColorsData(MapView view);

}
