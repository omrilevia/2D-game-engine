package com.omri.engine;

import org.joml.Vector2f;

import com.omri.components.Sprite;
import com.omri.components.SpriteRenderer;

public class Prefabs {
	public static GameObject generateSpriteObject(Sprite sprite, float spriteWidth, float spriteHeight) {
		GameObject block = new GameObject("Sprite_Object_Gen", 
				new Transform(new Vector2f(), new Vector2f(spriteWidth, spriteHeight)), 0);
		
		SpriteRenderer renderer = new SpriteRenderer();
		
		renderer.setSprite(sprite);
		block.addComponent(renderer);
		return block;
	}
}
