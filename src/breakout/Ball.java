package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * This class creates the ball objects needed for game play. Used by GameController to put the
 * original ball in the scene and by Main to add more balls when a power-up requires it.
 *
 * @author Livia Seibert
 */
public class Ball extends Circle {

  private int speed;
  private int xDirection;
  private int yDirection;
  private boolean isActive;
  private final int STANDARD_SPEED = 120;
  private final int FAST_SPEED = 180;

  /**
   * Constructor for Ball object, sets the radius and speed of the ball, as well as its initial x
   * and y direction. Also sets the image for the ball.
   */
  public Ball() {
    this.setRadius(15);
    this.speed = STANDARD_SPEED;
    this.xDirection = 1;
    this.yDirection = 1;
    isActive = true;
    this.setFill(
        new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("ball.png"))));
  }

  /**
   * Moves ball across the scene.
   *
   * @param elapsedTime time since last update.
   */
  public void move(double elapsedTime) {
    this.setCenterX(
        this.getCenterX() + xDirection * speed * elapsedTime);
    this.setCenterY(
        this.getCenterY() + yDirection * speed * elapsedTime);
  }

  /**
   * Applies speed powerup to ball.
   */
  public void makeFast() {
    speed = FAST_SPEED;
    this.setFill(new ImagePattern(
        new Image(getClass().getClassLoader().getResourceAsStream("ballred.png"))));
  }

  /**
   * Makes ball regular again after speed powerup.
   */
  public void makeStandard() {
    speed = STANDARD_SPEED;
    this.setFill(new ImagePattern(
        new Image(getClass().getClassLoader().getResourceAsStream("ball.png"))));
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
   * Used to detect speed for ball to check if power-up needs to be removed.
   *
   * @return speed current ball speed
   */
  public int getSpeed() {
    return speed;
  }

  /**
   * Used to access the standard speed of the ball without the speed powerup.
   *
   * @return STANDARD_SPEED regular speed of ball
   */
  public int getStandardSpeed() {
    return STANDARD_SPEED;
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
}
