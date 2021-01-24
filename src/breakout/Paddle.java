package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {

  private int xDirection;
  private Image image;
  private final int PADDLE_SPEED = 50;

  public Paddle(int width, int height) {
    xDirection = 1;
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

  // Changes direction of paddle motion.
  public void switchDirection() {
    xDirection *= -1;
  }

  public int getXDirection() {
    return xDirection;
  }

  public int getSpeed() {
    return PADDLE_SPEED;
  }

}
