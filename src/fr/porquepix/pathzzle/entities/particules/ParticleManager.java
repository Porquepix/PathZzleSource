package fr.porquepix.pathzzle.entities.particules;

import java.util.ArrayList;
import java.util.List;

import fr.porquepix.pathzzle.render.Shader;

public class ParticleManager {

	private static ParticleManager _instance = null;
	
	private List<ParticlesSystem> systems;
	private List<ParticlesSystem> systemsToRemove;
	
	protected ParticleManager() {
		systems = new ArrayList<ParticlesSystem>();
		systemsToRemove =  new ArrayList<ParticlesSystem>();
	}
	
	public static ParticleManager getInstance() {
		if (_instance == null) {
			_instance = new ParticleManager();
		}
		return _instance;
	}
	
	public void addParticles(ParticlesSystem ps) {
		systems.add(ps);
	}
	
	public void update() {
		systemsToRemove.clear();
		for (ParticlesSystem s : systems) {
			s.update();
			if (s.isRemoved()) {
				s.dispose();
				systemsToRemove.add(s);
			}
		}
		systems.removeAll(systemsToRemove);
	}
	
	public void render() {
		Shader.PARTICLE.bind();
		for (ParticlesSystem s : systems) {
			s.render();
		}
	}

	public void dispose() {
		for (ParticlesSystem s : systems) {
			s.dispose();
		}
		systemsToRemove.clear();
		systems.clear();
		_instance = null;
	}
	
}
