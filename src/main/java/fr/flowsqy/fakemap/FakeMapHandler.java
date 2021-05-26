package fr.flowsqy.fakemap;

import org.bukkit.map.MapView;

public interface FakeMapHandler {

    MapPacket createPacket();

    byte[] getColorsData(MapView view);

}
