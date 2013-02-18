package org.jtrace;

import static java.util.Arrays.asList;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.media.j3d.BoundingBox;

import org.jtrace.cameras.Camera;
import org.jtrace.geometry.GeometricObject;
import org.jtrace.lights.Light;
import org.jtrace.primitives.ColorRGB;

public class Scene {
	
    private final Set<GeometricObject> objects = new LinkedHashSet<GeometricObject>();
	private final Set<GeometricObject> withouBoundBox = new LinkedHashSet<GeometricObject>();
    private final Set<BoundingBox> boundingBoxs = new LinkedHashSet<BoundingBox>();
    private Boolean testBoundingBoxs = true;
    private final Set<Light> lights = new LinkedHashSet<Light>();
    private ColorRGB backgroundColor = ColorRGB.BLACK;
    private Camera camera;

    public Scene withBackground(final ColorRGB color) {
        backgroundColor = color;
        return this;
    }

    public Scene add(final Light light) {
        lights.add(light);
        return this;
    }

    public Scene add(final Light... paramLights) {
        lights.addAll(asList(paramLights));
        return this;
    }

    public Scene add(final GeometricObject object) {
        objects.add(object);
        return this;
    }
    
    public Scene add(final BoundingBox object) {
    	boundingBoxs.add(object);
        return this;
    }

    public Scene addWithoutBoundBox(final GeometricObject object) {
    	withouBoundBox.add(object);
        return this;
    }
    
    public Scene add(final GeometricObject... paramObjects) {
        objects.addAll(asList(paramObjects));
        return this;
    }

    public Set<GeometricObject> getObjects() {
        return objects;
    }

    public Set<Light> getLigths() {
        return lights;
    }

    public ColorRGB getBackgroundColor() {
        return backgroundColor;
    }

    public Camera getCamera() {
        return camera;
    }

    public Scene setCamera(final Camera camera) {
        this.camera = camera;
        return this;
    }

	public Boolean getTestBoundingBoxs() {
		return testBoundingBoxs;
	}

	public void setTestBoundingBoxs(boolean testBoundingBoxs){
		this.testBoundingBoxs = testBoundingBoxs;
	}
	
    public Set<GeometricObject> getWithouBoundBox() {
		return withouBoundBox;
	}

	public Set<BoundingBox> getBoundingBoxs() {
		return boundingBoxs;
	}
	
}
