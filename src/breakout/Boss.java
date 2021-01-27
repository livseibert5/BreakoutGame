package breakout;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * This class creates the boss enemy for the third level of game play. Used by GameController to add
 * the boss to the level. Extends Brick class because the boss essentially functions as an
 * un-killable brick since collision handling with the ball is handled in much the same way/
 */
public class Boss extends Brick {

  private int xDirection;
  private final static int BOSS_SPEED = 70;

  /**
   * Constructor for Boss object, sets the size and location of the enemy.
   *
   * @param height height of the game screen
   */
  public Boss(int height) {
    this.setX(0);
    this.setY(height - 150);
    this.setWidth(40);
    this.setHeight(40);
    this.setFill(
        new ImagePattern(new Image(getClass().getClassLoader().getResourceAsStream("cupid.png"))));
    xDirection = 1;
  }

  /**
   * Inverts x-direction of boss, used to make boss bounce off the walls.
   */
  public void invertXDirection() {
    xDirection *= -1;
  }

  /**
   * Accesses x-direction of boss, used to move boss.
   *
   * @return xDirection horizontal direction of boss
   */
  public int getXDirection() {
    return xDirection;
  }

  /**
   * Accesses speed of boss, used to move boss.
   *
   * @return BOSS_SPEED speed of boss
   */
  public int getSpeed() {
    return BOSS_SPEED;
  }
}
