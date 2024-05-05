package libraryStuff;
import javax.imageio.ImageIO;

import processing.core.PConstants;
import processing.core.PImage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SpriteExtractor {

	public static PImage extractSprite(String spriteSheetPath, int posX, int posY, int width, int height) {
		try {
			
			BufferedImage spriteSheet = ImageIO.read(new File(spriteSheetPath));
			
			BufferedImage sprite = spriteSheet.getSubimage(posX, posY, width, height);
			
			return toPImage(sprite);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private static PImage toPImage(BufferedImage image) {
		PImage pImage = new PImage(image.getWidth(), image.getHeight(), PConstants.ARGB);
		image.getRGB(0, 0, pImage.width, pImage.height, pImage.pixels, 0, pImage.width);
		pImage.updatePixels();
		return pImage;
	}
	
}
