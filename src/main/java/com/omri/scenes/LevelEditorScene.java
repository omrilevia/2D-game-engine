package com.omri.scenes;

import com.omri.components.*;
import org.joml.Vector2f;
import org.joml.Vector3f;

import com.omri.engine.Camera;
import com.omri.engine.GameObject;
import com.omri.engine.Prefabs;
import com.omri.engine.Transform;
import com.omri.renderer.DebugDraw;
import com.omri.util.AssetPool;

import imgui.ImGui;
import imgui.ImVec2;
public class LevelEditorScene extends Scene{
	private GameObject obj1, obj2;
	GameObject levelEditorStuff = new GameObject("LevelEditor", new Transform(new Vector2f()), 0);
	private Spritesheet sprites;
	MouseControls mouseControls = new MouseControls();
	public LevelEditorScene() {
		
	}
	
	@Override
	public void init() {
		levelEditorStuff.addComponent(new MouseControls());

		loadResources();
		this.camera = new Camera(new Vector2f(-250,0));
		sprites = AssetPool.getSpriteSheet("assets/images/spritesheets/decorationsAndBlocks.png");
		//DebugDraw.addLine2D(new Vector2f(0, 0), new Vector2f(800, 800), new Vector3f(1,0, 0), 1200);
		//DebugDraw.addLine2D(new Vector2f(0, 0), new Vector2f(800, 800), new Vector3f(1,0, 0), 1200);
		if(levelLoaded) {
			if (gameObjects.size() > 0) {
				this.activeGameObject = gameObjects.get(0);
			}
		}
		
//		SpriteRenderer obj1Renderer = new SpriteRenderer();
//		obj1Renderer.setColor(new Vector4f(1, 0, 0, 1));
//		obj1 = new GameObject("object1", new Transform(new Vector2f(200,200), new Vector2f(256, 256)), 4);
//		obj1.addComponent(obj1Renderer);
//		obj1.addComponent(new RigidBody());
//		this.addGameObjectToScene(obj1);
//		this.activeGameObject = obj1;
//
//		obj2 = new GameObject("object2", new Transform(new Vector2f(400,100), new Vector2f(256, 256)), 5);
//		SpriteRenderer obj2reRenderer = new SpriteRenderer();
//		Sprite obj2Sprite = new Sprite();
//		obj2Sprite.setTexture(AssetPool.getTexture("assets/images/blendImage2.png"));
//		obj2reRenderer.setSprite(obj2Sprite);
		
		
//		obj2.addComponent(obj2reRenderer);
//		this.addGameObjectToScene(obj2);
		//loadResources();
		
		
		
		
	}
	
	private void loadResources() {
		AssetPool.getShader("assets/shaders/default.glsl");
		AssetPool.getTexture("assets/images/blendImage2.png");
		AssetPool.addSpriteSheet("assets/images/spritesheets/decorationsAndBlocks.png", 
				new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/decorationsAndBlocks.png"), 16, 16, 81, 0));

		for (GameObject g: gameObjects) {
			if (g.getComponent(SpriteRenderer.class) != null) {
				SpriteRenderer spr = g.getComponent(SpriteRenderer.class);
				if (spr.getTexture() != null) {
					spr.setTexture(AssetPool.getTexture(spr.getTexture().getFilePath()));
				}
			}
		}
	}
	
	float t = 0.0f;
	float x = 0.0f;
	float y = 0.0f;
	@Override
	public void update(float dt) {
		levelEditorStuff.update(dt);
		
		//DebugDraw.addBox2D(new Vector2f(400, 200), new Vector2f(64, 32), t, new Vector3f(0, 1, 0), 2);
		//DebugDraw.addCircle(new Vector2f(x, y), 64, new Vector3f(0, 0, 1), 2);
		x += 50.0*dt;
		y += 50.0*dt;
		for(GameObject go : this.gameObjects) {
			go.update(dt);
		}
		t += 40.0f * dt;
		this.renderer.render();
	}
	
	@Override
	public void imgui() {
		ImGui.begin("test window");

		ImVec2 windowPos = new ImVec2();
		ImGui.getWindowPos(windowPos);
		ImVec2 windowSize = new ImVec2();
		ImGui.getWindowSize(windowSize);
		ImVec2 itemSpacing = new ImVec2();
		ImGui.getStyle().getItemSpacing(itemSpacing);
		
		float windowX2 = windowPos.x + windowSize.x;
		for(int i = 0; i < sprites.size(); i++) {
			Sprite sprite = sprites.getSprite(i);
			float spriteWidth = sprite.getWidth() * 2;
			float spriteHeight = sprite.getHeight() * 2;
			int id = sprite.getTexId();
			Vector2f[] texCoords = sprite.getTexCoords();
			
			ImGui.pushID(i);
			if(ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
				GameObject object = Prefabs.generateSpriteObject(sprite, 32, 32);
				levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
				// attach to mouse cursor
				//mouseControls.pickupObject(object);
				
			}
			ImGui.popID();
			
			ImVec2 lastButtonPos = new ImVec2();
			ImGui.getItemRectMax(lastButtonPos);
			float lastButtonX2 = lastButtonPos.x;
			float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
			
			if(i + 1 < sprites.size() && nextButtonX2 < windowX2) {
				ImGui.sameLine();
			}
			
		}
		
		ImGui.end();
	}
}
