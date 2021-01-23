package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

public class GameController {

  private Scene myScene;

  public GameController() {

  }

  public void setupGame(int level, int width, int height, Paint background) {
    if (level == 1) {
      Group root = new Group();
      Text testing = new Text(width / 2, height / 2, "TEST");
      root.getChildren().add(testing);
      myScene = new Scene(root, width, height, background);
    }
  }

  public Scene getScene() {
    return myScene;
  }

}
