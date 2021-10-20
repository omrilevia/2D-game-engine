package com.omri.components;

import com.omri.engine.GameObject;
import com.omri.engine.MouseListener;
import com.omri.engine.Window;
import static org.lwjgl.glfw.GLFW.*;
public class MouseControls extends Component{
	GameObject holdingObject = null;
	
	public void pickupObject(GameObject go) {
		this.holdingObject = go;
		Window.getScene().addGameObjectToScene(go);
	}
	
	public void place() {
		this.holdingObject = null;
	}
	
	@Override
	public void update(float dt) {
		if(holdingObject != null) {
			holdingObject.transform.position.x = MouseListener.getOrthoX() - 16;
			holdingObject.transform.position.y = MouseListener.getOrthoY();
			
			if(MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
				place();
			}
		}
	}
}
