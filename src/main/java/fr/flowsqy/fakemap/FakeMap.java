package fr.flowsqy.fakemap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapCursorCollection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

public class FakeMap {

    private final static Method handleMethod;
    private final static Method sendPacketMethod;
    private final static Field playerConnectionField;
    private final static Constructor<?> packetConstructor;
    private final static Field mapIdField;
    private final static Field scaleField;
    private final static Field trackingPositionField;
    private final static Field lockedField;
    private final static Field decorationsField;
    private final static Field startXField;
    private final static Field startYField;
    private final static Field widthField;
    private final static Field heightField;
    private final static Field mapColorsField;
    /*
    int a;          mapId
    byte b;         scale
    boolean c;      trackingPosition
    boolean d;      locked
    MapIcon[] e;    decorations
    int f;          startX
    int g;          startY
    int h;          width
    int i;          height
    byte[] j;       mapColors
     */


    static {
        try {
            final String versionName = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            final Class<?> mapPacketClass = Class.forName("net.minecraft.server." + versionName + ".PacketPlayOutMap");
            packetConstructor = mapPacketClass.getConstructor();
            mapIdField = mapPacketClass.getDeclaredField("a");
            scaleField = mapPacketClass.getDeclaredField("b");
            trackingPositionField = mapPacketClass.getDeclaredField("c");
            lockedField = mapPacketClass.getDeclaredField("d");
            decorationsField = mapPacketClass.getDeclaredField("e");
            startXField = mapPacketClass.getDeclaredField("f");
            startYField = mapPacketClass.getDeclaredField("g");
            widthField = mapPacketClass.getDeclaredField("h");
            heightField = mapPacketClass.getDeclaredField("i");
            mapColorsField = mapPacketClass.getDeclaredField("j");

            final Class<?> playerClass = Class.forName("org.bukkit.craftbukkit." + versionName + ".entity.CraftPlayer");
            handleMethod = playerClass.getDeclaredMethod("getHandle");
            final Class<?> entityPlayer = Class.forName("net.minecraft.server." + versionName + ".EntityPlayer");
            playerConnectionField = entityPlayer.getDeclaredField("playerConnection");
            final Class<?> playerConnectionClass = Class.forName("net.minecraft.server." + versionName + ".PlayerConnection");
            final Class<?> packetClass = Class.forName("net.minecraft.server." + versionName + ".Packet");
            sendPacketMethod = playerConnectionClass.getDeclaredMethod("sendPacket", packetClass);

            handleMethod.setAccessible(true);
            sendPacketMethod.setAccessible(true);
            playerConnectionField.setAccessible(true);
            packetConstructor.setAccessible(true);
            mapIdField.setAccessible(true);
            scaleField.setAccessible(true);
            trackingPositionField.setAccessible(true);
            lockedField.setAccessible(true);
            decorationsField.setAccessible(true);
            startXField.setAccessible(true);
            startYField.setAccessible(true);
            widthField.setAccessible(true);
            heightField.setAccessible(true);
            mapColorsField.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static final class Unsafe {

        public static Object createPacket() throws InvocationTargetException, InstantiationException, IllegalAccessException {
            return packetConstructor.newInstance();
        }

        public static Object getCursor(MapCursor cursor) {
            return null;//new MapDecoration()
        }

        public static Object[] getCursors(MapCursor... cursors) {
            Objects.requireNonNull(cursors);
            final Object[] decorations = new Object[cursors.length];
            for (int i = 0; i < cursors.length; i++) {
                decorations[i] = getCursor(cursors[i]);
            }
            return decorations;
        }

        public static Object[] getCursors(Iterable<MapCursor> cursors) {
            Objects.requireNonNull(cursors);
            final ArrayList<Object> decorations = new ArrayList<>();
            for (MapCursor cursor : cursors) {
                decorations.add(getCursor(cursor));
            }
            return decorations.toArray();
        }

        public static Object[] getCursors(MapCursorCollection cursors) {
            Objects.requireNonNull(cursors);
            final Object[] decorations = new Object[cursors.size()];
            for (int i = 0; i < cursors.size(); i++) {
                decorations[i] = getCursor(cursors.getCursor(i));
            }
            return decorations;
        }

        public static void initPacket(
                Object packet,
                int mapId,
                byte scale,
                boolean trackingPosition,
                boolean locked,
                Object[] decorations,
                int startX,
                int startY,
                int width,
                int height,
                byte[] mapColors
        ) throws IllegalAccessException {
            mapIdField.setInt(packet, mapId);
            scaleField.setByte(packet, scale);
            trackingPositionField.setBoolean(packet, trackingPosition);
            lockedField.setBoolean(packet, locked);
            decorationsField.set(packet, decorations);
            startXField.setInt(packet, startX);
            startYField.setInt(packet, startY);
            widthField.setInt(packet, width);
            heightField.setInt(packet, height);
            mapColorsField.set(packet, mapColors);
        }

        public static Object getConnection(Player player) throws InvocationTargetException, IllegalAccessException {
            return playerConnectionField.get(handleMethod.invoke(player));
        }

        public static void sendPacket(Object connection, Object packet) throws InvocationTargetException, IllegalAccessException {
            sendPacketMethod.invoke(connection, packet);
        }

    }

}
