package pictureProject;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class InSet {

		static final int SET_WIDTH  = 80; 
		static final int SET_HEIGHT = 60;	
	
		static List<Path> imagePaths = new ArrayList<>();  			// list of file Paths in input images folder
		static List<InputImage> imageList = new ArrayList<>();		// list of InputImage objects (each input image gets an object)
		static List<List<Integer>> aveArray = new ArrayList<>();	// 2D list of RGB averages for each object

		String pathString; 	    
		String resizedDirPath = "C:\\Users\\Megan\\Desktop\\ImageProjFolder\\resized";  // directory where resized images will go
		String resizedImagePath; 
		
	    
		public InSet() {}; 		// needed for using these static vars in other classes

	    InSet(String folderPath) throws IOException{   	// folderPath is the name of the input image directory
	    	setPathsList(folderPath);					// gets a list of paths to each image in folder
	    	resizeImages();								// resize them all and store them in new resized directory
	    	imagePaths.clear(); 						
	    	setPathsList(resizedDirPath);				// gets a list of paths to each resized image
	    	imagePaths.forEach(path -> {			
				try {
					pathString = path.toString(); 		
					InputImage inImage = new InputImage(pathString);	//InputImage() will process each image and make an InputImage object for each
					imageList.add(inImage);								//Add current one to list of InputImage objects
					aveArray.add(inImage.imageAves);					//Add current object's average rgb to average array
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
	    }
	    


		// Resize input images. Make smaller and uniform
	    public void resizeImages() throws IOException {
	    	for(Path path : imagePaths) 
			{
				resizedImagePath = resizedDirPath + "\\" + path.getFileName();
				resize(path.toString(), resizedImagePath, SET_WIDTH, SET_HEIGHT);
			}
	    }
	    
	    /**
	     * https://www.codejava.net/java-se/graphics/how-to-resize-images-in-java
	     * Resizes an image to a absolute width and height (the image may not be
	     * proportional)
	     * @param inputImagePath Path of the original image
	     * @param outputImagePath Path to save the resized image
	     * @param scaledWidth absolute width in pixels
	     * @param scaledHeight absolute height in pixels
	     * @throws IOException
	     */
	    public static void resize(String inputImagePath,
	        String outputImagePath, int scaledWidth, int scaledHeight)
	        throws IOException {
	        // reads input image
	        File inputFile = new File(inputImagePath);
	        BufferedImage inputImage = ImageIO.read(inputFile);
	 
	        // creates output image
	        BufferedImage outputImage = new BufferedImage(scaledWidth,
	                scaledHeight, inputImage.getType());
	 
	        // scales the input image to the output image
	        Graphics2D g2d = outputImage.createGraphics();
	        g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
	        g2d.dispose();
	 
	        // extracts extension of output file
	        String formatName = outputImagePath.substring(outputImagePath
	                .lastIndexOf(".") + 1);
	 
	        // writes to output file
	        ImageIO.write(outputImage, formatName, new File(outputImagePath));
	    }
	    

	    
	    // arg: string reference to name of directory with images.
	    // sets the class list imagePaths. This is the list of paths 
	    // to each image in the folder  
	    public static void setPathsList(String dir) throws IOException  {
	    Path imageFolder = Paths.get(dir);

			if(!Files.exists(imageFolder)) 
			{
				throw new IllegalArgumentException("Directory does not exist: " + imageFolder);
			}	
			try (DirectoryStream<Path> paths = Files.newDirectoryStream(imageFolder)){
				paths.forEach(filePath -> imagePaths.add(filePath)); 		// Add each path to the path list. 
			}
	    }
	    
}




