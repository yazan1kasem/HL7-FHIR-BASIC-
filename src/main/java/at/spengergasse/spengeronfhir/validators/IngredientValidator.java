package at.spengergasse.spengeronfhir.validators;

import at.spengergasse.spengeronfhir.entities.Ingredient;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IngredientValidator implements ConstraintValidator<IngredientValid, Ingredient> {
    @Override
    public void initialize(IngredientValid constraintAnnotation){
    }
    @Override
    public boolean isValid(Ingredient ingredient, ConstraintValidatorContext
            context) {
        boolean isValid = false;
        if (ingredient.getItemCodeableConcept() != null && ingredient.getItemReference() != null ||
                ingredient.getItemCodeableConcept() == null && ingredient.getItemReference() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Both itemCodeableConcept and itemReference cannot be non-null")
                    .addConstraintViolation();
            isValid = false;
        }  else {
            isValid = true;
        }
        return isValid;
    }
}
