# Dungeon of the Forgotten
Dungeon of the Forgotten è un dungeon crawler a turni in stile roguelike sviluppato in Java con interfaccia grafica JavaFX.
Il giocatore sceglie il proprio eroe tra due classi: *Guerriero* o *Arciere* e lo guida attraverso una serie di stanze generate proceduralmente, combattendo mostri, raccogliendo risorse e puntando a sconfiggere il boss finale per completare il dungeon.

## Come eseguire il progetto
### Prerequisiti
- Java 25 (LTS)
- Gradle

### Istruzioni

```bash
git clone https://github.com/Ciott04/MDP_Project.git
cd MDP_Project
```
### Build del progetto
```bash
gradle build
```

### Esecuzione
```bash
gradle run
```

## Strumenti AI

Nel progetto l'intelligenza artificiale è stata utilizzata principalmente come supporto creativo nella scelta del tema di gioco e come strumento di supporto allo sviluppo.
Nello specifico:
### Gemini per:
* **Design creativo**: aiutato nella decisione del tema del gioco, nelle meccaniche base e nella scelta dei nomi.

### Claude per:
* **Code review**: analisi del codice scritto per identificare errori, inconsistenze o violazioni dei principi SOLID e clean code (es. visibilità errata dei metodi, naming delle costanti, validazione degli argomenti).
* **Gestione della persistenza**:  supporto nella scelta e nell'organizzazione del sistema di persistenza.
* **Supporto al debugging**: individuazione e spiegazione degli errori di compilazione e dei test falliti, con possibili soluzioni.
* **Bilanciamento di gioco**: consulenza sulle formule di danno e sulle statistiche delle classi per ottenere un'esperienza di gioco equilibrata.
* **Generazione della documentazione**: supporto nella stesura della Javadoc per le classi e i metodi più significativi del progetto.
* **Generazione di test in JUnit**: generazione di 46 test in JUnit 5 per verificare la corretta funzionalità dei layer *model* e *engine*.

Le scelte progettuali, l'implementazione delle classi e la scrittura del codice sono state condotte da me, con l'AI utilizzata come strumento di confronto e revisione, in modo analogo a quanto avviene con un tutor o con la consultazione di documentazione tecnica.
