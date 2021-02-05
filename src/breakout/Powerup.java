package breakout;

import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

enum Power {FAST, EXTRA, LONGER}

/**
 * Class used to create Powerup objects. Used by Main to create a power-up that moves down the
 * screen when a power-up brick is broken. Powerup type is chosen randomly in the constructor.
 * Powerup activated when this object hits the paddle.
 *
 * @author Livia Seibert
 */
public class Powerup extends Circle {

  private Power powerType;
  private boolean used;
  private static final int POWERUP_SPEED = 60;

  /**
   * Constructor for power-up object, sets the type to be the same as the broken brick it came
   * from.
   */
  public Powerup() {
    this.setFill(
        new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("heart.png"))));
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
    this.setRadius(15);
    used = false;
  }

  /**
   * Moves powerup icon down the screen.
   *
   * @param elapsedTime time since last update
   */
  public void move(double elapsedTime) {
    this.setCenterY(this.getCenterY() + POWERUP_SPEED * elapsedTime);
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
    return powerType;
  }
}
