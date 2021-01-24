package breakout;

import java.io.FileNotFoundException;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import java.io.File;
import java.util.Scanner;

public class GameController {

  private Scene myScene;
  private int[][] brickLayout;

  public GameController() {
    brickLayout = new int[12][16];
  }

  public void setupGame(int level, int width, int height, Paint background) {
    if (level == 1) {
      readFile("level1.txt");
      Group root = new Group();
      Text testing = new Text(width / 2, height / 2, "TEST");
      root.getChildren().add(testing);
      myScene = new Scene(root, width, height, background);
    } else if (level == 2) {
      readFile("level2.txt");
    } else if (level == 3) {
      readFile("level3.txt");
    }
    assembleBricks();
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
        if (brickLayout[row][col] == 1) {

        } else if (brickLayout[row][col] == 2) {

        } else if (brickLayout[row][col] == 3) {

        }
      }
    }
  }

  public Scene getScene() {
    return myScene;
  }

}
