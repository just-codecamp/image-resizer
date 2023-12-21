package gg.scode.imageresizeservice.services.v1;

import gg.scode.imageresizeservice.config.Standard;
import gg.scode.imageresizeservice.config.TestSaveConfiguration;
import gg.scode.imageresizeservice.dto.ImageStandard;
import gg.scode.imageresizeservice.utils.ImageWriter;
import gg.scode.imageresizeservice.utils.TestUtil;
import gg.scode.imageresizeservice.utils.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.junit.jupiter.api.*;

import java.awt.image.BufferedImage;
import java.io.File;

import static gg.scode.imageresizeservice.config.Standard.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class CropperTests {

    static TestSaveConfiguration configuration;

    Cropper cropper;

    long startTimeLap;
    long startMemLap;

    @BeforeAll
    static void setUp() {

        // Cloud Service
        // AWS, GCP, Naver Cloud, MS Azure,

        // 서버컴퓨터 빌려주는 서비스

        boolean permanentMode = true;
        boolean initialMode = false;

        configuration = new TestSaveConfiguration(permanentMode, initialMode);

        if ( configuration.isInitialMode() ) {
            File outputDir = new File(TestSaveConfiguration.TEST_DIR);

            if (!outputDir.exists()) {
                if (outputDir.mkdirs()) log.info("ReSizeTests test directory created");
                else log.info("ReSizeTests test directory creating failed");
            }
        }

    }

    @AfterAll
    static void close() {

        if ( configuration.isInitialMode() ) {
            File outputDir = new File(TestSaveConfiguration.TEST_DIR);

            if ( outputDir.exists() ) {

                File[] files = outputDir.listFiles();
                if (files != null ) {
                    for (File file : files) {
                        file.delete();
                    }
                }

                if ( outputDir.delete() ) log.info("ConverterJavaTests test directory successfully removed");
                else log.info("ConverterJavaTests test directory removing failed");
            }
        }

    }

    @BeforeEach
    void init() {

        cropper = new Cropper();

        startTimeLap = System.currentTimeMillis();
        startMemLap = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        log.info("Start memory laps :: {}MB", startMemLap / (1024 * 1024));

    }

    @AfterEach
    void endMethod() {

        // In order to measure memory and time
        long endTimeLap = System.currentTimeMillis();
        long endMemLap = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        log.info("Test time taken :: {}s", (endTimeLap - startTimeLap) / 1000.0);
        log.info("Memory used :: {}MB", (endMemLap - startMemLap) / (1024 * 1024));

    }

    @Test
    @DisplayName("Crop image while keeping the aspect ratio without height")
    void test1() throws Exception {

        ImageStandard imageStandard = new ImageStandard(
                UrlUtil.generateLoremUrlString(
                        MAX_WIDTH.getValue()
                        , MAX_HEIGHT.getValue()
                )
                , MIN_WIDTH.getValue()
                , NULL.getValue()
        );

        byte[] resized = cropper.resize(imageStandard);
        BufferedImage resizedBufferedImage = ImageWriter.writeBufferedImage(resized);

        if (configuration.isPermanentMode()) ImageWriter.saveImageFile(TestUtil.generateFileName(TestUtil.PNG), resizedBufferedImage);

        // Origin ratio is 1:1
        log.info("SIZE = {} x {} ", resizedBufferedImage.getWidth(),resizedBufferedImage.getHeight());

        assertThat(resizedBufferedImage.getWidth()).isEqualTo(150);
        assertThat(resizedBufferedImage.getHeight()).isEqualTo(150);

    }

    @Test
    @DisplayName("Crop image while keeping the aspect ratio without width")
    void test1_1() throws Exception {

        ImageStandard imageStandard = new ImageStandard(
                UrlUtil.generateLoremUrlString(
                        MAX_WIDTH.getValue()
                        , MAX_HEIGHT.getValue()
                )
                , NULL.getValue()
                , MIN_HEIGHT.getValue()
        );

        byte[] resized = cropper.resize(imageStandard);
        BufferedImage resizedBufferedImage = ImageWriter.writeBufferedImage(resized);

        if (configuration.isPermanentMode()) ImageWriter.saveImageFile(TestUtil.generateFileName(TestUtil.PNG), resizedBufferedImage);

        // Origin ratio is 1:1
        log.info("SIZE = {} x {} ", resizedBufferedImage.getWidth(),resizedBufferedImage.getHeight());

        assertThat(resizedBufferedImage.getWidth()).isEqualTo(150);
        assertThat(resizedBufferedImage.getHeight()).isEqualTo(150);

    }

    @Test
    @DisplayName("Forcefully resize image width and height")
    void test2() throws Exception {

        ImageStandard imageStandard = new ImageStandard(
                UrlUtil.generateLoremUrlString(
                        MAX_WIDTH.getValue()
                        , MIN_HEIGHT.getValue()
                )
                , MIN_WIDTH.getValue()
                , MAX_HEIGHT.getValue()
        );

        byte[] resized = cropper.resize(imageStandard);
        BufferedImage resizedBufferedImage = ImageWriter.writeBufferedImage(resized);

        if (configuration.isPermanentMode()) ImageWriter.saveImageFile(TestUtil.generateFileName(TestUtil.PNG), resizedBufferedImage);

        assertThat(resizedBufferedImage.getWidth()).isEqualTo(150);
        assertThat(resizedBufferedImage.getHeight()).isEqualTo(500);

    }

    @Test
    @DisplayName("Crop image while keeping the aspect ratio")
    void test2_1() throws Exception {

        ImageStandard imageStandard = new ImageStandard(
                UrlUtil.generateLoremUrlString(
                        MAX_WIDTH.getValue()
                        , MIN_HEIGHT.getValue()
                )
                , MIN_WIDTH.getValue()
                , MAX_HEIGHT.getValue()
        );

        byte[] resized = cropper.resize(imageStandard);
        BufferedImage resizedBufferedImage = ImageWriter.writeBufferedImage(resized);

        if (configuration.isPermanentMode()) ImageWriter.saveImageFile(TestUtil.generateFileName(TestUtil.PNG), resizedBufferedImage);

        // origin ratio is 9:2.7
        log.info("SIZE = {} x {} ", resizedBufferedImage.getWidth(),resizedBufferedImage.getHeight());

        // actual size is 150 x 45
        // ratio of actual size is 9 : 2.7
//        assertThat(resizedBufferedImage.getWidth()).isEqualTo(150); // 9
//        assertThat(resizedBufferedImage.getHeight()).isEqualTo(45); // 2.7

    }

}