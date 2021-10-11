package com.omri.engine;

import org.joml.Vector2f;
import org.joml.Vector4f;

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
		
		sprites = AssetPool.getSpriteSheet("assets/images/spritesheet.png");
		
		obj1 = new GameObject("object1", new Transform(new Vector2f(200,100), new Vector2f(256, 256)), 4);
		obj1.addComponent(new SpriteRenderer(new Vector4f(1,0,0,1)));
		this.addGameObjectToScene(obj1);
		this.activeGameObject = obj1;
		
		obj2 = new GameObject("object2", new Transform(new Vector2f(400,100), new Vector2f(256, 256)), 5);
		obj2.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/blendImage2.png"))));
		this.addGameObjectToScene(obj2);
		//loadResources();
		
	}
	
	private void loadResources() {
		AssetPool.getShader("assets/shaders/default.glsl");
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
