package breakout;

enum Power {
  FAST,
  EXTRA,
  LONGER
}

public class PowerupBrick extends Brick {

  private Power powerType;

  public PowerupBrick(int lives, int width, int height) {
    super(lives, width, height);
    int type = (int) Math.random() * 3;
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
}
