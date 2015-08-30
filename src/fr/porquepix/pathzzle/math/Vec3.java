package fr.porquepix.pathzzle.math;

import org.lwjgl.util.vector.Vector3f;

public class Vec3 {

	private float x, y, z;
	
	public Vec3() {
		this(0.0f, 0.0f, 0.0f);
	}
	
	public Vec3(float x, float y, float z) {
		super();
		set(x, y, z);
	}

	public Vec3(Vec3 v) {
		this(v.x, v.y, v.z);
	}
	
	public Vec3 set(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}
	
	public Vec3 copy() {
		return new Vec3(this);
	}
	
	public Vec3 add(Vec3 v) {
		set(x + v.x, y + v.y, z + v.z);
		return this;
	}
	
	public Vec3 sub(Vec3 v) {
		set(x - v.x, y - v.y, z - v.z);
		return this;
	}
	
	public Vec3 scale(float factor) {
		set(x * factor, y * factor, z * factor);
		return this;
	}
	
	public float lengthSquared() {
		return x * x + y * y + z * z;
	}
	
	public float length() {
		return (float) Math.sqrt(lengthSquared());
	}
	
	public Vec3 cross(Vec3 a, Vec3 b) {
		set(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
		return this;
	}
	
	public static float dot(Vec3 a, Vec3 b) {
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}
	
	public float x() {
		return x;
	}

	public float y() {
		return y;
	}

	public float z() {
		return z;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public void x(float x) {
		setX(x);
	}

	public void y(float y) {
		setY(y);
	}

	public void z(float z) {
		setZ(z);
	}

	public Vec3 set(Vec3 vec) {
		set(vec.x, vec.y, vec.z);
		return this;
	}


	@Override
	public String toString() {
		return "Vec3f [x=" + x + ", y=" + y + ", z=" + z + "]";
	}
	
	public Vec3 addFactor(Vec3 v, float factor) {
		set(x + v.x * factor, y + v.y * factor, z + v.z * factor);
		return this;
	}

	public float normalise() {
		float len = length();
		scale(1.0f / len);
		return len;
	}

	public Vec3 sub(float x, float y, float z) {
		this.x -= x;
		this.y -= y;
		this.z -= z;
		return this;
	}

	public Vec3 add(float x, float y, float z) {
		this.x += x;
		this.y += y;
		this.z += z;
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vec3 other = (Vec3) obj;
		if (Float.floatToIntBits(x) != Float.floatToIntBits(other.x))
			return false;
		if (Float.floatToIntBits(y) != Float.floatToIntBits(other.y))
			return false;
		if (Float.floatToIntBits(z) != Float.floatToIntBits(other.z))
			return false;
		return true;
	}
	
	public Vec3 check() {
		float max = Math.max(Math.max(x, y), z);
		float min = Math.min(Math.min(x, y), z);
		
		float absMax = Math.abs(max - 1);
		float absMin = Math.abs(min);
		
		float v = 0;
		if (absMax > absMin) v = min;
		else v = max;
		int rv = 1;
		
		if (v < 0.5f) rv = -1;
		
		return new Vec3(v == x ? rv : 0, v == y ? rv : 0, v == z ? rv : 0);
	}
	
	public Vector3f toLWJGLVec() {
		return new Vector3f(x, y, z);
	}
	
}
