package pictureProject;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class BigImage extends InputImage implements Runnable{
	

	static File bigImage; 
	BufferedImage bBigImage;
	static BufferedImage mosaicImageBuff;	// buffer for mosaic image. 
	static String mosaicFileString = "C:\\Users\\Megan\\Desktop\\ImageProjFolder\\mosaic.png"; 
	static int bigImageW; 			// width of big image
	static int bigImageH; 			// height of big image

	static int currentXbig = 0; 	// current X pos in big pic buff
	static int currentYbig = 0;		// current Y pos in big pic buff
	static int currentXmos = 0; 	// current X pos in mosaic pic buff
	static int currentYmos = 0; 	// current Y pos in mosaic pic buff
	


	public List<Integer> portionAves; 		// 1D array for portion's rgb average
    int aveR = 0;
    int aveG = 0;
    int aveB = 0;
    int totalPortionPixels = 0; 
	
	
	BigImage(String file) throws IOException{

		bigImage = new File(file);
		bBigImage = ImageIO.read(bigImage); 	
		bigImageW = bBigImage.getWidth();
		bigImageH = bBigImage.getHeight();
		totalPortionPixels = InSet.SET_WIDTH * InSet.SET_HEIGHT; 
		mosaicImageBuff = new BufferedImage(bigImageW, bigImageH, BufferedImage.TYPE_INT_ARGB); // open buffer for writing the mosaic image
	
		// process big image until done
		while(currentYbig < bigImageH) 
		{
			while(currentXbig < bigImageW) 
			{
				getPortionAve(); 
				// process the current portion with constructor (get distance, get matching image, write to mosaic buff)
				new Portion(portionAves);	
				portionAves.clear();
	 		}
			currentXbig = 0; // after row is finished, start at X = 0 again 
			currentXmos = 0; 
			currentYbig+= InSet.SET_HEIGHT;  // after row is finished, move to next row
			currentYmos+= InSet.SET_HEIGHT; 

		}
	
		// after all the mosaic pieces have been written to mosaic buffer, write the buffer to a file
		ImageIO.write(mosaicImageBuff, "png", new File(mosaicFileString));
		InSet.resize(mosaicFileString, "C:\\Users\\Megan\\Desktop\\ImageProjFolder\\mosaicResized.png", 632, 840);
	
	}
    

	public void getPortionAve() throws IOException {
		
    	portionAves = new ArrayList<Integer>();		// new 1D array of rgb aves for the current portion
    	int i; 
    	int j; 
    	int red   = 0; 
    	int green = 0; 
    	int blue  = 0;
    	
		long getPortionAveStart = System.nanoTime();
		for(i = 0; i <  InSet.SET_WIDTH; i++) 
		{
			for(j = 0; j <  InSet.SET_HEIGHT; j++) 
			{
					if (currentXbig < bigImageW && currentYbig < bigImageH)
					{ // if we haven't reached the end
				    	
		    			Color c = new Color(bBigImage.getRGB(currentXbig, currentYbig));
		    			red   += c.getRed(); 
		    			green += c.getGreen(); 
		    			blue  += c.getBlue();
			    	
						
						BigImage.currentYbig++; 	// go to next Y position
					}
			}	
			currentXbig++; // after column is done, go to next column in big pic
			currentYbig-= InSet.SET_HEIGHT; // start at top of next column in big pic
		}
    	aveR = red/totalPortionPixels; 
    	portionAves.add(aveR);
    	aveG = green/totalPortionPixels; 
    	portionAves.add(aveG);
    	aveB = blue/totalPortionPixels; 
    	portionAves.add(aveB);

		long getPortionAveEnd = System.nanoTime();
		long getPortionAveTotal = getPortionAveEnd - getPortionAveStart; 
		System.out.println("getPortionAve total: " + getPortionAveTotal);
	}



	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
	}
	
}
	

   