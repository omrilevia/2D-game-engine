package com.omri.components;

import org.joml.Vector2f;

import com.omri.renderer.Texture;

public class Sprite {
	private Texture texture = null;
	private Vector2f[] texCoords = {
			new Vector2f(1, 1),
			new Vector2f(1, 0),
			new Vector2f(0, 0),
			new Vector2f(0, 1)
	};
//	public Sprite(Texture texture) {
//		this.texture = texture;
//		Vector2f[] texCoords = {
//				new Vector2f(1,1),
//				new Vector2f(1, 0),
//				new Vector2f(0, 0),
//				new Vector2f(0, 1)
//		};
//		this.texCoords = texCoords;
//	}
	
//	public Sprite(Texture texture, Vector2f[] texCoords) {
//		this.texCoords = texCoords;
//		this.texture = texture;
//	}
	
	public Texture getTexture() {
		return this.texture;
	}
	
	public Vector2f[] getTexCoords() {
		return this.texCoords;
	}

	public void setTexture(Texture texture2) {
		this.texture = texture2;
		
	}

	public void setTexCoords(Vector2f[] texCoords2) {
		this.texCoords = texCoords2;
		
	}
}
