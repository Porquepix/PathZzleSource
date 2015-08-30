package fr.porquepix.pathzzle.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import fr.porquepix.pathzzle.utils.Color4f;

public class Renderer {
	
	public static float[] quadData(float x, float y, float width, float height, float offsetX, float offsetY, Color4f color, int texture, float envWidth, float envHeight) {
		
		float xo = texture % (int) envWidth;
		float yo = texture / (int) envWidth;
		
		// position								color									texture map
		return new float[] {
			x + width, y, 						color.r, color.g, color.b, color.a,		(1 + xo) / envWidth, (0 + yo) / envHeight,
			x, y , 								color.r, color.g, color.b, color.a,		(0 + xo) / envWidth, (0 + yo) / envHeight,
			x, y + height, 						color.r, color.g, color.b, color.a,		(0 + xo) / envWidth , (1 + yo) / envHeight,
			x + width, y + height, 				color.r, color.g, color.b, color.a,		(1 + xo) / envWidth, (1 + yo) / envHeight,
		};
		
	}
	
	public static void renderVBO(int vbo, int bufferSize) {			
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glVertexAttribPointer(0, 2, GL_FLOAT, false, (2 + 4 + 2) * 4, 0);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, (2 + 4 + 2) * 4, 2 * 4);
		glVertexAttribPointer(2, 2, GL_FLOAT, false, (2 + 4 + 2) * 4, (2 + 4) * 4);
		
		glDrawArrays(GL_QUADS, 0, bufferSize);
	}	
	
}
