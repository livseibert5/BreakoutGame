package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * Class for bricks that contain a power-up. Extends brick because it shares the same basic
 * functionality of a single-hit brick with the added component that it contains a power-up.
 *
 * @author Livia Seibert
 */
public class PowerupBrick extends Brick {

  /**
   * Constructor for a PowerupBrick, calls the Brick class constructor and sets an image for
   * the brick.
   *
   * @param width  width of the game screen
   * @param height height of the game screen
   */
  public PowerupBrick(int width, int height) {
    super(1, width, height);
    this.setFill(
        new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("power.png"))));
  }
}
