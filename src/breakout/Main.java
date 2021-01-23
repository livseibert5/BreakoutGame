package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

  private static final int WIDTH = 600;
  private static final int HEIGHT = 800;
  private static final String TITLE = "Valentine's Breakout";
  private static final int FRAMES_PER_SECOND = 60;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private static final Paint BACKGROUND = Color.BEIGE;

  private boolean gameStarted = false;

  private GameController controller;
  private Instructions instructions;
  private Scene myScene;
  private Paddle paddle;
  private Ball ball;
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
/*
  // Create the game's "scene": what shapes will be in the game and their starting properties.
  private void setupGame(int level) {

    paddle = new Paddle();
  }*/

  // Change properties of shapes in small ways to animate them over time.
  private void step (double elapsedTime) {
    //paddle.setX(paddle.getX() + paddle.getXDirection() * paddle.getSpeed() * elapsedTime);
  }

  // What to do each time a key is pressed.
  private void handleKeyInput(KeyCode code) {
    if (code == KeyCode.RIGHT) {
      // Move paddle right.
      if (paddle.getXDirection() != 1) paddle.switchDirection();
    } else if (code == KeyCode.LEFT) {
      // Move paddle left.
      if (paddle.getXDirection() != -1) paddle.switchDirection();
    }
  }

  /**
   * Start the program.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
