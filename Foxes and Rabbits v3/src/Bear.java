import java.util.List;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 */
public class Bear extends Animal implements Actor
{
	/* Characteristics shared by all foxes (static fields). */

	// The age at which a fox can start to breed.
	private static final int BREEDING_AGE = 3;
	// The age to which a fox can live.
	private static final int MAX_AGE = 10;
	// The likelihood of a fox breeding.
	private static final double BREEDING_PROBABILITY = 0.3;
	// The maximum number of births.
	private static final int MAX_LITTER_SIZE = 3;
	// The food value of a single rabbit. In effect, this is the
	// number of steps a fox can go before it has to eat again.
	private static final int RABBIT_FOOD_VALUE = 4;
	// The fox's food level, which is increased by eating rabbits.
	private int foodLevel;
	
	private static final int FOX_FOOD_VALUE = 6;

	/**
	 * Create a fox. A fox is created as a new born (age zero
	 * and not hungry).
	 */
	public Bear(Field now, Field updated) {
		super(BREEDING_AGE, MAX_AGE, BREEDING_PROBABILITY, MAX_LITTER_SIZE, now, updated);
		foodLevel = 4;
	}

	/**
	 * This is what the fox does most of the time: it hunts for
	 * rabbits. In the process, it might breed, die of hunger,
	 * or die of old age.
	 * @param currentField The field currently occupied.
	 * @param updatedField The field to transfer to.
	 * @param newFoxes A list to add newly born foxes to.
	 */
	@Override
	public void act(List<Actor> newAnimals) {
		super.incrementAge();
		incrementHunger();
		if (super.isAlive()) {
			int numBirths = super.breed();
			for (int i = 0; i < numBirths; i++) {
				Bear b = new Bear(getField(), getUpdatedField());
        		Location l = updatedField.freeAdjacentLocation(super.getLocation());
        		if (l == null) break;
        		b.setLocation(l);
        		newAnimals.add(b);
        		updatedField.place(b, l);
			}
			Location food = findFood(updatedField, super.getLocation());
			if (food == null) {
				Location l = updatedField.freeAdjacentLocation(super.getLocation());
				if (l == null) super.setDead();
	        	else {
	        		updatedField.place(this, l);
	        		super.setLocation(l);
	        	}
			}
			else {
				updatedField.place(this, food);
				super.setLocation(food);
			}
		}
	}

	/**
	 * Make this fox more hungry. This could result in the fox's death.
	 */
	private void incrementHunger() 	{
		foodLevel--;
		if (foodLevel == 0) super.setDead();
	}

	/**
	 * Tell the fox to look for rabbits adjacent to its current location.
	 * Only the first live rabbit is eaten.
	 * 
	 * @param field The field in which it must look.
	 * @param location Where in the field it is located.
	 * @return Where food was found, or null if it wasn't.
	 */
	private Location findFood(Field field, Location location)
	{
		List<Location> adjacentLocations = field.adjacentLocations(location);
		Location temp = null; //temp Location, to return
		for (Location where : adjacentLocations) 
		{
			Object animal = field.getObjectAt(where);
			if (animal instanceof Rabbit)
			{
				Rabbit rabbit = (Rabbit) animal;
				rabbit.setDead();
				this.foodLevel += RABBIT_FOOD_VALUE;
				temp = where;
			}
			if (animal instanceof Fox)
			{
				Fox f = (Fox) animal;
				f.setDead();
				this.foodLevel += FOX_FOOD_VALUE;
				temp = where;
			}
		}
		return temp;
	}
}