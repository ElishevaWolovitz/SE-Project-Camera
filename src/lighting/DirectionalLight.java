package lighting;
import primitives.*;

public class DirectionalLight extends Light implements LightSource{
	private Vector direction; 
	
	/**
	 * constructor
	 * @param c -color 
	 * @param d - direction
	 */
	public DirectionalLight(Color c, Vector d)
	{
		super(c); 
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
    	return super.getIntensity(); // ??? 
    }
    
    /**
     * Get the direction of the light for the given point.
     * 
     * @param p The point to get the direction for.
     * 
     * @return The direction of the light.
     */
    public Vector getL(Point p)
    {
    	return direction; //???
    }

}
