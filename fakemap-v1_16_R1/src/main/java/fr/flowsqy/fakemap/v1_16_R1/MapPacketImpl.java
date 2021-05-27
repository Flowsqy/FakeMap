package fr.flowsqy.fakemap.v1_16_R1;

import fr.flowsqy.fakemap.MapPacket;
import net.minecraft.server.v1_16_R1.ChatComponentText;
import net.minecraft.server.v1_16_R1.MapIcon;
import net.minecraft.server.v1_16_R1.PacketPlayOutMap;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapCursorCollection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Objects;

public class MapPacketImpl implements MapPacket {

    // Packet
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

            final Class<?> mapPacketClass = PacketPlayOutMap.class;
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
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private final PacketPlayOutMap packet;

    public MapPacketImpl() {
        packet = new PacketPlayOutMap();
    }

    private static MapIcon.Type getType(MapCursor.Type type) {
        switch (type) {
            case WHITE_POINTER:
                return MapIcon.Type.PLAYER;
            case GREEN_POINTER:
                return MapIcon.Type.FRAME;
            case RED_POINTER:
                return MapIcon.Type.RED_MARKER;
            case BLUE_POINTER:
                return MapIcon.Type.BLUE_MARKER;
            case WHITE_CROSS:
                return MapIcon.Type.TARGET_X;
            case RED_MARKER:
                return MapIcon.Type.TARGET_POINT;
            case WHITE_CIRCLE:
                return MapIcon.Type.PLAYER_OFF_MAP;
            case SMALL_WHITE_CIRCLE:
                return MapIcon.Type.PLAYER_OFF_LIMITS;
            case MANSION:
                return MapIcon.Type.MANSION;
            case TEMPLE:
                return MapIcon.Type.MONUMENT;
            case BANNER_WHITE:
                return MapIcon.Type.BANNER_WHITE;
            case BANNER_ORANGE:
                return MapIcon.Type.BANNER_ORANGE;
            case BANNER_MAGENTA:
                return MapIcon.Type.BANNER_MAGENTA;
            case BANNER_LIGHT_BLUE:
                return MapIcon.Type.BANNER_LIGHT_BLUE;
            case BANNER_YELLOW:
                return MapIcon.Type.BANNER_YELLOW;
            case BANNER_LIME:
                return MapIcon.Type.BANNER_LIME;
            case BANNER_PINK:
                return MapIcon.Type.BANNER_PINK;
            case BANNER_GRAY:
                return MapIcon.Type.BANNER_GRAY;
            case BANNER_LIGHT_GRAY:
                return MapIcon.Type.BANNER_LIGHT_GRAY;
            case BANNER_CYAN:
                return MapIcon.Type.BANNER_CYAN;
            case BANNER_PURPLE:
                return MapIcon.Type.BANNER_PURPLE;
            case BANNER_BLUE:
                return MapIcon.Type.BANNER_BLUE;
            case BANNER_BROWN:
                return MapIcon.Type.BANNER_BROWN;
            case BANNER_GREEN:
                return MapIcon.Type.BANNER_GREEN;
            case BANNER_RED:
                return MapIcon.Type.BANNER_RED;
            case BANNER_BLACK:
                return MapIcon.Type.BANNER_BLACK;
            case RED_X:
                return MapIcon.Type.RED_X;
            default:
                return null;
        }
    }

    private static MapIcon getCursor(MapCursor cursor) {
        return new MapIcon(
                getType(cursor.getType()),
                cursor.getX(),
                cursor.getY(),
                cursor.getDirection(),
                cursor.getCaption() == null ? null : new ChatComponentText(cursor.getCaption())
        );
    }

    private static Object getCursors(MapCursor... cursors) {
        Objects.requireNonNull(cursors);
        final MapIcon[] decorations = new MapIcon[cursors.length];
        for (int i = 0; i < cursors.length; i++) {
            decorations[i] = getCursor(cursors[i]);
        }
        return decorations;
    }

    private static Object getCursors(Iterable<MapCursor> cursors) {
        Objects.requireNonNull(cursors);
        final ArrayList<MapIcon> decorations = new ArrayList<>();
        for (MapCursor cursor : cursors) {
            decorations.add(getCursor(cursor));
        }
        return decorations.toArray(new MapIcon[decorations.size()]);
    }

    private static Object getCursors(MapCursorCollection cursors) {
        Objects.requireNonNull(cursors);
        final MapIcon[] decorations = new MapIcon[cursors.size()];
        for (int i = 0; i < cursors.size(); i++) {
            decorations[i] = getCursor(cursors.getCursor(i));
        }
        return decorations;
    }

    private static void initPacket(
            Object packet,
            int mapId,
            byte scale,
            boolean trackingPosition,
            boolean locked,
            Object decorations,
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

    @Override
    public void init(int mapId, byte scale, boolean trackingPosition, boolean locked, MapCursorCollection cursors, int startX, int startY, int width, int height, byte[] mapColors) {
        try {
            initPacket(
                    packet,
                    mapId,
                    scale,
                    trackingPosition,
                    locked,
                    getCursors(cursors),
                    startX,
                    startY,
                    width,
                    height,
                    mapColors
            );
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(int mapId, byte scale, boolean trackingPosition, boolean locked, Iterable<MapCursor> cursors, int startX, int startY, int width, int height, byte[] mapColors) {
        try {
            initPacket(
                    packet,
                    mapId,
                    scale,
                    trackingPosition,
                    locked,
                    getCursors(cursors),
                    startX,
                    startY,
                    width,
                    height,
                    mapColors
            );
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void init(int mapId, byte scale, boolean trackingPosition, boolean locked, MapCursor[] cursors, int startX, int startY, int width, int height, byte[] mapColors) {
        try {
            initPacket(
                    packet,
                    mapId,
                    scale,
                    trackingPosition,
                    locked,
                    getCursors(cursors),
                    startX,
                    startY,
                    width,
                    height,
                    mapColors
            );
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(Iterable<Player> players) {
        for (Player player : players) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }

}
