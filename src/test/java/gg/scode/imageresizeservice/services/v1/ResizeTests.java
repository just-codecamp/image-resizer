package gg.scode.imageresizeservice.services.v1;

import gg.scode.imageresizeservice.config.TestSaveConfiguration;
import gg.scode.imageresizeservice.config.exceptions.AbnormalUriFormatException;
import gg.scode.imageresizeservice.dto.ImageStandard;
import gg.scode.imageresizeservice.utils.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.Optional;

import static gg.scode.imageresizeservice.config.Standard.*;
import static gg.scode.imageresizeservice.utils.ImageWriter.saveImageFile;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class ResizeTests {

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
    @DisplayName("Get image object from URL")
    void test1() throws Exception {
        ImageStandard imageStandard = new ImageStandard(UrlUtil.generateLoremUrlString(MAX_WIDTH.getValue(), MAX_HEIGHT.getValue()), MIN_WIDTH.getValue(), MIN_HEIGHT.getValue());
        Optional<Image> image = getImage(imageStandard);

        assertTrue(image.isPresent());
    }

    @Test
    @DisplayName("Get image object from unsafe URL")
    void test2() throws Exception {
        ImageStandard imageStandard = new ImageStandard(UrlUtil.generateMalformedUrlString(), MIN_WIDTH.getValue(), MIN_HEIGHT.getValue());
        assertThrows(AbnormalUriFormatException.class, () -> getImage(imageStandard));
    }

    @Test
    @DisplayName("Get image object from no image URL 1")
    void test2_1() throws Exception {
        ImageStandard imageStandard = new ImageStandard("https://naver.com", MIN_WIDTH.getValue(), MIN_HEIGHT.getValue());
        BufferedImage originImage = ImageIO.read(URI.create(imageStandard.getUrl()).toURL());

        Assertions.assertThat(originImage).isNull();
    }

    @Test
    @DisplayName("Get image object from no image URL 2")
    void test2_2() throws Exception {
        ImageStandard imageStandard = new ImageStandard("https://google.com", MIN_WIDTH.getValue(), MIN_HEIGHT.getValue());
        BufferedImage originImage = ImageIO.read(URI.create(imageStandard.getUrl()).toURL());

        Assertions.assertThat(originImage).isNull();
    }

    @Test
    @DisplayName("Get image object from unsplash url")
    void test2_3() throws Exception {
        ImageStandard imageStandard = new ImageStandard("https://unsplash.com/photos/a-scuba-diver-swims-through-an-underwater-cave-yx7TJle8LhM", MAX_WIDTH.getValue(), MAX_HEIGHT.getValue());
        BufferedImage originImage = ImageIO.read(URI.create(imageStandard.getUrl()).toURL());

        Assertions.assertThat(originImage).isNull();
    }

    @Test
    @DisplayName("Get image object from unsplash image url")
    void test2_4() throws Exception {
        ImageStandard imageStandard = new ImageStandard("https://images.unsplash.com/photo-1682687982167-d7fb3ed8541d?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", MAX_WIDTH.getValue(), MAX_HEIGHT.getValue());
        BufferedImage originImage = ImageIO.read(URI.create(imageStandard.getUrl()).toURL());

        Assertions.assertThat(originImage).isNotNull();
    }

    @Test
    @DisplayName("Check big image of unsplash resize to 500 x 500")
    void test2_5() throws Exception {

        ImageStandard imageStandard = new ImageStandard("https://images.unsplash.com/photo-1682687982167-d7fb3ed8541d?q=80&w=2071&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D", MAX_WIDTH.getValue(), MAX_HEIGHT.getValue());
        BufferedImage originImage = ImageIO.read(URI.create(imageStandard.getUrl()).toURL());

        if ( configuration.isPermanentMode()) saveImageFile("test2_5_origin_image.png", originImage);

        BufferedImage resizedImage = new BufferedImage(imageStandard.getWidth(), imageStandard.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originImage, 0,0, imageStandard.getWidth(), imageStandard.getHeight(), null);
        graphics2D.dispose();

        if ( configuration.isPermanentMode()) saveImageFile("test2_5_resized_image.png", resizedImage);

        Assertions.assertThat(originImage.getWidth()).isEqualTo(2071);
        Assertions.assertThat(originImage.getHeight()).isEqualTo(1381);

        Assertions.assertThat(resizedImage.getHeight()).isEqualTo(500);
        Assertions.assertThat(resizedImage.getWidth()).isEqualTo(500);

    }

    @Test
    @DisplayName("Check 500 x 500 image resize to 150 x 150")
    void test3() throws Exception {

        ImageStandard imageStandard = new ImageStandard(UrlUtil.generateLoremUrlString(MAX_HEIGHT.getValue(), MAX_HEIGHT.getValue()), MIN_WIDTH.getValue(), MIN_HEIGHT.getValue());
        BufferedImage originImage = ImageIO.read(URI.create(imageStandard.getUrl()).toURL());

        if ( configuration.isPermanentMode()) saveImageFile("test3_origin_image.png", originImage);

        BufferedImage resizedImage = new BufferedImage(imageStandard.getWidth(), imageStandard.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originImage, 0,0, imageStandard.getWidth(), imageStandard.getHeight(), null);
        graphics2D.dispose();

        if ( configuration.isPermanentMode()) saveImageFile("test3_resized_image.png", resizedImage);

        Assertions.assertThat(originImage.getHeight()).isEqualTo(500);
        Assertions.assertThat(originImage.getWidth()).isEqualTo(500);

        Assertions.assertThat(resizedImage.getHeight()).isEqualTo(150);
        Assertions.assertThat(resizedImage.getWidth()).isEqualTo(150);

    }

    @Test
    @DisplayName("Check 150 x 150 image resize to 300 x 300")
    void test4() throws Exception {

        ImageStandard imageStandard = new ImageStandard(UrlUtil.generateLoremUrlString(MIN_WIDTH.getValue(), MIN_HEIGHT.getValue()), 300, 300);
        BufferedImage originImage = ImageIO.read(URI.create(imageStandard.getUrl()).toURL());

        if ( configuration.isPermanentMode()) saveImageFile("test4_origin_image.png", originImage);

        BufferedImage resizedImage = new BufferedImage(imageStandard.getWidth(), imageStandard.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originImage, 0,0, imageStandard.getWidth(), imageStandard.getHeight(), null);
        graphics2D.dispose();

        if ( configuration.isPermanentMode()) saveImageFile("test4_resized_image.png", resizedImage);

        Assertions.assertThat(originImage.getHeight()).isEqualTo(150);
        Assertions.assertThat(originImage.getWidth()).isEqualTo(150);

        Assertions.assertThat(resizedImage.getHeight()).isEqualTo(300);
        Assertions.assertThat(resizedImage.getWidth()).isEqualTo(300);

    }

    private Optional<Image> getImage(ImageStandard imageStandard) throws AbnormalUriFormatException {
        try {
            URL url = URI.create(imageStandard.getUrl()).toURL();
            return Optional.ofNullable(ImageIO.read(url));
        } catch (Exception e) {
            throw new AbnormalUriFormatException("");
        }
    }

}
