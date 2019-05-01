import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;
import ij.plugin.frame.*;
import java.util.Arrays; 

public class Contrast_Enhancement implements PlugIn {

	public static final int HISTOGRAM_LENGTH = 256; 
	public final int GLEVELS = 256;
	public int LUT[] = new int[GLEVELS];

	public void setLut(int min, int max) {
		double incr = ((double)GLEVELS)/(max-min);
		double entryVal = 0;
		for (int i = 0; i < GLEVELS; i++) {
			LUT[i] = (int)entryVal;
			entryVal += incr;
		}
	}

	public void mapThruLut(ImageProcessor imp) {
		for (int r = 0; r < imp.getHeight(); r++) { // for each row...
			for (int c = 0; c < imp.getWidth(); c++) { // and each pixel
				imp.putPixel(c, r, LUT[imp.getPixel(c, r)]);
			}
		}
	}

	public void run(String arg) {
		ImagePlus img = IJ.getImage(); 
		ImageProcessor imp = img.getProcessor();
		int histo[] = imp.getHistogram();
		double bot = .03;
		double top = .97;
		
			Arrays.sort(histo); //sort the histogram
			int imageHistogramLength = histo.length; //find the length

			int min = imageHistogramLength / .03; //find the integer that is (approximately) .03 down the histogram
			int max = imageHistogramLength / .97; //find the integer that is (approximately) .97 down the histogram

			setLut(min, max); //set the lookup table
			mapThruLut(imp); //map through lookup table
			img.updateAndDraw(); //draw image
		}

	}
