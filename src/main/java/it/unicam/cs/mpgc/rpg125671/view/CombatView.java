package it.unicam.cs.mpgc.rpg125671.view;

import it.unicam.cs.mpgc.rpg125671.engine.*;
import it.unicam.cs.mpgc.rpg125671.model.Hero;
import it.unicam.cs.mpgc.rpg125671.model.Monster;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CombatView extends BorderPane {

    private final GameApp app;
    private final Label heroHpLabel = new Label();
    private final Label monsterHpLabel = new Label();
    private final TextArea combatLog = new TextArea();
    private final Button attackBtn = new Button("Attacca");
    private final Button potionBtn = new Button("Usa Pozione");

    public CombatView(GameApp app) {
        this.app = app;
        setPadding(new Insets(20));

        combatLog.setEditable(false);
        combatLog.setPrefRowCount(12);

        setTop(buildCombatants());
        setCenter(combatLog);
        setBottom(buildActions());

        GameEngine engine = app.getGameEngine();
        Monster monster = engine.getMap().getCurrentRoom().getMonster();
        combatLog.appendText("Un " + monster.getName() + " ti blocca la strada!\n");
        if (engine.getCurrentCombat().isHeroFirst())
            combatLog.appendText("Sei più veloce — attacchi per primo.\n\n");
        else
            combatLog.appendText("Il nemico è più veloce — attacca per primo.\n\n");

        updateLabels();
    }

    private HBox buildCombatants() {
        heroHpLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        monsterHpLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        Label vs = new Label("  VS  ");
        vs.setStyle("-fx-font-size: 20px;");

        HBox box = new HBox(20, heroHpLabel, vs, monsterHpLabel);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(0, 0, 15, 0));
        return box;
    }

    private HBox buildActions() {
        attackBtn.setPrefWidth(180);
        attackBtn.setOnAction(e -> executeAction(CombatAction.ATTACK));

        potionBtn.setPrefWidth(180);
        potionBtn.setOnAction(e -> executeAction(CombatAction.USE_POTION));

        HBox box = new HBox(15, attackBtn, potionBtn);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(15, 0, 0, 0));
        return box;
    }

    private void executeAction(CombatAction action) {
        GameEngine engine = app.getGameEngine();
        Hero hero = engine.getHero();

        if (action == CombatAction.USE_POTION &&
                !hero.getInventory().hasItem("Pozione curativa")) {
            combatLog.appendText("Non hai pozioni!\n");
            return;
        }

        TurnResult result = engine.executeCombatTurn(action);

        if (action == CombatAction.ATTACK && result.damageToMonster() > 0)
            combatLog.appendText("Infliggi " + result.damageToMonster() + " danni.\n");
        if (action == CombatAction.USE_POTION && result.heroHealed() > 0)
            combatLog.appendText("Ti curi di " + result.heroHealed() + " HP.\n");
        if (result.damageToHero() > 0)
            combatLog.appendText("Subisci " + result.damageToHero() + " danni.\n");
        if (result.bossHealed() > 0)
            combatLog.appendText("Il boss si cura di " + result.bossHealed() + " HP!\n");

        combatLog.appendText("\n");
        updateLabels();

        if (result.combatResult() == CombatResult.HERO_WON) {
            combatLog.appendText("Vittoria! Hai sconfitto il nemico.\n");
            disableActions();

            if (engine.getState() == GameState.GAME_WON) {
                Button endBtn = new Button("Hai vinto il gioco!");
                endBtn.setOnAction(e -> app.showEndGame(true));
                setBottom(endBtn);
                BorderPane.setAlignment(endBtn, Pos.CENTER);
            } else {
                Button continueBtn = new Button("Continua l'esplorazione");
                continueBtn.setOnAction(e -> app.showExploration());
                setBottom(continueBtn);
                BorderPane.setAlignment(continueBtn, Pos.CENTER);
            }
        } else if (result.combatResult() == CombatResult.HERO_LOST) {
            combatLog.appendText("Sei stato sconfitto...\n");
            disableActions();
            Button endBtn = new Button("Game Over");
            endBtn.setOnAction(e -> app.showEndGame(false));
            setBottom(endBtn);
            BorderPane.setAlignment(endBtn, Pos.CENTER);
        }
    }

    private void updateLabels() {
        GameEngine engine = app.getGameEngine();
        Hero hero = engine.getHero();
        Monster monster = engine.getMap().getCurrentRoom().getMonster();
        heroHpLabel.setText(hero.getName() + " — HP: " + hero.getCurrentHp() + "/" + hero.getMaxHp());
        monsterHpLabel.setText(monster.getName() + " — HP: " + monster.getCurrentHp() + "/" + monster.getMaxHp());

        potionBtn.setText("Usa Pozione (" +
                hero.getInventory().getItemCount("Pozione curativa") + ")");
    }

    private void disableActions() {
        attackBtn.setDisable(true);
        potionBtn.setDisable(true);
    }
}