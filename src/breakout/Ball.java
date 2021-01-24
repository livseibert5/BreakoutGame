package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Ball extends Circle {

  private Image image;

  public Ball() {
    this.setRadius(15);
    image = new Image("file:resources/ball.png");
    ImagePattern imagePattern = new ImagePattern(image);
    this.setFill(imagePattern);
  }

}
