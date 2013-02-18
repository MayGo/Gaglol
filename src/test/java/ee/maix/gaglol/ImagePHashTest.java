package ee.maix.gaglol;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ImagePHashTest {
	
	private ImageHash imageHash;

	@Before
	public void setUp() throws Exception {
		//imageHash=new ImagePHash(32,8);
		
		imageHash=new ImageSimpleHash();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTwoImagesEqual() {
		try {

			InputStream iis1 = getClass().getClassLoader()
					.getResource("test1.jpg").openStream();
			InputStream iis2 = getClass().getClassLoader()
					.getResource("test1.jpg").openStream();

			String hash1 = imageHash.getHash(iis1);
			String hash2 = imageHash.getHash(iis2);
			assertEquals("Two images should be the same", hash1, hash2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testTwoImagesAreTheSame() {
		try {

			InputStream iisOrig = getClass().getClassLoader()
					.getResource("test1.jpg").openStream();
			
			InputStream iis1 = getClass().getClassLoader()
					.getResource("test1rez1.jpg").openStream();
			
			InputStream iis2 = getClass().getClassLoader()
					.getResource("test1rez2.jpg").openStream();
			
			InputStream iis3 = getClass().getClassLoader()
					.getResource("test1rez3.jpg").openStream();

			
		
			
			String hashOrig = imageHash.getHash(iisOrig);
			String hash1 = imageHash.getHash(iis1);
			String hash2 = imageHash.getHash(iis2);
			String hash3 = imageHash.getHash(iis3);
			
			assertFalse("Image should not be compareble to its thumbnail", hashOrig.equals(hash1));
		//	assertEquals("Two images should be the same", hashOrig, hash1);
			assertEquals("Two images should be the same", hashOrig, hash2);
			assertEquals("Two images should be the same", hashOrig, hash3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testTwoImagesAreNotTheSame() {
		try {

			InputStream iis1 = getClass().getClassLoader()
					.getResource("test1.jpg").openStream();
			InputStream iis2 = getClass().getClassLoader()
					.getResource("test2.jpg").openStream();

			String hash1 = imageHash.getHash(iis1);
			String hash2 = imageHash.getHash(iis2);
			
			assertFalse("Two images are not the same", hash2.equals(hash1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
