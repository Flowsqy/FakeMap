package fr.flowsqy.fakemap.reflect;

import fr.flowsqy.fakemap.FakeMapHandler;
import fr.flowsqy.fakemap.MapPacket;
import org.bukkit.Bukkit;
import org.bukkit.map.MapView;

import java.lang.reflect.Field;

public class FakeMapImpl implements FakeMapHandler {

    // Colors data
    private final static Field worldMapField;
    private final static Field colorsField;

    static {
        try {
            final String versionName = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            final String nms = "net.minecraft.server." + versionName + ".";
            final String craftbukkit = "org.bukkit.craftbukkit." + versionName + ".";

            // Colors data
            final Class<?> craftMapViewClass = Class.forName(craftbukkit + "map.CraftMapView");
            final Class<?> worldMapClass = Class.forName(nms + "WorldMap");

            worldMapField = craftMapViewClass.getDeclaredField("worldMap");
            colorsField = worldMapClass.getDeclaredField("colors");

            worldMapField.setAccessible(true);
            colorsField.setAccessible(true);

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
            return (byte[]) colorsField.get(worldMapField.get(view));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
