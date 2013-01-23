package org.jtrace.examples;

import org.jtrace.Materials;
import org.jtrace.Scene;
import org.jtrace.Tracer;
import org.jtrace.ViewPlane;
import org.jtrace.cameras.Camera;
import org.jtrace.cameras.PinHoleCamera;
import org.jtrace.geometry.Plane;
import org.jtrace.geometry.Sphere;
import org.jtrace.lights.Light;
import org.jtrace.listeners.ImageListener;
import org.jtrace.listeners.TimeListener;
import org.jtrace.primitives.ColorRGB;
import org.jtrace.primitives.Point3D;
import org.jtrace.primitives.Vector3D;
import org.jtrace.shader.Shaders;

public class FourSpheresTwoPlanesPerspectiveExample {
    public static void main(final String[] args) {
        final ViewPlane viewPlane = new ViewPlane(1920, 1080);

        final Point3D lookAt = Point3D.ORIGIN;
        final Point3D eye = new Point3D(0, 0, 100);
        final Vector3D up = new Vector3D(0, 1, 0);

        final Point3D lightPosition = new Point3D(0, 20, -10);

        final Point3D planePoint = new Point3D(0, -10.1, 0);
        final Vector3D planeNormal = new Vector3D(0, 1, 0);

        final Sphere red = new Sphere(new Point3D(30, 0, 0), 10, Materials.metallic(ColorRGB.RED));
        final Sphere blue = new Sphere(new Point3D(-30, 0, 0), 10, Materials.metallic(ColorRGB.BLUE));
        final Sphere originSphere = new Sphere(Point3D.ORIGIN, 0.3f, Materials.metallic(ColorRGB.PURPLE));
        final Sphere lightSphere = new Sphere(lightPosition, 0.3f, Materials.metallic(ColorRGB.WHITE));

        final Plane lowerPlane = new Plane(planePoint, planeNormal, Materials.metallic(ColorRGB.YELLOW));
        final Plane upperPlane = new Plane(new Point3D(0, 30, 0), planeNormal.multiply(-1), Materials.metallic(ColorRGB.GREEN));
        final Light light = new Light(lightPosition);

        final Camera pinHoleCamera = new PinHoleCamera(eye, lookAt, up);
        pinHoleCamera.setZoomFactor(10);

        final Scene scene = new Scene().add(blue, red, originSphere, lightSphere, lowerPlane, upperPlane).add(light).setCamera(pinHoleCamera);

        final Tracer tracer = new Tracer();

        tracer.addListeners(new ImageListener("4Spheres2Planes.png", "png"), new TimeListener());
        tracer.addShaders(Shaders.ambientShader(), Shaders.diffuseShader(), Shaders.specularShader(4));

        tracer.render(scene, viewPlane);
    }
}
