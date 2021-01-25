package breakout;

import java.io.FileNotFoundException;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;

/**
 * This class creates the scenes for the game. Depending on
 * the level input, it sets up the brick layout and also creates
 * the ball and paddle.
 */
public class GameController {

  private Scene myScene;
  private Ball ball;
  private Paddle paddle;
  private Group root;
  private List<Brick> bricks;
  private Text score;
  private Text level;
  private int width;
  private int height;
  private final List<Circle> lives = new ArrayList<>();
  private final int[][] brickLayout;

  /**
   * Default constructor for a GameController object,
   * instantiates the brickLayout array to keep track of
   * the placement of the bricks on the screen.
   */
  public GameController() {
    brickLayout = new int[12][16];
  }

  /**
   * This function takes the current level, width and height
   * of the screen, and the desired background for the level
   * and creates the corresponding scene for the game from it.
   * @param level current level of gameplay
   * @param width width of the game screen
   * @param height height of the game screen
   * @param background background color for the scene
   */
  public void setupGame(int level, int width, int height, Paint background) {
    bricks = new ArrayList<>();
    root = new Group();
    this.width = width;
    this.height = height;
    if (level == 1) {
      readFile("resources/level1.txt");
    } else if (level == 2) {
      readFile("resources/level2.txt");
    } else if (level == 3) {
      readFile("resources/level3.txt");
    }
    assembleBricks();
    createPaddle();
    createBall();
    setLives();
    setScore();
    setLevel();
    myScene = new Scene(root, width, height, background);
  }

  public void setScore() {
    score = new Text(width - 100, 40, "Score: 0");
    root.getChildren().add(score);
  }

  public void setLevel() {
    level = new Text(width - 150, 40, "Level: 1");
    root.getChildren().add(level);
  }

  public void setLives() {
    for (int i = 1; i < 4; i++) {
      Circle life = new Circle();
      life.setRadius(20);
      life.setCenterX(i * 30);
      life.setCenterY(40);
      life.setFill(new ImagePattern(new Image("file:resources/rose.png")));
      root.getChildren().add(life);
      lives.add(life);
    }
  }

  /**
   * Takes in a .txt file and populates the brickLayout
   * array to depict the layout for the level.
   * @param filename .txt file that holds the level layout
   */
  private void readFile(String filename) {
    try {
      File file = new File(filename);
      Scanner reader = new Scanner(file);
      int row = 0;
      while (reader.hasNextLine()) {
        String line = reader.nextLine();
        String[] brickRow = line.split("");
        for (int col = 0; col < brickRow.length; col++) {
          brickLayout[row][col] = Integer.parseInt(brickRow[col]);
        }
        row++;
      }
    } catch (FileNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Iterates through the brickLayout array and places
   * bricks in the scene at the proper coordinates depending
   * on their location in the array. 1s are created as
   * single-hit bricks, 2s are created as multi-hit bricks,
   * and 3s are created as power-up bricks.
   */
  private void assembleBricks() {
    for (int row = 0; row < brickLayout.length; row++) {
      for (int col = 0; col < brickLayout[row].length; col++) {
        Brick brick = new Brick();
        if (brickLayout[row][col] == 1) {
          brick = new Brick(1, width, height);
        } else if (brickLayout[row][col] == 2) {
          brick = new Brick((int) Math.random() * 5 + 2, width, height);
        } else if (brickLayout[row][col] == 3) {
          brick = new PowerupBrick(1, width, height);
        }
        if (brickLayout[row][col] != 0) {
          brick.setX(col * brick.getWidth());
          brick.setY(row * brick.getHeight() + 100);
          root.getChildren().add(brick);
          bricks.add(brick);
        }
      }

    }
  }

  /**
   * Creates and sets coordinates for ball object
   * for the game.
   */
  public void createBall() {
    ball = new Ball();
    ball.setCenterX(width / 2);
    ball.setCenterY((height - paddle.getHeight() - 20)
        - paddle.getHeight() / 2 - ball.getRadius() / 2);
    root.getChildren().add(ball);
  }

  /**
   * Creates and sets coordinates dor the paddle
   * for the game.
   */
  public void createPaddle() {
    paddle = new Paddle(width, height);
    paddle.setX(width / 2 - paddle.getWidth() / 2);
    paddle.setY(height - paddle.getHeight() - 20);
    root.getChildren().add(paddle);
  }

  /**
   * Allows Main to access the paddle.
   * @return paddle paddle object used in gameplay
   */
  public Paddle getPaddle() {
    return paddle;
  }

  /**
   * Allows Main to access the ball.
   * @return ball ball object used in gameplay
   */
  public Ball getBall() {
    return ball;
  }

  /**
   * Allows Main to access a list of all the bricks
   * used in the scene so that collisions can be detected.
   * @return bricks list of Brick objects in scene
   */
  public List<Brick> getBricks() {
    return bricks;
  }

  /**
   * Allows Main to access the root of the scene
   * so that Brick objects can be deleted when they run
   * out of lives.
   * @return root Group object for the scene
   */
  public Group getRoot() {
    return root;
  }

  public List<Circle> getLives() {
    return lives;
  }

  public Text getScore() {
    return score;
  }

  public Text getLevel() {
    return level;
  }

  /**
   * Allows Main to access the scene itself so that
   * it can be displayed and used in gameplay.
   * @return myScene the scene that GameController created
   */
  public Scene getScene() {
    return myScene;
  }

}
