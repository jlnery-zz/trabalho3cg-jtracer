package org.jtrace.interceptor;

import java.awt.image.BufferedImage;

import org.jtrace.Hit;
import org.jtrace.Jay;
import org.jtrace.Scene;
import org.jtrace.Tracer;
import org.jtrace.TracerInterceptor;
import org.jtrace.geometry.GeometricObject;
import org.jtrace.geometry.Plane;
import org.jtrace.geometry.Triangle;
import org.jtrace.lights.Light;
import org.jtrace.primitives.ColorRGB;
import org.jtrace.primitives.Point3D;
import org.jtrace.primitives.Vector3D;
import org.jtrace.shader.Shader;

public class TextureInteceptor implements TracerInterceptor {
	private BufferedImage texturePly;
	private BufferedImage texturePlane;
	
	private static TracerInterceptor instance;


	private TextureInteceptor(BufferedImage texture, BufferedImage texturePlane) {
		this.texturePly = texture;
		this.texturePlane = texturePlane;
	}
	
	public static TracerInterceptor getInstance(BufferedImage texture, BufferedImage texturePlane){
		if(instance == null){
			instance = new TextureInteceptor(texture, texturePlane);
		}else {
			((TextureInteceptor) instance).setTexturePly(texture);
			((TextureInteceptor) instance).setTexturePlane(texturePlane);
		}
		
		return instance;
	}


	public void setTexturePly(BufferedImage texturePly) {
		this.texturePly = texturePly;
		
	}

	public void setTexturePlane(BufferedImage texturePlane) {
		this.texturePlane = texturePlane;
		
	}

	@Override
	public void init(Tracer tracer, Scene scene) {		
	}

	@Override
	public void beforeShade(Light light, ColorRGB color) {
		
	}

	@Override
	public boolean shouldShade(Shader shader, Light light, Hit hit, Jay jay,
			GeometricObject object) {
		if (object.getClass().equals(Triangle.class)) {
			Point3D hitPoint = hit.getPoint(jay);
			Vector3D radius = new Vector3D(Point3D.ORIGIN, hitPoint).normal();
			Vector3D northPole = new Vector3D(Point3D.ORIGIN, new Point3D(0,
					radius.module(), 0));
			Vector3D eastPole = new Vector3D(Point3D.ORIGIN, new Point3D(
					radius.module(), 0, 0));

			Double phi = Math.acos(-northPole.dot(radius));

			double v = phi / Math.PI;
			double theta = (Math.acos(radius.dot(eastPole) / Math.sin(phi)))
					/ (2 * Math.PI);
			double u;
			if (northPole.cross(eastPole).dot(radius) > 0) {
				u = theta;
			} else {
				u = 1 - theta;
			}
			int upos;
			int vpos;
			upos = Integer.valueOf(new Long(
					Math.round((texturePly.getWidth() - 1) * u)).toString());
			vpos = Integer.valueOf(new Long(
					Math.round((texturePly.getHeight() - 1) * v)).toString());
			object.getMaterial().setColor(new ColorRGB(texturePly.getRGB(upos, vpos)));			
		}else if(object.getClass().equals(Plane.class)){
			Point3D hitPoint = hit.getPoint(jay);
			double u = Math.abs(hitPoint.getX());
			double v = Math.abs(hitPoint.getZ());
			
			int upos = Integer.valueOf(new Long(
					Math.round(u)%texturePlane.getWidth()).toString());
			
			int vpos = Integer.valueOf(new Long(
					Math.round(v)%texturePlane.getHeight()).toString());
			
			object.getMaterial().setColor(new ColorRGB(texturePlane.getRGB(upos, vpos)));
			
		}
		return true;
	}

	@Override
	public void afterShade(Light light, ColorRGB color) {
		// TODO Auto-generated method stub
		
	}

}
