package  ee.maix.gaglol;

import java.util.List;

import ee.maix.gaglol.domain.Pic;

/**
 * Loads account aggregates. Called by the reward network to find and reconstitute Account entities from an external
 * form such as a set of RDMS rows.
 * 
 * Objects returned by this repository are guaranteed to be fully-initialized and ready to use.
 */
public interface PicRepository {

	
	/**
	 *  Save pic
	 * @param pic
	 * @return saved id
	 */
	public Long savePic(Pic pic);
	
	/**
	 * Load an picHash by its hash.
	 * @param hash pictures hash
	 * @return the picHash object
	 */
	public Pic findByHash(String hash);

	
	public List<Pic> getAllPics();
	
	public Pic getPicById(Long id) ;
}