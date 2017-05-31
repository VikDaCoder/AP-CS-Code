import java.util.List;
import java.util.Random;

public abstract class Animal {
	// The age at which a animal can start to breed.
	private int BREEDING_AGE;
	// The age to which a animal can live.
	private int MAX_AGE;
	// The likelihood of a animal breeding.
	private double BREEDING_PROBABILITY;
	// The maximum number of births.
	private int MAX_LITTER_SIZE;
	// A shared random number generator to control breeding.

	// The animal's age.
	private int age;
	// Whether the animal is alive or not.
	private boolean alive;
	// The animal's position
	private Location location;

	Field field;
	Field updatedField;
	
	public Animal(int BA, int MA, double BP, int MLS, Field now, Field updated) {
		this.BREEDING_AGE = BA;
		this.MAX_AGE = MA;
		this.BREEDING_PROBABILITY = BP;
		this.MAX_LITTER_SIZE = MLS;
		this.age = 0;
		this.alive = true;
		this.location = null;
		field = now;
		updatedField = updated;
	}

	public abstract void act(List<Actor> newAnimals); 

	public void incrementAge() {
		age++;
		if (age > MAX_AGE) alive = false;
	}

	public int breed() {
		Random r = new Random();
		if (canBreed() && r.nextDouble() < BREEDING_PROBABILITY) return r.nextInt(MAX_LITTER_SIZE);
		return 0;
	}

	private boolean canBreed() {
		return this.age >= BREEDING_AGE;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setLocation(int row, int col){
		this.location = new Location(row , col);
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getAge() {return age;}
	public void setAge(int a) {age = a;}
	public void setDead() {alive = false;}
	public Location getLocation() {return location;}
	public Field getField() {return field;}
	public Field getUpdatedField() {return updatedField;}
}
