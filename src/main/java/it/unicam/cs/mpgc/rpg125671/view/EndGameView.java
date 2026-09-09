package it.unicam.cs.mpgc.rpg125671.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class EndGameView extends VBox {

    public EndGameView(GameApp app, boolean won) {
        setAlignment(Pos.CENTER);
        setSpacing(20);
        setPadding(new Insets(40));

        Label title = new Label(won ? "VITTORIA!" : "SCONFITTA");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: " +
                (won ? "#2e7d32;" : "#c62828;"));

        Label subtitle = new Label(won
                ? "Hai sconfitto il boss e completato il dungeon!"
                : "Il tuo eroe è caduto in battaglia...");
        subtitle.setStyle("-fx-font-size: 16px;");

        Button menuBtn = new Button("Torna al Menu");
        menuBtn.setPrefWidth(200);
        menuBtn.setOnAction(e -> app.showMainMenu());

        getChildren().addAll(title, subtitle, menuBtn);
    }
}