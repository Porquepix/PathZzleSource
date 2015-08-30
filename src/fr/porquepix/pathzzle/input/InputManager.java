package fr.porquepix.pathzzle.input;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import fr.porquepix.pathzzle.math.Vec2;

public class InputManager {
	
	public static final int NUM_MOUSEBUTTONS = Mouse.EVENT_SIZE;
	public static final int NUM_KEYCODES = Keyboard.KEYBOARD_SIZE;
	
	private static List<Integer> currentKeys = new ArrayList<Integer>();
	private static List<Integer> downKeys = new ArrayList<Integer>();
	private static List<Integer> upKeys = new ArrayList<Integer>();
	
	private static List<Integer> currentMouse = new ArrayList<Integer>();
	private static List<Integer> downMouse = new ArrayList<Integer>();
	private static List<Integer> upMouse = new ArrayList<Integer>();

	public static void update() {
		upKeys.clear();
		for (int i = 0; i < NUM_KEYCODES; i++) {
			if (!getKey(i) && currentKeys.contains(i)) {
				upKeys.add(i);
			}
		}
		
		downKeys.clear();
		for (int i = 0; i < NUM_KEYCODES; i++) {
			if (getKey(i) && !currentKeys.contains(i)) {
				downKeys.add(i);
			}
		}
		
		currentKeys.clear();
		for (int i = 0; i < NUM_KEYCODES; i++) {
			if (getKey(i)) {
				currentKeys.add(i);
			}
		}
		
		
		upMouse.clear();
		for (int i = 0; i < NUM_MOUSEBUTTONS; i++) {
			if (!getMouse(i) && currentMouse.contains(i)) {
				upMouse.add(i);
			}
		}
		
		downMouse.clear();
		for (int i = 0; i < NUM_MOUSEBUTTONS; i++) {
			if (getMouse(i) && !currentMouse.contains(i)) {
				downMouse.add(i);
			}
		}
		
		currentMouse.clear();
		for (int i = 0; i < NUM_MOUSEBUTTONS; i++) {
			if (getMouse(i)) {
				currentMouse.add(i);
			}
		}
	}
	
	public static boolean getKey(int keyCode) {
		return Keyboard.isKeyDown(keyCode);
	}
	
	public static boolean getKeyDown(int keyCode) {
		return downKeys.contains(keyCode);
	}
	
	public static boolean getKeyeUp(int keyCode) {
		return upKeys.contains(keyCode);
	}
	
	public static boolean getMouse(int mouseButton) {
		return Mouse.isButtonDown(mouseButton);
	}
	
	public static boolean getMouseDown(int mouseButton) {
		return downMouse.contains(mouseButton);
	}
	
	public static boolean getMouseUp(int mouseButton) {
		return upMouse.contains(mouseButton);
	}
	
	public static Vec2 getCenterPos() {
		return new Vec2((float) Display.getWidth() / 2.0f, (float) Display.getHeight() / 2.0f);
	}
	
	public static void centerMouse() {
		setMousePosition(getCenterPos());
	}
	
	public static void setMousePosition(Vec2 pos) {
		Mouse.setCursorPosition((int) pos.x(), (int) pos.y());
	}
	
	public static Vec2 getMousePosition() {
		return new Vec2(Mouse.getX(), Display.getHeight() - Mouse.getY());
	}
	
}
