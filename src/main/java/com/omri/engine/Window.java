package com.omri.engine;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import com.omri.util.Time;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

public class Window {
	private int width, height;
	private String title;
	private static Window window = null;
	private long glfwWindow;
	
	private static Scene currentScene;
	
	public float r, g, b, a;
	//private boolean fadeToBlack = false;
	
	private Window() {
		this.width = 1920;
		this.height = 1080;
		this.title = "Mario";
		r = 1.0f;
		g = 1.0f;
		b = 1.0f;
		a = 1.0f;
	}
	
	public static void changeScene(int newScene) {
		switch(newScene) {
		case 0:
			currentScene = new LevelEditorScene();
			
			break;
		case 1:
			currentScene = new LevelScene();
			
			break;
		default:
			assert false : "Unknown scene " + newScene;
			break;
		}
		
		currentScene.init();
	}
	
	public static Window get() {
		if(Window.window == null) {
			Window.window = new Window();
		}
		
		return Window.window;
	}
	
	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");
		init();
		loop();
		
		// Free memory
		glfwFreeCallbacks(glfwWindow);
		glfwDestroyWindow(glfwWindow);
		
		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public void loop() {
		float beginTime = Time.getTime();
		float endTime = Time.getTime();
		float dt = -1.0f;
		while(!glfwWindowShouldClose(glfwWindow)) {
			// poll events
			glfwPollEvents();
			
			glClearColor(r, g, b, a);
			glClear(GL_COLOR_BUFFER_BIT);
			
			if(dt >= 0)
				currentScene.update(dt);
			
			
			
			glfwSwapBuffers(glfwWindow);
			endTime = Time.getTime();
			dt = endTime - beginTime;
			beginTime = endTime;
		}
		
		
	}

	public void init() {
		// Setup error callback
		GLFWErrorCallback.createPrint(System.err).set();
		
		//initialize glfw
		if(!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);
		
		
		// create window
		glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
		if(glfwWindow == NULL) {
			throw new IllegalStateException("Failed to create the GLFW window.");
		}
		
		// set mouse callbacks
		glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
		glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
		glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
		
		//set key callbacks
		glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);
		
		// make openGL context current
		glfwMakeContextCurrent(glfwWindow);
		
		//enable v-sync
		glfwSwapInterval(1);
		
		// make window visible
		glfwShowWindow(glfwWindow);
		
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
		
		Window.changeScene(0);
		
	}
}
