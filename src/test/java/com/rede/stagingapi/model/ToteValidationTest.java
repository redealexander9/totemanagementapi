package com.rede.stagingapi.model;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Set;

public class ToteValidationTest {
    private static Validator validator;
    private static ValidatorFactory factory;

    @BeforeEach
    void setup(){
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDownAll(){
        if(factory != null){
            factory.close();
        }
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
