package renderer;

//import java.util.List;
import java.util.MissingResourceException;

import primitives.*;
import static primitives.Util.*;

/**
 * Camera class
 */
public class Camera {

    private Point p0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double width; // width of the view plane in pixels
    private double height; // height of the view plane in pixels
    private double distance;  // distance from the camera to the view plane
    private ImageWriter imWr; 
    private RayTracerBase rtb; 

    /**
     * Camera constructor
     * 
     * @param p0  Camera position
     * @param vTo Camera direction
     * @param vUp Camera up vector
     * 
     * @throws IllegalArgumentException if vTo and vUp are not orthogonal
     */
    public Camera(Point p0, Vector vTo, Vector vUp) {
        this.p0 = p0;
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
        // verify that vUp and vTo are orthogonal
        if (vUp.dotProduct(vTo) != 0) {
            throw new IllegalArgumentException("vUp and vTo must be orthogonal");
        }
        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    /**
     * Set view plane size
     * 
     * @param width  The width of the view plane in pixels
     * @param height The height of the view plane in pixels
     *
     * @return Camera
     */
    public Camera setViewPlaneSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Set distance from the camera to the view plane
     * 
     * @param distance The distance from the camera to the view plane
     *
     * @return Camera
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }
    
    public Camera setVPSize(double width, double height)
    {
    	this.width = width; 
    	this.height = height; 
    	return this; 
    }
    /**
     * set ray tracer base 
     * @param r
     * @return camera 
     */
    public Camera setRayTracer(RayTracerBase r)
    {
    	rtb = r; 
    	return this; 
    }
    /**
     * set image writer 
     * @param iw
     * @return camera
     */
    public Camera setImageWriter(ImageWriter iw)
    {
    	imWr = iw; 
    	return this; 
    }
    /**
     * set height 
     * @param d
     * @return camera
     */
    public Camera setHeight(double d)
    {
    	height = d; 
    	return this; 
    }
    /**
     * set width 
     * @param d
     * @return camera 
     */
    public Camera setWidth(double d)
    {
    	width = d; 
    	return this; 
    }
    /**
     * set vRight
     * @param v
     * @return camera
     */
    public Camera setvRight(Vector v)
    {
    	vRight = v; 
    	return this; 
    }
    /**
     * set vTo
     * @param v
     * @return camrea
     */
    public Camera setvTo(Vector v)
    {
    	vTo = v; 
    	return this; 
    }
    /**
     * set vUp
     * @param v
     * @return camera
     */
    public Camera setvUp(Vector v)
    {
    	vUp = v; 
    	return this; 
    }
    /**
     * set p0
     * @param p
     * @return camera
     */
    public Camera setp0(Point p)
    {
    	p0 = p; 
    	return this; 
    }
    

    /**
     * Constructs a ray through a pixel from the camera
     * 
     * @param nX The number of pixels in the x direction
     * @param nY The number of pixels in the y direction
     * @param j  The pixel's x coordinate
     * @param i  The pixel's y coordinate
     * 
     * @return The ray through the pixel
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point pc = p0.add(vTo.scale(distance)); // center point of the view plane
        double pixelWidth = width / nX; // width of a pixel
        double pixelHeight = height / nY; // height of a pixel
        double pcX = (nX-1) / 2.0; // center pixel value in x direction
        double pcY = (nY-1)/ 2.0; // center pixel value in y direction
        double rightDistance = (j - pcX) * pixelWidth; // x offset of j from the center pixel
        double upDistance = -1 * (i - pcY) * pixelHeight; // y offset of i from the center pixel

        Point pij = pc; // start at the center of the view plane

        // we need to check if the distance is zero, because we can't scale a vector by
        // zero
        if (!isZero(rightDistance)) {
            // if the distance to move right is not zero, move right
            pij = pij.add(vRight.scale(rightDistance));
        }

        if (!isZero(upDistance)) {
            // if the distance to move up is not zero, move up
            pij = pij.add(vUp.scale(upDistance));
        }

        // construct a ray from the camera origin in the direction of the pixel at (j,i)
        return new Ray(p0, pij.subtract(p0).normalize());
    }

    /**
     * checks if any of the fields are null
     * @throws MissingResourceException
     */
    public Camera renderImage() throws MissingResourceException 
    {
    	 try {
    		 if(p0 == null || vUp == null || vRight == null || width == 0 || height == 0 || distance == 0 || imWr == null || rtb == null)
    			 throw new MissingResourceException(null, null, null);
    		  } catch (MissingResourceException e) {
    		    System.out.println("null field"); 
    		    
    		  }
    	 Ray r;
    	 Color c; 
    	 for( int i = 0; i < imWr.getNy(); i++)
    	 {
    		 for (int j = 0; j < imWr.getNx(); j++)
    		 {
    			r = constructRayThroughPixel(imWr.getNx(), imWr.getNy(), j, i);
    			c = rtb.traceRay(r); 
    			imWr.writePixel(j, i, c);
    		 }
    	 }
    	 
    	 return this;
    }
    
    public void printGrid(int interval, Color color)
    { 
    	if(imWr == null)
    		throw new MissingResourceException(null, null, null); 
    	
         // for each pixel, write the color
         for (int col = 0; col < imWr.getNy();col++ ) 
         {
             for (int row = 0; row < imWr.getNx(); row++) 
             {
            	 if (col % interval == 0 || row % interval == 0) {
        			imWr.writePixel(col, row, color);
                 }
             }
         }
         imWr.writeToImage();
    }
    
    public void writeToImage()
    {
    	if(imWr == null)
    		throw new MissingResourceException(null, null, null);
    	imWr.writeToImage();
    }
    
    
}
    