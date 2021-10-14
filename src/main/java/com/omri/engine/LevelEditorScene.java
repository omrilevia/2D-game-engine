package com.omri.engine;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.omri.components.RigidBody;
import com.omri.components.Sprite;
import com.omri.components.SpriteRenderer;
import com.omri.components.Spritesheet;
import com.omri.util.AssetPool;

import imgui.ImGui;
public class LevelEditorScene extends Scene{
	private GameObject obj1, obj2;
	private int spriteIndex = 0;
	private float spriteFlipTime = 0.2f;
	private float spriteFlipTimeLeft = 0.0f;
	private Spritesheet sprites;
	public LevelEditorScene() {
		
	}
	
	@Override
	public void init() {
		loadResources();
		this.camera = new Camera(new Vector2f());
		if(levelLoaded) {
			this.activeGameObject = gameObjects.get(0);
			return;
		}
		sprites = AssetPool.getSpriteSheet("assets/images/spritesheet.png");
		SpriteRenderer obj1Renderer = new SpriteRenderer();
		obj1Renderer.setColor(new Vector4f(1, 0, 0, 1));
		obj1 = new GameObject("object1", new Transform(new Vector2f(200,200), new Vector2f(256, 256)), 4);
		obj1.addComponent(obj1Renderer);
		obj1.addComponent(new RigidBody());
		this.addGameObjectToScene(obj1);
		this.activeGameObject = obj1;
		
		obj2 = new GameObject("object2", new Transform(new Vector2f(400,100), new Vector2f(256, 256)), 5);
		SpriteRenderer obj2reRenderer = new SpriteRenderer();
		Sprite obj2Sprite = new Sprite();
		obj2Sprite.setTexture(AssetPool.getTexture("assets/images/blendImage2.png"));
		obj2reRenderer.setSprite(obj2Sprite);
		
		
		obj2.addComponent(obj2reRenderer);
		this.addGameObjectToScene(obj2);
		//loadResources();
		
		
		
		
	}
	
	private void loadResources() {
		AssetPool.getShader("assets/shaders/default.glsl");
		AssetPool.getTexture("assets/images/blendImage2.png");
		AssetPool.addSpriteSheet("assets/images/spritesheet.png", new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"), 16, 16, 26, 0));
		
	}

	@Override
	public void update(float dt) {
		
		for(GameObject go : this.gameObjects) {
			go.update(dt);
		}
		
		this.renderer.render();
	}
	
	@Override
	public void imgui() {
		ImGui.begin("test window");
		ImGui.text("some text");
		ImGui.end();
	}
}
