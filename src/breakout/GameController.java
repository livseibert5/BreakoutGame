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
  private final int[][] brickLayout;

  /**
   * Default constructor for a GameController object, instantiates the brickLayout array to keep
   * track of the placement of the bricks on the screen.
   */
  public GameController() {
    brickLayout = new int[12][16];
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
    assembleBricks();
    createPaddle();
    createBall();
    setLives();
    setScore();
    setLevel();
    myScene = new Scene(root, width, height, background);
  }

  public void createBossEnemy() {
    boss = new Boss(width);
    root.getChildren().add(boss);
  }

  public Boss getBoss() {
    return boss;
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
        brickLayout[row][col] = Integer.parseInt(brickRow[col]);
      }
      row++;
    }
  }

  /**
   * Iterates through the brickLayout array and places bricks in the scene at the proper coordinates
   * depending on their location in the array. 1s are created as single-hit bricks, 2s are created
   * as multi-hit bricks, and 3s are created as power-up bricks.
   */
  private void assembleBricks() {
    for (int row = 0; row < brickLayout.length; row++) {
      for (int col = 0; col < brickLayout[row].length; col++) {
        Brick brick = new Brick();
        if (brickLayout[row][col] == 1) {
          brick = new Brick(1, width, height);
        } else if (brickLayout[row][col] == 2) {
          brick = new Brick((int) Math.round(Math.random() * 3) + 2, width, height);
        } else if (brickLayout[row][col] == 3) {
          brick = new PowerupBrick(width, height);
        }
        placeBrick(row, col, brick);
      }
    }
  }

  /**
   * Determines proper coordinates for each brick depending on their row and column and places them
   * in the scene.
   *
   * @param row   brick's row in brickLayout
   * @param col   brick's column in brickLayout
   * @param brick Brick object being placed
   */
  private void placeBrick(int row, int col, Brick brick) {
    if (brickLayout[row][col] != 0) {
      brick.setX(col * brick.getWidth());
      brick.setY(row * brick.getHeight() + 100);
      root.getChildren().add(brick);
      bricks.add(brick);
    }
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

  /**
   * Creates Circle objects to hold images that represent the number of lives the player has left.
   */
  public void setLives() {
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
  public void setScore() {
    score = new Text(width - 100, 40, "Score: 0");
    root.getChildren().add(score);
    Rectangle pageBreak = new Rectangle(0, 65, width, 3);
    root.getChildren().add(pageBreak);
  }

  /**
   * Creates a Text object to hold the current level of the game.
   */
  public void setLevel() {
    level = new Text(width - 150, 40, "Level: " + levelNumber);
    root.getChildren().add(level);
  }

  /**
   * Allows Main to access the paddle.
   *
   * @return paddle paddle object used in gameplay
   */
  public Paddle getPaddle() {
    return paddle;
  }

  /**
   * Allows Main to access the ball.
   *
   * @return ball ball object used in gameplay
   */
  public Ball getBall() {
    return ball;
  }

  /**
   * Allows Main to access a list of all the bricks used in the scene so that collisions can be
   * detected.
   *
   * @return bricks list of Brick objects in scene
   */
  public List<Brick> getBricks() {
    return bricks;
  }

  /**
   * Allows Main to access the root of the scene so that Brick objects can be deleted when they run
   * out of lives.
   *
   * @return root Group object for the scene
   */
  public Group getRoot() {
    return root;
  }

  /**
   * Allows Main to access the list of lives.
   *
   * @return lives Circle objects that represent lives
   */
  public List<Circle> getLives() {
    return lives;
  }

  /**
   * Allows main to access the score text box.
   *
   * @return score Text object that holds score
   */
  public Text getScore() {
    return score;
  }

  /**
   * Allows main to access the level text box.
   *
   * @return level Text object that holds current level
   */
  public Text getLevel() {
    return level;
  }

  /**
   * Allows Main to access the scene itself so that it can be displayed and used in gameplay.
   *
   * @return myScene the scene that GameController created
   */
  public Scene getScene() {
    return myScene;
  }

}
