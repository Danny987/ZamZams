/**
 * @author Mario LoPrinzi
 * @group Marcos Lemus 
 * @group Daniel Gomez
 * @group James Green
 * @group Ramon 
 * 2/1/14
 * CS 350.00
 * 
 */

import java.awt.*;

public class Player extends Character
{
  private float stamina = 0;
  private float staminaRegen = 0;
  private float sight = 0;
  private int fireTrapCount = 0;
  private float currentStamina = 0;
  // 1 = north, 2 = east, 3 = south, 4 = west
  private int direction = 0;

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
    this.stamina = stamina;
    this.currentStamina = stamina * FRAMERATE / 2;
    this.hear = hear;
    this.sight = sight;
    this.speed = speed;
    this.hitbox = new Rectangle(position.x, position.y, 50, 50);

  }

  public void useFireTrap()
  {
    if (this.fireTrapCount > 0)
    {
      fireTrapCount--;
    }
  }

  public void grabFireTrap()
  {
    fireTrapCount++;
  }

  public int getFireTrapCount()
  {
    return fireTrapCount;
  }

  public void setFireTrapCount(int fireTrapCount)
  {
    this.fireTrapCount = fireTrapCount;
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
    int moved = 0;
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
      if (!sprinting || currentStamina <= 0)
      {
        moved = mover(leftRight, upDown, this.speed);
        if (currentStamina < 0)
        {
          currentStamina = 0;
        }
      }
      else
      {
        moved = mover(leftRight, upDown, (2 * this.speed));
        currentStamina--;
      }
    }

    if (moved == 1)
    {
      moved = 0;
      return 1;
    }

    return 0;
  }

  private int mover(int leftRight, int upDown, float moveSpeed)
  {
    int moveFlag = 0;
    if (leftRight == 1 && upDown == 0)
    {

      // free movement
      this.position.x -= TILE * moveSpeed / FRAMERATE;
      this.hitbox = new Rectangle(position.x, position.y, 50, 50);
      moveFlag = 1;

    }

    else if (leftRight == 2 && upDown == 0)
    {

      // free movement
      this.position.x += TILE * moveSpeed / FRAMERATE;
      this.hitbox = new Rectangle(position.x, position.y, 50, 50);
      moveFlag = 1;

    }
    else if (leftRight == 0 && upDown == 1)
    {

      // free movement
      this.position.y -= TILE * moveSpeed / FRAMERATE;
      this.hitbox = new Rectangle(position.x, position.y, 50, 50);
      moveFlag = 1;

    }

    else if (leftRight == 0 && upDown == 2)
    {

      // free movement
      this.position.y += TILE * moveSpeed / FRAMERATE;
      this.hitbox = new Rectangle(position.x, position.y, 50, 50);
      moveFlag = 1;

    }

    else if (leftRight == 1 && upDown == 1)
    {
      // free movement
      this.position.x -= (TILE * moveSpeed / FRAMERATE) / Math.sqrt(2);
      this.position.y -= (TILE * moveSpeed / FRAMERATE) / Math.sqrt(2);
      this.hitbox = new Rectangle(position.x, position.y, 50, 50);
      moveFlag = 1;

    }
    else if (leftRight == 1 && upDown == 2)
    {

      // free movement
      this.position.x -= (TILE * moveSpeed / FRAMERATE) / Math.sqrt(2);
      this.position.y += (TILE * moveSpeed / FRAMERATE) / Math.sqrt(2);
      this.hitbox = new Rectangle(position.x, position.y, 50, 50);
      moveFlag = 1;

    }

    else if (leftRight == 2 && upDown == 1)
    {

      // free movement
      this.position.x += (TILE * moveSpeed / FRAMERATE) / Math.sqrt(2);
      this.position.y -= (TILE * moveSpeed / FRAMERATE) / Math.sqrt(2);
      this.hitbox = new Rectangle(position.x, position.y, 50, 50);
      moveFlag = 1;

    }

    else if (leftRight == 2 && upDown == 2)
    {

      // free movement
      this.position.x += (TILE * moveSpeed / FRAMERATE) / Math.sqrt(2);
      this.position.y += (TILE * moveSpeed / FRAMERATE) / Math.sqrt(2);
      this.hitbox = new Rectangle(position.x, position.y, 50, 50);
      moveFlag = 1;

    }
    return moveFlag;
  }

  public static void main(String[] args)
  {
    /**
     * test player test point and assert checks
     */
    Point test = new Point(800, 200);
    Level level = new Level("test_level.xml");
    Player p = new Player(1.0f, 1.0f, 1.0f, 1.0f, 1.0f);

    // level.houseList.get(0).drawHouse(true, true);
    Character.getCollisionMap().BuildcollisionMap(level.houseList.get(0));
    p.setPosition(test);
    assert p.move(0, 0, false) == (1);
    assert p.position.toString().equals("java.awt.Point[x=800,y=200]");
    assert p.move(1, 0, false) == (1);
    assert p.position.toString().equals("java.awt.Point[x=798,y=200]");
    assert p.move(2, 0, false) == (1);
    assert p.position.toString().equals("java.awt.Point[x=799,y=200]");
    assert p.move(1, 1, false) == (1);
    assert p.position.toString().equals("java.awt.Point[x=797,y=198]");
    assert p.move(1, 2, false) == (1);
    assert p.position.toString().equals("java.awt.Point[x=795,y=199]");
    assert p.move(2, 1, false) == (1);
    assert p.position.toString().equals("java.awt.Point[x=796,y=197]");
    assert p.move(2, 2, false) == (1);
    assert p.position.toString().equals("java.awt.Point[x=797,y=198]");
    assert p.move(0, 1, false) == (1);
    assert p.position.toString().equals("java.awt.Point[x=797,y=196]");
    assert p.move(0, 2, false) == (1);
    assert p.position.toString().equals("java.awt.Point[x=797,y=197]");

    /**
     * collision tests for zombie collision collision returns 1 if it hit a
     * zombie
     */
    test.setLocation(300, 100);
    p.setPosition(test);
    assert p.collision(Character.getCollisionMap(), 1, 0) == (1);
    test.setLocation(200, 100);
    p.setPosition(test);
    assert p.collision(Character.getCollisionMap(), 2, 0) == (1);
    test.setLocation(300, 150);
    p.setPosition(test);
    assert p.collision(Character.getCollisionMap(), 1, 1) == (1);
    test.setLocation(300, 50);
    p.setPosition(test);
    assert p.collision(Character.getCollisionMap(), 1, 2) == (1);
    test.setLocation(200, 150);
    p.setPosition(test);
    assert p.collision(Character.getCollisionMap(), 2, 1) == (1);
    test.setLocation(200, 50);
    p.setPosition(test);
    assert p.collision(Character.getCollisionMap(), 2, 2) == (1);
    test.setLocation(250, 150);
    p.setPosition(test);
    assert p.collision(Character.getCollisionMap(), 0, 1) == (1);
    test.setLocation(250, 50);
    p.setPosition(test);
    assert p.collision(Character.getCollisionMap(), 0, 2) == (1);

    /**
     * collsion test for blocked movement/no movement collision returns 0 for
     * any time it did not hit a zombie
     */
    Player p2 = new Player(1.0f, 1.0f, 1.0f, 1.0f, 1.0f);
    test.setLocation(50, 50);
    p2.setPosition(test);
    assert p2.collision(Character.getCollisionMap(), 0, 0) == (0);
    assert p2.objectCollision == false;
    assert p2.collision(Character.getCollisionMap(), 1, 0) == (0);
    assert p2.objectCollision == true;
    assert p2.collision(Character.getCollisionMap(), 1, 1) == (0);
    assert p2.objectCollision == true;
    assert p2.collision(Character.getCollisionMap(), 1, 2) == (0);
    assert p2.objectCollision == true;
    test.setLocation(level.houseList.get(0).getHouseWidth() * 50 - 50, 50);
    p2.setPosition(test);
    assert p2.collision(Character.getCollisionMap(), 2, 1) == (0);
    assert p2.objectCollision == true;
    assert p2.collision(Character.getCollisionMap(), 0, 1) == (0);
    assert p2.objectCollision == true;
    assert p2.collision(Character.getCollisionMap(), 2, 0) == (0);
    assert p2.objectCollision == true;
    test.setLocation(level.houseList.get(0).getHouseWidth() * 50 - 50,
        level.houseList.get(0).getHouseLength() * 50 - 50);
    p2.setPosition(test);
    assert p2.collision(Character.getCollisionMap(), 0, 2) == (0);
    assert p2.objectCollision == true;
    assert p2.collision(Character.getCollisionMap(), 2, 2) == (0);
    assert p2.objectCollision == true;

    /**
     * testing firetrap collision collision returns 0 if it didn't hit a zombie
     * */
    test.setLocation(1600, 200);
    p2.setPosition(test);
    assert p2.collision(Character.getCollisionMap(), 0, 2) == (0);
    assert p2.trapCollision == true;

  }
}
