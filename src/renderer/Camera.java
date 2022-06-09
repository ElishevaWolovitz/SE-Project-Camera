package renderer;

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
    private double distance; // distance from the camera to the view plane
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;
    private boolean supersampling; // supersampling true=ON, false=OFF
    private int supersamplingGridSize; // grid size of the supersampling (eg. 9 for 9x9 grid)

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
     * Set the supersampling on or off
     *
     * @param supersampling true=ON, false=OFF
     * @return the current camera
     */
    public Camera setSupersampling(boolean supersampling) {
        this.supersampling = supersampling;
        return this;
    }

    /**
     * Set the grid size of the supersampling
     * 
     * @param gridSize grid size of the supersampling (eg. 9 for 9x9 grid)
     * @return the current camera
     */
    public Camera setSupersamplingGridSize(int gridSize) {
        this.supersamplingGridSize = gridSize;
        return this;
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

    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * set ray tracer base
     * 
     * @param r
     * @return camera
     */
    public Camera setRayTracer(RayTracerBase r) {
        rayTracer = r;
        return this;
    }

    /**
     * set image writer
     * 
     * @param iw
     * @return camera
     */
    public Camera setImageWriter(ImageWriter iw) {
        imageWriter = iw;
        return this;
    }

    /**
     * set height
     * 
     * @param d
     * @return camera
     */
    public Camera setHeight(double d) {
        height = d;
        return this;
    }

    /**
     * set width
     * 
     * @param d
     * @return camera
     */
    public Camera setWidth(double d) {
        width = d;
        return this;
    }

    /**
     * set vRight
     * 
     * @param v
     * @return camera
     */
    public Camera setvRight(Vector v) {
        vRight = v;
        return this;
    }

    /**
     * set vTo
     * 
     * @param v
     * @return camrea
     */
    public Camera setvTo(Vector v) {
        vTo = v;
        return this;
    }

    /**
     * set vUp
     * 
     * @param v
     * @return camera
     */
    public Camera setvUp(Vector v) {
        vUp = v;
        return this;
    }

    /**
     * set p0
     * 
     * @param p
     * @return camera
     */
    public Camera setp0(Point p) {
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
        double pcX = (nX - 1) / 2.0; // center pixel value in x direction
        double pcY = (nY - 1) / 2.0; // center pixel value in y direction
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
        return new Ray(p0, pij.subtract(p0));
    }

    /**
     * checks if any of the fields are null
     * 
     * @throws MissingResourceException
     */
    public Camera renderImage() throws MissingResourceException {
        if (p0 == null || vUp == null || vRight == null || width == 0 || height == 0 || distance == 0
                || imageWriter == null || rayTracer == null) {
            throw new MissingResourceException(null, null, null);
        }
        Ray ray;
        Color color;
        int numRows = imageWriter.getNy();
        int numColumns = imageWriter.getNx();
        //if supersampling activated, calculate color of pixels using supersampling function
        if(supersampling)
        {
        	for (int row = 0; row < numRows; row++) 
        	{
	            for (int col = 0; col < numColumns; col++)
	            {
		                ray = constructRayThroughPixel(numColumns, numRows, col, row);
		                color = calcSupersamplingColor(ray);
		                imageWriter.writePixel(col, row, color);
		        }
        	}
        }
        //otherwise, calculate color of pixels without it (using only one ray per pixel)
        else
        {
	        for (int row = 0; row < numRows; row++) 
	        {
	            for (int col = 0; col < numColumns; col++) 
	            {
	                ray = constructRayThroughPixel(numColumns, numRows, col, row);
	                color = rayTracer.traceRay(ray);
	                imageWriter.writePixel(col, row, color);
	            }
	        }
        }
        
        return this;
    }

    /**
     * Calculates the color of a pixel using supersampling
     * 
     * @param ray the main ray to trace around
     * @return color of the pixel
     */
    private Color calcSupersamplingColor(Ray mainRay) {
        Ray ray;
        Color result = Color.BLACK;
        // height and width of the pixel
        double pixelWidth = width / imageWriter.getNx();
        double pixelHeight = height / imageWriter.getNy();
        // amount to move to get from one supersampling ray location to the next
        double raySpacingVertical = pixelHeight / (supersamplingGridSize + 1);
        double raySpacingHorizontal = pixelWidth / (supersamplingGridSize + 1);
        // locate the point of the top left ray to start constructing the grid from
        Point centerOfPixel = mainRay.getPoint(distance);
        Point topLeftRayPoint = centerOfPixel //
                .add(vRight.scale(-pixelWidth / 2 - raySpacingHorizontal)) //
                .add(vUp.scale(pixelHeight / 2 - raySpacingVertical));
        // create the grid of rays
        for (int row = 0; row < supersamplingGridSize; row++) {
            for (int col = 0; col < supersamplingGridSize; col++) {
                Point rayPoint = topLeftRayPoint;
                // move the ray down
                if (row > 0) {
                    rayPoint = rayPoint.add(vUp.scale(-row * raySpacingVertical));
                }
                // move the ray right
                if (col > 0) {
                    rayPoint = rayPoint.add(vRight.scale(col * raySpacingHorizontal));
                }
                // create the ray
                ray = new Ray(p0, rayPoint.subtract(p0));
                // trace the ray
                Color color = rayTracer.traceRay(ray);
                result = result.add(color);
                
            }
        }
        // divide the color by the number of rays to get the average color
        double numRays = (double) supersamplingGridSize * supersamplingGridSize;
        return result.reduce(numRays);
    }

    /**
     * Print a grid of lines to the image
     * 
     * @param interval The interval between lines
     * @param color    The color of the lines
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException(null, null, null);

        // for each pixel, write the color
        for (int row = 0; row < imageWriter.getNy(); row++) {
            for (int col = 0; col < imageWriter.getNx(); col++) {
                if (row % interval == 0 || col % interval == 0) {
                    imageWriter.writePixel(col, row, color);
                }
            }
        }
        imageWriter.writeToImage();
    }

    /**
     * Produces unoptimized png file of the image according to pixel color matrix in
     * the directory of the project
     */
    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException(null, null, null);
        imageWriter.writeToImage();
    }

}
