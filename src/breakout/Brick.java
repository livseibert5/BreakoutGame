package breakout;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;

public class Brick extends Rectangle {

  private int lives;
  private Image image;

  public Brick(int lives, int width, int height) {
    this.lives = lives;
    this.setWidth(width / 16);
    this.setHeight(height / 18);

    String imageName = "file:resources/pink.png";
    if (lives > 1) imageName = "file:resources/red.png";
    image = new Image(imageName);
    ImagePattern imagePattern = new ImagePattern(image);
    this.setFill(imagePattern);
  }

  public Brick() { this.lives = 1; }

  /**
   * Called when GameController registers a collision between
   * a brick and the ball. If a multi-hit brick drops to one life,
   * it's image changes to a single-hit brick. If the brick's lives
   * drop to zero, the brick disappears.
   */
  public void decrementLives() {
    lives--;
  }

  // Getter function returns the number of lives a brick has left.
  public int getLives() {
    return lives;
  }

}
