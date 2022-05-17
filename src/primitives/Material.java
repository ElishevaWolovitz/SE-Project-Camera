package primitives;

public class Material {
	public Double3 kD = Double3.ZERO; 
	public Double3 kS = Double3.ZERO; 
	int nShininess = 0; 
	
	/**
	 * setter for kD that recieves Double3
	 * @param d3 - Double3
	 * @return object
	 */
	public Material setKD(Double3 d3)
	{
		kD = d3; 
		return this; 
	}
	/**
	 * setter for kS that recieves Double3
	 * @param d3 - Double3
	 * @return object
	 */
	public Material setKS(Double3 d3)
	{
		kS = d3; 
		return this; 
	}
	/**
	 * setter for kD that recieves a double
	 * @param d - double
	 * @return object
	 */
	public Material setKD(double d)
	{
		kD = new Double3(d); 
		return this; 
	}
	/**
	 * setter for ks that recieves a double
	 * @param d - double
	 * @return object
	 */
	public Material setKS(double d)
	{
		kS = new Double3(d); 
		return this; 
	}
	/**
	 * setter for nShininess 
	 * @param x - int
	 * @return object
	 */
	public Material setNShininess(int x)
	{
		nShininess = x; 
		return this; 
	}
}
