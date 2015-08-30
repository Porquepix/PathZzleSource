package fr.porquepix.pathzzle.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

import fr.porquepix.pathzzle.Main;
import fr.porquepix.pathzzle.math.Vec3;

public class Shader {
	
	public static final Shader MAIN = new Shader("/shaders/main.vert", "/shaders/main.frag");
	public static final Shader PARTICLE = new Shader("/shaders/particle.vert", "/shaders/particle.frag");
	public static final Shader SKY = new Shader("/shaders/sky.vert", "/shaders/sky.frag");
	public static final Shader HUD = new Shader("/shaders/hud.vert", "/shaders/hud.frag");
	
	private static int LAST_SHADER = 0;
	
	public int program;
	
	public Shader(String vertex, String fragment) {
		program = glCreateProgram();
		
		if (program == GL_FALSE) {
			System.out.println("Shader program error !");
			System.exit(1);
		}
		
		createShader(loadShader(vertex), GL_VERTEX_SHADER);
		createShader(loadShader(fragment), GL_FRAGMENT_SHADER);
		
		glLinkProgram(program);
		glValidateProgram(program);
	}

	private void createShader(String source, int type) {
		int shader = glCreateShader(type);
		
		if (shader == GL_FALSE) {
			System.out.println("Shader error: " + shader);
			System.exit(1);
		}
		
		glShaderSource(shader, source);
		glCompileShader(shader);
		
		if (glGetShaderi(shader, GL_COMPILE_STATUS) == GL_FALSE) {
			System.out.println(glGetShaderInfoLog(shader, 4096));
			System.exit(1);
		}
		
		glAttachShader(program, shader);
	}


	private String loadShader(String input) {
		String r = "";
		
		try {
			InputStream is = Shader.class.getResourceAsStream(input);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String buffer = "";
			while ((buffer = reader.readLine()) != null) {
				r += buffer + "\n";
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return r;
	}
	
	public void setUniform(String name, int v) {
		glUniform1i(glGetUniformLocation(program, name), v);
	}
	
	public void setUniform(String name, float v) {
		glUniform1f(glGetUniformLocation(program, name), v);
	}
	
	public void setUniform(String name, Vec3 v) {
		glUniform3f(glGetUniformLocation(program, name), v.x(), v.y(), v.z());
	}
	
	public void setUniform(String name, Matrix4f m) {
		FloatBuffer fb = BufferUtils.createFloatBuffer(16);
		m.store(fb); 
		fb.flip();
		glUniformMatrix4(glGetUniformLocation(program, name), false, fb);
	}
	
	public void bind() {
		if (LAST_SHADER == program) return;
		
		LAST_SHADER = program;
		
		glUseProgram(program);
		
		setUniform("projectionMatrix", Main.getInstance().projectionMatrix);
		setUniform("viewMatrix", Main.getInstance().viewMatrix);
		setUniform("modelMatrix", Main.getInstance().modelMatrix);
	}

	public static void unbind() {
		LAST_SHADER = 0;
		glUseProgram(0);
	}

}
