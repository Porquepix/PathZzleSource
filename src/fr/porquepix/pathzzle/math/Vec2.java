package fr.porquepix.pathzzle.math;

public class Vec2 {

	private float x, y;

	public Vec2() {
		this(0, 0);
	}

	public Vec2(float x, float y) {
		set(x, y);
	}

	public Vec2(Vec2 v) {
		this(v.x, v.y);
	}

	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float lengthSquared() {
		return x * x + y * y;
	}

	public Vec2 scale(float factor) {
		set(x * factor, y * factor);
		return this;
	}

	public Vec2 add(Vec2 v) {
		set(x + v.x, y + v.y);
		return this;
	}

	public Vec2 sub(Vec2 v) {
		set(x - v.x, y - v.y);
		return this;
	}


	public float x() {
		return x;
	}

	public float y() {
		return y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void x(float x) {
		setX(x);
	}

	public void y(float y) {
		setY(y);
	}
	
	@Override
	public String toString() {
		return "Vec3f [x=" + x + ", y=" + y + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vec2 other = (Vec2) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		return true;
	}

}

