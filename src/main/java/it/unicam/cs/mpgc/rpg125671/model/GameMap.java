package it.unicam.cs.mpgc.rpg125671.model;

import java.util.*;

public class GameMap {

    private final List<Room> rooms;
    private int currentRoomIndex;

    public GameMap(List<Room> rooms) {
        this(rooms, 0);
    }

    public GameMap(List<Room> rooms, int currentRoomIndex) {
        if (rooms == null || rooms.isEmpty())
            throw new IllegalArgumentException("La mappa deve contenere almeno una stanza.");
        if (currentRoomIndex < 0 || currentRoomIndex >= rooms.size())
            throw new IllegalArgumentException("Indice della stanza corrente non valido.");
        this.rooms = List.copyOf(rooms);
        this.currentRoomIndex = currentRoomIndex;
    }

    public boolean hasNextRoom() { return currentRoomIndex < rooms.size() - 1; }

    public Room advanceToNextRoom() {
        if (!hasNextRoom())
            throw new IllegalStateException("Non ci sono altre stanze.");
        currentRoomIndex++;
        return getCurrentRoom();
    }

    // --- GETTER ---

    public Room getCurrentRoom() { return rooms.get(currentRoomIndex); }
    public int getCurrentRoomIndex() { return currentRoomIndex; }
    public List<Room> getRooms() { return rooms; }
}
