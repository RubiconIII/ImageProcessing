import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;
import ij.plugin.frame.*;

public class Seperable_Filter implements PlugIn {

	public void run(String arg) {
		ImagePlus img = IJ.getImage();
		ImageProcessor imp = img.getProcessor();
		int x = imp.getWidth();
		int y = imp.getHeight();
		double pixel;

		int[] kernal = {20, 30, 40, 50, 40, 30, 20};
		int kernalSum = 230; //kernal added up
		int kernalLength = 7;
		int halfIndex = kernalLength / 2;

		ImageProcessor copy = imp.duplicate(); //so we don't touch original values while calculating
		
		//run the horizontal wipe
		for (int row = 0; row < y; row++){ //for all the rows
			for (int col = halfIndex; col < x - halfIndex; col++){ //and all the columns (minus the offset, so we dont go out of bounds)
				pixel = 0;
				for (int i = -halfIndex; i < halfIndex; i++){
					pixel += copy.getPixel(col + i, row) * kernal[i+ halfIndex];
				}
				imp.putPixel(col, row, (int)(pixel / kernalSum));
			}
		}
		
		ImageProcessor secondCopy = imp.duplicate(); //so we don't touch original values while calculating
		
		//run the vertical wipe
		for (int col = 0; col < x; col++){ //for all the columns
			for (int row = halfIndex; row < y - halfIndex; row++){ //and all the rows (minus the offset, so we dont go out of bounds)
				pixel = 0;
				for (int i = -halfIndex; i < halfIndex; i++){
					pixel += copy.getPixel(col + i, row) * kernal[i + halfIndex]; //get the pixel data
				}
				imp.putPixel(col, row, (int)(pixel / kernalSum)); //and put it in the processor at our current position
			}
		img.updateAndDraw();
		}
		
	}
}
