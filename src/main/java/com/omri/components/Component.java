package com.omri.components;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.joml.Vector3f;
import org.joml.Vector4f;

import com.omri.engine.GameObject;

import imgui.ImGui;

public abstract class Component {
	private static int ID_COUNTER = 0;
	private int uid = -1;
	public transient GameObject gameObject = null;
	
	public void start() {
		
	}
	
	public void update(float dt) {
		
	}
	
	public void generateId() {
		if(this.uid == -1) {
			this.uid = ID_COUNTER;
			ID_COUNTER++;
		}
	}
	
	public int getUid() {
		return this.uid;
	}
	
	public static void init(int maxId) {
		ID_COUNTER = maxId;
	}
	
	public void imgui() {
		try {
			Field[] fields = this.getClass().getDeclaredFields();
			for(Field f : fields) {
				boolean isTransient = Modifier.isTransient(f.getModifiers());
				if(isTransient) {
					continue;
				}
				boolean isPrivate = Modifier.isPrivate(f.getModifiers());
				if(isPrivate) {
					f.setAccessible(true);
				}
				Class type = f.getType();
				Object value = f.get(this);
				String name = f.getName();
				
				if(type == int.class) {
					int val = (int)value;
					int[] imInt = {val};
					if(ImGui.dragInt(name + ": ", imInt)) {
						f.set(this, imInt[0]);
					}
				}
				else if(type == float.class) {
					float val = (float)value;
					float[] imFloat = {val};
					if(ImGui.dragFloat(name + ": ", imFloat)) {
						f.set(this, imFloat[0]);
					}
				}
				else if(type == boolean.class) {
					boolean val = (boolean)value;
					
					if(ImGui.checkbox(name + ": " , val)) {
						val = !val;
						f.set(this, !val);
					}
				}
				else if(type == Vector3f.class) {
					Vector3f val = (Vector3f)value;
					float[] imVec = {val.x, val.y, val.z};
					if(ImGui.dragFloat3(name + ": ", imVec)) {
						val.set(imVec[0], imVec[1], imVec[2]);
					}
				}
				else if(type == Vector4f.class) {
					Vector4f val = (Vector4f)value;
					float[] imVec = {val.x, val.y, val.z, val.w};
					if(ImGui.dragFloat4(name + ": ", imVec)) {
						val.set(imVec[0], imVec[1], imVec[2], imVec[3]);
					}
				}
				
				if(isPrivate) {
					f.setAccessible(false);
				}
			}
		}
		catch(IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
