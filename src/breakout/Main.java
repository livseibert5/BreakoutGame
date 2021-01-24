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
  private Ball ball;
  private List<Brick> bricks;
  private int level = 1;

  /**
   * Initialize what will be displayed and how it will be updated.
   */
  @Override
  public void start(Stage stage) throws Exception {
    controller = new GameController();
    instructions = new Instructions(WIDTH, HEIGHT, TITLE);
    myScene = instructions.getScene();
    stage.setScene(myScene);
    stage.setTitle(TITLE);
    stage.show();
    myScene.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        controller.setupGame(level, WIDTH, HEIGHT, BACKGROUND);
        retrieveGamePieces();
        myScene = controller.getScene();
        stage.setScene(myScene);
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), event -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
      }
    });
  }

  // Change properties of shapes in small ways to animate them over time.
  private void step (double elapsedTime) {
    ball.setCenterX(ball.getCenterX() + ball.getXDirection() * ball.getSpeed() * elapsedTime);
    ball.setCenterY(ball.getCenterY() + ball.getYDirection() * ball.getSpeed() * elapsedTime);
    if (ball.getCenterX() <= ball.getRadius() ||
        ball.getCenterX() >= WIDTH - ball.getRadius()) {
      ball.invertXDirection();
    }
    if (ball.getCenterY() <= ball.getRadius() ||
        ball.getCenterY() >= HEIGHT - ball.getRadius()) {
      ball.invertYDirection();
    }
    //paddle.setX(paddle.getX() + paddle.getXDirection() * paddle.getSpeed() * elapsedTime);
  }

  // What to do each time a key is pressed.
  private void handleKeyInput(KeyCode code, double elapsedTime) {
    if (code == KeyCode.RIGHT) {
      if (paddle.getXDirection() != 1) { paddle.switchDirection(); }
      paddle.setX(paddle.getX() + paddle.getXDirection() * paddle.getSpeed() * elapsedTime);
    } else if (code == KeyCode.LEFT) {
      if (paddle.getXDirection() != -1) paddle.switchDirection();
    } else if (code == KeyCode.DIGIT1) {
      controller.setupGame(1, WIDTH, HEIGHT, BACKGROUND);
    } else if (code == KeyCode.DIGIT2) {
      controller.setupGame(2, WIDTH, HEIGHT, BACKGROUND);
    } else if (code == KeyCode.DIGIT3) {
      controller.setupGame(3, WIDTH, HEIGHT, BACKGROUND);
    }
  }

  public void retrieveGamePieces() {
    this.paddle = controller.getPaddle();
    this.ball = controller.getBall();
    this.bricks = controller.getBricks();
  }

  /**
   * Start the program.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
