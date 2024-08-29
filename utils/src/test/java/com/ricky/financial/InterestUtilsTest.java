package com.ricky.financial;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/8/14
 * @className InterestUtilsTest
 * @desc
 */
class InterestUtilsTest {

    @Test
    public void calculateInterestRate() {
        // Given
        LocalDate beginDate = LocalDate.of(2024, 5, 21);
        BigDecimal principal = BigDecimal.valueOf(50000);
        BigDecimal interest = BigDecimal.valueOf(337.65);

        // When
        BigDecimal interestRate = InterestUtils.calculateInterestRate(beginDate, principal, interest);

        // Then
        System.out.println(interestRate);
        assertThat(interestRate).isEqualTo(BigDecimal.valueOf(0.028998));
    }

}