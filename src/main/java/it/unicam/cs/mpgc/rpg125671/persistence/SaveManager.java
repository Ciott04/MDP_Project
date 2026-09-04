package it.unicam.cs.mpgc.rpg125671.persistence;

import java.io.IOException;

public interface SaveManager {
    void save(GameSave gameSave, String fileName) throws IOException;
    GameSave load(String fileName) throws IOException;
    boolean saveExists(String fileName);
}
