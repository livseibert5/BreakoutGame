package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * This class creates the balls objects needed for game play.
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

  public double getTop() {
    return this.getCenterY() - this.getRadius();
  }

  public double getBottom() {
    return this.getCenterY() + this.getRadius();
  }

  public double getRight() {
    return this.getCenterX() + this.getRadius();
  }

  public double getLeft() {
    return this.getCenterX() - this.getRadius();
  }

  public void invertXDirection() {
    xDirection *= -1;
  }

  public void invertYDirection() {
    yDirection *= -1;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public int getSpeed() {
    return speed;
  }

  public void setInactive() {
    isActive = false;
  }

  public boolean getIsActive() {
    return isActive;
  }

  public int getXDirection() {
    return xDirection;
  }

  public int getYDirection() {
    return yDirection;
  }

}
