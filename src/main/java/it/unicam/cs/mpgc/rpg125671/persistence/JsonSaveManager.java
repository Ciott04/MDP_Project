package it.unicam.cs.mpgc.rpg125671.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JsonSaveManager implements SaveManager {

    private static final Path SAVE_DIR = Path.of("saves");
    private final Gson gson;

    public JsonSaveManager() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    @Override
    public void save(GameSave gameSave, String fileName) throws IOException {
        if (gameSave == null)
            throw new IllegalArgumentException("Il salvataggio non può essere null.");
        if (fileName == null || fileName.isBlank())
            throw new IllegalArgumentException("Il nome del file non può essere vuoto.");
        Files.createDirectories(SAVE_DIR);
        Path file = SAVE_DIR.resolve(fileName + ".json");
        Files.writeString(file, gson.toJson(gameSave));
    }

    @Override
    public GameSave load(String fileName) throws IOException {
        if (fileName == null || fileName.isBlank())
            throw new IllegalArgumentException("Il nome del file non può essere vuoto.");
        Path file = SAVE_DIR.resolve(fileName + ".json");
        if (!Files.exists(file))
            throw new IOException("File di salvataggio non trovato: " + file);
        String json = Files.readString(file);
        return gson.fromJson(json, GameSave.class);
    }

    @Override
    public boolean saveExists(String fileName) {
        if (fileName == null || fileName.isBlank()) return false;
        return Files.exists(SAVE_DIR.resolve(fileName + ".json"));
    }
}
