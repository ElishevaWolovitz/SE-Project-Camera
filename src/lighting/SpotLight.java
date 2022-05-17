package lighting;
import primitives.*; 

public class SpotLight extends PointLight{
	private Vector direction; 
	
	/**
	 * constructor
	 * @param c - color
	 * @param p - position
	 * @param d - direction
	 */
	public SpotLight(Color c, Point p, Vector d)
	{
		super(c, p);
		direction = d; 
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
    	double max; 
    	Color base = super.getIntensity(p); 
    	Vector dir = direction.normalize();
    	Vector l = super.getL(p); 
    	double dotProd = dir.dotProduct(l); 
    	if(dotProd > 0)
    		max = dotProd;
    	else 
    		max = 0; 
    	Color Il = base.scale(max); 
    	return Il; 
    }
    
    /**
     * Get the direction of the light for the given point.
     * 
     * @param p The point to get the direction for.
     * 
     * @return The direction of the light.
     */
    public Vector getL(Point p)//????
    {
    	return super.getL(p); 
    }
}
