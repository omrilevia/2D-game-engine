package com.omri.components;

import org.joml.Vector2f;
import org.joml.Vector4f;

import com.omri.engine.Component;
import com.omri.engine.Transform;
import com.omri.renderer.Texture;

import imgui.ImGui;

public class SpriteRenderer extends Component{
	private Sprite sprite = new Sprite();
	private Vector4f color = new Vector4f(1,1,1,1);
	
	/*
	 * (0,1)
	 * (0,0)
	 * (1,1)
	 * (1,0)
	 */
	
	private transient Transform lastTransform;
	private transient boolean isDirty = false;
	
//	public  SpriteRenderer(Vector4f color) {
//		this.color = color;
//		this.sprite = new Sprite(null);
//		this.isDirty = true;
//		
//	}
//	
//	public SpriteRenderer(Sprite sprite) {
//		this.sprite = sprite;
//		this.color = new Vector4f(1,1,1,1);
//		this.isDirty = true;
//	}
	
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
	
	@Override
	public void imgui() {
		//ImGui.text("Color Picker: ");
		float[] imColor = {color.x, color.y, color.z, color.w};
		if(ImGui.colorPicker4("Color picker: ", imColor)) {
			this.color.set(imColor[0], imColor[1], imColor[2], imColor[3]);
			this.isDirty = true;
		}
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
		//this.color.set(color);
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
