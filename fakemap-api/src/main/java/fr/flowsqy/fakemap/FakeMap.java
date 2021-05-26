package fr.flowsqy.fakemap;

import org.bukkit.map.MapView;

public class FakeMap {

    private static FakeMapHandler handler = null;

    /**
     * Get the running FakeMapHandler
     *
     * @return The FakeMapHandler implementation
     */
    public static FakeMapHandler getHandler() {
        return handler;
    }

    /**
     * Set the running FakeMapHandler
     *
     * @param handler The FakeMapHandler implementation
     */
    public static void setHandler(FakeMapHandler handler) {
        FakeMap.handler = handler;
    }

    /**
     * Create a packet
     *
     * @return The created packet
     */
    public static MapPacket createPacket() {
        return handler.createPacket();
    }

    /**
     * Get the colors data of a map
     *
     * @param view The MapView of the map
     * @return The colors data of the MapView
     */
    public static byte[] getColorsData(MapView view) {
        return handler.getColorsData(view);
    }

}
