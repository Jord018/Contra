package org.game.contra.components;

public class HealthComponent {
    private int health;
    private final int maxHealth;

    public HealthComponent(int maxHealth) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }

    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public void setHealth(int health) { this.health = Math.max(0, Math.min(health, maxHealth)); }
}
