package com.omri.components;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.omri.engine.Component;
import com.omri.engine.Transform;
import com.omri.renderer.Texture;

public class SpriteRenderer extends Component{
	private Sprite sprite;
	private Vector4f color;
	
	/*
	 * (0,1)
	 * (0,0)
	 * (1,1)
	 * (1,0)
	 */
	
	private Transform lastTransform;
	private boolean isDirty = false;
	
	public  SpriteRenderer(Vector4f color) {
		this.color = color;
		this.sprite = new Sprite(null);
		
	}
	
	public SpriteRenderer(Sprite sprite) {
		this.sprite = sprite;
		this.color = new Vector4f(1,1,1,1);
	}
	
	@Override
	public void start() {
		this.lastTransform = gameObject.transform.copy();
	}

	@Override
	public void update(float dt) {
		if(!this.lastTransform.equals(this.gameObject.transform)) {
			this.gameObject.transform.copy(this.lastTransform);
			isDirty = true;
		}
		
		//else isDirty = false;
		
	}
	
	public Vector4f getColor() {
		return this.color;
	}
	
	public Texture getTexture() {
		return sprite.getTexture();
		
	}
	
	public Vector2f[] getTexCoords() {
		return sprite.getTexCoords();
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
		this.isDirty = true;
	}
	
	public void setColor(Vector4f color) {
		this.color.set(color);
		if(!this.color.equals(color)) {
			this.isDirty = true;
			this.color.set(color);
		}
			//this.isDirty = true;
	}
	
	public boolean isDirty() {
		return this.isDirty;
	}
	
	public void setClean() {
		this.isDirty = false;
	}

}
