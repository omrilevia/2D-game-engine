package com.omri.components;

import org.joml.Vector2f;

import com.omri.renderer.Texture;

public class Sprite {
	private Texture texture = null;
	private float width, height;
	
	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	private Vector2f[] texCoords = {
			new Vector2f(1, 1),
			new Vector2f(1, 0),
			new Vector2f(0, 0),
			new Vector2f(0, 1)
	};
	
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
	
	public int getTexId() {
		return texture == null ? -1 : texture.getId();
	}
}
