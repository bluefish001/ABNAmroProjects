package com.abnamro.validation;

import org.junit.Test;
import static org.junit.Assert.assertFalse;

public class CommonValidatorTest {
    @Test
    public void testCommonValidationEmpty(){
        String record="";

        assertFalse(CommonValidator.commonValidate(record,1));
    }

    @Test
    public void testCommonValidationIllegalChar(){
        String record="315CL  432100020001SGXDC FUSGX NK    20100910JPY01B 0000000001 0000000000000000000060DUSD000000000030DUSD000000000000DJPY201008200012400     68805800009250000000@             O";

        assertFalse(CommonValidator.commonValidate(record,1));
    }
}
