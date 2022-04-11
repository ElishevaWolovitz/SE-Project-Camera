package unittests.renderer;

 import org.junit.jupiter.api.Test;

import primitives.Color;
import renderer.ImageWriter;

/**
 * Test writing an image to a file.
 * @author elana
 */
public class ImageWriterTests {
    
    /**
     * Create an image with a yellow grid on a blue background.
     * 
     * {@link ImageWriter#writeToImage()}
     */
    @Test
    public void writeToImage() {
        Color bgColor = new Color(java.awt.Color.BLUE);
        Color gridColor = new Color(java.awt.Color.YELLOW);
        int nX = 800;
        int nY = 500;
        int gridSpacing = 50;
        ImageWriter iw = new ImageWriter("test", nX, nY);
        // for each pixel, write the color
        for (int col = 0; col < nX; col++) {
            for (int row = 0; row < nY; row++) {
                if (col % gridSpacing == 0 || row % gridSpacing == 0) {
                    iw.writePixel(col, row, gridColor);
                } else {
                    iw.writePixel(col, row, bgColor);
                }
            }
        }
        iw.writeToImage();
    }
}
