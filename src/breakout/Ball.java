package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Ball extends Circle {

  private Image image;
  private int speed;
  private int xDirection;
  private int yDirection;

  public Ball() {
    this.setRadius(15);
    this.speed = 70;
    this.xDirection = 1;
    this.yDirection = 1;

    image = new Image("file:resources/ball.png");
    ImagePattern imagePattern = new ImagePattern(image);
    this.setFill(imagePattern);
  }

  public void setXDirection(int newDirection) { xDirection = newDirection; }
  public void invertXDirection() { xDirection *= -1; }
  public void invertYDirection() { yDirection *= -1; }

  public int getSpeed() { return speed; }
  public int getXDirection() { return xDirection; }
  public int getYDirection() { return yDirection; }

}
