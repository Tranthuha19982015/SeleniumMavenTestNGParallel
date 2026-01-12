package com.hatester.helpers;

import com.hatester.drivers.DriverManager;
import com.hatester.utils.LogUtils;
import org.monte.media.Format;
import org.monte.media.FormatKeys;
import org.monte.media.Registry;
import org.monte.media.math.Rational;
import org.monte.screenrecorder.ScreenRecorder;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.io.FileHandler;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import static com.hatester.constants.FrameworkConstant.*;
import static org.monte.media.FormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;

public class CaptureHelper extends ScreenRecorder {
    // Record with Monte Media library
    public static ScreenRecorder screenRecorder;
    public String name;

    //Hàm xây dựng
    public CaptureHelper(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat,
                         Format mouseFormat, Format audioFormat, File movieFolder, String name) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
    }

    //Hàm này bắt buộc để ghi đè custom lại hàm trong thư viện viết sẵn
    @Override
    protected File createMovieFile(Format fileFormat) throws IOException {
        if (!movieFolder.exists()) {
            movieFolder.mkdirs();   //Nếu movieFolder chưa tồn tại -> mkdirs() (tạo thư mục)
        } else if (!movieFolder.isDirectory()) {   //Nếu movieFolder không phải là thư mục -> ném IOException
            throw new IOException("\"" + movieFolder + "\" is not a directory.");
        }
        return new File(movieFolder, name + "_" + SystemHelper.getDateTimeNowFormat() + "."
                + Registry.getInstance().getExtension(fileFormat));  //Tạo File mới
    }

    // Start record video
    public static void startRecord(String videoRecordName) {
        //Tạo thư mục để lưu file video vào
        File file = new File(SystemHelper.getCurrentDir() + PropertiesHelper.getValue("VIDEO_RECORD_PATH"));
        //lấy kích thước màn hình để làm kích cỡ của khung video
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        Rectangle captureSize = new Rectangle(0, 0, width, height);   //quay toàn bộ màn hình

        //Tùy chỉnh những thông số trong video
        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        try {
            screenRecorder = new CaptureHelper(gc, captureSize, new Format(MediaTypeKey, FormatKeys.MediaType.FILE,
                    MimeTypeKey, MIME_AVI), new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,
                    ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE, CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
                    DepthKey, 24, FrameRateKey, Rational.valueOf(15), QualityKey, 1.0f, KeyFrameIntervalKey, 15 * 60),
                    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, "black", FrameRateKey, Rational.valueOf(30)),
                    null, file, videoRecordName);

            screenRecorder.start();  //gọi screenRecorder.start() để bắt đầu quay
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    // Stop record video
    public static void stopRecord() {
        try {
            if (screenRecorder != null) {
                screenRecorder.stop();
                screenRecorder = null;
                LogUtils.info("Video recording stopped successfully.");
            } else {
                LogUtils.info("screenRecorder is null — skipping stopRecord.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void takeScreenshot(String screenshotName) {
        //Tạo tham chiếu của TakeScreenshot
        TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();
        File source = ts.getScreenshotAs(OutputType.FILE);  //chụp ảnh màn hình trang hiện tại (snapshot của browser)

        //kiểm tra folder tồn tại không, nếu không thì tạo mới folder theo đường dẫn
        File theDir = new File(SCREENSHOT_PATH);  //Lưu file vào exports/screenshots
        if (!theDir.exists()) {
            theDir.mkdirs();
        }

        //lưu file ảnh với tên cụ thể vào đường dẫn
        try {
            FileHandler.copy(source, new File(SCREENSHOT_PATH + "/" + screenshotName + ".png"));
            //FileHandler.copy để copy file tạm sang vị trí đích
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
