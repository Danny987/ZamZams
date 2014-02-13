/**
 * @author Mario LoPrinzi
 * @group Marcos Lemus 
 * @group Daniel Gomez
 * @group James Green
 * @group Ramon 
 * 2/1/14
 * CS 351.00
 * 
 */

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Character
{
  private float stamina = 1;
  private float staminaRegen = 0;
  private float sight = 0;

  private boolean sprinting = false;

  /**
   * @param newPos
   *          starting position in pixels in a point object
   * @param hear
   *          distance player can hear in pixels
   * @param sight
   *          distance player can see in pixels
   * @param speed
   *          speed relative to tile of how fast a player walks per second.
   * @param stamRegen
   *          rate at which stamina recovers
   * 
   */
  public Player(float sight, float hear, float speed, float stamina,
      float stamRegen)
  {
    this.hear = hear;
    this.sight = sight;
    this.speed = speed;
    this.hitbox = new Rectangle(position.x, position.y, 50, 50);

  }

  public float getHear()
  {
    return this.hear;
  }

  public float getSight()
  {
    return this.sight;
  }

  public int move(int leftRight, int upDown, boolean sprint)
  {
    int moveFlag = 0;
    // 0 = no movement|| 1 = free movement in that direction
    int collide = collision(cMap, leftRight, upDown);

    if (leftRight == 0 && upDown == 0)
    {
      return 1;
    }

    if (collide == 1)
    {
      /**
       * player hit zombie
       * */
    }
    else if (trapCollision == true && sprinting == true)
    {
      /**
       * player has triggered a trap
       */
    }
    else if (this.objectCollision == false)
    {
      if (collide == 0)
      {
        if (leftRight == 1 && upDown == 0)
        {

          // free movement
          this.position.x -= TILE * this.speed / FRAMERATE;
          this.hitbox = new Rectangle(position.x + 1, position.y + 1, 51, 51);
          moveFlag = 1;

        }

        else if (leftRight == 2 && upDown == 0)
        {

          // free movement
          this.position.x += TILE * this.speed / FRAMERATE;
          this.hitbox = new Rectangle(position.x + 1, position.y + 1, 51, 51);
          moveFlag = 1;

        }
        else if (leftRight == 0 && upDown == 1)
        {

          // free movement
          this.position.y -= TILE * this.speed / FRAMERATE;
          this.hitbox = new Rectangle(position.x + 1, position.y + 1, 51, 51);
          moveFlag = 1;

        }

        else if (leftRight == 0 && upDown == 2)
        {

          // free movement
          this.position.y += TILE * this.speed / FRAMERATE;
          this.hitbox = new Rectangle(position.x + 1, position.y + 1, 51, 51);
          moveFlag = 1;

        }

        else if (leftRight == 1 && upDown == 1)
        {
          // //////////////////////change to square rt 2
          // //an adaptive timer function
          // free movement
          this.position.x -= (TILE * this.speed / FRAMERATE) / 2;
          this.position.y -= (TILE * this.speed / FRAMERATE) / 2;
          this.hitbox = new Rectangle(position.x + 1, position.y + 1, 51, 51);
          moveFlag = 1;

        }
        else if (leftRight == 1 && upDown == 2)
        {

          // free movement
          this.position.x -= (TILE * this.speed / FRAMERATE) / 2;
          this.position.y += (TILE * this.speed / FRAMERATE) / 2;
          this.hitbox = new Rectangle(position.x + 1, position.y + 1, 51, 51);
          moveFlag = 1;

        }

        else if (leftRight == 2 && upDown == 1)
        {

          // free movement
          this.position.x += (TILE * this.speed / FRAMERATE) / 2;
          this.position.y -= (TILE * this.speed / FRAMERATE) / 2;
          this.hitbox = new Rectangle(position.x + 1, position.y + 1, 51, 51);
          moveFlag = 1;

        }

        else if (leftRight == 2 && upDown == 2)
        {

          // free movement
          this.position.x += (TILE * this.speed / FRAMERATE) / 2;
          this.position.y += (TILE * this.speed / FRAMERATE) / 2;
          this.hitbox = new Rectangle(position.x + 1, position.y + 1, 51, 51);
          moveFlag = 1;

        }
      }
    }
    if (moveFlag == 1)
    {
      moveFlag = 0;
      return 1;
    }

    return 0;
  }

  public static void main(String[] args)
  {
    /**
     * test player test point and assert checks
     */
    Point test = new Point(200, 200);
    Player p = new Player(1, 1, 1, 1);
    p.setPosition(test);
    assert p.move(0, 0, false) == (1);
    assert p.position.toString().equals("java.awt.Point[x=200,y=200]");
    assert p.move(1, 0, false) == (0);
    assert p.position.toString().equals("java.awt.Point[x=198,y=200]");
    assert p.move(2, 0, false) == (0);
    assert p.position.toString().equals("java.awt.Point[x=199,y=200]");
    assert p.move(1, 1, false) == (0);
    assert p.position.toString().equals("java.awt.Point[x=198,y=199]");
    assert p.move(1, 2, false) == (0);
    assert p.position.toString().equals("java.awt.Point[x=197,y=199]");
    assert p.move(2, 1, false) == (0);
    assert p.position.toString().equals("java.awt.Point[x=197,y=198]");
    assert p.move(2, 2, false) == (0);
    assert p.position.toString().equals("java.awt.Point[x=197,y=198]");
    assert p.move(0, 1, false) == (0);
    assert p.position.toString().equals("java.awt.Point[x=197,y=196]");
    assert p.move(0, 2, false) == (0);
    assert p.position.toString().equals("java.awt.Point[x=197,y=197]");

    Level level = new Level("test_level.xml");
    /**
     * collision tests for free movement
     */
    assert p.collision(p.cMap, 1, 0) == (1);
    assert p.collision(p.cMap, 2, 0) == (1);
    assert p.collision(p.cMap, 1, 1) == (1);
    assert p.collision(p.cMap, 1, 2) == (1);
    assert p.collision(p.cMap, 2, 1) == (1);
    assert p.collision(p.cMap, 2, 2) == (1);
    assert p.collision(p.cMap, 0, 1) == (1);
    assert p.collision(p.cMap, 0, 2) == (1);
    /**
     * collsion test for blocked movement/no movement
     */

    Player p2 = new Player(1, 1, 1, 1);
    p2.setPosition(test);
    assert p2.collision(p.cMap, 0, 0) == (0);
    assert p2.collision(p.cMap, 1, 0) == (0);
    assert p2.collision(p.cMap, 1, 1) == (0);
    assert p2.collision(p.cMap, 1, 2) == (0);
    assert p2.collision(p.cMap, 2, 1) == (0);
    assert p2.collision(p.cMap, 0, 1) == (0);

    // temp constants for screen pixel size.
    assert p2.collision(p.cMap, 2, 0) == (0);
    assert p2.collision(p.cMap, 0, 2) == (0);
    assert p2.collision(p.cMap, 2, 2) == (0);
  }
}
