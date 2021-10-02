package com.omri.components;

import org.joml.Vector4f;

import com.omri.engine.Component;

public class SpriteRenderer extends Component{
	private Vector4f color;
	
	public  SpriteRenderer(Vector4f color) {
		this.color = color;
	}
	@Override
	public void start() {
		
	}

	@Override
	public void update(float dt) {
		
		
	}
	
	public Vector4f getColor() {
		return this.color;
	}

}