package com.minis.web;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.Iterator;

/**
 * @Author lnd
 * @Description
 *  作用：加载XML类型的文件到内存中，
 *  输入：xxx.xml 路径
 *  输出：Document 对象
 *  Example：将 minisMVC-servlet.xml 配置文件加载到内存中，生成 Resource 对象，Resource 对象包括 Document 对象、RootElement 对象、ElementIterator 对象
 *  其中，Document 对象用于读取 XML 文件，RootElement 对象用于获取 XML 文件的根元素，ElementIterator 对象用于遍历 XML 文件的所有元素
 * @Date 2024/4/17 15:05
 */
public class ClassPathXmlResource implements Resource {

    Document document;
    Element rootElement;
    Iterator elementIterator;

    public ClassPathXmlResource(URL xmlPath) {
        SAXReader saxReader = new SAXReader();
        try {
            this.document = saxReader.read(xmlPath);
            this.rootElement = document.getRootElement();
            this.elementIterator = this.rootElement.elementIterator();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasNext() {
        return elementIterator.hasNext();
    }

    @Override
    public Object next() {
        return elementIterator.next();
    }
}
