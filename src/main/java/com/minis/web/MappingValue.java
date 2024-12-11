package com.minis.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author lnd
 * @Description
 * @Date 2024/4/17 15:02
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class MappingValue {
    String uri;
    String clz;
    String method;
}
