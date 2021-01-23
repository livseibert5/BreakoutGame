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

#### Ball Sticks to Paddle Randomly
The ball will not always stick to the paddle, but it will at random intervals.
When this happens, the player will need to hit the up arrow to re-launch the ball.

## Block Ideas

#### Single-Hit Blocks
This block will break the first time it is hit by the ball.

#### Multi-Hit Blocks
This block will take between 1 and 5 hits by the ball to break. The block will
display the number of hits it has left before it breaks, decrementing the number
every time the ball hits it.

#### Power-Up Blocks
These blocks will release power-ups, but they type of power-up they release will
be chosen at random.

## Power-up Ideas
My game will have three types of power ups, and they will be released by breaking
certain blocks.

#### Extra Balls
Another ball will be introduced to the game. The player will not die if one of the
balls drops, only if both of them are missed by the paddle.

#### Ball Speeds Up
This power-up will make the game harder for the player, as the ball will start
moving faster and therefore be harder to deflect with the paddle.

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
This class would be used to oversee the main functionality of the entire game. Accordingly, it
would need to have a function to detect collisions between the ball and the brick.

#### Powerup
This class would be for the power-up objects. Since the power-up released from each power-up
block is chosen at random, the power-up class will need a random type generator function that
selects one of the three types of power-ups.