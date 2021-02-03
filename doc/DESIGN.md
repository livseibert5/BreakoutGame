# DESIGN Document for breakout
## Livia Seibert

## Role(s)
I created this breakout game by myself from the design to the implementation,
so all the files in this repository were authored by me.

## Design Goals
**Minimize Repetition** - I wanted to make sure each object I was
creating had a defined purpose so that I wasn't writing repetitive
code.

**Add Components Easily** - Once I had the classes written, I
wanted to be able to incorporate instances of their objects into the
game easily.

**Game Flow** - I wanted the game to progress from the start screen to the
win or loss screen without glitches.

## High-Level Design
**Main** is the class that acts as the driver for the game. Main calls 
setupGame in the **GameController** class, and this function takes the
current level as input and puts all the game pieces on the screen.
Then, game play is driven by the step function back in the Main class,
which detects for collisions and interactions between objects. There
is a **Ball** class, which extends Circle and is used to create the main
ball for game play as well as all the balls created as power-ups.
There is a **Paddle** class, which extends Rectangle and is used to create
the main paddle for game play. There are two classes used to implement
the three different types of bricks in the game. **Brick** is used to create
single and multi-hit blocks, with the difference between the two being
the number of lives specified as a class variable. **PowerupBrick** extends
Brick because it has all the same functionality as a regular brick,
except it also has a power-up type variable. The **Powerup** class extends
Circle, and Powerup objects are created when a PowerupBrick is broken.
This Powerup carries the same powerup type as the PowerupBrick it came
from, and the powerup is implemented when the Powerup object hits the
paddle. **Boss** also extends Brick, as the collision handling is very
similar, but it is only used in level 3 when the boss enemy is present.
The **Screen** class is used to create the instruction scene, the win scene,
and the loss scene.


## Assumptions or Simplifications
I depended very heavily on Main and GameController. Therefore, Main and
GameController have disproportionately more code than the other classes.
Main is also completely dependent on GameController, which means that GameController
needs to have a lot of public getter methods so that Main can get data from it.
Main then stores this data in its own class instance variables and uses it
to handle the functionality of the entire game. While I know this isn't
necessarily best code practice due to the dependencies, I didn't want to
combine GameController and Main, as the resulting class would be massive and
still have two jobs, setting the scene and updating the scene.

I simplified creating the menu bar at the top of the game as well, as instead
of creating an object for it and a paneled layout, I created text boxes, and a
line made from a Rectangle to divide the menu bar from the rest of game play.

## Changes from the Plan
I was able to implement both paddle features, all three types of bricks, and
all three powerups that I described in my plan. I implemented the four cheat
keys in the plan, plus a few extra as specified by the assignment. My levels
look like the layout in the plan, and the boss enemy in level 3 works well.

I implemented the Ball class from my plan, however the Ball class doesn't
really have a function to determine how the ball's motion should change when it
hits a block or paddle. Instead, the Main class detects collisions and calls
the invertXDirection and invertYDirection functions, which in turn change the
direction of the ball.

The Powerup class does contain a randomly generated powerup, however the random
generator function is in the PowerupBrick class and not the Powerup class itself.
This was so that the powerup type could be chosen right when the scene was set.

## How to Add New Features
**Powerups** - To add a new powerup, add more types to the Power enum
in the PowerupBrick class. Then, alter the random function generator in
the PowerupBrick class to reach one number higher, and have that number
correspond with the new Power type. Then, add a new if statement in
setPowerUp in Main to check for that type of Powerup, and have that if
statement trigger a new powerup action.

**Levels** - Create a new .txt file with a level layout on it and add it to
the data folder. Add an if statement in setupGame in GameController to
handle the new level number and read in the correct file. Change handleWin
in Main so that the win happens after the new level, and not after the third
level anymore.

**Bricks** - To create a new Brick type, create a new class that extends the
Brick class. Give this class new functions that give it the desired new
functionality, and then modify a layout .txt file to include this new type 
of brick.

**Other Changes** - To add a new object the game, create a class for it
in the breakout folder. Then, create a new private function in GameController
to initialize it and add it to the scene. Any collision handling for the new
object should be done in Main and called in the step function, and these
collisions should trigger some sort of effect or action in the game.
