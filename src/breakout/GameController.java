package breakout;

import java.io.FileNotFoundException;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import java.io.File;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class GameController {

  private Scene myScene;
  private Ball ball;
  private Paddle paddle;
  private Group root;
  private List<Brick> bricks;
  private int width;
  private int height;
  private int[][] brickLayout;

  public GameController() {
    brickLayout = new int[12][16];
  }

  public void setupGame(int level, int width, int height, Paint background) {
    bricks = new ArrayList<Brick>();
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
    myScene = new Scene(root, width, height, background);
  }

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
    }
  }

  private void assembleBricks() {
    for (int row = 0; row < brickLayout.length; row++) {
      for (int col = 0; col < brickLayout[row].length; col++) {
        Brick brick = new Brick();
        if (brickLayout[row][col] == 1) {
          brick = new Brick(1, width, height);
        } else if (brickLayout[row][col] == 2) {
          Random random = new Random();
          brick = new Brick(random.nextInt((5 - 2) + 2) + 2, width, height);
        } else if (brickLayout[row][col] == 3) {
          brick = new PowerupBrick(1, width, height);
        }
        brick.setX(col * brick.getWidth());
        brick.setY(row * brick.getHeight());
        root.getChildren().add(brick);
        bricks.add(brick);
      }

    }
  }

  public void createBall() {
    ball = new Ball();
    ball.setCenterX(width / 2);
    ball.setCenterY((height - paddle.getHeight() - 20)
        - paddle.getHeight() / 2 - ball.getRadius() / 2);
    root.getChildren().add(ball);
  }

  public void createPaddle() {
    paddle = new Paddle(width, height);
    paddle.setX(width / 2 - paddle.getWidth() / 2);
    paddle.setY(height - paddle.getHeight() - 20);
    root.getChildren().add(paddle);
  }

  public Paddle getPaddle() { return paddle; }

  public Ball getBall() { return ball; }

  public List<Brick> getBricks() { return bricks; }

  public Scene getScene() {
    return myScene;
  }

}
