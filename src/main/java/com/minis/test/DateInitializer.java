package com.minis.test;

import com.minis.web.WebBindingInitializer;
import com.minis.web.WebDataBinder;

import java.util.Date;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/6 08:23
 */
public class DateInitializer implements WebBindingInitializer {
    @Override
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(Date.class,"yyyy-MM-dd", false));
    }
}
