package gg.scode.imageresizeservice.utils.validators;

import gg.scode.imageresizeservice.dto.ImageStandard;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class WidthOrHeightValidator implements ConstraintValidator<WidthOrHeight, ImageStandard> {
    @Override
    public boolean isValid(ImageStandard imageStandard, ConstraintValidatorContext context) {
        return imageStandard.getHeight() != null || imageStandard.getWidth() != null;
    }

}
