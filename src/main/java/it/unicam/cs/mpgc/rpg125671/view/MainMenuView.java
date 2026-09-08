package it.unicam.cs.mpgc.rpg125671.view;

import it.unicam.cs.mpgc.rpg125671.engine.GameEngine;
import it.unicam.cs.mpgc.rpg125671.persistence.GameSave;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainMenuView extends VBox {
    public MainMenuView(GameApp app) {
        setAlignment(Pos.CENTER);
        setSpacing(20);
        setPadding(new Insets(40));

        Label title = new Label("Dungeon Crawler RPG");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        Button newGameBtn = new Button("Nuova Partita");
        newGameBtn.setPrefWidth(200);
        newGameBtn.setOnAction(e -> app.showHeroSelection());

        Button loadGameBtn = new Button("Carica Partita");
        loadGameBtn.setPrefWidth(200);
        loadGameBtn.setDisable(!app.getSaveManager().saveExists(app.getSaveName()));
        loadGameBtn.setOnAction(e -> {
            try {
                GameSave save = app.getSaveManager().load(app.getSaveName());
                app.setGameEngine(GameEngine.fromSave(save));
                app.showExploration();
            } catch (IOException ex) {
                loadGameBtn.setText("Errore nel caricamento");
                loadGameBtn.setDisable(true);
            }
        });

        getChildren().addAll(title, newGameBtn, loadGameBtn);
    }
}
