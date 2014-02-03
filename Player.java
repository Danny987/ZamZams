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

import java.awt.Point;
import java.awt.event.KeyEvent;

public class Player extends Character
{
  private float stamina = 0;
  private float staminaRegen = 0;
  private int sight = 0;
  private Point position = new Point();
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
  public Player(Point newPos, float hear, int sight, float speed,
      float stamRegen)
  {
    this.position = newPos;
    this.hear = hear;
    this.sight = sight;
    this.speed = speed;

  }

  /**
   * A getter method for the point ( in pixels ) of the player.
   * */
  public Point getPosition()
  {
    return this.position;
  }

  public int move(int leftRight, int upDown, boolean sprint)
  {
    // 0 = no movement|| 1 = free movement in that direction
    int collide = collision(this.position.getLocation(), leftRight, upDown);
    if (!sprint)
    {
      if (leftRight == 0 && upDown == 0)
      {
        return 1;
      }

      else if (leftRight == 1 && upDown == 0)
      {
        if (collide == 0)
        {
          // free movement
          this.position.x -= TILE * this.speed / FRAMERATE;
        }
        else
        {
          // no movement
        }

      }

      else if (leftRight == 2 && upDown == 0)
      {
        if (collide == 0)
        {
          // free movement
          this.position.x += TILE * this.speed / FRAMERATE;
        }
        else
        {
          // no movement
        }
      }
      else if (leftRight == 0 && upDown == 1)
      {
        if (collide == 0)
        {
          // free movement
          this.position.y -= TILE * this.speed / FRAMERATE;
        }
        else
        {
          // no movement
        }

      }

      else if (leftRight == 0 && upDown == 2)
      {
        if (collide == 0)
        {
          // free movement
          this.position.y += TILE * this.speed / FRAMERATE;
        }
        else
        {
          // no movement
        }

      }

      else if (leftRight == 1 && upDown == 1)
      {
        if (collide == 0)
        {
          // free movement
          this.position.x -= (TILE * this.speed / FRAMERATE) / 2;
          this.position.y -= (TILE * this.speed / FRAMERATE) / 2;
        }
        else
        {
          // no movement
        }

      }
      else if (leftRight == 1 && upDown == 2)
      {
        if (collide == 0)
        {
          // free movement
          this.position.x -= (TILE * this.speed / FRAMERATE) / 2;
          this.position.y += (TILE * this.speed / FRAMERATE) / 2;
        }
        else
        {
          // no movement
        }

      }

      else if (leftRight == 2 && upDown == 1)
      {
        if (collide == 0)
        {
          // free movement
          this.position.x += (TILE * this.speed / FRAMERATE) / 2;
          this.position.y -= (TILE * this.speed / FRAMERATE) / 2;
        }
        else
        {
          // no movement
        }

      }

      else if (leftRight == 2 && upDown == 2)
      {
        if (collide == 0)
        {
          // free movement
          this.position.x += (TILE * this.speed / FRAMERATE) / 2;
          this.position.y += (TILE * this.speed / FRAMERATE) / 2;
        }
        else
        {
          // no movement
        }

      }
    }
    else
    {
      if (leftRight == 0 && upDown == 0)
      {
        return 1;
      }

      else if (leftRight == 1 && upDown == 0)
      {
        if (collide == 0)
        {
          // free movement
          this.position.x -= TILE * this.speed / FRAMERATE;
        }
        else
        {
          // no movement
        }

      }

      else if (leftRight == 2 && upDown == 0)
      {
        if (collide == 0)
        {
          // free movement
          this.position.x += TILE * this.speed / FRAMERATE;
        }
        else
        {
          // no movement
        }
      }
      else if (leftRight == 0 && upDown == 1)
      {
        if (collide == 0)
        {
          // free movement
          this.position.y -= TILE * this.speed / FRAMERATE;
        }
        else
        {
          // no movement
        }

      }

      else if (leftRight == 0 && upDown == 2)
      {
        if (collide == 0)
        {
          // free movement
          this.position.y += TILE * this.speed / FRAMERATE;
        }
        else
        {
          // no movement
        }

      }

      else if (leftRight == 1 && upDown == 1)
      {
        if (collide == 0)
        {
          // free movement
          this.position.x -= (TILE * this.speed / FRAMERATE) / 2;
          this.position.y -= (TILE * this.speed / FRAMERATE) / 2;
        }
        else
        {
          // no movement
        }

      }
      else if (leftRight == 1 && upDown == 2)
      {
        if (collide == 0)
        {
          // free movement
          this.position.x -= (TILE * this.speed / FRAMERATE) / 2;
          this.position.y += (TILE * this.speed / FRAMERATE) / 2;
        }
        else
        {
          // no movement
        }

      }

      else if (leftRight == 2 && upDown == 1)
      {
        if (collide == 0)
        {
          // free movement
          this.position.x += (TILE * this.speed / FRAMERATE) / 2;
          this.position.y -= (TILE * this.speed / FRAMERATE) / 2;
        }
        else
        {
          // no movement
        }

      }

      else if (leftRight == 2 && upDown == 2)
      {
        if (collide == 0)
        {
          // free movement
          this.position.x += (TILE * this.speed / FRAMERATE) / 2;
          this.position.y += (TILE * this.speed / FRAMERATE) / 2;
        }
        else
        {
          // no movement
        }

      }

    }
    return 0;
  }

  public static void main(String[] args)
  {
    /**
     * test player test point and assert checks
     */
    Point test = new Point(200, 200);
    Player p = new Player(test, 1, 1, 1, 1);
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

    /**
     * collision tests for free movement
     */
    assert p.collision(test, 1, 0) == (1);
    assert p.collision(test, 2, 0) == (1);
    assert p.collision(test, 1, 1) == (1);
    assert p.collision(test, 1, 2) == (1);
    assert p.collision(test, 2, 1) == (1);
    assert p.collision(test, 2, 2) == (1);
    assert p.collision(test, 0, 1) == (1);
    assert p.collision(test, 0, 2) == (1);
    /**
     * collsion test for blocked movement/no movement
     */
    test.setLocation(0, 0);
    Player p2 = new Player(test, 1, 1, 1, 1);
    assert p2.collision(test, 0, 0) == (0);
    assert p2.collision(test, 1, 0) == (0);
    assert p2.collision(test, 1, 1) == (0);
    assert p2.collision(test, 1, 2) == (0);
    assert p2.collision(test, 2, 1) == (0);
    assert p2.collision(test, 0, 1) == (0);

    // temp constants for screen pixel size.
    int MAXHEIGHT = 1080;
    int MAXWIDTH = 1920;
    test.setLocation(MAXWIDTH, MAXHEIGHT);
    assert p2.collision(test, 2, 0) == (0);
    assert p2.collision(test, 0, 2) == (0);
    assert p2.collision(test, 2, 2) == (0);
  }
}
