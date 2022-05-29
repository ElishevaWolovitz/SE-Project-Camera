package primitives;

/**
 * Class representing the material of a geometry
 * 
 * @author elana
 * @author elish
 */
public class Material {
	/**
	 * class material with attributes: 
	 * kD, kD, kT, kR, nShininess all initialized to 0
	 * and methods: 
	 * setters for each attribute 
	 */
	public Double3 kD = Double3.ZERO;
	public Double3 kS = Double3.ZERO;
	public int nShininess = 0;
	public Double3 kT = Double3.ZERO;
	public Double3 kR = Double3.ZERO; 

	/**
	 * setter for kD that recieves Double3
	 * 
	 * @param d3 - Double3 to set kD to
	 * @return the material
	 */
	public Material setKD(Double3 d3) {
		kD = d3;
		return this;
	}

	/**
	 * setter for kS that recieves Double3
	 * 
	 * @param d3 - Double3 to set kS to
	 * @return the material
	 */
	public Material setKS(Double3 d3) {
		kS = d3;
		return this;
	}

	/**
	 * setter for kD that recieves a double
	 * 
	 * @param d - double to set kD to
	 * @return the material
	 */
	public Material setKD(double d) {
		kD = new Double3(d);
		return this;
	}

	/**
	 * setter for ks that recieves a double
	 * 
	 * @param d - double to set kS to
	 * @return the material
	 */
	public Material setKS(double d) {
		kS = new Double3(d);
		return this;
	}

	/**
	 * setter for nShininess
	 * 
	 * @param x - int to set nShininess to
	 * @return the material
	 */
	public Material setNShininess(int x) {
		nShininess = x;
		return this;
	}
	/**
	 * setter for kT with parameter of type Double3
	 * @param d3
	 * @return the object 
	 */
	public Material setKT(Double3 d3)
	{
		kT = d3; 
		return this; 
	}
	/**
	 * setter for kR with parameter of type Double3
	 * @param d3
	 * @return the object 
	 */
	public Material setKR(Double3 d3)
	{
		kR = d3; 
		return this; 
	}
	
	/**
	 * setter for kT with parameter of type double
	 * @param d
	 * @return the object 
	 */
	public Material setKT(double d)
	{
		kT = new Double3(d); 
		return this; 
	}
	/**
	 * setter for kR with parameter of type double
	 * @param d
	 * @return the object 
	 */
	public Material setKR(double d)
	{
		kR = new Double3(d); 
		return this; 
	}
}
