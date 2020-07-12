package world;

public class Camera {
	public static int x = 0;
	public static int y = 0;
	
	public static int clamp(int Now, int Min, int Max) {
		if(Now < Min) {
			Now = Min;
		}
		if(Now > Max) {
			Now = Max;
		}
		return Now;
	}
	
}
