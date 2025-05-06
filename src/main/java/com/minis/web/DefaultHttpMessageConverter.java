package com.minis.web;

import com.minis.util.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/6 08:45
 */
public class DefaultHttpMessageConverter implements HttpMessageConverter {
    String defaultContentType = "text/json;charset=UTF-8";
    String defaultCharacterEncoding = "UTF-8";
    ObjectMapper objectMapper;

    @Override
    public void write(Object obj, HttpServletResponse response) throws Exception {
        response.setContentType(defaultContentType);
        response.setCharacterEncoding(defaultCharacterEncoding);
        writeInternal(obj, response);
        response.flushBuffer();
    }

    private void writeInternal(Object obj, HttpServletResponse response) throws IOException {
        String jsonStr = this.objectMapper.writeValuesAsString(obj);
        PrintWriter writer = response.getWriter();
        writer.write(jsonStr);
    }
}
