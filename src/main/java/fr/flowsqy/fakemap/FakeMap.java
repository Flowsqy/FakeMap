package fr.flowsqy.fakemap;

import org.bukkit.Bukkit;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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


    }

}
