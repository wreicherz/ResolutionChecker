package de.db1337.ResolutionCheck;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bytedeco.javacv.FFmpegFrameGrabber;

/**
 * @author wr 
 * This is a Java class that processes video files to determine their
 *         resolution. The class has three methods: findVideos,
 *         startProcessingParallel, and processFile.
 * 
 *         The findVideos method is a recursive function that takes a File
 *         object representing a directory and returns a List<File> containing
 *         all files in that directory and its subdirectories.
 * 
 *         The startProcessingParallel method takes a minimum height and a list
 *         of files and evaluates all files in parallel to determine if their
 *         resolution is larger than the specified minimum height. It returns a
 *         List<String> containing the paths of the files with resolution less
 *         than the minimum height.
 * 
 *         The processFile method processes individual video files by checking
 *         their resolution using the FFmpegFrameGrabber library. If the
 *         resolution is less than the specified minimum height, the file's path
 *         and resolution are added to a list. If there is an error processing
 *         the file, an error message is also added to the list.
 * 
 *         Overall, this class provides a simple and efficient way to process
 *         large numbers of video files and find those that have a resolution
 *         less than a specified minimum height.
 *
 */
public class VideoResolutionUtil {

	private List<String> errorMessages;

	/**
	 * This is a recursive function in Java that finds all files files in a given
	 * directory and its subdirectories.
	 * 
	 * @param folder
	 * @return List<File>
	 */
	public List<File> findVideos(File folder) {
		File[] files = folder.listFiles();
		List<File> filelist = new ArrayList<>();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					findVideos(file);
				} else {
					filelist.add(file);
				}
			}
		}
		return filelist;
	}

	/**
	 * Starts the evaluation of all given files in parallel if the videos are larger
	 * resolution than the given minheight (eg. 720)
	 * 
	 * @param minheight
	 * @param filelist
	 * @return List<String> the found files with res less than minheight
	 */
	public List<String> startProcessingParallel(int minheight, List<File> filelist) {
		// init errorlist
		errorMessages = new ArrayList<>();
		filelist.parallelStream().forEach(file -> processFile(minheight, file));
		return errorMessages;
	}

	/**
	 * This code snippet processes video files in Java to determine their
	 * resolution. It checks the height of each video file using the
	 * FFmpegFrameGrabber library, and adds the file to a list if its resolution is
	 * less than a specified minimum height. If there is an error processing the
	 * file, the code adds an error message to a separate list.
	 * 
	 * @param minheight
	 * @param file
	 */
	private void processFile(int minheight, File file) {
		String fileName = file.getName().toLowerCase();
		if (fileName.endsWith(".mp4") || fileName.endsWith(".avi") || fileName.endsWith(".mkv")) {
			try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(file)) {
				grabber.start();
				int height = grabber.getImageHeight();
				grabber.stop();
				if (height < minheight) {
					errorMessages.add(file.getAbsolutePath() + " - " + height + "p");
				}
			} catch (Exception e) {
				errorMessages.add(file.getAbsolutePath() + " - " + e.getMessage());
			}
		}
	}

}
