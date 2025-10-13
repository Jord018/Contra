package org.game.contra.entities.Weapons;

public class WeaponStats {
    public float cooldown;
    public float projectileSpeed;
    public float damage;
    public boolean autoFire;

    public WeaponStats(float cooldown, float projectileSpeed, float damage, boolean autoFire) {
        this.cooldown = cooldown;
        this.projectileSpeed = projectileSpeed;
        this.damage = damage;
        this.autoFire = autoFire;
    }
}
