package it.unicam.cs.mpgc.rpg125671.view;

import it.unicam.cs.mpgc.rpg125671.engine.GameEngine;
import it.unicam.cs.mpgc.rpg125671.engine.ProceduralMapGenerator;
import it.unicam.cs.mpgc.rpg125671.model.Archer;
import it.unicam.cs.mpgc.rpg125671.model.Hero;
import it.unicam.cs.mpgc.rpg125671.model.HealingPotion;
import it.unicam.cs.mpgc.rpg125671.model.Warrior;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class HeroSelectionView extends VBox {

    private static final int ROOM_COUNT = 8;

    public HeroSelectionView(GameApp app) {
        setAlignment(Pos.CENTER);
        setSpacing(15);
        setPadding(new Insets(40));

        Label title = new Label("Scegli il tuo eroe");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Nome del personaggio");
        nameField.setMaxWidth(250);

        Button warriorBtn = new Button(String.format("Guerriero\nHP %d | ATK %d | DEF %d | SPD %d",
                Warrior.getBaseHp(), Warrior.getBaseAttack(), Warrior.getBaseDefense(), Warrior.getBaseSpeed()));
        warriorBtn.setPrefWidth(300);
        warriorBtn.setPrefHeight(60);
        warriorBtn.setOnAction(e -> startGame(app, new Warrior(getHeroName(nameField))));

        Button archerBtn = new Button(String.format("Arciere\nHP %d | ATK %d | DEF %d | SPD %d",
                Archer.getBaseHp(), Archer.getBaseAttack(), Archer.getBaseDefense(), Archer.getBaseSpeed()));
        archerBtn.setPrefWidth(300);
        archerBtn.setPrefHeight(60);
        archerBtn.setOnAction(e -> startGame(app, new Archer(getHeroName(nameField))));

        Button backBtn = new Button("Indietro");
        backBtn.setOnAction(e -> app.showMainMenu());

        getChildren().addAll(title, nameField, warriorBtn, archerBtn, backBtn);
    }

    private String getHeroName(TextField nameField) {
        String name = nameField.getText().trim();
        return name.isEmpty() ? "Eroe" : name;
    }

    private void startGame(GameApp app, Hero hero) {
        hero.getInventory().addItem(new HealingPotion());
        hero.getInventory().addItem(new HealingPotion());
        app.setGameEngine(new GameEngine(hero, new ProceduralMapGenerator(ROOM_COUNT)));
        app.showExploration();
    }
}
