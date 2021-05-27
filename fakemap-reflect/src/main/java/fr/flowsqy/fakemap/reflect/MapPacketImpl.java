package fr.flowsqy.fakemap.reflect;

import fr.flowsqy.fakemap.MapPacket;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapCursorCollection;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class MapPacketImpl implements MapPacket {

    // Packet send
    private final static Method handleMethod;
    private final static Method sendPacketMethod;
    private final static Field playerConnectionField;

    // Packet
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
    // Map Icon
    private final static Class<?> mapIconClass;
    private final static Constructor<?> mapIconConstructor;
    // Cursor Type
    private final static Object PLAYER;
    private final static Object FRAME;
    private final static Object RED_MARKER;
    private final static Object BLUE_MARKER;
    private final static Object TARGET_X;
    private final static Object TARGET_POINT;
    private final static Object PLAYER_OFF_MAP;
    private final static Object PLAYER_OFF_LIMITS;
    private final static Object MANSION;
    private final static Object MONUMENT;
    private final static Object BANNER_WHITE;
    private final static Object BANNER_ORANGE;
    private final static Object BANNER_MAGENTA;
    private final static Object BANNER_LIGHT_BLUE;
    private final static Object BANNER_YELLOW;
    private final static Object BANNER_LIME;
    private final static Object BANNER_PINK;
    private final static Object BANNER_GRAY;
    private final static Object BANNER_LIGHT_GRAY;
    private final static Object BANNER_CYAN;
    private final static Object BANNER_PURPLE;
    private final static Object BANNER_BLUE;
    private final static Object BANNER_BROWN;
    private final static Object BANNER_GREEN;
    private final static Object BANNER_RED;
    private final static Object BANNER_BLACK;
    private final static Object RED_X;
    // ChatComponent
    private final static Constructor<?> chatComponentTextConstructor;

    static {
        try {
            final String versionName = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            final String nms = "net.minecraft.server." + versionName + ".";
            final String craftbukkit = "org.bukkit.craftbukkit." + versionName + ".";
            final Class<?> mapPacketClass = Class.forName(nms + "PacketPlayOutMap");
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

            final Class<?> playerClass = Class.forName(craftbukkit + "entity.CraftPlayer");
            handleMethod = playerClass.getDeclaredMethod("getHandle");
            final Class<?> entityPlayer = Class.forName(nms + "EntityPlayer");
            playerConnectionField = entityPlayer.getDeclaredField("playerConnection");
            final Class<?> playerConnectionClass = Class.forName(nms + "PlayerConnection");
            final Class<?> packetClass = Class.forName(nms + "Packet");
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

            // MapIcon

            // Class
            mapIconClass = Class.forName(nms + "MapIcon");

            // Types
            final Class<?>[] iconSubClasses = mapIconClass.getDeclaredClasses();
            final Optional<Class<?>> optionalTypeClass = Arrays.stream(iconSubClasses)
                    .filter(clazz -> clazz.getSimpleName().equals("Type"))
                    .findAny();
            if (!optionalTypeClass.isPresent()) {
                throw new ClassNotFoundException("Can not find " + nms + "MapIcon.Type class");
            }

            final Class<?> typeClass = optionalTypeClass.get();

            PLAYER = getNMSType(typeClass, "PLAYER");
            FRAME = getNMSType(typeClass, "FRAME");
            RED_MARKER = getNMSType(typeClass, "RED_MARKER");
            BLUE_MARKER = getNMSType(typeClass, "BLUE_MARKER");
            TARGET_X = getNMSType(typeClass, "TARGET_X");
            TARGET_POINT = getNMSType(typeClass, "TARGET_POINT");
            PLAYER_OFF_MAP = getNMSType(typeClass, "PLAYER_OFF_MAP");
            PLAYER_OFF_LIMITS = getNMSType(typeClass, "PLAYER_OFF_LIMITS");
            MANSION = getNMSType(typeClass, "MANSION");
            MONUMENT = getNMSType(typeClass, "MONUMENT");
            BANNER_WHITE = getNMSType(typeClass, "BANNER_WHITE");
            BANNER_ORANGE = getNMSType(typeClass, "BANNER_ORANGE");
            BANNER_MAGENTA = getNMSType(typeClass, "BANNER_MAGENTA");
            BANNER_LIGHT_BLUE = getNMSType(typeClass, "BANNER_LIGHT_BLUE");
            BANNER_YELLOW = getNMSType(typeClass, "BANNER_YELLOW");
            BANNER_LIME = getNMSType(typeClass, "BANNER_LIME");
            BANNER_PINK = getNMSType(typeClass, "BANNER_PINK");
            BANNER_GRAY = getNMSType(typeClass, "BANNER_GRAY");
            BANNER_LIGHT_GRAY = getNMSType(typeClass, "BANNER_LIGHT_GRAY");
            BANNER_CYAN = getNMSType(typeClass, "BANNER_CYAN");
            BANNER_PURPLE = getNMSType(typeClass, "BANNER_PURPLE");
            BANNER_BLUE = getNMSType(typeClass, "BANNER_BLUE");
            BANNER_BROWN = getNMSType(typeClass, "BANNER_BROWN");
            BANNER_GREEN = getNMSType(typeClass, "BANNER_GREEN");
            BANNER_RED = getNMSType(typeClass, "BANNER_RED");
            BANNER_BLACK = getNMSType(typeClass, "BANNER_BLACK");
            RED_X = getNMSType(typeClass, "RED_X");

            // IChatBaseComponent class
            final Class<?> iChatBaseComponentClass = Class.forName(nms + "IChatBaseComponent");

            // ChatComponentText constructor
            final Class<?> chatComponentTextClass = Class.forName(nms + "ChatComponentText");
            chatComponentTextConstructor = chatComponentTextClass.getDeclaredConstructor(String.class);
            chatComponentTextConstructor.setAccessible(true);

            // MapIcon Constructor
            mapIconConstructor = mapIconClass.getDeclaredConstructor(typeClass, byte.class, byte.class, byte.class, iChatBaseComponentClass);
            mapIconConstructor.setAccessible(true);

        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private final Object packet;

    public MapPacketImpl() {
        try {
            packet = packetConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object getNMSType(Class<?> typeClass, String typeName) throws NoSuchFieldException, IllegalAccessException {
        final Field typeField = typeClass.getDeclaredField(typeName);
        typeField.setAccessible(true);
        return typeField.get(null);
    }

    private static Object getType(MapCursor.Type type) {
        switch (type) {
            case WHITE_POINTER:
                return PLAYER;
            case GREEN_POINTER:
                return FRAME;
            case RED_POINTER:
                return RED_MARKER;
            case BLUE_POINTER:
                return BLUE_MARKER;
            case WHITE_CROSS:
                return TARGET_X;
            case RED_MARKER:
                return TARGET_POINT;
            case WHITE_CIRCLE:
                return PLAYER_OFF_MAP;
            case SMALL_WHITE_CIRCLE:
                return PLAYER_OFF_LIMITS;
            case MANSION:
                return MANSION;
            case TEMPLE:
                return MONUMENT;
            case BANNER_WHITE:
                return BANNER_WHITE;
            case BANNER_ORANGE:
                return BANNER_ORANGE;
            case BANNER_MAGENTA:
                return BANNER_MAGENTA;
            case BANNER_LIGHT_BLUE:
                return BANNER_LIGHT_BLUE;
            case BANNER_YELLOW:
                return BANNER_YELLOW;
            case BANNER_LIME:
                return BANNER_LIME;
            case BANNER_PINK:
                return BANNER_PINK;
            case BANNER_GRAY:
                return BANNER_GRAY;
            case BANNER_LIGHT_GRAY:
                return BANNER_LIGHT_GRAY;
            case BANNER_CYAN:
                return BANNER_CYAN;
            case BANNER_PURPLE:
                return BANNER_PURPLE;
            case BANNER_BLUE:
                return BANNER_BLUE;
            case BANNER_BROWN:
                return BANNER_BROWN;
            case BANNER_GREEN:
                return BANNER_GREEN;
            case BANNER_RED:
                return BANNER_RED;
            case BANNER_BLACK:
                return BANNER_BLACK;
            case RED_X:
                return RED_X;
            default:
                return null;
        }
    }

    private static Object getCursor(MapCursor cursor) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        return mapIconConstructor.newInstance(
                getType(cursor.getType()),
                cursor.getX(),
                cursor.getY(),
                cursor.getDirection(),
                cursor.getCaption() == null ? null : chatComponentTextConstructor.newInstance(cursor.getCaption())
        );
    }

    private static Object getCursors(MapCursor... cursors) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Objects.requireNonNull(cursors);
        final Object decorations = Array.newInstance(mapIconClass, cursors.length);
        for (int i = 0; i < cursors.length; i++) {
            Array.set(decorations, i, getCursor(cursors[i]));
        }
        return decorations;
    }

    private static Object getCursors(Iterable<MapCursor> cursors) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Objects.requireNonNull(cursors);
        final ArrayList<Object> decorations = new ArrayList<>();
        for (MapCursor cursor : cursors) {
            decorations.add(getCursor(cursor));
        }
        final Object array = Array.newInstance(mapIconClass, decorations.size());
        for (int i = 0; i < decorations.size(); i++) {
            Array.set(array, i, decorations.get(i));
        }
        return array;
    }

    private static Object getCursors(MapCursorCollection cursors) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Objects.requireNonNull(cursors);
        final Object decorations = Array.newInstance(mapIconClass, cursors.size());
        for (int i = 0; i < cursors.size(); i++) {
            Array.set(decorations, i, getCursor(cursors.getCursor(i)));
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
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
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
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
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
        } catch (InvocationTargetException | IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void send(Iterable<Player> players) {
        try {
            for (Player player : players) {
                sendPacketMethod.invoke(playerConnectionField.get(handleMethod.invoke(player)), packet);
            }
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
