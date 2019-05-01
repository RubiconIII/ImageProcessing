import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;
import ij.plugin.frame.*;

public class Invert_Stripes implements PlugIn {
	public void run(String arg) {
		ImagePlus image = IJ.getImage();
		ImageProcessor proc = image.getProcessor();

		int w = image.getWidth();
		int h = image.getHeight();

		//flip the image horizontally
    		for ( int x = 0; x < w / 2; x++ ) {
        			for (int y = 0; y < h; y++) {
            			final int l = proc.getPixel( w - ( x + 1 ), y );
            			final int r = proc.getPixel( x, y );
            			proc.putPixel( x, y, l );
            			proc.putPixel( w - ( x + 1 ), y, r );
        			}
    		}

		int stripWidth = 32; //width of diagonal strips

		//invert image in diagonal strips
		int data = 0;
		for(int step = -w; step < w; step += stripWidth){
			for ( int y = 0; y < h; y++ ){
				for ( int x = 0; x < w; x++ ){
					int current = x - y;
					if ( step > current ) {
						data = 255 - proc.getPixel( x, y ); //inverts the pixel data
						proc.putPixel( x, y, data );
					}
					else {
						data = proc.getPixel( x, y );
						proc.putPixel( x, y, data );
					}
				}
			}
		}
	}
}

