package com.omri.scenes;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import com.omri.components.MouseControls;
import com.omri.components.RigidBody;
import com.omri.components.Sprite;
import com.omri.components.SpriteRenderer;
import com.omri.components.Spritesheet;
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
	
	private Spritesheet sprites;
	MouseControls mouseControls = new MouseControls();
	public LevelEditorScene() {
		
	}
	
	@Override
	public void init() {
		loadResources();
		this.camera = new Camera(new Vector2f(-250,0));
		sprites = AssetPool.getSpriteSheet("assets/images/spritesheets/decorationsAndBlocks.png");
		//DebugDraw.addLine2D(new Vector2f(0, 0), new Vector2f(800, 800), new Vector3f(1,0, 0), 1200);
		//DebugDraw.addLine2D(new Vector2f(0, 0), new Vector2f(800, 800), new Vector3f(1,0, 0), 1200);
		if(levelLoaded) {
			this.activeGameObject = gameObjects.get(0);
			//this.activeGameObject.addComponent(new RigidBody());
			return;
		}
		
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
		AssetPool.addSpriteSheet("assets/images/spritesheets/decorationsAndBlocks.png", 
				new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/decorationsAndBlocks.png"), 16, 16, 81, 0));
		
	}
	
	float t = 0.0f;
	@Override
	public void update(float dt) {
		mouseControls.update(dt);
		
		float x = ((float)Math.sin(t) * 200.0f) + 600;
		float y = ((float)Math.cos(t) * 200.0f) + 400;
		
		t+= 0.05f;
		
		//DebugDraw.addLine2D(new Vector2f(600,400), new Vector2f(x, y), new Vector3f(0,0,1), 1);
		DebugDraw.addLine2D(new Vector2f(600,400), new Vector2f(x, y), new Vector3f(0,0,1), 2);
		for(GameObject go : this.gameObjects) {
			go.update(dt);
		}
		
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
				GameObject object = Prefabs.generateSpriteObject(sprite, spriteWidth, spriteHeight);
				// attach to mouse cursor
				mouseControls.pickupObject(object);
				
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
