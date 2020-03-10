package pictureProject;

import java.io.IOException; 

public class Main {
	 
	
    public static void main( String[] args ) throws IOException{  	
    	
    	long startTime = System.nanoTime(); 
    	
 
//    	final int BIG_IMAGE_WIDTH  = 800; 
//    	final int BIG_IMAGE_HEIGHT = 1075; 
//    	final int BIG_IMAGE_WIDTH  = 1264; 
//    	final int BIG_IMAGE_HEIGHT = 1686; 
    	final int BIG_IMAGE_WIDTH  = 2400; 
    	final int BIG_IMAGE_HEIGHT = 3225;
    	
    	String bigImage 	= "C:\\Users\\Megan\\Desktop\\ImageProjFolder\\sophia.jpg";		// big image to be used
    	String bigResized 	= "C:\\Users\\Megan\\Desktop\\ImageProjFolder\\resizedMainImage.jpg";	// big image resized
    	
    	new InSet("C:\\Users\\Megan\\Desktop\\ImageProjFolder\\images");	// folder with images for mosaic
 
    	InSet.resize(bigImage, bigResized, BIG_IMAGE_WIDTH, BIG_IMAGE_HEIGHT);		// resize the big image
    	new BigImage(bigResized);													// run it through BigImage constructor
    	
    	
    	long endTime = System.nanoTime();
    	long totalTime = endTime - startTime; 
    	System.out.println("Total time: " + totalTime);
	}
    
  
}



















