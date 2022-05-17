package lighting;
import primitives.*; 

public class PointLight extends Light implements LightSource{
	private Point position; 
	double kC = 1; 
	double kL = 0; 
	double kQ = 0; 
	
	/**
	 * constructor
	 * @param c
	 * @param p
	 */
	public PointLight(Color c, Point p)
	{
		super(c); 
		position = p; 
	}
	
	/**
	 * @apiNote setter for kC
	 * @param a double
	 * @return object
	 */
	public PointLight setKC(double newKC)
	{
		kC = newKC; 
		return this; 
	}
	
	/**
	 * @apiNote setter for kL
	 * @param a double
	 * @return object
	 */
	public PointLight setKL(double newKL)
	{
		kC = newKL; 
		return this; 
	}
	
	/**
	 * @apiNote setter for kQ
	 * @param a double
	 * @return object
	 */
	public PointLight setKQ(double newKQ)
	{
		kC = newKQ; 
		return this; 
	}
	
	/**
     * Get the intensity of the light for the given point.
     * 
     * @param p The point to get the intensity for.
     *
     * @return the intensity of the light.
     */
    public Color getIntensity(Point p)
    {
    	Color I0 = super.getIntensity(); 
    	double dis = p.distance(position); 
    	double disSqr = p.distanceSquared(position); 
    	double kLdis = dis*kL; 
    	double kQdisSqr = disSqr*kQ; 
    	double sum = kC + kLdis + kQdisSqr; 
    	Color Il = I0.scale(1/sum); 
    	return Il; 
    }
    
    /**
     * Get the direction of the light for the given point.
     * 
     * @param p The point to get the direction for.
     * 
     * @return The direction of the light.
     */
    public Vector getL(Point p)//???
    {
    	Vector l = p.subtract(position);
    	return l; 
    }
}
