package com.mindhub.homebanking;

import com.mindhub.homebanking.utils.CardUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class CardUtilsTest {

    @Test
    public void cardNumberIsCreated(){
        String cardNumber = CardUtils.getCardNumber();
        assertThat(cardNumber, is(not(emptyOrNullString())));
    }

    @Test
    public void cardCvvIsCreated(){
        Integer cardCvv = CardUtils.getCvv();
        assertThat(cardCvv, is(not(nullValue())));
    }

    @Test
    public void cardCvvIsThreeDigitsLong(){
        Integer cardCvv = CardUtils.getCvv();
        assertThat(cardCvv, is(allOf(greaterThanOrEqualTo(100), lessThanOrEqualTo(999))));
    }
}
