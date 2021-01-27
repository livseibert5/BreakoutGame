package breakout;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Main driver of the game, handles all game behavior.
 */
public class Main extends Application {

  private static final int WIDTH = 700;
  private static final int HEIGHT = 700;
  private static final String TITLE = "Valentine's Breakout";
  private static final int FRAMES_PER_SECOND = 60;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private static final Paint BACKGROUND = Color.WHITE;
  private static final int POWERUP_SPEED = 60;

  private GameController controller;
  private Scene myScene;
  private Paddle paddle;
  private Stage stage;
  private Boss boss;
  private Group root;
  private List<Brick> bricks;
  private List<Circle> livesList;
  private List<Ball> balls = new ArrayList<>();
  private List<Powerup> powerups = new ArrayList<>();
  private Text scoreLabel;

  private int level = 1;
  private int lives = 3;
  private int score = 0;
  private boolean gamePlay = false;
  private double time = 0;
  private double powerupStart = 0;

  /**
   * Display instruction screen, then switch to first level and start running the step function.
   */
  @Override
  public void start(Stage stage) {
    controller = new GameController();
    Screen instructions = new Screen(Type.INSTRUCTIONS, WIDTH, HEIGHT, TITLE);
    myScene = instructions.getScene();
    this.stage = stage;
    stage.setScene(myScene);
    stage.setTitle(TITLE);
    stage.show();
    myScene.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
      setLevel(level);
      gamePlay = true;
      KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), event -> step(SECOND_DELAY));
      Timeline animation = new Timeline();
      animation.setCycleCount(Timeline.INDEFINITE);
      animation.getKeyFrames().add(frame);
      animation.play();
    });
  }

  /**
   * Updates location of game pieces to animate them over time. Handles the core functionality of
   * the game, including detecting and handling collisions, timing power-ups, and determining if a
   * level has been cleared.
   *
   * @param elapsedTime time increment since step function was last called
   */
  private void step(double elapsedTime) {
    if (gamePlay) {
      time += 1;
      updateBalls(elapsedTime);
      updatePaddle(elapsedTime);

      if (level == 3) {
        boss.setX(boss.getX() + boss.getXDirection() * boss.getSpeed() * elapsedTime);
        if (boss.getX() < 0 || boss.getX() >= WIDTH - boss.getWidth()) {
          boss.invertXDirection();
        }
      }

      if (bricks.isEmpty()) {
        handleWin();
      }
      checkPowerUps(elapsedTime);
    }
  }

  /**
   * Updates the location of the paddle to move it over time. Wraps the paddle to the other side of
   * the screen when it goes out of bounds.
   *
   * @param elapsedTime time increment since last paddle location update
   */
  private void updatePaddle(double elapsedTime) {
    paddle.setX(paddle.getX() + paddle.getXDirection() * paddle.getSpeed() * elapsedTime);
    if (paddle.getX() + paddle.getWidth() / 2 >= WIDTH) {
      paddle.setX(0);
    } else if (paddle.getX() + paddle.getWidth() / 2 <= 0) {
      paddle.setX(WIDTH - paddle.getWidth());
    }
  }

  /**
   * Updates the location of the balls to move them over time and checks for collisions.
   *
   * @param elapsedTime time increment since last ball update
   */
  private void updateBalls(double elapsedTime) {
    balls.forEach(
        ball -> {
          ball.setCenterX(
              ball.getCenterX() + ball.getXDirection() * ball.getSpeed() * elapsedTime);
          ball.setCenterY(
              ball.getCenterY() + ball.getYDirection() * ball.getSpeed() * elapsedTime);
          if (level == 3) {
            checkBossCollision(ball);
          }
          checkPaddleCollision(ball);
          checkWallCollision(ball);
          checkBrickCollision(ball);
        }
    );
    balls.removeIf(ball -> !ball.getIsActive());
  }

  /**
   * Decrements player's number of lives if a ball collides with the boss enemy.
   *
   * @param ball ball involved in potential collision
   */
  private void checkBossCollision(Ball ball) {
    if (collidesWithTop(boss, ball) || collidesWithBottom(boss, ball)) {
      ball.invertYDirection();
      decrementLives(ball);
    } else if (collidesWithRight(boss, ball)) {
      ball.invertXDirection();
      decrementLives(ball);
    }
  }

  /**
   * Moves power-ups down the screen and detects if they hit the paddle. Removes power-ups when
   * their time is up.
   *
   * @param elapsedTime time since last power-up check
   */
  private void checkPowerUps(double elapsedTime) {
    if (time - powerupStart >= 750) {
      removePowerUps();
    }
    powerups.forEach(
        powerup -> {
          powerup.setCenterY(powerup.getCenterY() + POWERUP_SPEED * elapsedTime);
          if (powerup.getBoundsInParent().intersects(paddle.getBoundsInParent())) {
            root.getChildren().remove(powerup);
            powerup.setUsed();
            setPowerUp(powerup);
          } else if (powerup.getCenterY() < paddle.getY() + paddle.getHeight()) {
            powerup.setUsed();
          }
        });
    powerups.removeIf(powerup -> powerup.getUsed());
  }

  /**
   * Called when the ball drops below the paddle or the ball hits the boss enemy in level 3. Only
   * called for ball dropping when there is one active ball. Decrements the lives of the player and
   * resets the ball if the player has more lives.
   *
   * @param ball ball to be respawned if all balls have fallen offscreen
   */
  private void decrementLives(Ball ball) {
    lives--;
    if (lives > 0) {
      ball.setCenterX(WIDTH / 2.0);
      ball.setCenterY((HEIGHT - paddle.getHeight() - 50)
          - paddle.getHeight() / 2 - ball.getRadius() / 2);
    } else {
      handleLoss();
    }
    if (!livesList.isEmpty()) {
      root.getChildren().remove(livesList.get(livesList.size() - 1));
      livesList.remove(livesList.size() - 1);
    }
  }

  /**
   * Called when the player destroys all the blocks for the level. Increments the level or shows the
   * final win screen if applicable
   */
  private void handleWin() {
    level++;
    if (level > 3) {
      gamePlay = false;
      root.getChildren().removeAll();
      Screen win = new Screen(Type.WIN, WIDTH, HEIGHT, TITLE);
      myScene = win.getScene();
      stage.setScene(myScene);
    } else {
      setLevel(level);
    }
  }

  /**
   * Called when the player's number of lives hits zero. Removes all the components from the screen
   * and displays the loss message.
   */
  private void handleLoss() {
    gamePlay = false;
    root.getChildren().removeAll();
    Screen loss = new Screen(Type.LOSS, WIDTH, HEIGHT, TITLE);
    myScene = loss.getScene();
    stage.setScene(myScene);
  }

  /**
   * Detects collisions between the ball and the paddle.
   */
  private void checkPaddleCollision(Ball ball) {
    if (intersectsPaddleThird(1, ball) || intersectsPaddleThird(3, ball)) {
      ball.invertYDirection();
      ball.invertXDirection();
    } else if (intersectsPaddleThird(2, ball)) {
      ball.invertYDirection();
    }
  }

  /**
   * Given the third of the paddle that we're checking for collisions, the function determines the
   * left and right bound of that third and checks if it has been hit.
   *
   * @param third which third of the paddle we're detecting
   * @param ball  ball involved in collision
   * @return boolean true if ball has collided with indicated third of paddle
   */
  private boolean intersectsPaddleThird(int third, Ball ball) {
    double left = paddle.getX();
    double right = paddle.getX() + paddle.getWidth();
    if (third == 1) {
      right = paddle.getX() + (1 / 3.0) * paddle.getWidth();
    } else if (third == 2) {
      left = paddle.getX() + (1 / 3.0) * paddle.getWidth();
      right = paddle.getX() + (2 / 3.0) * paddle.getWidth();
    } else if (third == 3) {
      left = paddle.getX() + (2 / 3.0) * paddle.getWidth();
    }
    return checkPaddleThirds(left, right, ball);
  }

  /**
   * Given the bounds of the third of the paddle, detects if a collision has occurred within those
   * thirds.
   *
   * @param left  left bound of third
   * @param right right bound of third
   * @param ball  ball involved in collision
   * @return boolean true if ball collided with paddle in indicated third
   */
  private boolean checkPaddleThirds(double left, double right, Ball ball) {
    return ball.getBottom() >= paddle.getY() &&
        ((ball.getRight() >= left &&
            ball.getRight() <= right) ||
            (ball.getLeft() >= left &&
                ball.getLeft() <= right));
  }

  /**
   * Determines if the ball hits a wall. If the ball hits the top wall, the y direction is inverted.
   * If the ball hits the left or right wall, the x direction is inverted. If the ball hits the
   * bottom wall, a life is lost.
   */
  private void checkWallCollision(Ball ball) {
    if (ball.getLeft() <= 0 || ball.getRight() >= WIDTH) {
      ball.invertXDirection();
    }
    if (ball.getTop() <= 75) {
      ball.invertYDirection();
    }
    if (ball.getBottom() >= HEIGHT && balls.size() == 1) {
      decrementLives(ball);
    } else if (ball.getBottom() >= HEIGHT) {
      root.getChildren().remove(ball);
      ball.setInactive();
    }
  }

  /**
   * Determines if the ball collides with a brick and alters ball motion accordingly.
   */
  private void checkBrickCollision(Ball ball) {
    for (Brick brick : bricks) {
      if (collidesWithTop(brick, ball) || collidesWithBottom(brick, ball)) {
        ball.invertYDirection();
        handleBrickCollision(brick);
        return;
      } else if (collidesWithRight(brick, ball) || collidesWithLeft(brick, ball)) {
        ball.invertXDirection();
        handleBrickCollision(brick);
        return;
      }
    }
  }

  /**
   * If the ball hit the brick, this function decrements the lives of the brick. If the brick is
   * dead, it increments the score of the game and removes the brick from the scene. If the brick is
   * a power-up brick, it applies the power-up.
   *
   * @param brick Brick that is collided with
   */
  private void handleBrickCollision(Brick brick) {
    brick.decrementLives();
    if (brick.getLives() <= 0) {
      score += 50;
      scoreLabel.setText("Score: " + score);
      bricks.remove(brick);
      root.getChildren().remove(brick);
      if (brick instanceof PowerupBrick) {
        dropPowerUp((PowerupBrick) brick);
      }
    }
  }

  /**
   * Called when power-up brick is broken, creates new power-up that can be caught by the paddle.
   *
   * @param brick power-up brick that was broken
   */
  private void dropPowerUp(PowerupBrick brick) {
    Powerup powerup = new Powerup(brick.getType());
    powerup.setCenterX(brick.getX() + brick.getWidth() / 2);
    powerup.setCenterY(brick.getY() + brick.getHeight() / 2);
    powerup.setRadius(15);
    powerups.add(powerup);
    root.getChildren().add(powerup);
  }

  /**
   * Called when a power-up brick is broken. Applies the power-up to the game.
   *
   * @param powerup powerup token to be enacted
   */
  private void setPowerUp(Powerup powerup) {
    powerupStart = time;
    if (powerup.getType() == Power.FAST) {
      balls.forEach(ball -> ball.setSpeed(160));
    } else if (powerup.getType() == Power.EXTRA) {
      generateNewBall();
    } else if (powerup.getType() == Power.LONGER) {
      paddle.expand();
    }
  }

  /**
   * Generates a new ball for the game, called when a new ball power-up is released.
   */
  private void generateNewBall() {
    controller.createBall();
    balls.add(controller.getBall());
  }

  /**
   * Reverts the game back to normal when the power-up is complete.
   */
  private void removePowerUps() {
    if (!balls.isEmpty() && balls.get(0).getSpeed() == 160) {
      balls.forEach(ball -> ball.setSpeed(120));
    } else if (paddle.getWidth() > WIDTH / 6.0) {
      paddle.setWidth(WIDTH / 6.0);
    }
  }

  /**
   * Detects collisions between ball and top of brick.
   *
   * @param brick Brick object to check collision with
   * @return boolean true if ball collided with top of brick
   */
  private boolean collidesWithTop(Brick brick, Ball ball) {
    return ball.getBottom() >= brick.getY() &&
        ball.getTop() < brick.getY() &&
        inXRange(brick, ball);
  }

  /**
   * Detects collisions between ball and bottom of brick.
   *
   * @param brick Brick object to detect collisions with
   * @return boolean true if ball collided with bottom of brick
   */
  private boolean collidesWithBottom(Brick brick, Ball ball) {
    return ball.getTop() <= brick.getBottom() &&
        ball.getBottom() > brick.getBottom() &&
        inXRange(brick, ball);
  }

  /**
   * Detects collisions between ball and right side of brick.
   *
   * @param brick Brick object to detect collisions with
   * @return boolean true if ball collided with right side of brick
   */
  private boolean collidesWithRight(Brick brick, Ball ball) {
    return ball.getLeft() <= brick.getRight() &&
        ball.getRight() > brick.getRight() &&
        inYRange(brick, ball);
  }

  /**
   * Detects collisions between ball and left side of brick.
   *
   * @param brick Brick object to detect collisions with
   * @return boolean true if ball collided with left side of brick
   */
  private boolean collidesWithLeft(Brick brick, Ball ball) {
    return ball.getRight() >= brick.getX() &&
        ball.getLeft() < brick.getX() &&
        inYRange(brick, ball);
  }

  /**
   * Detects if the ball is within the horizontal range of the brick.
   *
   * @param brick Brick object that serves as reference range
   * @param ball  ball we're checking for collision with brick
   * @return boolean true if ball is within same x range as brick
   */
  private boolean inXRange(Brick brick, Ball ball) {
    return ball.getCenterX() > brick.getX() && ball.getCenterX() < brick.getRight();
  }

  /**
   * Detects if the ball is within the vertical range of the brick.
   *
   * @param brick Brick object that serves as reference range
   * @param ball  ball we're checking for collision with brick
   * @return boolean true if ball is within same y range as brick
   */
  private boolean inYRange(Brick brick, Ball ball) {
    return ball.getCenterY() >= brick.getY() && ball.getCenterY() <= brick.getBottom();
  }

  /**
   * Determines behavior when a key is lifted.
   *
   * @param code key input
   */
  private void handleKeyLift(KeyCode code) {
    if (code == KeyCode.RIGHT || code == KeyCode.LEFT) {
      paddle.stop();
    }
  }

  /**
   * Determines behavior when a key is pressed.
   *
   * @param code key input
   */
  private void handleKeyInput(KeyCode code) {
    if (code == KeyCode.RIGHT) {
      paddle.moveRight();
    } else if (code == KeyCode.LEFT) {
      paddle.moveLeft();
    } else if (code == KeyCode.DIGIT1) {
      level = 1;
      setLevel(1);
    } else if (code == KeyCode.DIGIT2) {
      level = 2;
      setLevel(2);
    } else if (code == KeyCode.DIGIT3 || code == KeyCode.DIGIT4 ||
        code == KeyCode.DIGIT5 || code == KeyCode.DIGIT6 ||
        code == KeyCode.DIGIT7 || code == KeyCode.DIGIT8 || code == KeyCode.DIGIT9) {
      level = 3;
      setLevel(3);
    } else if (code == KeyCode.L) {
      incrementLives();
    } else if (code == KeyCode.R) {
      setLevel(level);
    } else if (code == KeyCode.N && level != 3) {
      level++;
      setLevel(level);
    } else if (code == KeyCode.P) {
      createRandomPowerup();
    } else if (code == KeyCode.B && level == 3) {
      root.getChildren().remove(boss);
    }
  }

  /**
   * Creates a random power-up, called when user presses the 'P' cheat key.
   */
  private void createRandomPowerup() {
    PowerupBrick random = new PowerupBrick(WIDTH, HEIGHT);
    random.setX(WIDTH / 2);
    random.setY(HEIGHT / 2);
    dropPowerUp(random);
  }

  /**
   * Adds a new life to the screen when the 'L' cheat code is used.
   */
  private void incrementLives() {
    lives++;
    Circle life = new Circle((livesList.size() + 1) * 30, 40, 20,
        new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("rose.png"))));
    livesList.add(life);
    root.getChildren().add(life);
  }

  /**
   * Creates a new scene for the level given.
   *
   * @param level level to be played next
   */
  private void setLevel(int level) {
    balls = new ArrayList<>();
    lives = 3;
    controller.setupGame(level, WIDTH, HEIGHT, BACKGROUND);
    myScene = controller.getScene();
    retrieveGamePieces();
    stage.setScene(myScene);
  }

  /**
   * Gets the paddle, ball, bricks, and root from GameController and makes them class variables in
   * Main so that they can be easily used and updated.
   */
  private void retrieveGamePieces() {
    this.paddle = controller.getPaddle();
    Ball ball = controller.getBall();
    this.bricks = controller.getBricks();
    this.root = controller.getRoot();
    this.livesList = controller.getLivesLabel();
    this.scoreLabel = controller.getScoreLabel();
    balls.add(ball);
    if (level == 3) {
      boss = controller.getBoss();
    }
    myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
    myScene.setOnKeyReleased(e -> handleKeyLift(e.getCode()));
  }

  /**
   * Start the program.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
