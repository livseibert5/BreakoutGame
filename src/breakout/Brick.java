package breakout;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.ImagePattern;
import javafx.scene.image.Image;

/**
 * This class creates the single-hit and multi-hit
 * blocks that compose each level.
 */
public class Brick extends Rectangle {

  private int lives;

  /**
   * Constructor for Brick class, sets the initial
   * amount of lives the brick has and determines the
   * width and height of the brick depending on the width
   * and height of the game screen. Also sets the image for
   * the brick.
   * @param lives number of times the brick must be hit
   *              to disappear, one life means the block
   *              is a single-hit block and more than one
   *              life is a multi-hit block
   * @param width width of the entire game screen
   * @param height height of the entire game screen
   */
  public Brick(int lives, int width, int height) {
    this.lives = lives;
    this.setWidth(width / 16);
    this.setHeight(height / 18);
    selectImage();
  }

  /**
   * Default constructor for brick. Creates an empty brick
   * object that displays nothing on the screen.
   */
  public Brick() { }

  /**
   * Called when Main registers a collision between a brick and the ball.
   * Subtracts one life from the brick. If a multi-hit brick drops to one
   * life, it's image changes to a single-hit brick.
   */
  public void decrementLives() {
    lives--;
    if (lives > 0) {
      selectImage();
    }
  }

  /**
   * Determines the appropriate image for the brick
   * depending on how many lives the brick has left.
   */
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

  /**
   * Accesses the number of lives the brick has left.
   * @return lives number of hits until the block breaks.
   */
  public int getLives() {
    return lives;
  }

}
