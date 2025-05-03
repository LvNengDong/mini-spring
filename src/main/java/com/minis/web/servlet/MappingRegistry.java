package com.minis.web.servlet;

import lombok.Data;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/3 00:17
 */
@Data
public class MappingRegistry {
    private List<String> urlMappingNames = new ArrayList<>();
    private Map<String,Object> mappingObj = new HashMap<>();
    private Map<String, Method> mappingMethod = new HashMap<>();
}
