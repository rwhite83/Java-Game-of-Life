package asmt2A;

import java.awt.Point;
import java.util.ArrayList;

public abstract class LifeForm {

	public static final int MAX_UNFED = 5;
	public static final int MAX_MOVE_ATTEMPTS = 8;
	public static final int MINIMUM_MATE_NEIGHBOURS = 1;
	public static final int MINIMUM_NULL_NEIGHBOURS = 2;
	public static final int MINIMUM_FOOD_NEIGHBOURS = 2;

	/**
	 * an array list of Pointor positions relative any given cell
	 */
	public static Point UpLeft = new Point(-1, 1);
	public static Point Up = new Point(0, 1);
	public static Point UpRight = new Point(1, 1);
	public static Point Left = new Point(-1, 0);
	public static Point Right = new Point(1, 0);
	public static Point DownLeft = new Point(-1, -1);
	public static Point Down = new Point(0, -1);
	public static Point DownRight = new Point(1, -1);

	Point[] moves = new Point[] { UpLeft, Up, UpRight, Left, Right, DownLeft, Down, DownRight };

	protected Point position;
	protected Colour colour;
	protected boolean moved = false;
	protected World world;

	protected int moveAttempts;
	protected int lastFeed;
	protected int myNeighbours;
	protected int myEdibleCount;
	protected int nullNeighbours;

	/**
	 * an arraylist to check for fertile positions around a particular grid location
	 */
	ArrayList<Point> viableMoves = new ArrayList<Point>();

	protected LifeForm(World world, Point position, Colour colour) {
		this.world = world;
		this.position = position;
		this.colour = colour;
	}

	protected abstract boolean hasFed();

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public boolean getMoved() {
		return moved;
	}

	public Colour getColour() {
		return colour;
	}

	public void resetLastFed() {
		lastFeed = 0;
	}

	/**
	 * method to check if a step the animal tries to move to is within the
	 * boundaries of the world
	 * 
	 * @param x vertical position
	 * @param y horizontal position
	 * @return boolean result
	 */
	public boolean isInBounds(Point point) {
		return (point.x < world.worldBounds.x && point.y < world.worldBounds.y) && (point.x >= 0 && point.y >= 0);
	}

	/**
	 * method to check if a cell the animal tries to move to is null
	 * 
	 * @param x vertical position
	 * @param y horizontal position
	 * @return boolean result
	 */
	public boolean isNull(Point point) {
		return World.cell[(int) point.x][(int) point.y].life == null;
	}

	/**
	 * method to check if the contents of a cell moving into are edible
	 * 
	 * @param x vertical position
	 * @param y horizontal position
	 * @return boolean result
	 */

	public boolean isEdible(Point Point) {
		return false;
	}

	public boolean isBirthable() {
		return (myNeighbours >= MINIMUM_MATE_NEIGHBOURS && nullNeighbours >= MINIMUM_NULL_NEIGHBOURS
				&& myEdibleCount >= MINIMUM_FOOD_NEIGHBOURS);
	}

	/**
	 * 
	 * @param Point the target Pointor orchestrates a move of an Herbivore to a new
	 *              cell
	 */
	void moveSpace(Point point) {
		World.cell[point.x][point.y].life = World.cell[position.x][position.y].life;
		World.cell[position.x][position.y].life = null;
		position = point;
		moved = true;
	}

	/**
	 * destroys the animal by making it's own cell null and sets the cell's colour
	 * to null's sienna
	 */
	public void die() {
		World.cell[position.x][position.y].life = null;
	}

	public void eat(Point pointFrom, Point pointTo) {
		World.cell[pointTo.x][pointTo.y].life.die();
		moveSpace(pointTo);
	}

	public void neighbourCheck(Point tempPoint) {
		myNeighbours = 0;
		myEdibleCount = 0;
		nullNeighbours = 0;
		viableMoves.clear();
		for (int i = 0; i < moves.length; i++) {
			Point currentPoint = new Point(position.x + moves[i].x, position.y + moves[i].y);
			if (currentPoint.x < world.worldBounds.x && currentPoint.y < world.worldBounds.y && currentPoint.x >= 0
					&& currentPoint.y >= 0) {
				if (World.cell[currentPoint.x][currentPoint.y].life instanceof HerbivoreEdible
						&& World.cell[currentPoint.x][currentPoint.y].life instanceof OmnivoreEdible) {
					myNeighbours++;
					myEdibleCount++;
					myEdibleCount++;
					World.cell[currentPoint.x][currentPoint.y].life.viableMoves.add(currentPoint);
				} else if (World.cell[currentPoint.x][currentPoint.y].life instanceof CarnivoreEdible
						&& World.cell[currentPoint.x][currentPoint.y].life instanceof OmnivoreEdible) {
					myNeighbours++;
					myEdibleCount++;
					myEdibleCount++;
					World.cell[currentPoint.x][currentPoint.y].life.viableMoves.add(currentPoint);
				} else if (World.cell[currentPoint.x][currentPoint.y].life instanceof OmnivoreEdible) {
					myNeighbours++;
					myEdibleCount++;
					World.cell[currentPoint.x][currentPoint.y].life.viableMoves.add(currentPoint);
				} else if (World.cell[currentPoint.x][currentPoint.y].life instanceof CarnivoreEdible) {
					myNeighbours++;
					myEdibleCount++;
					World.cell[currentPoint.x][currentPoint.y].life.viableMoves.add(currentPoint);
				} else if (World.cell[currentPoint.x][currentPoint.y].life == null) {
					nullNeighbours++;
					viableMoves.add(currentPoint);
				}
			}
		}
	}

	public void live() {
		if (lastFeed >= MAX_UNFED) {
			die();
			return;
		}
		giveBirth(myNeighbours, nullNeighbours, myEdibleCount, World.cell[position.x][position.y].life);
		neighbourCheck(position);
		moveAttempts = 0;
		while (moved == false && moveAttempts < MAX_MOVE_ATTEMPTS) {

			moveAttempts++;
			int randomPositionInt = RandomGenerator.nextNumber(moves.length);
			Point temp = moves[randomPositionInt];
			Point newAnimalPoint = new Point(position.x + temp.x, position.y + temp.y);
			if (isInBounds(newAnimalPoint)) {
				if (isNull(newAnimalPoint)) {
					moveSpace(newAnimalPoint);

					lastFeed++;
				}
				if (isEdible(newAnimalPoint)) {
					eat(position, newAnimalPoint);
					lastFeed = 0;
					moved = true;
				}
				if (isBirthable()) {
					World.cell[oldAnimalPoint.x][oldAnimalPoint.y].life = new Herbivore(world, oldAnimalPoint);
					World.cell[oldAnimalPoint.x][oldAnimalPoint.y].life.setMoved(true);
				}

			}
		}
	}

	public void giveBirth(int numberOfMates, int emptyCells, int foodAvailable, LifeForm life) {
		if (numberOfMates >= MINIMUM_MATE_NEIGHBOURS && emptyCells >= MINIMUM_NULL_NEIGHBOURS
				&& foodAvailable >= MINIMUM_FOOD_NEIGHBOURS) {
			viableMoves.clear();
			neighbourCheck(position);
			int randomPositionInt = RandomGenerator.nextNumber(moves.length);
			Point temp = moves[randomPositionInt];
			Point newAnimalPoint = new Point(position.x + temp.x, position.y + temp.y);
			if (isInBounds(newAnimalPoint)) {
				if (isNull(newAnimalPoint)) {
					World.cell[newAnimalPoint.x][newAnimalPoint.y].life = life;
					// World.cell[newAnimalPoint.x][newAnimalPoint.y].life.setMoved(true);
					// World.cell[newAnimalPoint.x][newAnimalPoint.y].life.resetLastFed();
				}
			}
		}
	}
}
