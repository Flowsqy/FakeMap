package fr.flowsqy.fakemap;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCursor;
import org.bukkit.map.MapCursorCollection;

public interface MapPacket {

    /**
     * Initialize the packet
     *
     * @param mapId            The id of the map
     * @param scale            The scale of the map
     * @param trackingPosition If the map should track players
     * @param locked           If the map is locked
     * @param cursors          The cursors on the map
     * @param startX           The x starting position of colors data
     * @param startY           The y starting position of colors data
     * @param width            The width on the map to update
     * @param height           The height on the map to update
     * @param mapColors        The map colors data
     */
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

    /**
     * Initialize the packet
     *
     * @param mapId            The id of the map
     * @param scale            The scale of the map
     * @param trackingPosition If the map should track players
     * @param locked           If the map is locked
     * @param cursors          The cursors on the map
     * @param startX           The x starting position of colors data
     * @param startY           The y starting position of colors data
     * @param width            The width on the map to update
     * @param height           The height on the map to update
     * @param mapColors        The map colors data
     */
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

    /**
     * Initialize the packet
     *
     * @param mapId            The id of the map
     * @param scale            The scale of the map
     * @param trackingPosition If the map should track players
     * @param locked           If the map is locked
     * @param cursors          The cursors on the map
     * @param startX           The x starting position of colors data
     * @param startY           The y starting position of colors data
     * @param width            The width on the map to update
     * @param height           The height on the map to update
     * @param mapColors        The map colors data
     */
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

    /**
     * Send the packet to players
     *
     * @param players The players who will receive the packet
     */
    void send(Iterable<Player> players);

}
