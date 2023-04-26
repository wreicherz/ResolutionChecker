package de.db1337.ResolutionCheck;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class VideoResolutionUtilTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}


	    @Test
	    void findVideos_shouldReturnListOfAllFilesInDirectoryAndSubdirectories() {
	        VideoResolutionUtil util = new VideoResolutionUtil();
	        List<File> fileList = util.findVideos(new File("src/test/resources"));
	        List<String> fileNames = new ArrayList<>();
	        for (File file : fileList) {
	            fileNames.add(file.getName());
	        }
	        assertTrue(fileNames.contains("sample_720p.mp4"));
	        assertTrue(fileNames.contains("sample_1080p.mp4"));
	        assertTrue(fileNames.contains("sample_480p.mp4"));
	        //assertTrue(fileNames.contains("sample_360p.mp4"));
	        assertTrue(fileNames.contains("sample_240p.mp4"));
	        assertEquals(fileList.size(), 4);
	    }

	    @Test
	    void startProcessingParallel_shouldReturnListOfFilesWithResolutionLessThanMinimumHeight() {
	        VideoResolutionUtil util = new VideoResolutionUtil();
	        List<File> fileList = util.findVideos(new File("src/test/resources"));
	        List<String> errorMessages = util.startProcessingParallel(720, fileList);
	        assertEquals(errorMessages.size(), 2);
	        assertTrue(errorMessages.contains(new File("src/test/resources/sample_480p.mp4").getAbsolutePath() + " - 480p"));
	        assertTrue(errorMessages.contains(new File("src/test/resources/sample_240p.mp4").getAbsolutePath() + " - 240p"));
	    }

	    @Test
	    void startProcessingParallel_shouldReturnListOfFilesWithResolutionLessThanMinimumHeight3() {
	    	VideoResolutionUtil util = new VideoResolutionUtil();
	    	List<File> fileList = util.findVideos(new File("src/test/resources"));
	    	List<String> errorMessages = util.startProcessingParallel(1080, fileList);
	    	assertEquals(errorMessages.size(), 3);
	    	assertTrue(errorMessages.contains(new File("src/test/resources/sample_720p.mp4").getAbsolutePath() + " - 720p"));
	    	assertTrue(errorMessages.contains(new File("src/test/resources/sample_480p.mp4").getAbsolutePath() + " - 480p"));
	    	assertTrue(errorMessages.contains(new File("src/test/resources/sample_240p.mp4").getAbsolutePath() + " - 240p"));
	    }
	    
	   

	}



