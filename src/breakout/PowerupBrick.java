package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

enum Power {
  FAST,
  EXTRA,
  LONGER
}

public class PowerupBrick extends Brick {

  private Power powerType;

  public PowerupBrick(int lives, int width, int height) {
    super(lives, width, height);
    this.setFill(new ImagePattern(new Image("file:resources/power.png")));
    int type = (int) Math.round(Math.random() * 3);
    if (type == 0) {
      powerType = Power.FAST;
    }
    if (type == 1) {
      powerType = Power.EXTRA;
    }
    if (type == 2) {
      powerType = Power.LONGER;
    }
  }

  public Power getType() {
    return powerType;
  }
}
