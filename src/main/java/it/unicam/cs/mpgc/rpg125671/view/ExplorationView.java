package it.unicam.cs.mpgc.rpg125671.view;

import it.unicam.cs.mpgc.rpg125671.engine.GameEngine;
import it.unicam.cs.mpgc.rpg125671.engine.GameState;
import it.unicam.cs.mpgc.rpg125671.model.Hero;
import it.unicam.cs.mpgc.rpg125671.model.Room;
import it.unicam.cs.mpgc.rpg125671.model.RoomType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Schermata di esplorazione della mappa.
 * Mostra le statistiche aggiornate dell'eroe in alto, le informazioni sulla stanza corrente
 * al centro e i bottoni di azione (entra, avanza, salva) in basso.
 * I bottoni vengono abilitati o disabilitati in base allo {@link GameState} corrente,
 * per guidare il giocatore senza permettere azioni non valide.
 */
public class ExplorationView extends BorderPane {

    private final GameApp app;
    private final Label roomInfo = new Label();
    private final Label messageLabel = new Label();
    private final Button enterBtn = new Button("Entra nella stanza");
    private final Button advanceBtn = new Button("Avanza");
    private final Button saveBtn = new Button("Salva");

    public ExplorationView(GameApp app) {
        this.app = app;
        setPadding(new Insets(20));

        setTop(buildHeroStats());
        setCenter(buildRoomArea());
        setBottom(buildButtons());

        update();
    }

    private VBox buildHeroStats() {
        Hero hero = app.getGameEngine().getHero();
        Label stats = new Label(String.format(
                "%s (Lv.%d) | HP: %d/%d | ATK: %d | DEF: %d | SPD: %d | Pozioni: %d",
                hero.getName(), hero.getLevel(),
                hero.getCurrentHp(), hero.getMaxHp(),
                hero.getAttack(), hero.getDefense(), hero.getSpeed(),
                hero.getInventory().getItemCount("Pozione curativa")
        ));
        stats.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        VBox box = new VBox(stats);
        box.setPadding(new Insets(0, 0, 15, 0));
        return box;
    }

    private VBox buildRoomArea() {
        roomInfo.setStyle("-fx-font-size: 18px;");
        messageLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

        VBox box = new VBox(10, roomInfo, messageLabel);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private HBox buildButtons() {
        enterBtn.setPrefWidth(180);
        enterBtn.setOnAction(e -> onEnterRoom());

        advanceBtn.setPrefWidth(180);
        advanceBtn.setOnAction(e -> onAdvance());

        saveBtn.setPrefWidth(180);
        saveBtn.setOnAction(e -> onSave());

        HBox box = new HBox(15, enterBtn, advanceBtn, saveBtn);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20, 0, 0, 0));
        return box;
    }

    /**
     * Aggiorna lo stato visivo della schermata: testo della stanza corrente
     * e abilitazione dei bottoni in base allo stato del gioco.
     * Chiamato all'inizializzazione e dopo ogni azione dell'utente.
     */
    private void update() {
        GameEngine engine = app.getGameEngine();
        GameState state = engine.getState();
        Room room = engine.getMap().getCurrentRoom();
        int roomNum = engine.getMap().getCurrentRoomIndex() + 1;
        int totalRooms = engine.getMap().getRooms().size();

        roomInfo.setText("Stanza " + roomNum + "/" + totalRooms + " — " + describeRoom(room));

        enterBtn.setDisable(state != GameState.EXPLORING || room.isCompleted());
        advanceBtn.setDisable(state != GameState.ROOM_COMPLETED || !engine.getMap().hasNextRoom());
        saveBtn.setDisable(state == GameState.IN_COMBAT);
    }

    /**
     * Gestisce il click su "Entra nella stanza".
     * Delega a {@link GameEngine#enterCurrentRoom()} e in base al risultato:
     * naviga alla {@link CombatView} se è un combattimento,
     * mostra il messaggio di ricompensa se è un tesoro,
     * oppure naviga alla schermata di vittoria se è stata l'ultima stanza.
     */
    private void onEnterRoom() {
        GameEngine engine = app.getGameEngine();
        Room room = engine.enterCurrentRoom();

        if (engine.getState() == GameState.IN_COMBAT) {
            app.showCombat();
            return;
        }

        if (room.getType() == RoomType.TREASURE) {
            messageLabel.setText("Hai trovato: " + room.getReward().getName() + "!");
        } else {
            messageLabel.setText("La stanza è vuota. Puoi riposarti.");
        }

        if (engine.getState() == GameState.GAME_WON) {
            app.showEndGame(true);
            return;
        }
        refreshView();
    }

    private void onAdvance() {
        app.getGameEngine().advanceToNextRoom();
        messageLabel.setText("");
        refreshView();
    }

    /**
     * Gestisce il salvataggio della partita.
     * Serializza lo stato tramite {@link GameEngine#toSave()} e lo persiste
     * con il {@link SaveManager}. Mostra un messaggio di conferma o errore.
     */
    private void onSave() {
        try {
            app.getSaveManager().save(app.getGameEngine().toSave(), app.getSaveName());
            messageLabel.setText("Partita salvata!");
        } catch (IOException ex) {
            messageLabel.setText("Errore nel salvataggio.");
        }
    }

    /**
     * Ricostruisce il pannello delle statistiche dell'eroe e aggiorna i bottoni.
     * Chiamato dopo ogni azione che modifica lo stato (avanzamento, completamento stanza).
     */
    private void refreshView() {
        setTop(buildHeroStats());
        update();
    }

    /**
     * @param room la stanza da descrivere.
     * @return una stringa leggibile con tipo e nome del contenuto della stanza.
     */
    private String describeRoom(Room room) {
        return switch (room.getType()) {
            case MONSTER -> "Stanza Mostro (" + room.getMonster().getName() + ")";
            case BOSS -> "Stanza Boss (" + room.getMonster().getName() + ")";
            case TREASURE -> "Stanza Tesoro";
            case EMPTY -> "Stanza Vuota";
        };
    }
}