import java.awt.Point;

/**
 * @author Mario LoPrinzi
 * 
 */
public class Zombie
{
	private int walkType = 0; // 1 is random walk and 2 is line walk
	private float probabiltyA, probablityB, smell, speed;
	private Point position = new Point(0, 0);

	public Zombie(Point position, float smell, float speed, int walkType,
			float probabilityA, float probabilityB)
	{
		this.position = position;
		this.smell = smell;
		this.speed = speed;
		this.walkType = walkType;
		this.probabiltyA = probabilityA;
		this.probablityB = probabilityB;
	}
	
	public void setPosition(Point position) {
		this.position = position;
	}
	
	public Point getPosition() {
		return position;
	}

	public void setSmell(float adjustedSmell)
	{
		this.smell = adjustedSmell;
	}
}