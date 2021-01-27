package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.Scene;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;

/**
 * This class creates the scenes for the game. Depending on the level input, it sets up the brick
 * layout and also creates the ball and paddle.
 */
public class GameController {

  private Scene myScene;
  private Ball ball;
  private Paddle paddle;
  private Group root;
  private List<Brick> bricks;
  private Text score;
  private Text level;
  private Boss boss;
  private int levelNumber;
  private int width;
  private int height;
  private final List<Circle> lives = new ArrayList<>();

  /**
   * Default constructor for a GameController object.
   */
  public GameController() {
  }

  /**
   * This function takes the current level, width and height of the screen, and the desired
   * background for the level and creates the corresponding scene for the game from it.
   *
   * @param level      current level of gameplay
   * @param width      width of the game screen
   * @param height     height of the game screen
   * @param background background color for the scene
   */
  public void setupGame(int level, int width, int height, Paint background) {
    this.levelNumber = level;
    bricks = new ArrayList<>();
    root = new Group();
    this.width = width;
    this.height = height;
    if (level == 1) {
      readFile("level1.txt");
    } else if (level == 2) {
      readFile("level2.txt");
    } else if (level == 3) {
      readFile("level3.txt");
      createBossEnemy();
    }
    createPaddle();
    createBall();
    setLivesLabel();
    setScoreLabel();
    setLevelLabel();
    myScene = new Scene(root, width, height, background);
  }

  /**
   * Takes in a .txt file and populates the brickLayout array to depict the layout for the level.
   *
   * @param filename .txt file that holds the level layout
   */
  private void readFile(String filename) {
    Scanner reader = new Scanner(getClass().getClassLoader().getResourceAsStream(filename));
    int row = 0;
    while (reader.hasNextLine()) {
      String line = reader.nextLine();
      String[] brickRow = line.split("");
      for (int col = 0; col < brickRow.length; col++) {
        if (Integer.parseInt(brickRow[col]) != 0) {
          assembleBricks(row, col, Integer.parseInt(brickRow[col]));
        }
      }
      row++;
    }
  }

  public void assembleBricks(int row, int col, int type) {
    Brick brick = new Brick();
    if (type == 1) {
      brick = new Brick(1, width, height);
    } else if (type == 2) {
      brick = new Brick((int) Math.round(Math.random() * 3) + 2, width, height);
    } else if (type == 3) {
      brick = new PowerupBrick(width, height);
    }
    brick.setX(col * brick.getWidth());
    brick.setY(row * brick.getHeight() + 100);
    root.getChildren().add(brick);
    bricks.add(brick);
  }

  /**
   * Creates and sets coordinates for ball object for the game and adds the ball to the scene.
   */
  public void createBall() {
    ball = new Ball();
    ball.setCenterX(width / 2);
    ball.setCenterY((height - paddle.getHeight() - 20)
        - paddle.getHeight() / 2 - ball.getRadius() / 2);
    root.getChildren().add(ball);
  }

  /**
   * Creates and sets coordinates for the paddle for the game and adds the paddle to the scene.
   */
  public void createPaddle() {
    paddle = new Paddle(width, height);
    paddle.setX(width / 2 - paddle.getWidth() / 2);
    paddle.setY(height - paddle.getHeight() - 20);
    root.getChildren().add(paddle);
  }

  public void createBossEnemy() {
    boss = new Boss(height);
    root.getChildren().add(boss);
  }

  /**
   * Creates Circle objects to hold images that represent the number of lives the player has left.
   */
  public void setLivesLabel() {
    for (int i = 1; i < 4; i++) {
      Circle life = new Circle(i * 30, 40, 20,
          new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("rose.png"))));
      root.getChildren().add(life);
      lives.add(life);
    }
  }

  /**
   * Creates a Text object to hold the current score of the game.
   */
  public void setScoreLabel() {
    score = new Text(width - 100, 40, "Score: 0");
    root.getChildren().add(score);
    Rectangle pageBreak = new Rectangle(0, 65, width, 3);
    root.getChildren().add(pageBreak);
  }

  /**
   * Creates a Text object to hold the current level of the game.
   */
  public void setLevelLabel() {
    level = new Text(width - 150, 40, "Level: " + levelNumber);
    root.getChildren().add(level);
  }

  public Paddle getPaddle() {
    return paddle;
  }

  public Ball getBall() {
    return ball;
  }

  public List<Brick> getBricks() {
    return bricks;
  }

  public Group getRoot() {
    return root;
  }

  public List<Circle> getLivesLabel() {
    return lives;
  }

  public Text getScoreLabel() {
    return score;
  }

  public Text getLevelLabel() {
    return level;
  }
  public Boss getBoss() {
    return boss;
  }

  public Scene getScene() {
    return myScene;
  }

}
