package pictureProject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Portion extends InputImage{

	static File tempImageFile = new File("C:\\Users\\Megan\\Desktop\\ImageProjFolder\\tempImage.jpg"); 
	static String matchFileString; 
	static List<Double> distArray = new ArrayList<>(); // 
	static int minIndex; 
	
	Portion(String tempImage) throws IOException{
		super(tempImage);
		InSet.aveArray.forEach(entry -> EuclCalc(entry, this.imageAves)); 	//make distance array
		getMatch(InSet.imageList, Portion.distArray);			// get matching image
		writePixelsToMosaicBuff();
		distArray.clear();
	};
	
	Portion(List<Integer> portionAves) throws IOException{
		//super(tempImage);
		//InSet.aveArray.forEach(entry -> EuclCalc(entry, this.imageAves)); 	//make distance array
		InSet.aveArray.forEach(entry -> EuclCalc(entry, portionAves)); 	//make distance array
		getMatch(InSet.imageList, Portion.distArray);			// get matching image
		writePixelsToMosaicBuff();
		distArray.clear();
	};
	
	
    public static void EuclCalc(List<Integer> aves, List<Integer> testAves) {
    	double eDistance; 
    	int r1 = aves.get(0), g1 = aves.get(1), b1 = aves.get(2); 
    	int r2 = testAves.get(0), g2 = testAves.get(1), b2 = testAves.get(2);
    	double a = (r2 - r1) * (r2 - r1); 
    	double b = (g2 - g1) * (g2 - g1); 
    	double c = (b2 - b1) * (b2 - b1); 
    	eDistance = Math.sqrt(a + b + c);
    	distArray.add(eDistance);
    	 
    }

    public static void getMatch(List<InputImage> imageList, List<Double> distArray) {
    	double min = 10000;
    	int minIndex = 0;
    	int index = 0; 
    	for(double dist : distArray) 
    	{
    		if(dist < min) 
    		{
    			min = dist; 
    			minIndex = index;    			
       		}
    		index++; 
       	}
    	matchFileString = imageList.get(minIndex).pathString;
    }
    
    
	public void writePixelsToMosaicBuff() throws IOException {
		long startWritePixelsPortion = System.nanoTime();
		File image = new File(matchFileString);		// open the match input file
		bImage = ImageIO.read(image);				// put it in a buffer for reading
			for(int i = 0; i < InSet.SET_WIDTH; i++) 
			{
				for( int j = 0; j < InSet.SET_HEIGHT; j++) 
				{
					if (BigImage.currentXmos < BigImage.bigImageW && BigImage.currentYmos < BigImage.bigImageH)
					{
						BigImage.mosaicImageBuff.setRGB(BigImage.currentXmos, BigImage.currentYmos, bImage.getRGB(i, j));  // need to keep filling newImage buffer until full, then write it to file
						BigImage.currentYmos++; 
					}
				}
				BigImage.currentXmos++; 
				BigImage.currentYmos -= InSet.SET_HEIGHT; 
			}
		long endWritePixelsPortion = System.nanoTime();
		long totalWritePixelsPortionTime = endWritePixelsPortion - startWritePixelsPortion; 
		System.out.println("total writePixelsToMosaicBuff portion time: " + totalWritePixelsPortionTime);
	}
    
    
	
}
