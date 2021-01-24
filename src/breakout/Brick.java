package breakout;

public class Brick {

  private int lives;

  public Brick(int lives) {
    this.lives = lives;
  }

  /**
   * Called when GameController registers a collision between
   * a brick and the ball. If a multi-hit brick drops to one life,
   * it's image changes to a single-hit brick. If the brick's lives
   * drop to zero, the brick disappears.
   */
  public void decrementLives() {
    // TODO: Implement function.
  }

  // Getter function returns the number of lives a brick has left.
  public int getLives() {
    return lives;
  }

}
