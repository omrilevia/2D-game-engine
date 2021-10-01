package com.omri.components;

import com.omri.engine.Component;

public class SpriteRenderer extends Component{
	boolean firstTime = true;
	@Override
	public void start() {
		System.out.println("I'm starting!");
	}

	@Override
	public void update(float dt) {
		if(firstTime)
		System.out.println("I'm updating!");
		
		firstTime = false;
		
	}

}
