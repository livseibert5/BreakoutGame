package breakout;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;

public class Brick extends Rectangle {

  private int lives;

  public Brick(int lives, int width, int height) {
    this.lives = lives;
    this.setWidth(width / 16);
    this.setHeight(height / 18);
    selectImage();
  }

  public Brick() {
    this.lives = 1;
  }

  /**
   * Called when GameController registers a collision between a brick and the ball. If a multi-hit
   * brick drops to one life, it's image changes to a single-hit brick. If the brick's lives drop to
   * zero, the brick disappears.
   */
  public void decrementLives() {
    lives--;
    if (lives > 0) {
      selectImage();
    }
  }

  public void selectImage() {
    String imageName = "file:resources/pink.png";
    if (lives == 2) {
      imageName = "file:resources/red2.png";
    }
    if (lives == 3) {
      imageName = "file:resources/red3.png";
    }
    if (lives == 4) {
      imageName = "file:resources/red4.png";
    }
    if (lives == 5) {
      imageName = "file:resources/red5.png";
    }
    this.setFill(new ImagePattern(new Image(imageName)));
  }

  // Getter function returns the number of lives a brick has left.
  public int getLives() {
    return lives;
  }

}
