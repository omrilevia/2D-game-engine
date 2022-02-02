package com.omri.renderer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.omri.engine.Window;
import com.omri.util.AssetPool;
public class DebugDraw {
	private static int MAX_LINES = 500;
	
	private static List<Line2D> lines = new ArrayList<>();
	// 6 floats per vetex, 3 for pos 3 for color, 2 vertices for each line
	
	private static float[] vertexArray = new float[MAX_LINES * 6 * 2];
	private static Shader shader = AssetPool.getShader("assets/shaders/debugLine2D.glsl");
	
	private static int vaoID;
	private static int vboID;
	
	private static boolean started = false;
	
	public static void start() {
		// generate vao
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		
		// create vbo and buffer some memory
		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, vertexArray.length * Float.BYTES, GL_DYNAMIC_DRAW);
		
		// enable the vertex array attributes
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * Float.BYTES, 0);
		glEnableVertexAttribArray(0);
		
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * Float.BYTES, 3 * Float.BYTES);
		glEnableVertexAttribArray(1);
		
		// TODO: set line width
		glLineWidth(2.0f);
	}
	
	public static void beginFrame() {
		if(!started) {
			start();
			started = true;
		}
		
		// remove dead lines
		for(int i = 0; i < lines.size(); i++) {
			if(lines.get(i).beginFrame() < 0) {
				lines.remove(i);
				i--;
			}
		}
	}
	
	public static void draw() {
		if(lines.size() <= 0) {
			return;
		}
		int index = 0;
		for(Line2D line : lines) {
			for(int i = 0; i < 2; i++) {
				Vector2f position = i == 0 ? line.getFrom() : line.getTo();
				Vector3f color = line.getColor();
				
				// load position
				vertexArray[index] = position.x;
				vertexArray[index+1] = position.y;
				vertexArray[index+2] = -10.0f;
				
				// load color
				vertexArray[index+3] = color.x;
				vertexArray[index+4] = color.y;
				vertexArray[index+5] = color.z;
				
				index+=6;
			}
		}
		
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferSubData(GL_ARRAY_BUFFER, 0, Arrays.copyOfRange(vertexArray, 0, lines.size() * 6 * 2));
		
		// use shader
		shader.uploadMat4f("uProjection", Window.getScene().camera().getProjectionMatrix());
		shader.uploadMat4f("uView", Window.getScene().camera().getViewMatrix());
		
		// bind the vao
		glBindVertexArray(vaoID);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		// draw the batch
		glDrawArrays(GL_LINES, 0, lines.size());
		
		// disable attributes
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glBindVertexArray(0);
		// detach shader
		shader.detach();
	}
	
	public static void addLine2D(Vector2f from, Vector2f to) {
		//TODO: add constants for common colors
		addLine2D(from, to, new Vector3f(0,1,0), 1);
	}
	
	public static void addLine2D(Vector2f from, Vector2f to, Vector3f color) {
		addLine2D(from, to, color, 2);
	}
	
	public static void addLine2D(Vector2f from, Vector2f to, Vector3f color, int lifetime) {
		if(lines.size() >= MAX_LINES)return ;
		DebugDraw.lines.add(new Line2D(from, to, color, lifetime));
	}
}
