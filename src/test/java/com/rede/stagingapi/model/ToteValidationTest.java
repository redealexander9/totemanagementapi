package com.rede.stagingapi.model;
import com.rede.stagingapi.model.Tote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Set;

public class ToteValidationTest {
    private Validator validator;

    @BeforeEach
    void setup(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void osnMustBe4Digits(){
        Tote tote = new Tote();
        tote.setOsn("12345");

        Set<ConstraintViolation<Tote>> violations = validator.validate(tote);
        assertEquals(1, violations.size());
        assertEquals("OSN must be 4 digits, each 0-9", violations.iterator().next().getMessage());

    }
}
