package org.jtrace.examples.io;

import java.awt.Toolkit;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.jtrace.Materials;
import org.jtrace.Scene;
import org.jtrace.Tracer;
import org.jtrace.ViewPlane;
import org.jtrace.cameras.Camera;
import org.jtrace.cameras.PinHoleCamera;
import org.jtrace.geometry.Triangle;
import org.jtrace.io.PlyReader;
import org.jtrace.lights.Light;
import org.jtrace.listeners.ImageListener;
import org.jtrace.listeners.TimeListener;
import org.jtrace.primitives.ColorRGB;
import org.jtrace.primitives.Point3D;
import org.jtrace.primitives.Vector3D;
import org.jtrace.shader.Shaders;

public class BeethovenPLYExample {
	public static void main(String[] args) throws IOException {
		InputStream is = SimplePLYExample.class.getResourceAsStream("beethoven.ply");
		List<Triangle> triangles;
 
		triangles = PlyReader.read(is, Materials.metallic(ColorRGB.WHITE));
		Scene scene = new Scene();
		
		for (Triangle t : triangles) {
			scene.add(t);
		}
		
		scene.add(new Light(0, 0, 5));
		
		Camera camera = new PinHoleCamera(new Point3D(0, 0, 10), Point3D.ORIGIN, Vector3D.UNIT_Y);
		
		camera.setZoomFactor(50);
		
		scene.setCamera(camera);
		
		Tracer tracer = new Tracer();
		
		tracer.addShaders(Shaders.ambientShader(), Shaders.diffuseShader(), Shaders.specularShader(64));
		
		tracer.addListeners(new ImageListener("beethoven.png", "png"), new TimeListener());
		
		tracer.render(scene, new ViewPlane(1280, 768));
		
		Toolkit.getDefaultToolkit().beep();
	}

}
