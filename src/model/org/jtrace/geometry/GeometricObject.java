package org.jtrace.geometry;

import org.jtrace.Hit;
import org.jtrace.Jay;
import org.jtrace.Material;

public abstract class GeometricObject {
	
	private Material material;
	private Boolean inShadow; 
	
	public GeometricObject(Material material) {
		this.material = material;
		setInShadow(false);
	}

	public abstract Hit hit(Jay jay);
	
	public Material getMaterial() {
		return material;
	}

	public Boolean getInShadow() {
		return inShadow;
	}

	public void setInShadow(Boolean inShadow) {
		this.inShadow = inShadow;
	}

}
