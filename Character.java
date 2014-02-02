import java.awt.Point;


abstract class Character
{
  protected final int TILE = 50;
  protected final int FRAMERATE = 30;
  protected float speed;
  protected float hear;

   int collision(Point position, int leftRight, int upDown)
   {
     //0 = free movement
     //1 = wall or object in way
     //2 = zombie collision, player dead
     //3 = door collision, player win
     
     /**add 51 to x for checking right upper corner
      * add 51 to y for bottom left corner
      * add 51 to x and y for bottom right corner*/
     return 0;
   }
}
