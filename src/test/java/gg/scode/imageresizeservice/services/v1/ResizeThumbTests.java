package gg.scode.imageresizeservice.services.v1;

import gg.scode.imageresizeservice.config.TestSaveConfiguration;
import gg.scode.imageresizeservice.dto.ImageStandard;
import gg.scode.imageresizeservice.utils.UrlUtil;
import gg.scode.imageresizeservice.utils.ImageWriter;
import gg.scode.imageresizeservice.utils.TestUtil;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.awt.image.BufferedImage;
import java.io.File;

import static gg.scode.imageresizeservice.config.Standard.*;

@Slf4j
public class ResizeThumbTests {

    static TestSaveConfiguration configuration;

    long startTimeLap;
    long startMemLap;

    @BeforeAll
    static void setUp() {

        boolean permanentMode = false;
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

    @BeforeEach
    void init() {

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

    @Test
    @DisplayName("Image Resize Test 1")
    void test1() throws Exception {

        ImageStandard imageStandard = new ImageStandard(UrlUtil.generateLoremUrlString(MAX_WIDTH.getValue(), MAX_HEIGHT.getValue()), MIN_WIDTH.getValue(), MIN_HEIGHT.getValue());
//        BufferedImage originImage = ImageIO.read(TestUtil.getImageUrl(imageStandard.getUrl()));

        BufferedImage resizedImage = Thumbnails.of(UrlUtil.convertUrl(imageStandard.getUrl()))
                .size(imageStandard.getWidth(), imageStandard.getHeight())
                .asBufferedImage();

        // Save image file
        if (configuration.isPermanentMode()) ImageWriter.saveImageFile(TestUtil.generateFileName(TestUtil.PNG),resizedImage);

//        Assertions.assertThat(originImage.getWidth()).isEqualTo(500);
//        Assertions.assertThat(originImage.getHeight()).isEqualTo(500);

        Assertions.assertThat(resizedImage.getWidth()).isEqualTo(150);
        Assertions.assertThat(resizedImage.getHeight()).isEqualTo(150);

    }

    @Test
    @DisplayName("Image Resize Test 2 - feat keep aspect ratio")
    void test2() throws Exception {

        ImageStandard imageStandard = new ImageStandard("https://images.unsplash.com/photo-1682687982167-d7fb3ed8541d?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", MAX_WIDTH.getValue(), MAX_HEIGHT.getValue());
//        BufferedImage originImage = ImageIO.read(TestUtil.getImageUrl(imageStandard.getUrl()));

        BufferedImage resizedImage = Thumbnails.of(UrlUtil.convertUrl(imageStandard.getUrl()))
                .size(imageStandard.getWidth(), imageStandard.getHeight())
//                .keepAspectRatio(false)
                .asBufferedImage();

        // Save image file
        if (configuration.isPermanentMode()) ImageWriter.saveImageFile(TestUtil.generateFileName(TestUtil.PNG),resizedImage);

        Assertions.assertThat(resizedImage.getWidth()).isEqualTo(500);
        //
//        Assertions.assertThat(resizedImage.getHeight()).isEqualTo(500); // actual 333

    }
    @Test
    @DisplayName("Image Resize Test 3 - keep aspect ratio")
    void test3() throws Exception {

        ImageStandard imageStandard = new ImageStandard("https://images.unsplash.com/photo-1682687982167-d7fb3ed8541d?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", MAX_WIDTH.getValue(), MAX_HEIGHT.getValue());

        BufferedImage resizedImage = Thumbnails.of(UrlUtil.convertUrl(imageStandard.getUrl()))
                .size(imageStandard.getWidth(), imageStandard.getHeight())
                .keepAspectRatio(false)
                .asBufferedImage();

        // Save image file
        if (configuration.isPermanentMode()) ImageWriter.saveImageFile(TestUtil.generateFileName(TestUtil.PNG),resizedImage);

        Assertions.assertThat(resizedImage.getWidth()).isEqualTo(500);
        Assertions.assertThat(resizedImage.getHeight()).isEqualTo(500);

    }

}
