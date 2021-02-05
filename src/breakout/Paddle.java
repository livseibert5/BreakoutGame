package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * This class creates the paddle object needed for game play. Used by GameController class to place
 * paddle into scene.
 *
 * @author Livia Seibert
 */
public class Paddle extends Rectangle {

  private int xDirection;
  private int screenWidth;
  private int standardWidth;
  private int expandWidth;
  private final int PADDLE_SPEED = 120;

  /**
   * Constructor for paddle class, width and height of paddle are determined with. respect to the
   * game screen's dimensions.
   *
   * @param width  width of game screen
   * @param height height of game screen
   */
  public Paddle(int width, int height) {
    xDirection = 0;
    this.screenWidth = width;
    this.standardWidth = width / 6;
    this.expandWidth = standardWidth * 2;
    this.setWidth(standardWidth);
    this.setHeight(height / 21);
    this.setFill(
        new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("paddle.png"))));
  }

  /**
   * Called when a power-up is achieved that lengthens the paddle. Doubles the length of the
   * paddle.
   */
  public void expand() {
    this.setWidth(expandWidth);
  }

  /**
   * Retracts the width of the paddle after the expand powerup wears off.
   */
  public void retract() {
    this.setWidth(standardWidth);
  }

  /**
   * Sets the x direction to 1 so that the paddle can move right.
   */
  public void moveRight() {
    xDirection = 1;
  }

  /**
   * Sets the x direction to -1 so that the paddle can move left.
   */
  public void moveLeft() {
    xDirection = -1;
  }

  /**
   * Moves the paddle across the game screen.
   *
   * @param elapsedTime time since last update
   */
  public void move(double elapsedTime) {
    this.setX(this.getX() + xDirection * PADDLE_SPEED * elapsedTime);
    if (this.getX() + this.getWidth() / 2 >= screenWidth) {
      this.setX(0);
    } else if (this.getX() + this.getWidth() / 2 <= 0) {
      this.setX(screenWidth - this.getWidth());
    }
  }

  /**
   * Sets the x direction to 0 so that the paddle stops.
   */
  public void stop() {
    xDirection = 0;
  }
}
