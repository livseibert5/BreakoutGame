# breakout

This project implements the game of Breakout.

Name: Livia Seibert

### Timeline

Start Date: 1/23/21

Finish Date: 1/30/21

Hours Spent: 40

### Resources Used
GeeksForGeeks file reading tutorial: https://www.geeksforgeeks.org/different-ways-reading-text-file-java/

### Running the Program

Main class: Main.java, calls GameController

Data files needed: level1.txt, level2.txt, and level3.txt are needed
to create the layouts for the levels, ball.png, ballred.png, cupid.png,
heart.png, cupidleft.png, paddle.png, pink.png, power.png, red2.png,
red3.png, red4.png, red5.png, and rose.png are all needed to display
the graphics for the program without crashing.

Key/Mouse inputs:
mouse click starts game, left/right arrows move the paddle

Cheat keys:
'L' - gives the player an additional life
'R' - resets the level
'1-9' - jumps to corresponding level, or highest existing level
'N' - moves to next level
'P' - drops a power-up
'B' - removes the boss enemy in the third level
'E' - drops another ball into gameplay


### Notes/Assumptions

Assumptions or Simplifications: Overall, my implementation of the breakout game
adheres to the plan I laid out in PLAN.md. While I have all the classes that I
intended to implement, I designed some of them differently for my actual implementation.
For example, the Powerup class exists, however it is PowerupBrick that has the function
to generate a random power-up type rather than the Powerup class. Otherwise, I was
able to include all of the features that I had planned for.

Known Bugs: When a life is lost at the same time as the L
cheat key is pressed, sometimes the positioning of the life icons in the header are
thrown off. Sometimes a ball registers a hit more than once
and breaks before it is supposed to.

Extra credit: I added a boss enemy in level 3. The boss moves back and
forth across the screen horizontally for the duration of the level.
The boss takes one of the player's lives when the ball hits it. I added
an extra cheat key which drops another ball into the game when the E key is pressed.


### Impressions
This project starts off very difficult, as it is tough to visualize how
to break the game down into components, and I personally had no experience
with javafx prior to this class. However, as I went along it got easier and
easier as I got more comfortable with javafx and knew exactly which pieces I
had to work on to make the project function as intended. Therefore, I think
this is a good project as it throws you into the deep end (but provides you
with the tools to succeed), and you learn through trial and error.
