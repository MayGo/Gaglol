package ee.maix.gaglol;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


/**
 * 
 * @author MayGo
 *
 */
@Component
public interface ImageHash {

	/**
	 * 
	 * @param is
	 * @return Returns a 'binary string' (like. 001010111011100010). 
	 */
	public String getHash(InputStream is)  throws IOException;
	
	
}
