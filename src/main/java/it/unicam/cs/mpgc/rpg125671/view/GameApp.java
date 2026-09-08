package it.unicam.cs.mpgc.rpg125671.view;

import it.unicam.cs.mpgc.rpg125671.engine.GameEngine;
import it.unicam.cs.mpgc.rpg125671.persistence.JsonSaveManager;
import it.unicam.cs.mpgc.rpg125671.persistence.SaveManager;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApp extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String SAVE_NAME = "savegame";

    private Stage stage;
    private GameEngine gameEngine;
    private final SaveManager saveManager = new JsonSaveManager();

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        stage.setTitle("Dungeon of the Forgotten");
        stage.setScene(new Scene(new MainMenuView(this), WIDTH, HEIGHT));
        stage.show();
    }

    void switchView(Parent view) {
        stage.getScene().setRoot(view);
    }

    // --- Navigazione ---

    void showMainMenu() {
        switchView(new MainMenuView(this));
    }
    void showHeroSelection() {
        switchView(new HeroSelectionView(this));
    }

    void showExploration() {
        switchView(new ExplorationView(this));
    }

    void showCombat() {
        switchView(new CombatView(this));
    }

    void showEndGame(boolean won) {
        switchView(new EndGameView(this, won));
    }

    // ---Accesso allo stato ---
    GameEngine getGameEngine() {
        return gameEngine;
    }

    void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

    SaveManager getSaveManager() {
        return saveManager;
    }

    String getSaveName() {
        return SAVE_NAME;
    }

}
