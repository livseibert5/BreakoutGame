package breakout;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

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

  private GameController controller;
  private Scene myScene;
  private Paddle paddle;
  private Stage stage;
  private Ball ball;
  private Group root;
  private List<Brick> bricks;
  private List<Circle> livesList;
  private Text scoreLabel;
  private Text levelLabel;
  private int removedBricks = 0;
  private int level = 1;
  private int lives = 3;
  private int score = 0;

  /**
   * Display instruction screen, then switch to first level and
   * start running the step function.
   */
  @Override
  public void start(Stage stage) throws Exception {
    controller = new GameController();
    Screen instructions = new Screen(Type.INSTRUCTIONS, WIDTH, HEIGHT, TITLE);
    myScene = instructions.getScene();
    this.stage = stage;
    stage.setScene(myScene);
    stage.setTitle(TITLE);
    stage.show();
    myScene.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        setLevel(level);
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), event -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
      }
    });
  }

  /**
   * Change properties of shapes in small ways to animate them over time.
   * Handles collisions between the ball and the paddle, the ball and the
   * bricks, and the ball and the walls so that the ball bounces.
   * @param elapsedTime time since beginning of game
   */
  private void step(double elapsedTime) {
    ball.setCenterX(ball.getCenterX() + ball.getXDirection() * ball.getSpeed() * elapsedTime);
    ball.setCenterY(ball.getCenterY() + ball.getYDirection() * ball.getSpeed() * elapsedTime);
    paddle.setX(paddle.getX() + paddle.getXDirection() * paddle.getSpeed() * elapsedTime);

    if (bricks.size() == removedBricks) handleWin();
    checkPaddleCollision();
    checkWallCollision();
    checkBrickCollision();
  }

  public void handleWin() {
    level++;

    if (level > 3) {
      Screen win = new Screen(Type.WIN, WIDTH, HEIGHT, TITLE);
      myScene = win.getScene();
    } else {
      levelLabel.setText("Level: " + String.valueOf(level));
      setLevel(level);
    }
  }

  public void handleLoss() {
    Screen loss = new Screen(Type.LOSS, WIDTH, HEIGHT, TITLE);
    myScene = loss.getScene();
  }

  /**
   * Detects collisions between the ball and the paddle,
   * inverts the y direction of the ball if detected.
   */
  private void checkPaddleCollision() {
    if (ball.getBoundsInParent().intersects(paddle.getBoundsInParent())) {
      ball.invertYDirection();
    }
  }

  /**
   * Determines if the ball hits the left or right wall
   * or the top or bottom wall. If the ball hits the top
   * or wall, the y direction is inverted. If the
   * ball hits the left or right wall, the x direction is
   * inverted. If the ball hits the bottom wall, a life is lost.
   */
  private void checkWallCollision() {
    if (ball.getCenterX() <= ball.getRadius() ||
        ball.getCenterX() >= WIDTH - ball.getRadius()) {
      ball.invertXDirection();
    }
    if (ball.getCenterY() <= ball.getRadius() + 80) {
      ball.invertYDirection();
    }
    if (ball.getCenterY() >= HEIGHT - ball.getRadius()) {
      lives--;
      if (lives > 0) {
        ball.setCenterX(WIDTH / 2);
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
  }

  /**
   * Determines if the ball collides with a brick.
   * If it hits the top or bottom of the brick, the y
   * direction is inverted. If it hits the left or right
   * of the brick, the x direction is inverted. Each
   * time the brick is hit, the brick loses a life. If
   * the brick's lives hit zero, it is removed from the game.
   */
  private void checkBrickCollision() {
    for (Brick brick : bricks) {
      if (collidesWithTop(brick) || collidesWithBottom(brick)) {
        ball.invertYDirection();
        handleBrickCollision(brick);
      } else if (collidesWithRight(brick) || collidesWithLeft(brick)) {
        ball.invertXDirection();
        handleBrickCollision(brick);
      }
    }
  }

  public void handleBrickCollision(Brick brick) {
    brick.decrementLives();
    if (brick.getLives() <= 0) {
      score += 50;
      scoreLabel.setText("Score: " + String.valueOf(score));
      brick.setWidth(0);
      brick.setHeight(0);
      removedBricks++;
      root.getChildren().remove(brick);
    }
  }

  /**
   * Detects collisions between ball and top of brick.
   * @param brick Brick object to check collision with
   * @return boolean true if ball collided with top of brick
   */
  private boolean collidesWithTop(Brick brick) {
    return ball.getCenterY() + ball.getRadius() >= brick.getY() &&
        ball.getCenterY() + ball.getRadius() <= brick.getY() + (1 * brick.getHeight() / 6) &&
        ((ball.getCenterX() + ball.getRadius() >= brick.getX() &&
            ball.getCenterX() + ball.getRadius() <= brick.getX() + brick.getWidth()) ||
            (ball.getCenterX() - ball.getRadius() >= brick.getX() &&
                ball.getCenterX() - ball.getRadius() <= brick.getX() + brick.getWidth()));
  }

  /**
   * Detects collisions between ball and bottom of brick.
   * @param brick Brick object to detect collisions with
   * @return boolean true if ball collided with bottom of brick
   */
  private boolean collidesWithBottom(Brick brick) {
    return ball.getCenterY() - ball.getRadius() <= brick.getY() + brick.getHeight() &&
        ball.getCenterY() - ball.getRadius() >= brick.getY() + (5 * brick.getHeight() / 6) &&
        ((ball.getCenterX() + ball.getRadius() >= brick.getX() &&
            ball.getCenterX() + ball.getRadius() <= brick.getX() + brick.getWidth()) ||
            (ball.getCenterX() - ball.getRadius() >= brick.getX() &&
                ball.getCenterX() - ball.getRadius() <= brick.getX() + brick.getWidth()));
  }

  /**
   * Detects collisions between ball and right side of brick.
   * @param brick Brick object to detect collisions with
   * @return boolean true if ball collided with right side of brick
   */
  private boolean collidesWithRight(Brick brick) {
    return ball.getCenterX() - ball.getRadius() <= brick.getX() + brick.getWidth() &&
        ball.getCenterX() - ball.getRadius() >= brick.getX() + (5 * brick.getWidth() / 6) &&
        ((ball.getCenterY() + ball.getRadius() >= brick.getY() &&
            ball.getCenterY() + ball.getRadius() <= brick.getY() + brick.getHeight()) ||
            (ball.getCenterY() - ball.getRadius() >= brick.getY() &&
                ball.getCenterY() - ball.getRadius() <= brick.getY() + brick.getHeight()));
  }

  /**
   * Detects collisions between ball and left side of brick.
   * @param brick Brick object to detect collisions with
   * @return boolean true if ball collided with left side of brick
   */
  private boolean collidesWithLeft(Brick brick) {
    return ball.getCenterX() + ball.getRadius() >= brick.getX() &&
        ball.getCenterX() + ball.getRadius() <= brick.getX() + (1 * brick.getWidth() / 6) &&
        ((ball.getCenterY() + ball.getRadius() >= brick.getY() &&
            ball.getCenterY() + ball.getRadius() <= brick.getY() + brick.getHeight()) ||
            (ball.getCenterY() - ball.getRadius() >= brick.getY() &&
                ball.getCenterY() - ball.getRadius() <= brick.getY() + brick.getHeight()));
  }

  /**
   * Determines behavior when a key is lifted.
   * @param code key input
   */
  private void handleKeyLift(KeyCode code) {
    if (code == KeyCode.RIGHT || code == KeyCode.LEFT) {
      paddle.stop();
    }
  }

  /**
   * Determines behavior when a key is pressed.
   * @param code key input
   */
  private void handleKeyInput(KeyCode code) {
    if (code == KeyCode.RIGHT) {
      if (paddle.getXDirection() != 1) {
        paddle.moveRight();
      }
    } else if (code == KeyCode.LEFT) {
      if (paddle.getXDirection() != -1) {
        paddle.moveLeft();
      }
    } else if (code == KeyCode.DIGIT1) {
      setLevel(1);
    } else if (code == KeyCode.DIGIT2) {
      setLevel(2);
    } else if (code == KeyCode.DIGIT3) {
      setLevel(3);
    } else if (code == KeyCode.L) {
      lives++;
    }
  }

  /**
   * Creates a new scene for the level given.
   * @param level level to be played next
   */
  public void setLevel(int level) {
    controller.setupGame(level, WIDTH, HEIGHT, BACKGROUND);
    myScene = controller.getScene();
    retrieveGamePieces();
    stage.setScene(myScene);
  }

  /**
   * Gets the paddle, ball, bricks, and root from
   * GameController and makes them class variables in
   * Main so that they can be easily used and updated.
   */
  public void retrieveGamePieces() {
    this.paddle = controller.getPaddle();
    this.ball = controller.getBall();
    this.bricks = controller.getBricks();
    this.root = controller.getRoot();
    this.livesList = controller.getLives();
    this.scoreLabel = controller.getScore();
    this.levelLabel = controller.getLevel();
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
