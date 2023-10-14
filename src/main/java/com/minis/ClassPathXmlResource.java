package com.minis;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

/**
 * @Author lnd
 * @Description 解析 XML
 * @Date 2023/10/14 13:07
 */
public class ClassPathXmlResource implements Resource{

    /* 对外提供的3个用于操作XML配置文件的对象 */
    Document document;
    Element rootElement;
    Iterator elementIterator;

    public ClassPathXmlResource(String fileName) {
        SAXReader saxReader = new SAXReader();
        try {
            URL xmlPath = this.getClass().getClassLoader().getResource(fileName);
            document = saxReader.read(xmlPath);
            rootElement = document.getRootElement();
            elementIterator = rootElement.elementIterator();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
