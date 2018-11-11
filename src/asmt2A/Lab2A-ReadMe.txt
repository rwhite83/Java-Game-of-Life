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
is randomly selected.  neighbourCheck is then called on this cell.  If it has exactly four 







The program is designed to take in four parameters from the command line,
a r, s, t, Rt, or d to generate a rectangle of '*', a square of '*', 
a triangle of '@', a right triangle of '@', or a diamond of '#'
of denote rectangle, t, or d, and produce a rectangle of * respectively.  
Also taken in at the command line are two numerical value, one for height, 
and one for width.  

The width value is ignored by the diamond, triangle, and right triangle, 
and instead the height is based on the width.  If improper parameters 
are input at the command line, error messages from the template provided 
me are printed out.  Likewise a restriction of numbers input being between 
1 and 100 is established.

In the main, the prewritten code establishes which shape is to be drawn, and uses
something called a 'hash map' to determine the type and feed it to the shape type 
variable.  

getShape in the Main class then creates an object of the designated type as a
parent shape.  As it passes through the shape constructor, a 2 dimentional string 
array is created called shapeArray.  Control then passes to the Rectangle, Triangle,
Right Triangle, or Diamond class, where the third constructor parameter is hard coded.
In the case of square, it is passed to the square class which is a child of the rectangle
class, and different dimensions are passed thorough the super constructs only, and the
rectangle class does all the work.

The array is then populated with values.  The rectangle is straight forward, all 
index positions are populated with *.  The triangle was tricker.  I used an incrementing
variable i to create a sequentially wider middle zone where the @ would go,
and spaces in index positions outside that zone.  For right triangle I used a similar
method, but I set the height and width to both be the width, and i started my 'k'
centre point at zero to cascade down from the left.  The diamond I recycled the triangle
code as well, but added three lines which decremented the i after the for loops gets
past the halfway point of the height. 

At the command line arguments a displayer type is also brought in, and the getDisplayer
function in main puts a value in the displayer variable, either GUIDisplayer or ConsoleDisplayer.
displayer.displayShaper(shape) calls the abstract display shape, which depending on the type
flows to GUI Displayer or ConsoleDisplayer.  If Console Displayer, it uses the same 
standard method to print out the contents of the array.  If GUIDisplayer, it launches
DisplayerFrame, which implements code provided by Dennis to create a javaFX window with the
characters displayed on buttons.

When parameters s 9 5 ConsoleDisplayer are passed in, the output is:

*********
*********
*********
*********
*********
*********
*********
*********
*********

When paramters r 9 5 ConsoleDisplayer are passed in, the output is:

*********
*********
*********
*********
*********

When parameters t 7 7 ConsoleDisplayer are passed in, the output is:

   @
  @@@
 @@@@@
@@@@@@@

When parameters d 15 15 ConsoleDisplayer are passed in, the output is:

       #
      ###
     #####
    #######
   #########
  ###########
 #############
###############
 #############
  ###########
   #########
    #######
     #####
      ###
       #

When parameters Rt 9 9 ConsoleDisplayer are passed in, the outpus is:

@
@@
@@@
@@@@
@@@@@
@@@@@@
@@@@@@@
@@@@@@@@
@@@@@@@@@

I am not sure how to represent results in a readme file for the GUIDisplayer,
I can demonstrate upon request, but they do work.

Side note:  I wanted to make right triangle a child class of triangle, and I could
get it work but it only if I was left with a lot of empty spaces, which looked bad in
the GUIDisplayer.  I opted instead to just create it directly as a child of the 
shape class instead to avoid this problem, since the question didn't specifically
suggest this hierarchy as it did with the square-is-a-rectangle.
       