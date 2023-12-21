package gg.scode.imageresizeservice.dto;

import gg.scode.imageresizeservice.utils.validators.HttpsURL;
import gg.scode.imageresizeservice.utils.validators.WidthOrHeight;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@WidthOrHeight
@NoArgsConstructor
@AllArgsConstructor
public class ImageStandard {

    @NotNull
    @HttpsURL
    @URL(protocol = "https")
    private String url;

    @Positive
    @Max(value = 1500)
    private Integer width;

    @Positive
    @Max(value = 1500)
    private Integer height;

    private Boolean keepRatio = false;

    @NotBlank
    private String key;

    public ImageStandard(String url, Integer width, Integer height) {
        this.url = url;
        this.width = width;
        this.height = height;
    }

}
