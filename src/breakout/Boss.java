package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * This class creates the boss enemy for the third level of game play. Used by GameController to add
 * the boss to the level. Extends Brick class because the boss essentially functions as an
 * un-killable brick since collision handling between the brick and the ball and the ball and the
 * boss is handled in much the same way.
 *
 * @author Livia Seibert
 */
public class Boss extends Brick {

  private int xDirection;
  private int screenWidth;
  private final static int BOSS_SPEED = 70;

  /**
   * Constructor for Boss object, sets the size and location of the enemy.
   *
   * @param height height of the game screen
   */
  public Boss(int height, int width) {
    this.screenWidth = width;
    this.setX(0);
    this.setY(height - 150);
    this.setWidth(40);
    this.setHeight(40);
    this.setFill(
        new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("cupid.png"))));
    xDirection = 1;
  }

  /**
   * Moves boss enemy across the screen.
   *
   * @param elapsedTime time since last update
   */
  public void move(double elapsedTime) {
    this.setX(this.getX() + xDirection * BOSS_SPEED * elapsedTime);
    if (this.getX() < 0 || this.getX() >= screenWidth - this.getWidth()) {
      xDirection *= -1;
      if (xDirection == 1) {
        this.setFill(new ImagePattern(
            new Image(getClass().getClassLoader().getResourceAsStream("cupid.png"))));
      } else if (xDirection == -1) {
        this.setFill(new ImagePattern(
            new Image(getClass().getClassLoader().getResourceAsStream("cupidleft.png"))));
      }
    }
  }
}
