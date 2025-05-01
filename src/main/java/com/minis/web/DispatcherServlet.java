package com.minis.web;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author lnd
 * @Description
 * @Date 2024/4/17 14:45
 */
@Slf4j
public class DispatcherServlet extends HttpServlet {

    private Map<String, MappingValue> mappingValues; // uri:MappingValue
    private Map<String, Class<?>> mappingClz = new HashMap<>(); // uri:Clazz
    private Map<String, Object> mappingObjs = new HashMap<>(); // uri:bean

    private String contextConfigLocation;

    public void init(ServletConfig config) throws ServletException {
        super.init(config); // 调用父类 HttpServlet 的 init 方法，加载 web.xml 中的配置信息
        contextConfigLocation = config.getInitParameter("contextConfigLocation"); // 获取 web.xml 中的 init-param.contextConfigLocation 配置信息
        URL xmlPath = null;
        try {
            xmlPath = this.getServletContext().getResource(contextConfigLocation); // 获取绝对路径
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        // 加载 minisMVC-servlet.xml 配置文件到内存中，生成 Resource 对象
        Resource rs = new ClassPathXmlResource(xmlPath);
        XmlConfigReader reader = new XmlConfigReader();
        // 解析 Resource 对象，生成 MappingValue 对象
        mappingValues = reader.loadConfig(rs);
        Refresh();
    }

    // 读取 mappingValues 中的 Bean 定义，加载类，创建实例
    protected void Refresh() {
        for (Map.Entry<String, MappingValue> entry : mappingValues.entrySet()) {
            String id = entry.getKey();
            String className = entry.getValue().getClz();
            Object obj = null;
            Class<?> clz = null;
            try {
                clz = Class.forName(className);
                obj = clz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mappingClz.put(id, clz);
            mappingObjs.put(id, obj);
        }
    }

    /**
     * DispatcherServlet 用来处理所有的 Web 请求，但是目前我们只是简单地实现了 Get 请求的处理，通过 Bean 的 id 获取其对应的类和方法，依赖反射机制进行调用。
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath(); // 获取请求的path
        if (this.mappingValues.get(path) == null) {
            log.warn("http request path not matched , path={}", path);
            return;
        }
        log.info("http request path matched success , path={}", path);
        Class<?> clz = this.mappingClz.get(path); // 获取bean类定义
        Object obj = this.mappingObjs.get(path);  // 获取bean实例
        String methodName = this.mappingValues.get(path).getMethod(); //获取调用方法名
        Object objResult = null;
        try {
            Method method = clz.getMethod(methodName);
            objResult = method.invoke(obj); // 方法调用
        } catch (Exception ignored) {
        }
        // 将方法返回值写入response
        response.getWriter().append(objResult.toString());
    }
}
