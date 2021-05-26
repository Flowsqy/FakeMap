package fr.flowsqy.fakemap;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapCursorCollection;

public interface MapPacket {

    void init(
            int mapId,
            byte scale,
            boolean trackingPosition,
            boolean locked,
            MapCursorCollection cursors,
            int startX,
            int startY,
            int width,
            int height,
            byte[] mapColors
    );

    void init(
            int mapId,
            byte scale,
            boolean trackingPosition,
            boolean locked,
            Iterable<MapCursor> cursors,
            int startX,
            int startY,
            int width,
            int height,
            byte[] mapColors
    );

    void init(
            int mapId,
            byte scale,
            boolean trackingPosition,
            boolean locked,
            MapCursor[] cursors,
            int startX,
            int startY,
            int width,
            int height,
            byte[] mapColors
    );

    void send(Iterable<Player> players);

}
