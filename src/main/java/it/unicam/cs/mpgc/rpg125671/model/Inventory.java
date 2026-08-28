package it.unicam.cs.mpgc.rpg125671.model;

import java.util.*;

public class Inventory {

    private final Map<String, List<Item>> items;

    public Inventory() {
        this.items = new HashMap<>();
    }

    public void addItem(Item item) {
        items.computeIfAbsent(item.getName(), k -> new ArrayList<>()).add(item);
    }

    public Item removeItem(String itemName) {
        List<Item> stack = items.get(itemName);
        if (stack == null || stack.isEmpty())
            throw new IllegalArgumentException("Oggetto non presente nell'inventario");
        Item removed = stack.removeLast();
        if (stack.isEmpty())
            items.remove(itemName);
        return removed;
    }

    public boolean hasItem(String itemName) {
        List<Item> stack = items.get(itemName);
        return stack != null && !stack.isEmpty();
    }

    public int getItemCount(String itemName) {
        List<Item> stack = items.get(itemName);
        return stack == null ? 0 : stack.size();
    }

    public Map<String, Integer> getSummary() {
        Map<String, Integer> summary = new HashMap<>();
        for (Map.Entry<String, List<Item>> entry : items.entrySet())
            summary.put(entry.getKey(), entry.getValue().size());
        return Map.copyOf(summary);
    }

}
