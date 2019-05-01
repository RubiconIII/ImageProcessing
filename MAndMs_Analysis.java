import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;
import ij.plugin.frame.*;
import ij.blob.*;

//counts the number of M&Ms in an RGB image
public class MAndMs_Analysis implements PlugIn {

public int getMAndMs(ImagePlus color) {
    ManyBlobs allBlobs = new ManyBlobs(color);
    allBlobs.findConnectedComponents();
    
    int minMm = 20000; //min m&m size
    int total = 0;
    double size = 0;
    
    for (int i = 0; i < allBlobs.size(); i++) {
        if (allBlobs.get(i).getEnclosedArea() > (minMm)) {
        total++;
        size += allBlobs.get(i).getEnclosedArea();
        }
    }
    
    IJ.log("Total Area = " + size + ". Amount of M&M's = " + total);
    return total;
}

 public void run(String arg) {
  ImagePlus img = IJ.getImage();
  ImageConverter converted = new ImageConverter(img);

  converted.convertToHSB();
  int totalMms = 0;

  ImageStack stack = img.getImageStack();
  ImagePlus blue = NewImage.createByteImage("Blue M&Ms", img.getWidth(), img.getHeight(), 1, NewImage.FILL_BLACK);
  ImageProcessor blueProcessor = blue.getProcessor();

  for (int r = 0; r < blue.getHeight(); r++) {
   for (int c = 0; c < blue.getWidth(); c++) {
    int h = (int) stack.getVoxel(c, r, 0);
    int b = (int) stack.getVoxel(c, r, 2);
    if (h > 140 && h < 150 && b > 50) {
     blueProcessor.putPixel(c, r, 0);
    } 
    
    else {
     blueProcessor.putPixel(c, r, 255);
    }
   }
  }

  ImagePlus brown = NewImage.createByteImage("Brown M&Ms", img.getWidth(), img.getHeight(), 1, NewImage.FILL_BLACK);
  ImageProcessor brownProcessor = brown.getProcessor();
  for (int r = 0; r < brown.getHeight(); r++) {
   for (int c = 0; c < brown.getWidth(); c++) {
    int h = (int) stack.getVoxel(c, r, 0);
    int satPix = (int) stack.getVoxel(c, r, 1);
    int b = (int) stack.getVoxel(c, r, 2);

    if (h > 8 && h < 22 && satPix > 60 && b < 80) {
     brownProcessor.putPixel(c, r, 0);
    } 

    else {
     brownProcessor.putPixel(c, r, 255);
    }
   }
  }

  ImagePlus green = NewImage.createByteImage("Green M&Ms", img.getWidth(), img.getHeight(), 1, NewImage.FILL_BLACK);
  ImageProcessor greenProcessor = green.getProcessor();
  for (int r = 0; r < green.getHeight(); r++) {
   for (int c = 0; c < green.getWidth(); c++) {
    int h = (int) stack.getVoxel(c, r, 0);
    int b = (int) stack.getVoxel(c, r, 2);

    if (h > 50 && h < 70 && b > 85) {
     greenProcessor.putPixel(c, r, 0);
    } 

    else {
     greenProcessor.putPixel(c, r, 255);
    }
   }
  }

  ImagePlus red = NewImage.createByteImage("Red M&Ms", img.getWidth(), img.getHeight(), 1, NewImage.FILL_BLACK);
  ImageProcessor redProcessor = red.getProcessor();
  for (int r = 0; r < red.getHeight(); r++) {
   for (int c = 0; c < red.getWidth(); c++) {
    int h = (int) stack.getVoxel(c, r, 0);
    int b = (int) stack.getVoxel(c, r, 2);

    if ((h > 208 || h < 7) && b > 108) {
     redProcessor.putPixel(c, r, 0);
    } 

    else {
     redProcessor.putPixel(c, r, 255);
    }
   }
  }

  ImagePlus yellow = NewImage.createByteImage("Yellow M&Ms", img.getWidth(), img.getHeight(), 1, NewImage.FILL_BLACK);
  ImageProcessor yellowProcessor = yellow.getProcessor();
  for (int r = 0; r < yellow.getHeight(); r++) {
   for (int c = 0; c < yellow.getWidth(); c++) {
    int h = (int) stack.getVoxel(c, r, 0);
    int b = (int) stack.getVoxel(c, r, 2);

    if (h > 26 && h < 40 && b > 200) {
     yellowProcessor.putPixel(c, r, 0);
    } 
    
    else {
     yellowProcessor.putPixel(c, r, 255);
    }
   }
  }

  ImagePlus orange = NewImage.createByteImage("Orange M&Ms", img.getWidth(), img.getHeight(), 1, NewImage.FILL_BLACK);
  ImageProcessor orangeProcessor = orange.getProcessor();
  for (int r = 0; r < orange.getHeight(); r++) {
   for (int c = 0; c < orange.getWidth(); c++) {
    int h = (int) stack.getVoxel(c, r, 0);
    int b = (int) stack.getVoxel(c, r, 2);

    if (h > 7 && h < 15 && b > 200) {
     orangeProcessor.putPixel(c, r, 0);
    } 

    else {
     orangeProcessor.putPixel(c, r, 255);
    }
   }
  }

  blue.show();
  IJ.log("Blue M&Ms");
  totalMms += getMAndMs(blue);

  brown.show();
  IJ.log("Brown M&Ms");
  totalMms += getMAndMs(brown);

  green.show();
  IJ.log("Green M&Ms");
  totalMms += getMAndMs(green);

  red.show();
  IJ.log("Red M&Ms");
  totalMms += getMAndMs(red);

  yellow.show();
  IJ.log("Yellow M&Ms");
  totalMms += getMAndMs(yellow);

  orange.show();
  IJ.log("Orange M&Ms");
  totalMms += getMAndMs(orange);

  IJ.log("Total M&Ms: " +  totalMms);
 }
}
