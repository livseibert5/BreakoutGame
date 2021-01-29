package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

enum Power {FAST, EXTRA, LONGER}

/**
 * Class for bricks that contain a power-up. Extends brick because it shares the same basic
 * functionality of a single-hit brick with the added component that it contains a power-up.
 */
public class PowerupBrick extends Brick {

  private Power powerType;

  /**
   * Constructor for a PowerupBrick, calls the Brick class constructor, sets an image for the brick,
   * then randomly generates a number that corresponds to one of the three types of power-up.
   * Power.FAST makes the ball speed up, Power.EXTRA produces another ball, and Power.LONGER doubles
   * the length of the paddle.
   *
   * @param width  width of the game screen
   * @param height height of the game screen
   */
  public PowerupBrick(int width, int height) {
    super(1, width, height);
    this.setFill(
        new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("power.png"))));
    int type = (int) Math.round(Math.random() * 2) + 1;
    if (type == 1) {
      powerType = Power.FAST;
    }
    if (type == 2) {
      powerType = Power.EXTRA;
    }
    if (type == 3) {
      powerType = Power.LONGER;
    }
  }

  /**
   * Accesses the type of power-up the block contains.
   *
   * @return powerType type of power-up from the Power enum
   */
  public Power getType() {
    return powerType;
  }
}
