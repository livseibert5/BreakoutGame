package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * This class creates the paddle object needed for game play. Used by GameController class to place
 * paddle into scene.
 */
public class Paddle extends Rectangle {

  private int xDirection;
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
    this.setWidth(width / 6);
    this.setHeight(height / 21);
    this.setFill(
        new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("paddle.png"))));
  }

  /**
   * Called when a power-up is achieved that lengthens the paddle. Doubles the length of the
   * paddle.
   */
  public void expand() {
    double width = this.getWidth() * 2;
    this.setWidth(width);
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
   * Sets the x direction to 0 so that the paddle stops.
   */
  public void stop() {
    xDirection = 0;
  }

  /**
   * Accesses the current x direction of the paddle.
   *
   * @return xDirection returns horizontal direction of paddle
   */
  public int getXDirection() {
    return xDirection;
  }

  /**
   * Accesses the speed of the paddle.
   *
   * @return PADDLE_SPEED velocity of paddle
   */
  public int getSpeed() {
    return PADDLE_SPEED;
  }

}
