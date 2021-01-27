package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Boss extends Brick {

  private int xDirection;
  private final static int BOSS_SPEED = 70;

  public Boss(int height) {
    this.setX(0);
    this.setY(height - 150);
    this.setWidth(40);
    this.setHeight(40);
    this.setFill(
        new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("cupid.png"))));
    xDirection = 1;
  }

  public void invertXDirection() {
    xDirection *= -1;
  }

  public int getXDirection() {
    return xDirection;
  }

  public int getSpeed() {
    return BOSS_SPEED;
  }
}
