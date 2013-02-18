package ee.maix.gaglol;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.awt.*;
import java.awt.image.*;

@Component
public class ImageSimpleHash implements ImageHash {

									// difference of factors
	public ImageSimpleHash(){
	}
	




	// compare the two images in this object.
	public String getHash(InputStream is) throws IOException {
		BufferedImage img1 = ImageIO.read(is);
		int pixelW = 32;

		double scaleX = (double) pixelW / img1.getWidth();
		int pixelH = (int) (img1.getHeight() * scaleX);

		img1 = resize(img1, pixelW, pixelH);
		img1 = blackAndWhite(img1, pixelW,pixelH);
		String hash = "";
	

		// loop through whole image and compare individual blocks of images
		for (int y = 0; y < pixelH; y++) {
			for (int x = 0; x < pixelW; x++) {
				int b1 = img1.getRGB(x, y) & 0xff;
				if(b1>0)b1=1;
				hash += b1;
			}

		}
		
		String shortHash=new String(new BigInteger(hash, 2).toByteArray());
		//System.out.println(shortHash);
		return shortHash;
	}

	private BufferedImage resize(BufferedImage image, int width, int height) {
		BufferedImage resizedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}

	private BufferedImage blackAndWhite(BufferedImage image, int width,
			int height) {
		BufferedImage blackAndWhiteImg = new BufferedImage(width, height,
				BufferedImage.TYPE_BYTE_BINARY);

		Graphics2D graphics = blackAndWhiteImg.createGraphics();
		graphics.drawImage(image, 0, 0, null);
		return blackAndWhiteImg;
	}

}
