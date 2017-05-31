import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

/**
 * A simple predator-prey simulator, based on a field containing
 * rabbits and foxes.
 */
public class Simulator
{
	/* Constants representing configuration information for the simulation. */

	// The default width for the grid.
	private static final int DEFAULT_WIDTH = 100;
	// The default depth of the grid.
	private static final int DEFAULT_DEPTH = 100;
	// The probability that a fox will be created in any given grid position.
	private static final double FOX_CREATION_PROBABILITY = 0.07;
	// The probability that a rabbit will be created in any given grid position.
	private static final double RABBIT_CREATION_PROBABILITY = 0.08;
	private static final double BEAR_CREATION_PROBABILITY = 0.03; 

	/* Instance variables */

	// Lists of animals in the field. Separate lists are kept for ease of iteration.
	private List<Actor> foxes;
	private List<Actor> rabbits;
	private List<Actor> bears;

	// The current state of the field.
	private Field field;
	// A second field, used to build the next stage of the simulation.
	private Field updatedField;
	// The current step of the simulation.
	private int step;
	// A graphical view of the simulation.
	private SimulatorView view;

	/**
	 * Construct a simulation field with default size.
	 */
	public Simulator() {
		this(DEFAULT_DEPTH, DEFAULT_WIDTH);
	}

	/**
	 * Create a simulation field with the given size.
	 * @param depth Depth of the field. Must be greater than zero.
	 * @param width Width of the field. Must be greater than zero.
	 */
	public Simulator(int depth, int width) {
		foxes = new ArrayList<>();
		rabbits = new ArrayList<>();
		bears = new ArrayList<>();
		field = new Field(depth, width);
		updatedField = new Field(depth, width);
		step = 0;
		// Create a view of the state of each location in the field, don't remove
		view = new SimulatorView(depth, width);
		view.setColor(Rabbit.class, Color.orange);
		view.setColor(Fox.class, Color.blue);
		view.setColor(Bear.class, Color.red);

		// Setup a valid starting point, don't remove
		reset();
	}

	/**
	 * Run the simulation from its current state for a reasonably long period,
	 * e.g. 500 steps.
	 */
	public void runLongSimulation()
	{
		simulate(500);
	}

	/**
	 * Run the simulation from its current state for the given number of steps.
	 * Stop before the given number of steps if it ceases to be viable.
	 * @param numSteps The number of steps to run for.
	 */
	public void simulate(int numSteps)
	{
		for(int step = 1; step <= numSteps && view.isViable(field); step++) {
			simulateOneStep();
		}
	}

	/**
	 * Run the simulation from its current state for a single step.
	 * Iterate over the whole field updating the state of each
	 * fox and rabbit.
	 */
	public void simulateOneStep() {
		try { Thread.sleep(100); } catch (Exception e) {} // Slow it down a bit, don't remove
		step++;
		List<Actor> newRabbits = new ArrayList<>();
		for (int i = 0; i < rabbits.size(); i++) {
			rabbits.get(i).act(newRabbits);
			if (!rabbits.get(i).isAlive()) {	
				rabbits.remove(i);
				i--;
			}
		}
		rabbits .addAll(newRabbits);
		List<Actor> newFoxes = new ArrayList<>();
		for (int i = 0; i < foxes.size(); i++) {
			foxes.get(i).act(newFoxes);
			if (!foxes.get(i).isAlive()) {
				foxes.remove(i);
				i--;
			}
		}
		foxes.addAll(newFoxes);
		List<Actor> newBears = new ArrayList<>();
		for (int i = 0; i < bears.size(); i++) {
			bears.get(i).act(newBears);
			if (!bears.get(i).isAlive()) {
				bears.remove(i);
				i--;
			}
		}
		bears.addAll(newBears);
		field = new Field(updatedField);
		updatedField.clear();
		view.showStatus(step, field); // Display the new field on screen, don't remove
	}

	/**
	 * Reset the simulation to a starting position.
	 */
	public void reset() {
		step = 0;
		rabbits.clear();
		foxes.clear();
		field.clear();
		updatedField.clear();
		populate();

		// Show the starting state in the view
		view.showStatus(step, field);
	}

	/**
	 * Populate a field with foxes and rabbits.
	 * @param field The field to be populated.
	 */
	private void populate() {
		Random rand = new Random();
		field.clear();
		for (int r = 0; r < field.getDepth(); r++) {
			for (int c = 0; c < field.getWidth(); c++) {
				if (BEAR_CREATION_PROBABILITY > rand.nextDouble()) {
					Actor b = new Bear(field, updatedField);
					b.setLocation(new Location(r, c));
					bears.add(b);
					field.place(b, r, c);
				}
			}
		}
		for (int r = 0; r < field.getDepth(); r++) {
			for (int c = 0; c < field.getWidth(); c++) {
				if (FOX_CREATION_PROBABILITY > rand.nextDouble()) {
					Actor f = new Fox(field, updatedField);
					f.setLocation(new Location(r, c));
					foxes.add(f);
					field.place(f, r, c);
				}
			}
		}
		for (int r = 0; r < field.getDepth(); r++) {
			for (int c = 0; c < field.getWidth(); c++) {
				if (field.getObjectAt(r, c) == null && RABBIT_CREATION_PROBABILITY > rand.nextDouble()) {
					Actor rab = new Rabbit(field, updatedField);
					rab.setLocation(new Location(r,c));
					rabbits.add(rab);
					field.place(rab, r, c);
				}
			}
		}
	}
	
}