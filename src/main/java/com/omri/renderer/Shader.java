package com.omri.renderer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Shader {
	
	private int shaderProgramID;
	private String vertexSource;
	private String fragmentSource;	
	private String filePath;
	
	public Shader(String filePath) {
		this.filePath = filePath;
		
		try {
			String source = new String(Files.readAllBytes(Paths.get(filePath)));
			String[] splitStrings = source.split("(#type)( )+([a-zA-Z]+)");
			//System.out.println(Arrays.asList(splitStrings).toString());
			// find the first pattern after #type 'pattern'
			int index = source.indexOf("#type") + 6;
			int eol = source.indexOf("\r\n", index);
			String firstPatternString = source.substring(index, eol).trim();
			
			// find second
			index = source.indexOf("#type", eol) + 6;
			eol = source.indexOf("\r\n", index);
			String secondPatternString = source.substring(index, eol).trim();
			
			System.out.println(firstPatternString);
			System.out.println(secondPatternString);
			
			if(firstPatternString.equals("vertex")) {
				vertexSource = splitStrings[1];
				
			}
			else if(firstPatternString.equals("fragment")) {
				fragmentSource = splitStrings[1];
			}
			else {
				throw new IOException("Unexpected token " + firstPatternString);
			}
			
			if(secondPatternString.equals("vertex")) {
				vertexSource = splitStrings[2];
				
			}
			else if(secondPatternString.equals("fragment")) {
				fragmentSource = splitStrings[2];
			}
			else {
				throw new IOException("unexpected token " + secondPatternString);
			}
			System.out.println(filePath);
			
			System.out.println(vertexSource);
			//System.out.println(fragmentSource);
		}
		catch(IOException e) {
			e.printStackTrace();
			assert false : "Error, could not open file for shader: " + filePath;
		}
	}
	
	public void compile() {
		int vertexID, fragmentID;
		// compile and link shaders
		
		// load and compile vertex shader
		vertexID = glCreateShader(GL_VERTEX_SHADER);
		
		// pass shader src to gpu
		glShaderSource(vertexID, vertexSource);
		glCompileShader(vertexID);
		
		// check for errors in compilation
		int success = glGetShaderi(vertexID, GL_COMPILE_STATUS);
		if(success == GL_FALSE) {
			int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
			System.out.println("Error: defaultShader compilation failed " + filePath);
			System.out.println(glGetShaderInfoLog(vertexID, len));
			assert false : "";
		}
		
		// load and compile fragment shader
		fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
		
		// pass shader src to gpu
		glShaderSource(fragmentID, fragmentSource);
		glCompileShader(fragmentID);
		
		// check for errors in compilation
		success = glGetShaderi(fragmentID, GL_COMPILE_STATUS);
		if(success == GL_FALSE) {
			int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
			System.out.println("Error: Fragment shader compilation failed " + filePath);
			System.out.println(glGetShaderInfoLog(fragmentID, len));
			assert false : "";
		}
		// link shaders and check for errors
		shaderProgramID = glCreateProgram();
		glAttachShader(shaderProgramID, vertexID);
		glAttachShader(shaderProgramID, fragmentID);
		glLinkProgram(shaderProgramID);
		
		// check for linking errors
		success = glGetProgrami(shaderProgramID, GL_LINK_STATUS);
		
		if(success == GL_FALSE) {
			int len = glGetProgrami(shaderProgramID, GL_INFO_LOG_LENGTH);
			System.out.println("Error: default shader linking failed " + filePath);
			System.out.println(glGetProgramInfoLog(shaderProgramID, len));
		}
	}
	
	public void use() {
		// Bind shader program
		glUseProgram(shaderProgramID);
	}
	
	public void detach() {
		// unbind shader program
		glUseProgram(0);
	}
}
