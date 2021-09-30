package com.omri.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import com.omri.renderer.Shader;
import com.omri.util.Time;
public class LevelEditorScene extends Scene{
	private String vertexShaderSrc = "#version 330 core\r\n"
			+ "\r\n"
			+ "layout (location=0) in vec3 aPos;\r\n"
			+ "layout (location=1) in vec4 aColor;\r\n"
			+ "\r\n"
			+ "out vec4 fColor;\r\n"
			+ "\r\n"
			+ "void main(){\r\n"
			+ "	fColor = aColor;\r\n"
			+ "	gl_Position = vec4(aPos, 1.0);\r\n"
			+ "}";
	private String fragmentShaderSrc = "#version 330 core\r\n"
			+ "\r\n"
			+ "in vec4 fColor;\r\n"
			+ "\r\n"
			+ "out vec4 color;\r\n"
			+ "\r\n"
			+ "void main(){\r\n"
			+ "	color = fColor;\r\n"
			+ "}";
	
	private int vertexID, fragmentID, shaderProgram;
	
	private float[] vertexArray = {
		// position				// color
		 100.5f, -50.5f, 0.0f,		1.0f, 0.0f, 0.0f, 1.0f,	// bottom right  0
		-0.5f,  100.5f, 0.0f,		0.0f, 1.0f, 0.0f, 1.0f,	// top left      1
		 100.5f,  100.5f, 0.0f, 	0.0f, 0.0f, 1.0f, 1.0f, // top right     2
		-0.5f, -0.5f, 0.0f, 	1.0f, 1.0f, 0.0f, 1.0f  // bottom left   3
	};
	
	// important : must be in counter-clockwise order
	/*
	 * 			x 			x
	 * 
	 * 
	 * 			x			x
	 */
	private int[] elementArray = {
		2, 1, 0, // top right triangle
		0, 1, 3	 // bottom left triangle
	};
	
	private int vaoID, vboID, eboID;
	
	private Shader defaultShader;
	
	public LevelEditorScene() {
		
	}
	
	@Override
	public void init() {
		this.camera = new Camera(new Vector2f());
		defaultShader = new Shader("assets/shaders/default.glsl");
		defaultShader.compile();
		// Generate VAO (vertex attribute), vbo (vertex buffer), ebo (element buffer) objects, and send to GPU
		vaoID = glGenVertexArrays();
		glBindVertexArray(vaoID);
		
		// create a float buffer of vertices
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexArray.length);
		vertexBuffer.put(vertexArray).flip();
		
		// create vbo and upload the vertex buffer
		vboID = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboID);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
		
		// create the indices and upload
		IntBuffer elementBuffer = BufferUtils.createIntBuffer(elementArray.length).put(elementArray).flip();
		eboID = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, elementBuffer, GL_STATIC_DRAW);
		
		// add vertex attribute pointers
		int positionsSize = 3;
		int colorSize = 4;
		int floatSizeBytes = 4;
		int vertexSizeBytes = (positionsSize + colorSize) * floatSizeBytes; 
		glVertexAttribPointer(0, positionsSize, GL_FLOAT, false, vertexSizeBytes, 0);
		glEnableVertexAttribArray(0);
		
		glVertexAttribPointer(1, colorSize, GL_FLOAT, false, vertexSizeBytes, positionsSize * floatSizeBytes);
		glEnableVertexAttribArray(1);
		
		
	}

	@Override
	public void update(float dt) {
		camera.position.x -= dt * 30.0f;
		camera.position.y -= dt* 30.0f;
		// Bind shader program
		defaultShader.use();
		
		defaultShader.uploadMat4f("uProjection", camera.getProjectionMatrix());
		defaultShader.uploadMat4f("uView", camera.getViewMatrix());
		defaultShader.uploadFloat("uTime", Time.getTime());
		
		// Bind vao that we're using
		glBindVertexArray(vaoID);
		
		// Enable the vertex attribute pointers
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		glDrawElements(GL_TRIANGLES, elementArray.length, GL_UNSIGNED_INT, 0);
		
		// unbind everything
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		
		glBindVertexArray(0);
		defaultShader.detach();
	}
}
