package com.minis.web;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author lnd
 * @Description
 * @Date 2024/4/17 14:45
 */
// 注意注意：这个类中使用到的外部依赖，都需要放到 WEB-INF/lib 目录下，否则会报错
@Slf4j
public class DispatcherServlet extends HttpServlet {

    WebApplicationContext webApplicationContext;

    /*
        | 变量             | 作用                                                   |
        |------------------|--------------------------------------------------------|
        | packageNames     | 用于存储需要扫描的 package 列表                        |
        | controllerObjs   | 用于存储 controller 的名称与对象的映射关系             |
        | controllerNames  | 用于存储 controller 名称数组列表                        |
        | controllerClasses| 用于存储 controller 名称与类的映射关系                  |
        | urlMappingNames  | 是保存自定义的 @RequestMapping 名称（URL 的名称）的列表 |
        | mappingObjs      | 保存 URL 名称与对象的映射关系                           |
        | mappingMethods   | 保存 URL 名称与方法的映射关系                           |
    */
    private List<String> packageNames = new ArrayList<>();
    private Map<String, Object> controllerObjs = new HashMap<>();
    private List<String> controllerNames = new ArrayList<>();
    private Map<String, Class<?>> controllerClasses = new HashMap<>();
    private List<String> urlMappingNames = new ArrayList<>();
    private Map<String, Object> mappingObjs = new HashMap<>();
    private Map<String, Method> mappingMethods = new HashMap<>();


    private String contextConfigLocation;

    public void init(ServletConfig config) throws ServletException {
        try {
            super.init(config); // 调用父类 HttpServlet 的 init 方法，加载 web.xml 中的配置信息
            this.webApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
            contextConfigLocation = config.getInitParameter("contextConfigLocation"); // 获取 web.xml 中的 init-param.contextConfigLocation 配置信息
            URL xmlPath = this.getServletContext().getResource(contextConfigLocation);
            this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
            refresh();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 读取 mappingValues 中的 Bean 定义，加载类，创建实例
    protected void refresh() {
        // 初始化Controller
        initController();
        // 初始化 URL 映射
        initMapping();
    }

    private void initMapping() {
        for (String controllerName : this.controllerNames) {
            Class<?> clazz = this.controllerClasses.get(controllerName);
            Object obj = this.controllerObjs.get(controllerName);
            Method[] methods = clazz.getDeclaredMethods();

            for (Method method : methods) {
                if (method.isAnnotationPresent(RequestMapping.class)) {
                    // 建立方法名和URL的映射关系
                    String url = method.getAnnotation(RequestMapping.class).value();
                    this.urlMappingNames.add(url);
                    this.mappingObjs.put(url, obj);
                    this.mappingMethods.put(url, method);
                }
            }
        }
    }

    private void initController() {
        try {
            // 1. 扫描指定的包，获取所有的类名
            this.controllerNames = scanPackages(this.packageNames);
            for (String controllerName : this.controllerNames) {
                // 加载类
                Class<?> clz = Class.forName(controllerName);
                this.controllerClasses.put(controllerName, clz);
                // 创建实例
                Object obj = clz.newInstance();
                this.controllerObjs.put(controllerName, obj);
            }
        } catch (Exception e) {
            log.error("init controller error", e);
        }
    }

    private List<String> scanPackages(List<String> packageNames) {
        List<String> tempControllerNames = new ArrayList<>();
        for (String packageName : packageNames) {
            tempControllerNames.addAll(scanPackage(packageName));
        }
        return tempControllerNames;
    }

    private List<String> scanPackage(String packageName) {
        List<String> tempControllerNames = new ArrayList<>();
        try {
            String filePath = "/" + packageName.replaceAll("\\.", "/"); // 将以.分隔的包名换成以/分隔的uri
            URI uri = this.getClass().getResource(filePath).toURI();
            File dir = new File(uri);
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    scanPackage(packageName + "." + file.getName());
                } else {
                    String controllerName = packageName + "." + file.getName().replace(".class", "");// 去掉.class后缀
                    tempControllerNames.add(controllerName);
                }
            }
        } catch (Exception e) {
            log.error("find controller name from package error , packageName={}", packageName, e);
        }
        return tempControllerNames;
    }

    /**
     * DispatcherServlet 用来处理所有的 Web 请求，但是目前我们只是简单地实现了 Get 请求的处理，通过 Bean 的 id 获取其对应的类和方法，依赖反射机制进行调用。
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getServletPath(); // 获取请求的path
        if (!this.urlMappingNames.contains(path)) {
            log.info("http request path not matched , path={}", path);
            return;
        }
        log.info("http request path matched success , path={}", path);
        Object obj = this.mappingObjs.get(path); // 获取bean实例
        Method method = this.mappingMethods.get(path); //获取调用方法
        Object objResult = null;
        try {
            objResult = method.invoke(obj); // 方法调用
        } catch (Exception ignored) {
        }
        // 将方法返回值写入response
        response.getWriter().append(objResult.toString());
    }
}
