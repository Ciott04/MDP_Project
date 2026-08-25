package it.unicam.cs.mpgc.rpg125671.Model;

public abstract class Hero {

    private final String name;

    private int maxHp;
    private int currentHp;

    private int maxExp;
    private int currentExp;
    private int lvl;

    private int att;

    private int def;

    private int speed;

    public Hero(String name, int maxHp, int att, int def, int speed) {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Il nome non può essere vuoto.");
        if (maxHp <= 0)
            throw new IllegalArgumentException("I punti vita massimi devono essere maggiori di 0.");
        if (att <= 0 || def <= 0 || speed <= 0)
            throw new IllegalArgumentException("Gli attributi dell'eroe non possono essere negativi.");

        this.name = name;
        this.maxHp = maxHp;
        currentHp = maxHp;
        maxExp = 0;
        currentExp = 0;
        lvl = 0;
        this.att = att;
        this.def = def;
        this.speed = speed;
    }



    public void gainExp(int exp) {
        if (exp <= 0)
            throw new IllegalArgumentException("I punti esperienza non possono essere minori di 1.");
        currentExp += exp;
        if (currentExp >= maxExp) {
            currentExp -= maxExp;
            lvlUp();
            maxExp++;
            attUp();
            defUp();
            speedUp();
        }
    }

    public void lvlUp() {
        lvl++;
    }

    public abstract void attUp();
    public abstract void defUp();
    public abstract void speedUp();



    public String getName() {
        return name;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getExp() {
        return currentExp;
    }

    public int getAtt() {
        return att;
    }

    public int getDef() {
        return def;
    }

    public int getSpeed() {
        return speed;
    }

}
