package com.minis.util;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/6 08:49
 */
public interface ObjectMapper {
    void setDateFormat(String dateFormat);
    void setDecimalFormat(String decimalFormat);
    String writeValuesAsString(Object obj);
}
