package com.api.idsa.common.validation.validator;

import com.api.idsa.common.validation.annotation.YearRange;
import com.api.idsa.domain.academic.dto.request.GenerationRequest;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class YearRangeValidator implements ConstraintValidator<YearRange, GenerationRequest> {

    @Override
    public void initialize(YearRange constraintAnnotation) {
    }

    @Override
    public boolean isValid(GenerationRequest request, ConstraintValidatorContext context) {
        if (request.getYearStart() == null || request.getYearEnd() == null) {
            return true;
        }
        
        boolean isValid = request.getYearEnd().isAfter(request.getYearStart());
        
        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                  .addPropertyNode("yearEnd")
                  .addConstraintViolation();
        }
        
        return isValid;
    }
}
