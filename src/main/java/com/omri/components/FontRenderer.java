package com.omri.components;

public class FontRenderer extends Component{
	
	
	@Override
	public void start() {
		if(gameObject.getComponent(SpriteRenderer.class) != null) {
			System.out.println("found sprite renderer");
		}
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
		
	}

}
