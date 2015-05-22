package builtin.graphics;

/**
 * @author Oliver Chu
 */
public class MintGameChar {
    private float life = 100;
    private float mana = 100;
    private float attack = 25;
    private float defense = 0.25f;
    private String name = "???";
    
    public boolean isDead() {
        return life <= 0;
    }
    
    // Returns true if and only if this game character is dead (0 life).
    public boolean takeDamage(float rawDamage) {
        float blocked = (1 - defense) * rawDamage;
        float realDamage = rawDamage - blocked;
        if (realDamage < 1.0) {
            System.out.println(name + " has been hit for 1 damage!");
            --life;
        } else {
            System.out.println(name + " has been hit for " +
                               realDamage + " damage!");
            life -= realDamage;
        }
        return isDead();
    }
}
