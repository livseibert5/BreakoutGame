package breakout;

import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {

  private int xDirection;
  private final int PADDLE_SPEED = 50;

  public Paddle() {
    xDirection = 1;
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
