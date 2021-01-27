package breakout;

import javafx.scene.shape.Circle;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class Powerup extends Circle {

  private Power powerupType;
  private boolean used;

  public Powerup(Power type) {
    this.setFill(
        new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("heart.png"))));
    this.setRadius(15);
    used = false;
    powerupType = type;
  }

  public void setUsed() {
    used = true;
  }

  public boolean getUsed() {
    return used;
  }

  public Power getType() {
    return powerupType;
  }
}
