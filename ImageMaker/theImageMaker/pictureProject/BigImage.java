package pictureProject;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class BigImage extends InputImage implements Runnable{

	public String fileName;			// big image file
	static File tempImageFile;  	// portion of big image that is being processed
	static BufferedImage mosaicImageBuff;	// buffer for mosaic image. 
	static String mosaicFileString = "C:\\Users\\Megan\\Desktop\\ImageProjFolder\\mosaic.png"; 
	static int bigImageW; 			// width of big image
	static int bigImageH; 			// height of big image

	static int currentXbig = 0; 	// current X pos in big pic buff
	static int currentYbig = 0;		// current Y pos in big pic buff
	static int currentXmos = 0; 	// current X pos in mosaic pic buff
	static int currentYmos = 0; 	// current Y pos in mosaic pic buff
	static List<Double> distArray = new ArrayList<>(); // array for distances between each image and the current portion
	static String matchFileString; 	// name of closest match file	
	
	
	
	BigImage(String file) throws IOException{
		
		super(file);		// call InputImage constructor on bigImage 
		tempImageFile = new File("C:\\Users\\Megan\\Desktop\\ImageProjFolder\\tempImage.png");  // holds temp portion of big pic 
		fileName = file; 	
		bigImageW = super.width; 
		bigImageH = super.height; 
		mosaicImageBuff = new BufferedImage(bigImageW, bigImageH, BufferedImage.TYPE_INT_ARGB); // open buffer for writing the mosaic image
	
		// process big image until done
		while(currentYbig < bigImageH) 
		{
			while(currentXbig < bigImageW) 
			{
				// write portion to a temp image file
				writeTempPortion();			
				// process the current portion with constructor (get distance, get matching image, write to mosaic buff)
				new Portion(tempImageFile.toString());	
	 		}
			currentXbig = 0; // after row is finished, start at X = 0 again 
			currentXmos = 0; 
			currentYbig+= InSet.SET_HEIGHT;  // after row is finished, move to next row
			currentYmos+= InSet.SET_HEIGHT; 
		}
	
		// after the entire big pic has been written to mosaic buffer, write the buffer to a file
		ImageIO.write(mosaicImageBuff, "png", new File(mosaicFileString));
		InSet.resize(mosaicFileString, "C:\\Users\\Megan\\Desktop\\ImageProjFolder\\mosaicResized.png", 632, 840);
	
	}
    
	
	

	public void writeTempPortion() throws IOException {
		long writeTempPortionStart = System.nanoTime();
		File originalImage = new File(fileName);  			// open the BigPic file
		BufferedImage bigImgBuff = null; 					// buffer for bigPic 
		
		try {
			bigImgBuff = ImageIO.read(originalImage);		// read bigPic into its buffer
			// open new buffer for writing portion to a temp image file
			BufferedImage newImageBuff = new BufferedImage(InSet.SET_WIDTH, InSet.SET_HEIGHT, BufferedImage.TYPE_INT_ARGB); 
			// read bigImage currentXbig & currentYbig into newImageBuff at i, j
			for(int i = 0; i < InSet.SET_WIDTH; i++) 
			{			
				for( int j = 0; j < InSet.SET_HEIGHT; j++) 
				{
						if (currentXbig < bigImageW && currentYbig < bigImageH)
						{ // if we haven't reached the end

							newImageBuff.setRGB(i, j, bigImgBuff.getRGB(currentXbig, currentYbig)); // write pixel into buffer
							BigImage.currentYbig++; 	// go to next Y position
						}
				}	
				BigImage.currentXbig++; // after column is done, go to next column in big pic
				BigImage.currentYbig-= InSet.SET_HEIGHT; // start at top of next column in big pic
			}
		ImageIO.write(newImageBuff, "png", tempImageFile); // when we get the whole portion, write it to tempfile
		} catch(IOException e) {
			e.printStackTrace();
		}
		long writeTempPortionEnd = System.nanoTime();
		long writeTempPortionTotal = writeTempPortionEnd - writeTempPortionStart; 
		System.out.println("writeTempPortion total: " + writeTempPortionTotal);
	}


	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		
	}
	
}
	

   