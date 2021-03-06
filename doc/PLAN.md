# Game Plan
### Livia Seibert

## Interesting Breakout Variants
I thought Brick Breaker Hero was an interesting variant of Breakout. I like
that the game feels more like a video game than a traditional version of
Breakout due to the boss enemy and the graphics. I think that the simplicity
of Brick Breaker Hero shows that it's possible to make a unique game from the
basics of Breakout with just a few creative design choices. The different boss
enemies also give each level its own feel, which makes the game more exciting
and encourages the user to keep completing levels to see a new game setting.

Centipong is another version of Breakout that I liked. I think variants with
multiple balls add another layer of complexity and a more fast-paced feel to
the game, and I think that the centipede up top introduces these extra balls
in a creative way. The different colors and balls flying across the screen
creates an exciting visual as well, especially when they all experience a
power up at the same time. I think these graphics are a great example of how
to make a game look good without unnecessarily complex elements.

## Paddle Ideas

#### Paddle Warps Across Screen
When the paddle hits the left or right of the screen, it appears on the other
side of the screen instead of bouncing off the wall.

#### Ball Bounces Differently
The ball will bounce differently depending on which part of the paddle it hits.
On the outer thirds, the ball reverses its direction and goes back the way it
came, and on the middle third the ball bounces normally.

## Block Ideas

#### Single-Hit Blocks
This block will break the first time it is hit by the ball.

#### Multi-Hit Blocks
This block will take between 1 and 5 hits by the ball to break. The block will
display the number of hits it has left before it breaks, decrementing the number
every time the ball hits it.

#### Power-Up Blocks
These blocks will release power-ups, but the type of power-up they release will
be chosen at random.

## Power-up Ideas
My game will have three types of power ups, and they will be released by breaking
power-up blocks. The look of the power-up block will not reveal which of the power-ups
it releases, as the power-up is generated randomly. The selected power-up will simply
be applied to the game, and play will continue normally.

#### Extra Balls
Another ball will be introduced to the game. The player will not die if one of the
balls drops, only if both of them are missed by the paddle.

#### Ball Speeds Up
This power-up will make the game harder for the player, as the ball will start
moving faster and therefore be harder to deflect with the paddle. However, a faster
ball can also break bricks more quickly.

#### Paddle Gets Longer
This power-up will make the length of the player's paddle increase. Therefore, it
will be easier for the player to hit the ball with the paddle since it now takes
up more of the screen.

## Cheat Key Ideas

#### 'R'
Resets the current level to the beginning.

#### 'N'
Automatically clears the level and moves to the next one.

#### 'P'
Random power-up is applied to the game.

#### 'L'
Player gets another life.

## Level Descriptions

#### General Overview
This game is going to be Valentine's Day themed. The player will
start with three lives, represented by roses, and the ball will have a heart on it. The single-hit
bricks will be pink, the multi-hit bricks will be red with the number of hits they have left
written on them, and the power-up bricks will be red and pink with hearts. The boss enemy will
be a baby cupid, and all the bricks in each level will be arranged as hearts. To clear a level,
the player must destroy all the bricks. The player will control the paddle with the left and right
arrow keys on the keyboard.

#### Level 1
Level 1 will be the most basic level of the game. Single-hit blocks will be arranged in a
large outline of a heart with a region in the middle that contains more single-hit blocks
as well as power-ups.

    0000000000000000
    0001110000111000
    0010001001000100
    0100000110000010
    0100000000000010
    0010000330000100
    0001001111001000
    0000100000010000
    0000010000100000
    0000001001000000
    0000000110000000
    0000000000000000

In this picture, 1s are single-hit blocks and 3s are power-up blocks.
0s are just empty space. This is true for the diagrams below as well.

#### Level 2
Level 2 will be slightly more difficult, with smaller hearts made of blocks scattered around
the screen and multi-hit blocks introduced. Some hearts will be made entirely of multi-hit
blocks, and others will be made entirely of single-hit blocks. Each heart will have a power-up
block at its center.

    011011000220220
    100100102002002
    013331000233320
    001010000020200
    000100000002000
    000000000000000
    022022000110110
    200200201002001
    023332000133310
    002020000010100
    000200000001000
    000000000000000    

In this diagram, there are four hearts. The top left heart and bottom right heart are made of
single-hit blocks and the top-right and bottom-left hearts are made from multi-hit blocks. The
3s in the middle of each heart are the power-ups.

#### Level 3
The final level will feature a completely solid heart, with all the outer blocks being multi-hit
blocks. Four power-ups will be placed outside the heart for assistance. The inside of the
heart will be made with alternating layers of multi-hit and single-hit blocks, with power-up
blocks interspersed within. This level will have a boss enemy as well that is a baby cupid sliding
back and forth across the screen. If the ball hits the enemy, the player loses a life.

    0000000000000000
    0302220000222030
    0021112002111200
    0232221221222320
    0212121112112120
    0023211331123200
    0002121111212000
    0000212332120000
    0300023223200030
    0000002112000000
    0000000220000000
    0000000000000000

This is the filled-in heart with the multi-hit blocks on the outside, and the single-hit blocks
on the inside. The 3s surrounding the heart are the starting power-ups. The cupid boss enemy will
go back and forth where that last row of zeroes are.

## Class Ideas

#### Paddle
This class would be used to create the paddle object. Since one of the power ups makes the
paddle grow in length, the paddle class would need to have an expand function to widen the
paddle.

#### Ball
This class would be used to create the main ball for the game as well as additional balls
needed for power-ups. Since the ball's main functionality is to bounce, we would need a
function to determine how the ball's motion should change when it hits a block or paddle.

#### Bricks
This class would be used for the single and multi-hit blocks in the game. I think these two
types of blocks can share a class, as a multi-hit block is just a single hit block with more
lives. Once a multi-hit block is down to one life, it can become a single-hit block. Accordingly,
we will need a function that decrements a block's life when the ball collides with it.

#### Game Controller
This class would be used to switch between levels of the game. It will need a function that takes
the current level as input and creates a new scene according to the layout of that level. Then it
should return that scene to the main driver class.

#### Powerup
This class would be for the power-up objects. Since the power-up released from each power-up
block is chosen at random, the power-up class will need a random type generator function that
selects one of the three types of power-ups.