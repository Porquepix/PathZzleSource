package fr.porquepix.pathzzle.utils;

public class Animation {

	private int length;
	private int speed;
	private boolean loop;
	
	private int frame = 0;
	private boolean playing = false;
	private int time = 0;
	
	public Animation(int length, int speed, boolean loop) {
		this.length = length;
		this.speed = speed;
		this.loop = loop;
	}
	
	public void update() {
		if (playing) {
			time++;
			if (time > speed) {
				frame++;
				if (frame >= length) {
					if (loop) frame = 0;
					else frame = length - 1;
				}
				time = 0;
			}
		}
	}
	
	public Animation play() {
		playing = true;
		return this;
	}
	
	public Animation pause() {
		playing = false;
		return this;
	}
	
	public Animation stop() {
		playing = false;
		frame = 0;
		return this;
	}
	
	public int getCurrentFrame() {
		return frame;
	}

	public Animation copy() {
		return new Animation(length, speed, loop);
	}
	
}
