package com.minis.web;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author lnd
 * @Description 解析 minisMVC-servlet.xml 配置文件中的 <component-scan> 标签，扫描指定包下的所有类，将其注册到容器中
 * @Date 2025/5/1 15:43
 */
@Slf4j
public class XmlScanComponentHelper {

    public static final SAXReader saxReader = new SAXReader();
    private static List<String> packages = new ArrayList<String>();

    public static List<String> getNodeValue(URL xmlPath) throws DocumentException {
        Document document = saxReader.read(xmlPath);
        Element root = document.getRootElement();
        Iterator it = root.elementIterator();
        while (it.hasNext()) { // 得到 XML 中所有的 base-package 节点
            Element element = (Element) it.next();
            String path = element.attributeValue("base-package");
            packages.add(path);
        }
        log.info("SpringMVC scan all base-package: {}", packages);
        return packages;
    }
}
