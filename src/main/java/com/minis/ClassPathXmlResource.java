package com.minis;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

/**
 * @Author lnd
 * @Description
 *      Part2：配置文件加载器
 *          不是直接读取并处理XML配置文件，而是对外提供了一个可以读XML配置文件的入口
 * @Date 2023/10/14 13:07
 */
@Slf4j
public class ClassPathXmlResource implements Resource{

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
            document = saxReader.read(xmlPath);
            rootElement = document.getRootElement();
            elementIterator = rootElement.elementIterator();
        } catch (Exception e) {
            log.info("生成XML配置文件读取器失败,xmlPath:{}", xmlPath, e);
        }
    }
}
