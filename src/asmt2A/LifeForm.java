package asmt2A;

import java.awt.Point;
import java.util.ArrayList;

public abstract class LifeForm {

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

	protected int plantNeighbours;
	protected int herbivoreNeighbours;
	protected int carnivoreNeighbours;
	protected int omnivoreNeighbours;

	protected int herbivoreEdibleCount;
	protected int omnivoreEdibleCount;
	protected int carnivoreEdibleCount;

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

	/**
	 * various parent methods defined in children classes
	 */
	public void live() {
	}

	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	public boolean getMoved() {
		return moved;
	}

	public Colour getColour() {
		return colour;
	}

	/**
	 * method to check if a step the animal tries to move to is within the
	 * boundaries of the world
	 * 
	 * @param x vertical position
	 * @param y horizontal position
	 * @return boolean result
	 */
	public boolean inBoundsCheck(Point Point) {
		return (Point.x < world.worldBounds.x && Point.y < world.worldBounds.y) && (Point.x >= 0 && Point.y >= 0);
	}

	/**
	 * method to check if a cell the animal tries to move to is null
	 * 
	 * @param x vertical position
	 * @param y horizontal position
	 * @return boolean result
	 */
	public boolean isNullCheck(Point Point) {
		return World.cell[(int) Point.x][(int) Point.y].life == null;
	}

	/**
	 * method to check if the contents of a cell moving into are edible
	 * 
	 * @param x vertical position
	 * @param y horizontal position
	 * @return boolean result
	 */
	public boolean isEdibleCheck(Point Point) {
		return false;
	}

	/**
	 * 
	 * @param Point the target Pointor
	 * @return returns true if it's a viable move position
	 */
	public boolean viablePosition(Point Point) {
		boolean herbivoreEdible = isEdibleCheck(Point);
		boolean isNull = isNullCheck(Point);
		return (herbivoreEdible || isNull);

	}

	/**
	 * 
	 * @param Point the target Pointor orchestrates a move of an Herbivore to a new
	 *             cell
	 */
	void moveSpace(Point Point) {
		World.cell[Point.x][Point.y].life = World.cell[position.x][position.y].life;
		World.cell[position.x][position.y].life = null;
		position = Point;
		moved = true;
	}

	/**
	 * destroys the animal by making it's own cell null and sets the cell's colour
	 * to null's sienna
	 */
	public void die() {
		World.cell[position.x][position.y].life = null;
	}

	/**
	 * goes through all neighboring cells, if they are within global bounds it
	 * checks if they are herbivore edible and increments plantNeighbors count if it
	 * is, and checks if the cell is null and increments if it is. if it's a viable
	 * position, it adds that position to a the fertileViable array list
	 */
	public void neighborCheck(Point tempPoint) {
		plantNeighbours = 0;
		nullNeighbours = 0;
		viableMoves.clear();
		for (int i = 0; i < moves.length; i++) {
			Point currentPoint = new Point(position.x + moves[i].x, position.y + moves[i].y);
			if (currentPoint.x < world.worldBounds.x && currentPoint.y < world.worldBounds.y && currentPoint.x >= 0
					&& currentPoint.y >= 0) {
				if (World.cell[currentPoint.x][currentPoint.y].life instanceof Plant) {
					plantNeighbours++;
				} else if (World.cell[currentPoint.x][currentPoint.y].life instanceof Herbivore) {
					herbivoreNeighbours++;
				} else if (World.cell[currentPoint.x][currentPoint.y].life instanceof Carnivore) {
					carnivoreNeighbours++;
				} else if (World.cell[currentPoint.x][currentPoint.y].life instanceof Omnivore) {
					omnivoreNeighbours++;
				} else if (World.cell[currentPoint.x][currentPoint.y].life instanceof HerbivoreEdible && World.cell[currentPoint.x][currentPoint.y].life instanceof Herbivore) {
					herbivoreEdibleCount++;
				} else if (World.cell[currentPoint.x][currentPoint.y].life instanceof CarnivoreEdible) {
					carnivoreEdibleCount++;
				} else if (World.cell[currentPoint.x][currentPoint.y].life instanceof OmnivoreEdible) {
					omnivoreEdibleCount++;
				} else if (World.cell[currentPoint.x][currentPoint.y].life == null) {
					nullNeighbours++;
					viableMoves.add(currentPoint);
				}
			}
		}
	}
}
