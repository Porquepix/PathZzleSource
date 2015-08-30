package fr.porquepix.pathzzle.entities.particules;

import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;

import fr.porquepix.pathzzle.render.Renderer;

public class ParticlesSystem {

	
	private FloatBuffer buffer;
	private int bufferSize;
	private int vbo;
	
	private List<Particle> particles;
	private List<Particle> particlesToRemove;
	private boolean removed;
	
	public ParticlesSystem(int x, int y, int num, Particle p) {
		particles = new ArrayList<Particle>();
		particlesToRemove = new ArrayList<Particle>();
		
		this.removed = false;
		
		for (int i = 0; i < num; i++) {
			particles.add(new Particle(p, x, y));
		}
		
		buffer = BufferUtils.createFloatBuffer(num * 4 * (2 + 4 + 2));	
		createVBO();
	}
	
	private void createVBO() {
		fillBuffer();
		
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, buffer, GL_DYNAMIC_DRAW);
		buffer.clear();
	}

	private void fillBuffer() {
		bufferSize = 0;
		buffer.clear();
		
		for (Particle p : particles) {
			buffer.put(p.getData());
			bufferSize += 4;
		}
		
		buffer.flip();
	}
	
	private void updateVBO() {
		fillBuffer();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferSubData(GL_ARRAY_BUFFER, 0, buffer);
		buffer.clear();
	}

	public void update() {
		particlesToRemove.clear();
		for (Particle p : particles) {
			p.update();
			if (p.isRemoved()) {
				particlesToRemove.add(p);
			}
		}
		particles.removeAll(particlesToRemove);
		if (particles.size() == 0) {
			removed = true;
		}
		updateVBO();
	}

	public void render() {
		Renderer.renderVBO(vbo, bufferSize);
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public void dispose() {
		glDeleteBuffers(vbo);
		buffer.clear();
		particlesToRemove.clear();
		particles.clear();
	}
	
}
