package breakout;

import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * Class used to create Powerup objects. Used by Main to create a power-up that moves down the
 * screen when a power-up brick is broken. Carries the same type of power-up as the brick it
 * came from. Powerup activated when this object hits the paddle.
 *
 * @author Livia Seibert
 */
public class Powerup extends Circle {

  private Power powerupType;
  private boolean used;

  /**
   * Constructor for power-up object, sets the type to be the same as the broken brick it came
   * from.
   *
   * @param type type of power-up
   */
  public Powerup(Power type) {
    this.setFill(
        new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("heart.png"))));
    this.setRadius(15);
    used = false;
    powerupType = type;
  }

  /**
   * Power-up is set as used when it has been activated and then subsequently ended. That way it can
   * be deleted from the power-up list.
   */
  public void setUsed() {
    used = true;
  }

  /**
   * Accesses whether or not power-up has been used to determine whether or not it should be removed
   * from play.
   *
   * @return boolean true if power-up has already been activated
   */
  public boolean getUsed() {
    return used;
  }

  /**
   * Gets type of power-up so that Main knows what power-up to apply to gameplay.
   *
   * @return Power enum type of power-up
   */
  public Power getType() {
    return powerupType;
  }
}
