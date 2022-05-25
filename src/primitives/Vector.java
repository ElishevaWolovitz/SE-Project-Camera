package primitives;

/**
 * Class representing a vector
 * 
 * @author elisheva wolovitz
 * @author elana
 */
public class Vector extends Point {
	/**
	 * contructor that accepts 3 doubles
	 * 
	 * @param d1
	 * @param d2
	 * @param d3
	 */
	public Vector(double d1, double d2, double d3) {
		super(d1, d2, d3);
		if (xyz.equals(Double3.ZERO)) {
			throw new IllegalArgumentException("ERROR zero vector not allowed");
		}
	}

	/**
	 * contructor that accepts a variable of type Double3
	 * 
	 * @param dbl - Double3 to set xyz to
	 */
	public Vector(Double3 dbl) {
		super(dbl);
		if (xyz.equals(Double3.ZERO)) {
			throw new IllegalArgumentException("ERROR zero vector not allowed");
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector))
			return false;
		Vector other = (Vector) obj;
		return super.equals(other);
	}

	@Override
	public String toString() {
		String st = "";
		st += xyz;
		return st;
	}

	/**
	 * adds two vectors together to get a third vector
	 * and throws an expcetion if the result is a zero vector
	 * 
	 * @param v2 - the vector to add to this vector
	 * 
	 * @return a new vector that is the sum of the two vectors
	 */
	@Override
	public Vector add(Vector v2) {
		return new Vector(this.xyz.add(v2.xyz));
	}

	/**
	 * scales a vector by a number and checks not the zero vector
	 * 
	 * @param num
	 * @return a vector v2 scaled by num
	 */
	public Vector scale(double num) {
		return new Vector(this.xyz.scale(num));
	}

	/**
	 * does a dotproduct on 2 vectors v1 and v2
	 * 
	 * @param v2
	 * @return a double that is the dot product of v1 and v2
	 */
	public double dotProduct(Vector v2) {
		return v2.xyz.d1 * this.xyz.d1 + v2.xyz.d2 * this.xyz.d2 + v2.xyz.d3 * this.xyz.d3;
	}

	/**
	 * Calculate the square of the length of a vector
	 * 
	 * @return a double of the vector that calls this function, squared
	 */
	public double lengthSquared() {
		return this.dotProduct(this);
	}

	/**
	 * Length of the vector
	 * 
	 * @return a double that is the length of the vector that called this function
	 */
	public double length() {
		double d = this.lengthSquared();
		return java.lang.Math.sqrt(d);
	}

	/**
	 * Normalizes the vector in place
	 * 
	 * @return a normalized vector
	 */
	public Vector normalize() {
		this.xyz = this.xyz.scale(1 / this.length());
		return this;
	}

	/**
	 * does a cross product of vectors v1 and v2
	 * 
	 * @param v2 - the vector to cross with this vector
	 * @return a vector v3 that is the cross product of v1 and v2
	 */
	public Vector crossProduct(Vector v2) {
		double dotProd = this.dotProduct(v2);
		double mag = (this.length()) * (v2.length());
		if (dotProd == mag) {
			throw new IllegalArgumentException("ERROR: parallel vectors");
		}
		double d1, d2, d3;
		d1 = (this.xyz.d2 * v2.xyz.d3) - (this.xyz.d3 * v2.xyz.d2);
		d2 = -1 * ((this.xyz.d1 * v2.xyz.d3) - (this.xyz.d3 * v2.xyz.d1));
		d3 = (this.xyz.d1 * v2.xyz.d2) - (this.xyz.d2 * v2.xyz.d1);
		Vector v3 = new Vector(d1, d2, d3);
		if (v3.xyz.equals(Double3.ZERO)) {
			throw new IllegalArgumentException("Vector result cannot be the zero vector");
		} else
			return v3;
	}

}
