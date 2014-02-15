
public class Game {

	public static void main(String[] args) {
		Level level = new Level("config.xml");
		if(level.errorFlag < 1)
		{
			for(int i = 0; i < level.houseList.size(); i++)
			{
				level.houseList.get(i).printProperties();
				level.houseList.get(i).drawHouse();
			}
		}
	}
}
