package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * This class creates the single-hit and multi-hit blocks that compose each level.
 */
public class Brick extends Rectangle {

  private int lives;

  /**
   * Constructor for Brick class, sets the initial amount of lives the brick has and determines the
   * width and height of the brick depending on the width and height of the game screen. Also sets
   * the image for the brick.
   *
   * @param lives  number of times the brick must be hit to disappear, one life means the block is a
   *               single-hit block and more than one life is a multi-hit block
   * @param width  width of the entire game screen
   * @param height height of the entire game screen
   */
  public Brick(int lives, int width, int height) {
    this.lives = lives;
    this.setWidth(width / 16.0);
    this.setHeight(height / 18.0);
    selectImage();
  }

  /**
   * Default constructor for brick. Creates an empty brick object that displays nothing on the
   * screen.
   */
  public Brick() {
  }

  public double getRight() {
    return this.getX() + this.getWidth();
  }

  public double getBottom() {
    return this.getY() + this.getHeight();
  }

  /**
   * Called when Main registers a collision between a brick and the ball. Subtracts one life from
   * the brick. If a multi-hit brick drops to one life, it's image changes to a single-hit brick.
   */
  public void decrementLives() {
    lives--;
    if (lives > 0) {
      selectImage();
    }
  }

  /**
   * Determines the appropriate image for the brick depending on how many lives the brick has left.
   */
  public void selectImage() {
    String imageName;
    switch(lives) {
      case 2:
        imageName = "red2.png";
        break;
      case 3:
        imageName = "red3.png";
        break;
      case 4:
        imageName = "red4.png";
        break;
      case 5:
        imageName = "red5.png";
        break;
      default:
        imageName = "pink.png";
    }
    this.setFill(
        new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream(imageName))));
  }

  public int getLives() {
    return lives;
  }

}
