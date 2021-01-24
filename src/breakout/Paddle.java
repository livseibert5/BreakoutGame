package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {

  private int xDirection;
  private Image image;
  private final int PADDLE_SPEED = 50;

  public Paddle(int width, int height) {
    xDirection = 0;
    this.setWidth(width / 6);
    this.setHeight(height / 21);
    image = new Image("file:resources/paddle.png");
    ImagePattern imagePattern = new ImagePattern(image);
    this.setFill(imagePattern);
  }

  // Increases length of paddle.
  public void expand() {
    // TODO: Implement function.
  }

  public void moveRight() {
    xDirection = 1;
  }
  public void moveLeft() {
    xDirection = -1;
  }
  public void stop() { xDirection = 0; }

  public int getXDirection() {
    return xDirection;
  }

  public int getSpeed() {
    return PADDLE_SPEED;
  }

}
