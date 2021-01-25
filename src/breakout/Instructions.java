package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Creates the instructions scene for the game.
 */
public class Instructions {

  private static final Paint BACKGROUND = Color.PINK;

  private Scene myScene;
  private String directionText = "Welcome to Valentine's Breakout!\n"
      + "Your goal is to clear all the blocks from each level.\n"
      + "You have three lives to complete this goal, be sure to collect power-ups as well.\n"
      + "Be sure to watch out for baby cupid, good luck!";
  private Text titleText;
  private Text directions;
  private Text play;

  /**
   * Constructor for an Instructions object. Takes the
   * width and height of the game screen, as well as the title.
   * Creates a scene to display the title and instructions.
   * @param width width of the game screen
   * @param height height of the game screen
   * @param title title of the game
   */
  public Instructions(int width, int height, String title) {
    Group root = new Group();
    createText(width, height, title);
    root.getChildren().add(titleText);
    root.getChildren().add(directions);
    root.getChildren().add(play);
    myScene = new Scene(root, width, height, BACKGROUND);
  }

  /**
   * Creates the text for the screen, determines the location
   * of the text based on the width and height of the screen.
   * @param width width of the game screen
   * @param height height of the game screen
   * @param title title of the game
   */
  public void createText(int width, int height, String title) {
    titleText = new Text(width / 3, height / 4, title);
    directions = new Text(5, height / 3, directionText);
    play = new Text(width / 3 + 45, height / 2, "CLICK TO PLAY");
    titleText.setFont(new Font(24));
    directions.setFont(new Font(18));
    play.setFont(new Font(14));
    titleText.setTextAlignment(TextAlignment.CENTER);
    directions.setTextAlignment(TextAlignment.CENTER);
    play.setTextAlignment(TextAlignment.CENTER);
  }

  /**
   * Accesses the instructions scene.
   * @return myScene instructions scene created
   */
  public Scene getScene() {
    return myScene;
  }
}
