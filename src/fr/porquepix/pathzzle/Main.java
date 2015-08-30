package fr.porquepix.pathzzle;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Matrix4f;

import fr.porquepix.pathzzle.audio.AudioClip;
import fr.porquepix.pathzzle.audio.AudioManager;
import fr.porquepix.pathzzle.entities.particules.ParticleManager;
import fr.porquepix.pathzzle.game.GameState;
import fr.porquepix.pathzzle.gui.MenuManager;
import fr.porquepix.pathzzle.input.InputManager;
import fr.porquepix.pathzzle.render.Shader;
import fr.porquepix.pathzzle.render.Sky;
import fr.porquepix.pathzzle.utils.Config;

public class Main {

	private static Main _instance = null;

	public static final int GAME_WIDTH = 1280;
	public static final int GAME_HEIGHT = 930;
	public static final String TITLE = "PathZZle";
	public static final String VERSION = "Alpha 1.5.0";

	private Sky sky;
	
	public Matrix4f projectionMatrix = new Matrix4f();
	public Matrix4f viewMatrix = new Matrix4f();
	public Matrix4f modelMatrix = new Matrix4f();

	protected Main() {
	}

	public static Main getInstance() {
		if (_instance == null) {
			_instance = new Main();
		}
		return _instance;
	}

	public void initDisplay() {
		try {
			Display.setTitle(TITLE + " (" + VERSION + ")");
			Display.setDisplayMode(new DisplayMode(GAME_WIDTH, GAME_HEIGHT));
			Display.setResizable(false);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public void init() {
		initDisplay();

		System.out.println("LWJGL Version : " + Sys.getVersion());
		System.out.println("GPU : " + Display.getAdapter());

		initOpenGL();

		loadSounds();

		loadStuff();
	}

	private void loadSounds() {
		AudioManager.getInstance().addMusic(
				new AudioClip(0, "/music/main.wav", true));
		AudioManager.getInstance().addSounds(
				new AudioClip(1, "/sounds/menu_confirm.wav"));
		AudioManager.getInstance().addSounds(
				new AudioClip(2, "/sounds/abort.wav"));
		AudioManager.getInstance().addSounds(
				new AudioClip(3, "/sounds/right.wav"));
		AudioManager.getInstance().addSounds(
				new AudioClip(4, "/sounds/win.wav"));
		AudioManager.getInstance().addSounds(
				new AudioClip(5, "/sounds/break.wav"));

		AudioManager.getInstance().setMusicVolume(
				(int) Config.getInstance().get(Config.MUSIC_LEVEL) / 100f);
		AudioManager.getInstance().setSoundsVolume(
				(int) Config.getInstance().get(Config.SOUNDS_LEVEL) / 100f);
	}

	private void loadStuff() {
		sky = new Sky();
	}

	private void initOpenGL() {
		glShadeModel(GL_SMOOTH);

		glEnable(GL_ALPHA_TEST);

		glEnable(GL_TEXTURE_2D);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);		
	}

	public void startGameLoop() {
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

		AudioManager.getInstance().play(0);

		long before = System.nanoTime();
		double ns = 1000000000.0 / 60;
		long timer = System.currentTimeMillis();

		int frames = 0;

		while (!Display.isCloseRequested()) {
			long now = System.nanoTime();
			double elapsed = now - before;

			if (elapsed > ns) {
				update();
				before += ns;
			}

			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				System.out.println(frames + " FPS");
				frames = 0;
				timer += 1000;
			}

			Display.update();
		}

		dispose();
		Display.destroy();
		System.exit(0);
	}

	public void dispose() {
		Config.getInstance().save();

		ParticleManager.getInstance().dispose();
		MenuManager.getInstance().dispose();
		AudioManager.getInstance().dispose();

		GameState.getInstance().dispose();

		System.gc();
	}

	private void update() {
		InputManager.update();
		ParticleManager.getInstance().update();

		sky.update();

		GameState.getInstance().update();
	}

	private void render() {
		if (Display.wasResized()) {
			glViewport(0, 0, Display.getWidth(), Display.getHeight());
		}

		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		setOrtho2D(0, Display.getWidth(), Display.getHeight(), 0);
		// Reset view and model matrices
        viewMatrix = new Matrix4f();
        modelMatrix = new Matrix4f();
        
		sky.render();

		Shader.MAIN.bind();
		GameState.getInstance().render();

		ParticleManager.getInstance().render();
		 
		viewMatrix = new Matrix4f();
	    modelMatrix = new Matrix4f();
		GameState.getInstance().renderGUI();
	}

	private void setOrtho2D(float left, float right, float bottom, float top) {
		float x_orth = 2 / (right - left);
		float y_orth = 2 / (top - bottom);

		projectionMatrix.m00 = x_orth;
		projectionMatrix.m10 = 0;
		projectionMatrix.m20 = 0;
		projectionMatrix.m30 = -1;
		projectionMatrix.m01 = 0;
		projectionMatrix.m11 = y_orth;
		projectionMatrix.m21 = 0;
		projectionMatrix.m31 = 1;
		projectionMatrix.m02 = 0;
		projectionMatrix.m12 = 0;
		projectionMatrix.m22 = 0;
		projectionMatrix.m32 = 0;
		projectionMatrix.m03 = 0;
		projectionMatrix.m13 = 0;
		projectionMatrix.m23 = 0;
		projectionMatrix.m33 = 1;
	}

	public Sky getSky() {
		return sky;
	}

}
