package fr.flowsqy.fakemap.v1_16_R3;

import fr.flowsqy.fakemap.FakeMapHandler;
import fr.flowsqy.fakemap.MapPacket;
import net.minecraft.server.v1_16_R3.WorldMap;
import org.bukkit.craftbukkit.v1_16_R3.map.CraftMapView;
import org.bukkit.map.MapView;

import java.lang.reflect.Field;

public class FakeMapImpl implements FakeMapHandler {

    // Colors data
    private final static Field worldMapField;

    static {
        try {
            // Colors data
            worldMapField = CraftMapView.class.getDeclaredField("worldMap");

            worldMapField.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MapPacket createPacket() {
        return new MapPacketImpl();
    }

    @Override
    public byte[] getColorsData(MapView view) {
        try {
            return ((WorldMap) worldMapField.get(view)).colors;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
