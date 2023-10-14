package com.minis.resource;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * @Author lnd
 * @Description 加载配置文件到内存中
 * @Date 2023/10/14 13:07
 */
@Slf4j
public class ClassPathXmlResource implements Resource {

    /* 对外提供的3个用于操作XML配置文件的对象 */
    Document document;
    Element rootElement;
    Iterator elementIterator;

    public ClassPathXmlResource(String fileName) {
        SAXReader saxReader = new SAXReader();
        URL xmlPath = null;
        try {
            // 将配置文件装在进来，生成一个迭代器，可以用于遍历
            xmlPath = this.getClass().getClassLoader().getResource(fileName);
            /** Part2：加载配置文件到内存中，将 XML 配置文件读取到内存中，用一个 document 对象表示 */
            document = saxReader.read(xmlPath); // document 就是配置文件在内存中的映像
            rootElement = document.getRootElement();
            elementIterator = rootElement.elementIterator();
        } catch (Exception e) {
            log.info("生成XML配置文件读取器失败,xmlPath:{}", xmlPath, e);
        }
    }

    /* hasNext 和 next 都是 Iterator 接口要求必须优子类重写的类 */
    @Override
    public boolean hasNext() {
        return elementIterator.hasNext();
    }

    @Override
    public Object next() {
        return elementIterator.next();
    }
}
