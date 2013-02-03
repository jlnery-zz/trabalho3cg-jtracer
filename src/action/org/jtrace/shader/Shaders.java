package org.jtrace.shader;

import java.awt.image.BufferedImage;


public class Shaders {
	static Shader ambientShader;
	static Shader diffuseShader;
	static SpecularShader specularShader;
	
	public static Shader ambientShader() {
		if(ambientShader == null){
			ambientShader = new AmbientShader();
		}
		return ambientShader;
	}
	
	public static Shader diffuseShader() {
		if(diffuseShader == null){
			diffuseShader = new DiffuseShader();
		}
		return diffuseShader;
	}
	
	public static Shader specularShader(double specularFactor) {
		if(specularShader == null){
			specularShader = new SpecularShader(specularFactor);
		}else {
			specularShader.setSpecularFactor(specularFactor);
		}
		return specularShader;
	}
}
