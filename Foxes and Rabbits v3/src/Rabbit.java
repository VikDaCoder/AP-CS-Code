import java.util.List;
/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 */
public class Rabbit extends Animal implements Actor
{
    /* Characteristics shared by all rabbits (static fields). */

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 3;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 20;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.2;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 5;
    

    /**
     * Create a new rabbit. A rabbit is created with age zero (a new born).
     */
    public Rabbit(Field now, Field updated) {
    	super(BREEDING_AGE, MAX_AGE, BREEDING_PROBABILITY, MAX_LITTER_SIZE, now, updated);
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param updatedField The field to transfer to.
     * @param newRabbits A list to add newly born rabbits to.
     */
    @Override
    public void act(List<Actor> newAnimals) {
        incrementAge();
        if (isAlive()) {
        	int numBirths = breed();
        	for (int i = 0; i < numBirths; i++) {
        		Rabbit r = new Rabbit(getField(), getUpdatedField());
        		Location l = updatedField.freeAdjacentLocation(super.getLocation());
        		if (l == null) break;
        		r.setLocation(l);
        		newAnimals.add(r);
        		updatedField.place(r, l);
        	}
        	Location l = updatedField.freeAdjacentLocation(super.getLocation());
        	if (l == null) super.setDead();
        	else {
        		updatedField.place(this, l);
        		this.setLocation(l);
        	}
        }
    }
    
    @Override
    public String toString() {
    	return getAge() + " year old Rabbit at" + super.getLocation();
    }
}