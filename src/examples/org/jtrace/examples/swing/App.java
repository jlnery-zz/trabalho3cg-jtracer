package org.jtrace.examples.swing;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.jtrace.Material;
import org.jtrace.Materials;
import org.jtrace.Scene;
import org.jtrace.cameras.Camera;
import org.jtrace.cameras.PinHoleCamera;
import org.jtrace.examples.io.SimplePLYExample;
import org.jtrace.geometry.Plane;
import org.jtrace.geometry.Sphere;
import org.jtrace.geometry.Triangle;
import org.jtrace.io.PlyReader;
import org.jtrace.lights.Light;
import org.jtrace.primitives.ColorRGB;
import org.jtrace.primitives.Point3D;
import org.jtrace.primitives.ReflectanceCoefficient;
import org.jtrace.primitives.Vector3D;

public class App {

    private static MainWindow window = new MainWindow();

    public static void main(final String[] args) {
        window.setVisible(true);
    }

    public static Scene createScene() {
    	
    	InputStream is = SimplePLYExample.class.getResourceAsStream("simple.ply");
		List<Triangle> triangles = null;
 
		try {
			triangles = PlyReader.read(is, Materials.metallic(ColorRGB.GREEN));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene = new Scene();
		
		for (Triangle t : triangles) {
			scene.add(t);
		}
    	
        final Point3D lookAt = Point3D.ORIGIN;
        
        // final Point3D eye = new Point3D(-15, 0, 100);
        final Point3D eye = new Point3D(0, 1.2, 100);
        
        final Vector3D up = new Vector3D(0, 1, 0);

        final Point3D centerRed  = new Point3D(0, 0, -10);
        final Point3D centerBlue = new Point3D(-10, 0, -20);

        final Point3D planePoint = new Point3D(0, 20, 0);
        final Vector3D planeNormal = new Vector3D(0, -1, 0);

        final ReflectanceCoefficient kAmbient = new ReflectanceCoefficient(0.07, 0.07, 0.07);
        final ReflectanceCoefficient kDiffuse = new ReflectanceCoefficient(0.3, 0.3, 0.3);

        final Material redMaterial = new Material(ColorRGB.RED, kAmbient, kDiffuse);
        final Material blueMaterial = new Material(ColorRGB.BLUE, kAmbient, kDiffuse);
        //final Material planeMaterial1 = new Material(ColorRGB.YELLOW, kAmbient, kDiffuse);
        final Material planeMaterial2 = new Material(ColorRGB.RED, kAmbient, kDiffuse);
        
        final Sphere red = new Sphere(centerRed, 10, redMaterial);
        final Sphere blue = new Sphere(centerBlue, 10, blueMaterial);

        //final Plane plane1 = new Plane(planePoint, planeNormal, planeMaterial1);
        
        final Plane plane2 = new Plane(planePoint.multiply(-1), planeNormal.multiply(-1), planeMaterial2);

        final Light light = new Light(0, 20, 10);

        final Camera pinHoleCamera = new PinHoleCamera(eye, lookAt, up);
        pinHoleCamera.setZoomFactor(10);
        scene.add(blue, red, /*plane1,*/ plane2).add(light).setCamera(pinHoleCamera);
        return scene;
    }

}
