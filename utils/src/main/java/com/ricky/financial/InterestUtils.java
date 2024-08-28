package com.ricky.financial;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author Ricky
 * @version 1.0
 * @date 2024/8/14
 * @className InterestUtils
 * @desc
 */
public class InterestUtils {

    private InterestUtils() {}

    /**
     * 默认保留小数位数
     */
    public static final int DEFAULT_SCALE = 2;

    /**
     * 默认舍入模式
     */
    public static final RoundingMode DEFAULT_ROUNDINGMODE = RoundingMode.HALF_UP;

    /**
     * 计算年化利率
     * @param beginDate 开始日期
     * @param endDate 截止日期
     * @param principal 本金
     * @param interest 总利息
     * @return 年化利率
     */
    public static BigDecimal calculateInterestRate(LocalDate beginDate, LocalDate endDate, BigDecimal principal, BigDecimal interest) {
        if (principal.compareTo(BigDecimal.ZERO) == 0) {
            throw new IllegalArgumentException("Principal cannot be zero.");
        }
        long daysBetween = ChronoUnit.DAYS.between(beginDate, endDate);
        if (daysBetween == 0) {
            throw new IllegalArgumentException("End date must be after start date.");
        }

        return interest.multiply(BigDecimal.valueOf(365))
                .divide(BigDecimal.valueOf(daysBetween), 6, DEFAULT_ROUNDINGMODE)
                .divide(principal, 6, DEFAULT_ROUNDINGMODE);
    }

    /**
     * 计算年化利率
     * @param beginDate 开始日期
     * @param principal 本金
     * @param interest 总利息
     * @return 年化利率
     */
    public static BigDecimal calculateInterestRate(LocalDate beginDate, BigDecimal principal, BigDecimal interest) {
        return calculateInterestRate(beginDate, LocalDate.now(), principal, interest);
    }

    /**
     * 格式化百分数
     * @param bigDecimal 原数
     * @return 返回一个字符串，表示格式化后的百分数，以%结尾
     */
    public static String formatPercentage(BigDecimal bigDecimal) {
        return bigDecimal.multiply(BigDecimal.valueOf(100)) + "%";
    }

}
