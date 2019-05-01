import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;
import ij.plugin.frame.*;
import ij.blob.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Blob_Analysis implements PlugIn {

	public void run(String arg) {
		int topBlobs = 10; //the number of blobs to tell me about
		ImagePlus imp = IJ.getImage();
		ManyBlobs allBlobs = new ManyBlobs(imp);
		allBlobs.setBackground(0);
		allBlobs.findConnectedComponents();
		List<BlobStat> statsOfAllBlobs = new ArrayList<BlobStat>();
		

		int i = 0;
		while (i < allBlobs.size()) { //for all of the blobs
			//get the stats we care about
			double perimeter = allBlobs.get(i).getPerimeter();
			double area = allBlobs.get(i).getEnclosedArea();
			int x = (int)allBlobs.get(i).getCenterOfGravity().getX();
			int y = (int)allBlobs.get(i).getCenterOfGravity().getY();
			
			BlobStat currentBlob = new BlobStat(x, y, area, perimeter); 
			statsOfAllBlobs.add(currentBlob); //and store them in our statsOfAllBlobs ArrayList
			i++;
		}
		
		Collections.sort(statsOfAllBlobs); //then sort it
		Collections.reverse(statsOfAllBlobs); //largest first

		
		IJ.log("Here are the stats from the top " + topBlobs + " Blobs (sorted by area):" );

		double totalArea = 0;
		for (int j = 0; j <= topBlobs - 1; j ++){
			double currentArea = statsOfAllBlobs.get(j).area;
			totalArea += currentArea; //add up the total area from the top blobs

			IJ.log("Blob " + (j + 1) + " Area: " + currentArea);
			IJ.log("Blob " + (j + 1) + " Perimeter: " + statsOfAllBlobs.get(j).perimeter);
			IJ.log("Blob " + (j + 1) + " Center:  X: " + statsOfAllBlobs.get(j).x + " , Y: " + statsOfAllBlobs.get(j).y);
		}

		IJ.log("The total area from all those Blobs is:" + totalArea);
	}
}


class BlobStat implements Comparable<BlobStat>{
	int x, y;
	double area, perimeter;

    public BlobStat(int x, int y, double area, double perimeter) {
		this.x = x;
		this.y = y;
		this.area = area;
		this.perimeter = perimeter;
	}

	@Override
    public int compareTo(BlobStat b) { //compare areas when sorting
		if (this.area < b.area)
			return -1;
  		else if (b.area < this.area)
			return 1;
  		return 0;
    }
}
