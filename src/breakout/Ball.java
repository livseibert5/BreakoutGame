package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * This class creates the balls objects needed for game play. Used by GameController to put the
 * original ball in the scene and by Main to add more balls when a power-up requires it.
 *
 * @author Livia Seibert
 */
public class Ball extends Circle {

  private int speed;
  private int xDirection;
  private int yDirection;
  private boolean isActive;

  /**
   * Constructor for Ball object, sets the radius and speed of the ball, as well as it's initial x
   * and y direction. Also sets the image for the ball.
   */
  public Ball() {
    this.setRadius(15);
    this.speed = 120;
    this.xDirection = 1;
    this.yDirection = 1;
    isActive = true;
    this.setFill(
        new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("ball.png"))));
  }

  /**
   * Returns y-coordinate of top of ball. Used to detect collisions.
   *
   * @return location of top of ball
   */
  public double getTop() {
    return this.getCenterY() - this.getRadius();
  }

  /**
   * Returns y-coordinate of bottom of ball. Used to detect collisions.
   *
   * @return location of bottom of ball
   */
  public double getBottom() {
    return this.getCenterY() + this.getRadius();
  }

  /**
   * Returns x-location of right of ball. Used to detect collisions.
   *
   * @return location of right of ball.
   */
  public double getRight() {
    return this.getCenterX() + this.getRadius();
  }

  /**
   * Returns x-location of left of ball. Used to detect collisions.
   *
   * @return location of left of ball
   */
  public double getLeft() {
    return this.getCenterX() - this.getRadius();
  }

  /**
   * Inverts x-direction of ball, called when ball collides with an object.
   */
  public void invertXDirection() {
    xDirection *= -1;
  }

  /**
   * Inverts y-direction of ball, called when ball collides with an object.
   */
  public void invertYDirection() {
    yDirection *= -1;
  }

  /**
   * Used to set new speed for ball to apply and remove speed power-up.
   *
   * @param speed speed of ball
   */
  public void setSpeed(int speed) {
    this.speed = speed;
  }

  /**
   * Used to detect speed for ball to check if power-up needs to be removed.
   *
   * @return speed current ball speed
   */
  public int getSpeed() {
    return speed;
  }

  /**
   * When a ball drops below the paddle, it is set as inactive. This is so the ball can be removed
   * from the list of balls without altering a list in place.
   */
  public void setInactive() {
    isActive = false;
  }

  /**
   * Determines if the ball is active, if not active ball is removed from the list of balls.
   *
   * @return isActive boolean true if ball is in play
   */
  public boolean getIsActive() {
    return isActive;
  }

  /**
   * Allows access to x-direction of ball, used to update motion of ball.
   *
   * @return xDirection horizontal direction of ball.
   */
  public int getXDirection() {
    return xDirection;
  }

  /**
   * Allows access to y-direction of ball, used to update motion of ball.
   *
   * @return yDirection vertical direction of ball.
   */
  public int getYDirection() {
    return yDirection;
  }
}
