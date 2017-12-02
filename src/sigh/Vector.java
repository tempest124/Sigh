package sigh;



public class Vector {
	
	public float x, y;
	
	public Vector() {
		this(0, 0);
	}
	
	public Vector(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void add(Vector other) {
		this.x += other.x;
		this.y += other.y;
	}
	
	public void sub(Vector other) {
		this.x -= other.x;
		this.y -= other.y;
	}
	
	@Override
	public String toString() {
		return "{" + x + ", " + y + "}";
	}
	
}
