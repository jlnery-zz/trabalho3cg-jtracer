package org.jtrace.geometry;

import org.jtrace.Hit;
import org.jtrace.Jay;
import org.jtrace.Material;
import org.jtrace.NotHit;
import org.jtrace.primitives.Point3D;
import org.jtrace.primitives.Vector3D;

public class Triangle extends GeometricObject {

	private Point3D v1;
	private Point3D v2;
	private Point3D v3;

	private Plane plane;

	private Vector3D a12, a23, a31;

	/**
	 * Creates a Triangle with the vertices in points <code>paramV1</code>,
	 * <code>paramV2</code> and <code>paramV3</code>. <br>
	 * 
	 * The Triangle normal is calculated by the cross product between the
	 * vectors a31 and a12,<br>
	 * formed by v1-v3 and v2 - v1, respectively.
	 * 
	 * @param paramV1 the vertice v1.
	 * @param paramV2 the vertice v2.
	 * @param paramV3 the vertice v3.
	 * @param material the Triangle's Material.
	 */
	public Triangle(Point3D paramV1, Point3D paramV2, Point3D paramV3, Material material) {
		super(material);

		v1 = paramV1;
		v2 = paramV2;
		v3 = paramV3;

		a12 = new Vector3D(v1, v2);
		a31 = new Vector3D(v3, v1);
		a23 = new Vector3D(v2, v3);

		Vector3D normal = a31.cross(a12).normal();

		plane = new Plane(v1, normal, getMaterial());
	}
	
	/**
	 * Creates a Triangle with the vertices in points <code>paramV1</code>,
	 * <code>paramV2</code> and <code>paramV3</code> and the normal <code>normal</code>.
	 * 
	 * @param paramV1 the vertice v1.
	 * @param paramV2 the vertice v2.
	 * @param paramV3 the vertice v3.
	 * @param normal the Triangle's normal vector.
	 * @param material the Triangle's Material.
	 */
	public Triangle(Point3D paramV1, Point3D paramV2, Point3D paramV3, Vector3D normal, Material material) {
		super(material);

		v1 = paramV1;
		v2 = paramV2;
		v3 = paramV3;

		a12 = new Vector3D(v1, v2);
		a31 = new Vector3D(v3, v1);
		a23 = new Vector3D(v2, v3);

		plane = new Plane(v1, normal.normal(), getMaterial());
	}


	@Override
	public Hit hit(Jay jay) {
		Hit planeHit = plane.hit(jay);

		if (!planeHit.isHit()) {
			return planeHit;
		}
		
		Point3D p = jay.getOrigin().add( jay.getDirection().multiply( planeHit.getT() ) );
		
		Vector3D v1p = new Vector3D(v1, p);
		Vector3D v2p = new Vector3D(v2, p);
		Vector3D v3p = new Vector3D(v3, p);
		
		Vector3D t1 = a12.cross(v1p);
		Vector3D t2 = a23.cross(v2p);
		Vector3D t3 = a31.cross(v3p);
		
		Double d1 = t1.dot(t2);
		Double d2 = t1.dot(t3);
		
		if (d1 > 0.0 && d2 > 0.0) {
			return new Hit(planeHit.getT(), planeHit.getNormal());
		}
		
		return new NotHit();
	}
}
