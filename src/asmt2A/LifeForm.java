package asmt2A;

import java.awt.Point;
import java.util.ArrayList;

public abstract class LifeForm {

	public static final int MAX_UNFED = 5;
	public int minMateNeighbours;
	public int minNullNeighbours;
	public int minFoodNeighbours;

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

	protected boolean hasFed() {
		return (lastFeed < MAX_UNFED);
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
		return (World.cell[point.x][point.y].life == null);
	}

	/**
	 * method to check if the contents of a cell moving into are edible
	 * 
	 * @param x vertical position
	 * @param y horizontal position
	 * @return boolean result
	 */

	protected abstract boolean isEdible(Point point);

	protected abstract boolean isMyType(Point point);

	protected abstract void giveBirth(Point newSpawnPoint);

	public boolean canGiveBirth(Point point) {
		return (myNeighbours >= minMateNeighbours && nullNeighbours >= minNullNeighbours
				&& myEdibleCount >= minFoodNeighbours);
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
			Point possibleMove = new Point(position.x + moves[i].x, position.y + moves[i].y);
			if (isInBounds(possibleMove)) {
				if (isEdible(possibleMove)) {
					myEdibleCount++;
					viableMoves.add(possibleMove);
				}
				if (isMyType(possibleMove))
					myNeighbours++;
				else if (World.cell[possibleMove.x][possibleMove.y].life == null) {
					nullNeighbours++;
					viableMoves.add(possibleMove);
				}
			}
		}

	}

	public void live() {
		if (!hasFed()) {
			die();
		} else {
			neighbourCheck(position);
			moveAttempts = 0;
			if (viableMoves.size() == 0) {
				lastFeed++;
				return;
			}

			// create position to move
			int randomPositionInt = RandomGenerator.nextNumber(viableMoves.size());
			Point moveTempPoint = viableMoves.get(randomPositionInt);

			if (isNull(moveTempPoint)) {
				moveSpace(moveTempPoint);
				lastFeed++;
			}
			if (isEdible(moveTempPoint)) {
				eat(position, moveTempPoint);
				lastFeed = 0;
				moved = true;
			}

			viableMoves.remove(randomPositionInt);

			while (viableMoves.size() > 0) {
				randomPositionInt = RandomGenerator.nextNumber(viableMoves.size());
				moveTempPoint = viableMoves.get(randomPositionInt);
				if ((!isNull(moveTempPoint)) && (!canGiveBirth(moveTempPoint)))
					viableMoves.remove(randomPositionInt);
				else {
					giveBirth(moveTempPoint);
					World.cell[moveTempPoint.x][moveTempPoint.y].life.setMoved(true);
					break;
				}
			}
		}
	}
}
