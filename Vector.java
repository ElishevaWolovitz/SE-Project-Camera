package primitives;

/**
 * this class is a vector that inherits from point
 * @author elisheva wolovitz
 *
 */

public class Vector extends Point{
	
	/**
	 * contructor that accepts 3 doubles
	 * @param d1
	 * @param d2
	 * @param d3
	 */
	public Vector(double d1, double d2, double d3)
	{
		super(d1, d2, d3);
		if(xyz.equals(Double3.ZERO))
		{
			throw new IllegalArgumentException("ERROR zero vector not allowed");
		} 
	}
	/**
	 * contructor that accepts a variable of type Double3
	 * @param dbl
	 */
	public Vector(Double3 dbl)
	{
		super(dbl); 
		if(dbl == Double3.ZERO)
		{
			throw new IllegalArgumentException("ERROR zero vector not allowed");
		} 
	}
	public boolean equals(Object obj)
	{
		if (this==obj) return true;
		if (obj==null) return false;
		if (!(obj instanceof Vector)) return false;
		Vector other= (Vector)obj;
		return super.equals(other); 
	}

	public String toString()
	{
		String st="";
		st += xyz;
		return st     ;
	}
		
	/**
	 * adds two vectors together to get a third vector 
	 * and throws an expcetion if the result is a zero vector
	 * @param v1
	 * @param v2
	 * @return a vector v3
	 */
	public Vector add( Vector v2)
	{
		Vector v3 = new Vector(this.xyz.add(v2.xyz));
		if(v3.xyz.equals(Double3.ZERO))
		{
			throw new IllegalArgumentException("Vector result cannot be the zero vector");
		}
		else 
			return v3; 
	}
	/**
	 * scales a vector by a number and checks not the zero vector
	 * @param v1
	 * @param num
	 * @return a vector v2 scaled by num
	 */
	public Vector scale( double num)
	{
		Vector v2 = new Vector(this.xyz.scale(num)); 
		if(v2.xyz.equals(Double3.ZERO))
		{
			throw new IllegalArgumentException("Vector result cannot be the zero vector");
		}
		else 
			return v2; 
	}
	/**
	 * does a dotproduct on 2 vectors v1 and v2 
	 * @param v1
	 * @param v2
	 * @return a double that is the dot product of v1 and v2 
	 */
	public double dotProduct(Vector v2)
	{
		Vector v3 = new Vector(this.xyz.product(v2.xyz));
		if(v2.xyz.equals(Double3.ZERO))
		{
			throw new IllegalArgumentException("Vector result cannot be the zero vector");
		}
		double d = v3.xyz.d1 + v3.xyz.d2 + v3.xyz.d3; 
		return d; 
	}
 
	/**
	 * 
	 * @return a double of the vector that calls this function, squared
	 */
	public double lengthSquared()
	{
		double dA = (this.xyz.d1)*(this.xyz.d1); 
		double dB = (this.xyz.d2)*(this.xyz.d2); 
		double dC = (this.xyz.d3)*(this.xyz.d3);
		return (dA + dB + dC); 
		
	}
	/**
	 * 
	 * @return a double that is the length of the vector that called this function
	 */
	public double length()
	{
		double d = this.lengthSquared();
		return java.lang.Math.sqrt(d);
	}
	/**
	 * 
	 * @return a normalized vector
	 */
	public Vector normalize()
	{
		double d = this.length();
		Vector v = new Vector(this.xyz.reduce(d));
		if(v.xyz.equals(Double3.ZERO))
		{
			throw new IllegalArgumentException("Vector result cannot be the zero vector");
		}
		else 
			return v; 
	}
	
	/**
	 * does a cross product of vectors v1 and v2
	 * @param v1
	 * @param v2
	 * @return a vector v3 that is the cross product of v1 and v2
	 */
	
	public Vector crossProduct(Vector v2)
	{
		double dotProd = this.dotProduct(v2); 
		double mag = (this.length()) *(v2.length()); 
		if(dotProd == mag)
		{
			throw new IllegalArgumentException("ERROR: parallel vectors"); 
		}
		double d1, d2, d3; 
		d1 = (this.xyz.d2 * v2.xyz.d3) - (this.xyz.d3*v2.xyz.d2); 
		d2 = -1*((this.xyz.d1 * v2.xyz.d3) - (this.xyz.d3*v2.xyz.d1));
		d3 = (this.xyz.d1 * v2.xyz.d2) - (this.xyz.d2*v2.xyz.d1); 
		Vector v3 = new Vector(d1, d2, d3); 
		if (v3.xyz.equals(Double3.ZERO))
		{
			throw new IllegalArgumentException("Vector result cannot be the zero vector");
		}
		else 
			return v3; 
	}

}
