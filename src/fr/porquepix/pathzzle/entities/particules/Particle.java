package fr.porquepix.pathzzle.entities.particules;

import java.util.Random;

import fr.porquepix.pathzzle.math.Vec2;
import fr.porquepix.pathzzle.render.Renderer;
import fr.porquepix.pathzzle.render.Texture;
import fr.porquepix.pathzzle.utils.Color4f;

public class Particle {

	private static final Random random = new Random();
	
	private boolean removed;
	private float x, y;
	private double rx, ry;
	private int size;
	private float speed;
	private int lifetime;
	private int time;
	private boolean fade;
	private float baseAlpha;
	
	private Color4f color;
	private Vec2 direction;
	
	public Particle() {
		this.removed = false;
		this.time = 0;
	}
	
	public Particle(Particle p, int x, int y) {
		this(p.color, p.size, p.speed, p.lifetime, p.direction, p.fade);
		this.x = x;
		this.y = y;
		
		rx = random.nextGaussian();
		ry = random.nextGaussian();
	}
	
	public Particle(Color4f color, int size, float speed, int lifetime, Vec2 direction) {
		this();
		this.color = color;
		this.size = size;
		this.speed = speed;
		this.lifetime = lifetime;
		this.direction = direction;
		this.fade = false;
	}
	
	public Particle(Texture texture, int size, float speed, int lifetime, int[] randomness) {

	}
	
	public Particle(Color4f color, int size, float speed, int lifetime, Vec2 direction, boolean fade) {
		this(color, size, speed, lifetime, direction);
		this.fade = fade;
		this.baseAlpha = color.a;
	}

	public void update() {
		time++;
		
		if (time > lifetime) {
			removed = true;
			return;
		}
		
		if (fade) {
			color.a = baseAlpha - ((float) time / lifetime) * baseAlpha;
 		}
		
		x += (rx + direction.x()) * speed;
		y += (ry + direction.y()) * speed;
	}
	
	public boolean isRemoved() {
		return removed;
	}

	public float[] getData() {
		return Renderer.quadData(x, y, size, size, 0, 0, color, 0, 1.0f, 1.0f);
	}
	
}
