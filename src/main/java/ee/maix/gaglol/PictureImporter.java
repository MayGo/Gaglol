package ee.maix.gaglol;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import ee.maix.gaglol.domain.Pic;

import static org.junit.Assert.*;

@Service
public class PictureImporter {

	@Autowired
	PicRepository picRepository;

	@Autowired
	@Qualifier("imageSimpleHash")
	ImageHash imageHash;

	private static final Logger log = LoggerFactory.getLogger(PictureImporter.class);

	URL startUrl;
	String nextLinkSelector;

	public PictureImporter() {

	}

	public PictureImporter(URL url, String nextLinkSelector) {
		this.startUrl = url;
		this.nextLinkSelector = nextLinkSelector;
	}
	public void startImportingAll() {
		while (getPic()) {
			log.debug("<Import next picture------------------------------------------>");
		}
	}
	public boolean getPic() {
		try {
			log.info("Starting to import from " + startUrl + " with nextLinkSelector=" + nextLinkSelector);
			Document doc = Jsoup.parse(startUrl, 3000);

			Element pic = doc.select("div[class=img-wrap] img").first();
			if (pic == null)
				throw new NullPointerException("Pic cannot be null");
			
			String imgUrlStr = pic.attr("src");
			if (imgUrlStr.startsWith("//"))
				imgUrlStr = "http:" + imgUrlStr;

			savePicture(startUrl, new URL(imgUrlStr), pic.attr("alt"));

			Elements els = doc.select(nextLinkSelector);
			if (els.isEmpty())
				throw new NullPointerException(nextLinkSelector + " selector cannot return empty. May be unsafe content");

			String nextUrlStr = els.first().attr("href");
			if (saveNextUrl(new URL(nextUrlStr)))
				return true;

			return false;
		} catch (IOException ex) {
			log.error("Error parsing from url " + startUrl + ": " + ex.toString());
			return false;
		} catch (NullPointerException ex) {
			log.error("Error parsing from url " + startUrl + ". Probably no next link on page: " + ex.toString());
			return true;
		}
	}
	private boolean saveNextUrl(URL url) {
		log.info("Saving url:" + url.toString());
		this.startUrl = url;
		return true;
	}
	private String getPicturesHash(URL imgUrl) throws IOException {
		InputStream is = imgUrl.openStream();
		String hash = imageHash.getHash(is);
		log.debug(imgUrl + " image stream opened and generated hash: " + hash);
		return hash;
	}
	private boolean savePicture(URL url, URL imgUrl, String heading) {
		log.info("Saving picture with url:" + imgUrl.toString() + " and heading:" + heading);
		Pic pic;
		try {
			String hash = getPicturesHash(imgUrl);
			pic = picRepository.findByHash(hash);
			if (pic == null)
				pic = new Pic(hash);

			pic.addPicPost(url, imgUrl, heading);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		picRepository.savePic(pic);
		return true;
	}
}
