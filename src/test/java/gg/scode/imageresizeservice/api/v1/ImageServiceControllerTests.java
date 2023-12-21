package gg.scode.imageresizeservice.api.v1;

import gg.scode.imageresizeservice.config.Standard;
import gg.scode.imageresizeservice.dto.ImageStandard;
import gg.scode.imageresizeservice.services.v1.Cropper;
import gg.scode.imageresizeservice.utils.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.stream.Stream;

import static gg.scode.imageresizeservice.config.Standard.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest
class ImageServiceControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    Cropper cropper;

    static Stream<String> getMalformedUrlStrings() {
        return UrlUtil.generateMalformedUrlStrings().stream();
    }

    static Stream<String> getLoremUrlStrings() {
        return UrlUtil.generateLoremUrlStrings(30).stream();
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("getLoremUrlStrings")
    @DisplayName("Accurate parameters")
    void test1(String url) throws Exception {

        ImageStandard imageStandard = new ImageStandard(
            url
            , MIN_WIDTH.getValue()
            , MIN_HEIGHT.getValue()
        );

        given(cropper.resize(imageStandard)).willReturn(new byte[]{});

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/v1/resize")
                    .param("url", imageStandard.getUrl())
                    .param("width", imageStandard.getWidth().toString())
                    .param("height", imageStandard.getHeight().toString())
                )
                .andDo(print());

        actions.andExpect(status().isCreated());

    }

    @Disabled
    @ParameterizedTest
    @MethodSource("getMalformedUrlStrings")
    @DisplayName("Malformed urls are given")
    void test1_1(String url) throws Exception {

        ImageStandard imageStandard = new ImageStandard(
                url
                , MIN_WIDTH.getValue()
                , MIN_HEIGHT.getValue()
        );

        given(cropper.resize(imageStandard)).willReturn(new byte[]{});

        ResultActions actions = mockMvc.perform(MockMvcRequestBuilders.get("/v1/resize")
                        .param("url", imageStandard.getUrl())
                        .param("width", imageStandard.getWidth().toString())
                        .param("height", imageStandard.getHeight().toString())
                )
                .andDo(print());

        actions.andExpect(status().isBadRequest());

    }


}