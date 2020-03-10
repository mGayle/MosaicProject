package pictureProject;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

	

public class InputImage {

	public List<Integer> imageAves; 		// 1D array for individual InputImage object's rgb average

	
	BufferedImage bImage;		
	String pathString; 
	int width = 0; 
	int height = 0; 
	int totalPixels = 0; 
	File initialImage;
    int aveR = 0;
    int aveG = 0;
    int aveB = 0;

    
    InputImage(){};
    
    InputImage(Path pathC) throws IOException{
    	pathString = pathC.toString(); 
		getImage(pathString);
		getAve(); 
		
    }
	
    InputImage(String fileStringC) throws IOException{    	
    	pathString = fileStringC; 
		getImage(fileStringC);			// dont really need this?  
		getAve(); 						// get image's average rgb. add to class ave array. Index corresponds to InputImage list index.
    }
    
/* ***** dont really need this? 
 * getImage() 
 * args: String pathName - name of the image being processed by this method
 * 		opens the image file, sets objects width, height and total pixel count.  
 */
    public void getImage(String pathName) throws IOException {
		initialImage = new File(pathName);
		bImage = ImageIO.read(initialImage);
        width = bImage.getWidth();
        height = bImage.getHeight();
        totalPixels = width * height; 
    }

    
    public void getAve() {
    	imageAves = new ArrayList<Integer>();		// new 1D array for the object (do i need to make new array each time? can .clear() instead to save space and time?
    	int i; 
    	int j; 
    	int red   = 0; 
    	int green = 0; 
    	int blue  = 0;
        
    	for(i = 0; i < width; i++) 
    	{
    		for(j = 0; j < height; j++) 
    		{
    			Color c = new Color(bImage.getRGB(i, j));
    			red   += c.getRed(); 
    			green += c.getGreen(); 
    			blue  += c.getBlue();
    		}
    	}
    	aveR = red/totalPixels; 
    	imageAves.add(aveR);
    	aveG = green/totalPixels; 
    	imageAves.add(aveG);
    	aveB = blue/totalPixels; 
    	imageAves.add(aveB);
    }
    
    
    public void printSpecs() {
    	System.out.println("####################################################");
    	System.out.println("Filename: " + this.pathString);
    	System.out.println("width: " + this.width);
    	System.out.println("height: " + this.height); 
    	System.out.println("aveR: " + this.aveR + " aveG: " + this.aveG + " aveB: " + this.aveB);
    }
    
}






