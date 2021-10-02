package com.omri.engine;

import java.util.List;

import com.omri.renderer.Renderer;

import java.util.ArrayList;

public abstract class Scene {
	
	protected Camera camera;
	protected Renderer renderer = new Renderer();
	private boolean isRunning = false;
	protected List<GameObject> gameObjects = new ArrayList<>();
	public Scene() {
		
		
		
	}
	
	public void init() {
		
	}
	
	public void start() {
		
		for(GameObject go : gameObjects) {
			go.start();
			this.renderer.add(go);
		}
		isRunning = true;
	}
	
	public void addGameObjectToScene(GameObject go) {
		if(!isRunning) {
			gameObjects.add(go);
		}
		else {
			gameObjects.add(go);
			go.start();
			this.renderer.add(go);
		}
	}
	
	public abstract void update(float dt);
	
	public Camera camera() {
		return this.camera;
	}
	
}
