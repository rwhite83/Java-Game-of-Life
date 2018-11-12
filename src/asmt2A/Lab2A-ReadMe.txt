This program created by:
W. Ross White
A00975239
Set B

On or around:
November 11, 2018

The random number generator class provided by my instructor:
Dennis Richards

This is a game of life program designed to model interaction between eating and 
moving herbivores,and seeding plants.

A tour of the program:

There is an enumerator class to define my colours which are associated with
contents of cells.

There is a random number generator provided by my instructor.

There is an edible interface to implement in my plant class, so an herbivore
recognizes it as something it can eat; they will recognize as edible anything
which implements the herbivore edible interface.

My main simply instantiates a game, and calls the play function on it.

Game function instantiates a new world, and handles my GUI, including the 
start function which populates a gridpane with buttons defined in world, 
the launchGUI function which launches the GUI after instantiating a 2D button 
array, and handles button presses, which call the turn function, which calls
world turn on the newly created world.

In the world class, a 2D array of cells is declared, then in the world
constructor, a createWorld function is called, which instantiates the 2D array,
and according to the rules presented by Dennis populates the array with herbivores
and plants (or null), calling their constructors sequentially in cells.

This class also has a turn function for when the buttons are clicked.  It calls
a live function on all cells which contain a lifeform, then sets a moved variable
to false after the move (a variable which ensures lifeforms only perform a behaviour
once per turn, move in the case of herbivores, and plants not participate in a spawn
on the turn of their creation.  It then calls a colorize function on every cell, which
reasserts the colours of the lifeform in a cell, and a default colour for cells with
no lifeform.

The world class also has an inner cell class, with a constructor which passes in 
horizontal and vertical coordinates, as well as a lifeform.

On turn in the world class, it calls a live function on the lifeforms.  This is
referencing the lifeform class, which has empty methods defined by herbivore and 
plant classes which extend it. Also in lifeform class is an array of vectors to
indicate all the positions around any given cell, which both children can use
in their processing their operations.  Also included are other primary methods
defined by plants and herbivores.

In the herbivore class, I have a constructor which passes in location variables and sets
a lastfeed variable to zero.  When the live function is called on lifeform, the herbivore
responds by calling a move function. The animal picks a spot at random around it.  It then calls
three check functions, in bounds, is edible, and is null.  If it is in bounds, and is
either edible or null, the animal moves there.  If it moves to a null, lastfeed is
incremented, if it is an edible, lastfeed is reset to zero.  If lastfeed reaches
5, on it's next turn it calls a die function which sets its cell's lifeform variable
to null.  this all happens in a while loop which uses the same moved variable as
mentioned earlier, to keep attempting to move until a spot to move can be found.  
There is also a variable called moveAttempts which increments until it reaches 8, the
maximum possible positions it could move to, to account for scenerios in which it cannot
move anywhere.

My plant class has a constructor which takes in location variables and passes them in
in addition to creating a vector for this particular plant.  The first is neighbour check, 
which takes in a vector and first resets nullNeighbours and plantNeighbours to zero and clears
a fertile arraylist of vector positions.  It then checks every position around the passed in 
vector.  If the position contains a plant, in increments plantNeighbours, if it is empty(null) 
it increments nullNeighbours and adds the vector to the fertile arraylist. If fertile arraylist 
is greater than zero, and nullNeighbours is greater than or equal to three, one of it's elements 
is randomly selected.  neighbourCheck is then called on this cell.  If it has exactly four plants
around it, a new plant is created in the cell.