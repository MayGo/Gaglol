package ee.maix.gaglol;

import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class PictureImporterTests {

	@Autowired
	
	PictureImporter pictureImporter;
	
	@Test
	public void test() {
			pictureImporter.startImportingAll();
	}
}
