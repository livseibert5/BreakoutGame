package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

enum Type {INSTRUCTIONS, WIN, LOSS}

/**
 * Creates the instructions scene for the game.
 */
public class Screen {

  private static final Paint BACKGROUND = Color.PINK;

  private final Scene myScene;
  private final String directionText = "Welcome to Valentine's Breakout!\n"
      + "Your goal is to clear all the blocks from each level.\n"
      + "You have three lives to complete this goal, be sure to collect power-ups as well.\n"
      + "Watch out for baby cupid, good luck!";
  private final String winText = "Congratulations, you won!";
  private final String lossText = "Sorry, you lost. Better luck next time!";
  private final Group root;
  private final int width;
  private final int height;

  /**
   * Constructor for an Instructions object. Takes the width and height of the game screen, as well
   * as the title. Creates a scene to display the title and instructions.
   *
   * @param width  width of the game screen
   * @param height height of the game screen
   * @param title  title of the game
   */
  public Screen(Type type, int width, int height, String title) {
    root = new Group();
    this.width = width;
    this.height = height;
    if (type == Type.INSTRUCTIONS) {
      createInstructionText(title);
    } else if (type == Type.WIN) {
      createWinText();
    } else if (type == Type.LOSS) {
      createLossText();
    }
    myScene = new Scene(root, width, height, BACKGROUND);
  }

  /**
   * Creates the text for the screen, determines the location of the text based on the width and
   * height of the screen.
   *
   * @param title title of the game
   */
  public void createInstructionText(String title) {
    Text titleText = new Text(width / 3 + 20, height / 4, title);
    Text directions = new Text(40, height / 3, directionText);
    Text play = new Text(width / 3 + 65, height / 2, "CLICK TO PLAY");
    titleText.setFont(new Font(24));
    directions.setFont(new Font(18));
    play.setFont(new Font(14));
    titleText.setTextAlignment(TextAlignment.CENTER);
    directions.setTextAlignment(TextAlignment.CENTER);
    play.setTextAlignment(TextAlignment.CENTER);
    root.getChildren().add(titleText);
    root.getChildren().add(directions);
    root.getChildren().add(play);
  }

  /**
   * Creates the text for the win screen and adds it to the scene.
   */
  public void createWinText() {
    Text win = new Text(width / 3, height / 4, winText);
    root.getChildren().add(win);
  }

  /**
   * Creates the text for the loss screen and adds it to the screen.
   */
  public void createLossText() {
    Text loss = new Text(width / 3, height / 4, lossText);
    root.getChildren().add(loss);
  }

  /**
   * Accesses the scene created.
   *
   * @return myScene scene created
   */
  public Scene getScene() {
    return myScene;
  }
}
