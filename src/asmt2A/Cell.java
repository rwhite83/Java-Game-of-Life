package asmt2A;

import java.awt.Point;

/**
* inner cell class includes vertical and horizontal position in the cell array,
* the colour to be associated with that kind of cell, a lifeform to associate
* with it (passed in the constructor)
*/
public class Cell {

    /**
     * position is the dimensions of a particular cell within the 2D
     * cell array
     */
    public Point position;

    /**
     * declaration of a lifeform used to associate a lifeform with a cell
     */
    public LifeForm life;

    /**
     * cell constructor
     * 
     * @param position   is the position of a cell in it's array
     * @param life       the lifeform variable for a cell
     */
    Cell(Point position, LifeForm life) {
        this.life = life;
        this.position = position;
    }
}