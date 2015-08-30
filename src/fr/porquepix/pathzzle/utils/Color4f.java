package fr.porquepix.pathzzle.utils;

import java.awt.Color;

import fr.porquepix.pathzzle.math.Vec3;

public class Color4f {
	
	public float r, g, b, a;
	
	public Color4f(float r, float g, float b) {
		this(r, g, b, 1.0f);
	}
	
	public Color4f(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}
	
	public Color4f(int hexa) {
		Color c = new Color(hexa, false);
		
		this.r = c.getRed() / 255f;
		this.g = c.getGreen() / 255f;
		this.b = c.getBlue() / 255f;
		this.a = 1.0f;
	}
	
	public Color4f(Color4f c) {
		this(c.r, c.g, c.b, c.a);
	}

	public static Color4f interpolate(Color4f c0, Color4f c1, float t) {
		float r = c0.r + (c1.r - c0.r) * t; 
		float g = c0.g + (c1.g - c0.g) * t; 
		float b = c0.b + (c1.b - c0.b) * t; 
		float a = c0.a + (c1.a - c0.a) * t; 
		
		return new Color4f(r, g, b, a);
	}
	
	public Color4f add(Color4f c) {
		r += c.r;
		g += c.g;
		b += c.b;
		a += c.a;
		
		return this;
	}
	
	public Color4f sub(Color4f c) {
		r -= c.r;
		g -= c.g;
		b -= c.b;
		a -= c.a;
		
		return this;
	}
	
	public Color4f scale(float t) {
		r *= t;
		g *= t;
		b *= t;
		a *= t;
		
		return this;
	}
	
	public Color4f copy() {
		return new Color4f(this);
	}

	public Vec3 toVec3() {
		return new Vec3(r, g, b);
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Color4f)) {
			return false;
		}
		Color4f a = (Color4f) o;
		return a.a == this.a && a.r == this.r && a.g == this.g && a.b == this.b;
	}

}
