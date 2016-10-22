package TuxPowa.View;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class VideoPlayer {

	private Mat currentImage;
	private String fileName;
	private VideoCapture videoCapture;

	int CV_CAP_PROP_POS_MSEC = 0;
	int CV_CAP_PROP_POS_FRAMES = 1;
	int CV_CAP_PROP_POS_AVI_RATIO = 2;
	int CV_CAP_PROP_FRAME_WIDTH = 3;
	int CV_CAP_PROP_FRAME_HEIGHT = 4;
	int CV_CAP_PROP_FPS = 5;
	int CV_CAP_PROP_FOURCC = 6;
	int CV_CAP_PROP_FRAME_COUNT = 7;
	int CV_CAP_PROP_FORMAT = 8;
	int CV_CAP_PROP_MODE = 9;
	int CV_CAP_PROP_BRIGHTNESS = 10;
	int CV_CAP_PROP_CONTRAST = 11;
	int CV_CAP_PROP_SATURATION = 12;
	int CV_CAP_PROP_HUE = 13;
	int CV_CAP_PROP_GAIN = 14;
	int CV_CAP_PROP_EXPOSURE = 15;
	int CV_CAP_PROP_CONVERT_RGB = 16;
	int CV_CAP_PROP_RECTIFICATION = 18;
	int CV_CAP_PROP_MONOCROME = 19;
	int CV_CAP_PROP_SHARPNESS = 20;
	int CV_CAP_PROP_AUTO_EXPOSURE = 21;
	int CV_CAP_PROP_GAMMA = 22;
	int CV_CAP_PROP_TEMPERATURE = 23;
	int CV_CAP_PROP_TRIGGER = 24;
	int CV_CAP_PROP_TRIGGER_DELAY = 25;
	int CV_CAP_PROP_WHITE_BALANCE_RED_V = 26;
	int CV_CAP_PROP_ZOOM = 27;
	int CV_CAP_PROP_FOCUS = 28;
	int CV_CAP_PROP_GUID = 29;
	int CV_CAP_PROP_ISO_SPEED = 30;
	int CV_CAP_PROP_BACKLIGHT = 32;
	int CV_CAP_PROP_PAN = 33;
	int CV_CAP_PROP_TILT = 34;
	int CV_CAP_PROP_ROLL = 35;
	int CV_CAP_PROP_IRIS = 36;
	int CV_CAP_PROP_SETTINGS = 37;

	/**
	 * It's the constructor of the class VideoPlayer.
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	public VideoPlayer() {
		super();
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		fileName = null;
		currentImage = new Mat();
		videoCapture = new VideoCapture();
	}

	/**
	 * To open a video file and get ready to play it.
	 * 
	 * @param fileName
	 *            it's the name of the video file.
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	public void open(String fileName) {
		this.fileName = fileName;
		videoCapture.open(fileName);
	}

	/**
	 * To close the current video file.
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	public void close() {
		this.fileName = null;
	}

	/**
	 * To check if the video player has opened a video file or not.
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @return if it has opened, return true; else return false.
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	public boolean isOpened() {
		return this.fileName != null;
	}

	/**
	 * To check if the video is over or not.
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @return if it has opened, return true; else return false.
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	public Boolean hasNextImage() {
		return videoCapture.read(currentImage);
	}

	/**
	 * To demand the next image of the video.
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @return if it has opened, return true; else return false.
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	public BufferedImage nextImage() {
		return Mat2BufferedImage(currentImage);
	}

	/**
	 * To get the current position of the video file in milliseconds or video
	 * capture timestamp.
	 * 
	 * @return the current position of the video file in milliseconds
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @return if it has opened, return true; else return false.
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 * 
	 */
	public int getCurrentPositionMillisecond() {
		return (int) videoCapture.get(CV_CAP_PROP_POS_MSEC);
	}

	/**
	 * To get the 0-based index of the frame to be decoded/captured next.
	 * 
	 * @return the number of frame
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @return if it has opened, return true; else return false.
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	public int getCurrentPositionFrame() {
		return (int) videoCapture.get(CV_CAP_PROP_POS_FRAMES);
	}

	/**
	 * To get the width of the frames in the video stream.
	 * 
	 * @return the width of the frames
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @return if it has opened, return true; else return false.
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	public int getVideoWidth() {
		return (int) videoCapture.get(CV_CAP_PROP_FRAME_WIDTH);
	}

	/**
	 * To get the height of the frames in the video stream.
	 * 
	 * @return the height of the frames
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @return if it has opened, return true; else return false.
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	public int getVideoHeight() {
		return (int) videoCapture.get(CV_CAP_PROP_FRAME_HEIGHT);
	}

	/**
	 * To get the frame rate.
	 * 
	 * @return frame rate.
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @return if it has opened, return true; else return false.
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	public int getVideoFrameRate() {
		return (int) videoCapture.get(CV_CAP_PROP_FPS);
	}

	/**
	 * To get the number of frames in the video file.
	 * 
	 * @return number of frames in the video file
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @return if it has opened, return true; else return false.
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	public int getVideoLengthFrame() {
		return (int) videoCapture.get(CV_CAP_PROP_FRAME_COUNT);
	}

	/**
	 * To set current position of the video file in milliseconds
	 * 
	 * @param time
	 *            current position of the video file in milliseconds
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @return if it has opened, return true; else return false.
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	public void setCurrentPositionMillisecond(int time) {
		videoCapture.set(CV_CAP_PROP_POS_MSEC, time);
	}

	/**
	 * To set 0-based index of the frame to be decoded/captured next.
	 * 
	 * @param frame
	 *            0-based index of the frame
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @return if it has opened, return true; else return false.
	 * 
	 * @version 1.0
	 */
	public void setCurrentPositionFrame(int frame) {
		videoCapture.set(CV_CAP_PROP_POS_FRAMES, (double)frame);
	}

	/**
	 * To set the relative position of the video file: 0 - start of the film, 1
	 * - end of the film.
	 * 
	 * @param pos relative position of the video file: 0 - start of the film, 1
	 * - end of the film.
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @return if it has opened, return true; else return false.
	 * 
	 * @version 1.0
	 */

	public void setRelativePosition(double pos) {
		videoCapture.set(CV_CAP_PROP_POS_AVI_RATIO, pos);
	}
	
	/**
	 * To set the frame rate
	 * 
	 * @param rate frame rate
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @return if it has opened, return true; else return false.
	 * 
	 * @version 1.0
	 */
	public void setFrameRate(int rate){
		videoCapture.set(CV_CAP_PROP_FPS, rate);
	}
	
	/**
	 * To transfer a mat to a buffered image.
	 * 
	 * @param m
	 *            the mat that we want to transfer.
	 * 
	 * @return the buffered image corresponding.
	 * 
	 * @author ZHANG Heng, YAN Yutong
	 * 
	 * @return if it has opened, return true; else return false.
	 * 
	 * @version 1.0
	 * 
	 * @since 1.0
	 */
	private BufferedImage Mat2BufferedImage(Mat m) {

		int type = BufferedImage.TYPE_BYTE_GRAY;
		if (m.channels() > 1) {
			type = BufferedImage.TYPE_3BYTE_BGR;
		}
		int bufferSize = m.channels() * m.cols() * m.rows();
		byte[] b = new byte[bufferSize];
		m.get(0, 0, b); // get all the pixels
		BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
		final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		System.arraycopy(b, 0, targetPixels, 0, b.length);
		return image;
	}
}
