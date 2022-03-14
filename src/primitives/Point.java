package primitives;
/**
 * 
 * @author elisheva wolovitz
 *this class creates an object that represent a 3d point
 */

public class Point {
	final Double3 xyz;
	/**
	 * Zero point (0,0,0)
	 */
	public static final Point ZERO = new Point(0, 0, 0);
	/**
	 * constructor that recieves an object of type Double3
	 * @param xyz
	 */
	public Point(Double3 xyz) {
			this.xyz = xyz;
	} 
	/**
	 * a constructor that receives 3 doubles
	 * @param d1
	 * @param d2
	 * @param d3
	 */
	public Point(double d1, double d2, double d3)
	{
			this.xyz = new Double3(d1, d2, d3);
	}
	
	public boolean equals(Object obj)
	{
		if (this==obj) return true;
		if (obj==null) return false;
		if (!(obj instanceof Point)) return false;
		Point other= (Point)obj;
		return xyz.equals(other.xyz); 
	}

	public String toString()
	{
		return this.xyz.toString();
	}
		
	
	/**
	 * 
	 * @param p
	 * @return a vector that is the difference between 2 points
	 */
	public Vector subtract(Point p)
	{
		Double3 d = this.xyz.subtract(p.xyz);
		Vector v = new Vector(d); 
		return v; 
	}
	/**
	 * 
	 * @param v
	 * @return
	 */
	public Point add(Vector v)
	{
		Point p = new Point(this.xyz.add(v.xyz));
		return p; 
	}
	public double distanceSquared(Point p2)
	{
		double dA = (this.xyz.d1 - p2.xyz.d1)*(this.xyz.d1 - p2.xyz.d1); 
		double dB = (this.xyz.d2 - p2.xyz.d2)*(this.xyz.d2 - p2.xyz.d2); 
		double dC = (this.xyz.d3 - p2.xyz.d3)*(this.xyz.d3 - p2.xyz.d3);
		return (dA + dB + dC); 
	}
	public double distance(Point p2)
	{
		double d = this.distanceSquared(p2); 
		return  java.lang.Math.sqrt(d); 
	}
	
	

}
