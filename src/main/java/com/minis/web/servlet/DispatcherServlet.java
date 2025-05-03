package com.minis.web.servlet;

import com.minis.web.AnnotationConfigWebApplicationContext;
import com.minis.web.WebApplicationContext;
import com.minis.web.XmlScanComponentHelper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * @Author lnd
 * @Description
 * @Date 2025/5/2 23:29
 */
public class DispatcherServlet extends HttpServlet {

    public static final String WEB_APPLICATION_CONTEXT_ATTRIBUTE = DispatcherServlet.class.getName() + ".CONTEXT";

    private WebApplicationContext parentApplicationContext;
    private WebApplicationContext webApplicationContext;

    private String contextConfigLocation;

    private List<String> packageNames = new ArrayList<>();

    private Map<String, Object> controllerObjs = new HashMap<>();
    private List<String> controllerNames = new ArrayList<>();
    private Map<String, Class<?>> controllerClasses = new HashMap<>();

    private HandlerMapping handlerMapping;
    private HandlerAdapter handlerAdapter;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try {
            super.init(config); // 调用父类 HttpServlet 的 init 方法，加载 web.xml 中的配置信息
            this.parentApplicationContext = (WebApplicationContext) this.getServletContext().getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
            contextConfigLocation = config.getInitParameter("contextConfigLocation"); // 获取 web.xml 中的 init-param.contextConfigLocation 配置信息
            URL xmlPath = this.getServletContext().getResource(contextConfigLocation);
            this.packageNames = XmlScanComponentHelper.getNodeValue(xmlPath);
            this.webApplicationContext = new AnnotationConfigWebApplicationContext(contextConfigLocation, this.parentApplicationContext);
            refresh();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, this.webApplicationContext);
        try {
            doDispatch(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpServletRequest processedRequest = request;
        HandlerMethod handlerMethod = this.handlerMapping.getHandler(processedRequest);
        if (handlerMethod == null) {
            return;
        }
        HandlerAdapter ha = this.handlerAdapter;
        ha.handle(processedRequest, response, handlerMethod);
    }


    private void refresh() {
        initController();
        initHandlerMappings(this.webApplicationContext);
        initHandlerAdapters(this.webApplicationContext);
        initViewResolvers(this.webApplicationContext);
    }

    private void initViewResolvers(WebApplicationContext webApplicationContext) {

    }

    private void initHandlerAdapters(WebApplicationContext webApplicationContext) {
        this.handlerAdapter = new RequestMappingHandlerAdapter(webApplicationContext);
    }

    private void initHandlerMappings(WebApplicationContext webApplicationContext) {
        this.handlerMapping = new RequestMappingHandlerMapping(webApplicationContext);
    }

    private void initController() {
        try {
            this.controllerNames = Arrays.asList(this.webApplicationContext.getBeanDefinitionNames());
            for (String controllerName : this.controllerNames) {
                this.controllerClasses.put(controllerName, Class.forName(controllerName));
                this.controllerObjs.put(controllerName, this.webApplicationContext.getBean(controllerName));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
