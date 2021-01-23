package breakout;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;

public class Main extends Application {

  private GameController controller;

  /**
   * Initialize what will be displayed and how it will be updated.
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    // TODO: Show menu screen and instructions.
    // TODO: Launch step function.
  }

  // Change properties of shapes in small ways to animate them over time.
  private void step (double elapsedTime) {
    // TODO: Call game controller to set the level up and commence game play.
  }

  // What to do each time a key is pressed.
  private void handleKeyInput(KeyCode code) {

  }

  /**
   * Start the program.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
