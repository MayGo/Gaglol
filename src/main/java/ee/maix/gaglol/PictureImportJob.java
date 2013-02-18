package ee.maix.gaglol;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class PictureImportJob {
	private static final Logger log = LoggerFactory.getLogger(PictureImportJob.class);
	
	List<PictureImporter> importers = new ArrayList<PictureImporter>();

	
	public PictureImportJob(){
		System.out.println(",........dsfd....");
	}
	
	@Scheduled(fixedRate = 5000)
	public void startImport() {
		log.info("Starting import");
		System.out.println(",............");
		int totalImported=0;
		for (PictureImporter pictureImporter : importers) {
			totalImported+=startInThread(pictureImporter);
		}
		log.info("Total imported pictures:"+totalImported);
	}

	@Async
	private int startInThread(PictureImporter pictureImporter) {
		int imported = 0;
		while (pictureImporter.getPic()) {
			imported++;
		}
		log.info("Imported pictures:"+imported);
		return imported;
	}

}
