package fr.porquepix.pathzzle.gui;

import java.nio.FloatBuffer;

public abstract class GuiComponent {
	
	protected FloatBuffer buffer;
	protected int bufferSize;
	protected int vbo;
	protected boolean updateVBO = false;
	
	public abstract void update();
	public abstract void renderGUI();
	
}
