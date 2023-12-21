package gg.scode.imageresizeservice.utils.validators;

import gg.scode.imageresizeservice.dto.ImageStandard;
import gg.scode.imageresizeservice.utils.UrlUtil;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static gg.scode.imageresizeservice.config.Standard.*;

@Slf4j
class WidthOrHeightValidatorTests {

    @Mock
    ConstraintValidatorContext context;

    WidthOrHeightValidator validator = new WidthOrHeightValidator();


    @Test
    @DisplayName("Given All accurate parameter")
    void test1() throws Exception {
        ImageStandard imageStandard = new ImageStandard(UrlUtil.generateLoremUrlString(MAX_WIDTH.getValue(), MAX_HEIGHT.getValue()), MIN_WIDTH.getValue(), MIN_HEIGHT.getValue());
        Assertions.assertTrue(validator.isValid(imageStandard, context));
    }

    @Test
    @DisplayName("Given accurate url String and Width")
    void test2() throws Exception {
        ImageStandard imageStandard = new ImageStandard(UrlUtil.generateLoremUrlString(MAX_WIDTH.getValue(), MAX_HEIGHT.getValue()), MIN_WIDTH.getValue(), null);
        Assertions.assertTrue(validator.isValid(imageStandard, context));
    }

    @Test
    @DisplayName("Given accurate url String and Height")
    void test3() throws Exception {
        ImageStandard imageStandard = new ImageStandard(UrlUtil.generateLoremUrlString(MAX_WIDTH.getValue(), MAX_HEIGHT.getValue()), null, MIN_HEIGHT.getValue());
        Assertions.assertTrue(validator.isValid(imageStandard, context));
    }

}