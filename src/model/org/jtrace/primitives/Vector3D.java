package org.jtrace.primitives;

/**
 * Basic class to represent a vector in a three-dimensional space.
 * 
 * @author raphaelpaiva
 * @author brunocosta
 * @author flaviocdc
 * 
 */
public class Vector3D {
	public static final Vector3D NULL = new Vector3D(0, 0, 0);
	public static final Vector3D UNIT_Y = new Vector3D(0, 1, 0);
	public static final Vector3D UNIT_X = new Vector3D(1, 0, 0);
	public static final Vector3D UNIT_Z = new Vector3D(0, 0, 1);

	private Point3D coordinate;

	/**
	 * Creates a {@link Vector3D} from a {@link Point3D} representing its
	 * coordinates.
	 * 
	 * @param coordinate
	 *            a {@link Point3D} representing the Vector coordinates.
	 */
	public Vector3D(final Point3D coordinate) {
		this.coordinate = coordinate;
	}

	/**
	 * Creates a {@link Vector3D} from its coordinates.<br>
	 * 
	 * Equivalent to calling <code>Vector3D(new Point3D(x, y, z))</code>.
	 * 
	 * @param x
	 *            axis coordinate
	 * @param y
	 *            axis coordinate
	 * @param z
	 *            axis coordinate
	 */
	public Vector3D(final double x, final double y, final double z) {
		this(new Point3D(x, y, z));
	}

	/**
	 * Creates a {@link Vector3D} from another vector's coordinates. <br>
	 * 
	 * Equivalent to calling <code>Vector3D(v2.getCoordinates())</code>.
	 * 
	 * @param v2
	 *            another vector
	 */
	public Vector3D(final Vector3D v2) {
		this(v2.getCoordinate());
	}

	/**
	 * Creates a {@link Vector3D} from {@link Point3D} a to {@link Point3D} b. <br>
	 * 
	 * Equivalent to calling <code>Vector3D(b.subtract(a))</code>;
	 * 
	 * @param a
	 *            {@link Point3D} where the vector starts.
	 * @param b
	 *            {@link Point3D} where the vector ends.
	 */
	public Vector3D(Point3D a, Point3D b) {
		this(b.subtract(a));
	}

	/**
	 * Multiplies the vector by a scalar value.<br>
	 * 
	 * Suppose a vector A(x, y, z) and a scalar s:<br>
	 * 
	 * s*A = B(s*x, s*y, s*z)
	 * 
	 * @param multiplier
	 *            the scalar value to multiply the {@link Vector3D} by.
	 * @return a new {@link Vector3D} equivalent to
	 *         <code>(this*multiplier)</code>
	 */
	public Vector3D multiply(double multiplier) {
		return new Vector3D(coordinate.getX() * multiplier, coordinate.getY()
				* multiplier, coordinate.getZ() * multiplier);
	}

	/**
	 * Performs the dot product with v2. <br>
	 * 
	 * Suppose the vectors A(x, y, z) and B(u, v, w):<br>
	 * 
	 * A.B = x*u + y*v + z*w
	 * 
	 * @param v2
	 *            the {@link Vector3D} to perform the dot product with.
	 * @return a new {@link Vector3D} equivalent to <code>(this.v2)</code>
	 */
	public double dot(final Vector3D v2) {
		double result = coordinate.getX() * v2.getCoordinate().getX();
		result += coordinate.getY() * v2.getCoordinate().getY();
		result += coordinate.getZ() * v2.getCoordinate().getZ();
		return result;
	}

	/**
	 * Performs the dot product with itself.<br>
	 * 
	 * Equivalent to calling <code>dot(this)</code>
	 * 
	 * @return a new {@link Vector3D} equivalent to <code>(this.this)</code>
	 */
	public double dot() {
		return dot(this);
	}

	/**
	 * Calculates the module of the vector.<br>
	 * 
	 * Suppose a vector A(x, y, z):<br>
	 * 
	 * |A| = sqrt(x*x + y*y + z*z)
	 * 
	 * @return the module of the {@link Vector3D}
	 */
	public double module() {
		double result = coordinate.getX() * coordinate.getX();
		result += coordinate.getY() * coordinate.getY();
		result += coordinate.getZ() * coordinate.getZ();
		return Math.sqrt(result);
	}

	/**
	 * Performs the normalization of the vector. <br>
	 * 
	 * Suppose a vector A:<br>
	 * 
	 * � = A/|A|
	 * 
	 * @return a new {@link Vector3D} equivalent to �
	 */
	public Vector3D normal() {
		double module = module();

		double x = coordinate.getX() / module;
		double y = coordinate.getY() / module;
		double z = coordinate.getZ() / module;

		return new Vector3D(x, y, z);
	}

	/**
	 * Calculates the angle between <code>this</code> and
	 * <code>otherVector</code>
	 * 
	 * suppose that <code>this</code> is a vector A, and
	 * <code>otherVector</code> is a vector B.
	 * 
	 * theta = arccos(A.B/(|A|*|B|))
	 * 
	 * @param otherVector
	 *            the vector to calculate the angle between.
	 * @return the angle between <code>this</code> and <code>otherVector</code>
	 *         in radians
	 */
	public double angleBetween(Vector3D otherVector) {
		double thisModule = module();
		double otherModule = otherVector.module();

		double thisDotOther = dot(otherVector);

		double cosTheta = thisDotOther / (thisModule * otherModule);

		return Math.acos(cosTheta);
	}

	public Point3D getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(final Point3D coordinate) {
		this.coordinate = coordinate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((coordinate == null) ? 0 : coordinate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector3D other = (Vector3D) obj;
		if (coordinate == null) {
			if (other.coordinate != null)
				return false;
		} else if (!coordinate.equals(other.coordinate))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return coordinate.toString();
	}

	public Vector3D cross(Vector3D otherVector) {
		double newX, newY, newZ;

		newX = getY() * otherVector.getZ() - getZ() * otherVector.getY();
		newY = getZ() * otherVector.getX() - getX() * otherVector.getZ();
		newZ = getX() * otherVector.getY() - getY() * otherVector.getX();

		return new Vector3D(newX, newY, newZ);
	}

	public Vector3D add(Vector3D otherVector) {
		return new Vector3D(this.getX() + otherVector.getX(), this.getY()
				+ otherVector.getY(), this.getZ() + otherVector.getZ());
	}

	public Vector3D subtract(Vector3D otherVector) {
		return new Vector3D(this.getX() - otherVector.getX(), this.getY()
				- otherVector.getY(), this.getZ() - otherVector.getZ());
	}

	public Vector3D divide(double d) {
		return new Vector3D(this.getX() / d, this.getY() / d, this.getZ() / d);
	}

	public boolean isParallelTo(Vector3D otherVector) {
		return cross(otherVector).equals(NULL);
	}

	public double getX() {
		return coordinate.getX();
	}

	public double getY() {
		return coordinate.getY();
	}

	public double getZ() {
		return coordinate.getZ();
	}

}
