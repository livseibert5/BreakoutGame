package breakout;

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

public class Main extends Application {

  private static final int WIDTH = 700;
  private static final int HEIGHT = 700;
  private static final String TITLE = "Valentine's Breakout";
  private static final int FRAMES_PER_SECOND = 60;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private static final Paint BACKGROUND = Color.WHITE;

  private boolean gameStarted = false;

  private GameController controller;
  private Instructions instructions;
  private Scene myScene;
  private Paddle paddle;
  private Stage stage;
  private Ball ball;
  private Group root;
  private List<Brick> bricks;
  private int level = 1;
  private int lives = 3;

  /**
   * Initialize what will be displayed and how it will be updated.
   */
  @Override
  public void start(Stage stage) throws Exception {
    controller = new GameController();
    instructions = new Instructions(WIDTH, HEIGHT, TITLE);
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

  // Change properties of shapes in small ways to animate them over time.
  private void step(double elapsedTime) {
    ball.setCenterX(ball.getCenterX() + ball.getXDirection() * ball.getSpeed() * elapsedTime);
    ball.setCenterY(ball.getCenterY() + ball.getYDirection() * ball.getSpeed() * elapsedTime);
    paddle.setX(paddle.getX() + paddle.getXDirection() * paddle.getSpeed() * elapsedTime);
    
    if (ball.getCenterX() + ball.getRadius() >= HEIGHT) {
      lives--;
    }
    checkPaddleCollision();
    checkWallCollision();
    checkBrickCollision();
  }

  private void checkPaddleCollision() {
    if (ball.getBoundsInParent().intersects(paddle.getBoundsInParent())) {
      ball.invertYDirection();
    }
  }

  private void checkWallCollision() {
    if (ball.getCenterX() <= ball.getRadius() ||
        ball.getCenterX() >= WIDTH - ball.getRadius()) {
      ball.invertXDirection();
    }
    if (ball.getCenterY() <= ball.getRadius() ||
        ball.getCenterY() >= HEIGHT - ball.getRadius()) {
      ball.invertYDirection();
    }
  }

  private void checkBrickCollision() {
    for (Brick brick : bricks) {
      if (collidesWithTop(brick) || collidesWithBottom(brick)) {
        ball.invertYDirection();
        brick.decrementLives();
      } else if (collidesWithRight(brick) || collidesWithLeft(brick)) {
        ball.invertXDirection();
        brick.decrementLives();
      }

      if (brick.getLives() <= 0) {
        brick.setWidth(0);
        brick.setHeight(0);
        root.getChildren().remove(brick);
      }
    }
  }

  private boolean collidesWithTop(Brick brick) {
    if (ball.getCenterY() + ball.getRadius() >= brick.getY() &&
        ball.getCenterY() + ball.getRadius() <= brick.getY() + (1 * brick.getHeight() / 4) &&
        ((ball.getCenterX() + ball.getRadius() >= brick.getX() &&
            ball.getCenterX() + ball.getRadius() <= brick.getX() + brick.getWidth()) ||
            (ball.getCenterX() - ball.getRadius() >= brick.getX() &&
                ball.getCenterX() - ball.getRadius() <= brick.getX() + brick.getWidth()))) {
      return true;
    }
    return false;
  }

  private boolean collidesWithBottom(Brick brick) {
    if (ball.getCenterY() - ball.getRadius() <= brick.getY() + brick.getHeight() &&
        ball.getCenterY() - ball.getRadius() >= brick.getY() + (3 * brick.getHeight() / 4) &&
        ((ball.getCenterX() + ball.getRadius() >= brick.getX() &&
            ball.getCenterX() + ball.getRadius() <= brick.getX() + brick.getWidth()) ||
            (ball.getCenterX() - ball.getRadius() >= brick.getX() &&
                ball.getCenterX() - ball.getRadius() <= brick.getX() + brick.getWidth()))) {
      return true;
    }
    return false;
  }

  private boolean collidesWithRight(Brick brick) {
    if (ball.getCenterX() - ball.getRadius() <= brick.getX() + brick.getWidth() &&
        ball.getCenterX() - ball.getRadius() >= brick.getX() + (3 * brick.getWidth() / 4) &&
        ((ball.getCenterY() + ball.getRadius() >= brick.getY() &&
            ball.getCenterY() + ball.getRadius() <= brick.getY() + brick.getHeight()) ||
            (ball.getCenterY() - ball.getRadius() >= brick.getY() &&
                ball.getCenterY() - ball.getRadius() <= brick.getY() + brick.getHeight()))) {
      return true;
    }
    return false;
  }

  private boolean collidesWithLeft(Brick brick) {
    if (ball.getCenterX() + ball.getRadius() >= brick.getX() &&
        ball.getCenterX() + ball.getRadius() <= brick.getX() + (3 * brick.getWidth() / 4) &&
        ((ball.getCenterY() + ball.getRadius() >= brick.getY() &&
            ball.getCenterY() + ball.getRadius() <= brick.getY() + brick.getHeight()) ||
            (ball.getCenterY() - ball.getRadius() >= brick.getY() &&
                ball.getCenterY() - ball.getRadius() <= brick.getY() + brick.getHeight()))) {
      return true;
    }
    return false;
  }

  private void handleKeyLift(KeyCode code) {
    if (code == KeyCode.RIGHT || code == KeyCode.LEFT) {
      paddle.stop();
    }
  }

  // What to do each time a key is pressed.
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

  public void setLevel(int level) {
    controller.setupGame(level, WIDTH, HEIGHT, BACKGROUND);
    myScene = controller.getScene();
    retrieveGamePieces();
    stage.setScene(myScene);
  }

  public void retrieveGamePieces() {
    this.paddle = controller.getPaddle();
    this.ball = controller.getBall();
    this.bricks = controller.getBricks();
    this.root = controller.getRoot();
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
