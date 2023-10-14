package com.minis;

import java.util.Iterator;

/**
 * @Author lnd
 * @Description
 * @Date 2023/10/14 13:07
 */
public interface Resource extends Iterator<Object> {
}

/*
1、 Iterator 接口的作用？
    Java中实现Iterator接口的作用是用于遍历集合类中的元素，可以在不暴露集合内部实现细节的情况下，
    对集合进行迭代访问。Iterator接口提供了一些方法，例如next()用于获取下一个元素，hasNext()
    用于判断是否还有下一个元素等，使得遍历集合变得更加方便和灵活。同时，实现Iterator接口的类可以
    使用增强for循环进行遍历，增强for循环本质上也是通过调用Iterator接口的方法来实现遍历的。

    Iterator 接口的实现类必须重写 next 和 hasNext 方法


2、Resource 接口为什么要继承 Iterator 接口？

    以 XmlResource 为例，XmlResource 会读取XML配置文件，生成一个配置文件的内存映像，在当前项目中就是 document 对象
        > document = saxReader.read(xmlPath);

    但是在Spring中，内存映像(document)是不能被直接使用的，document 中的数据是很多个 BeanDefinition 的合集，
    Spring 需要将 document 解析处理成一个个 BeanDefinition，所以这里需要用到一个操作就是“遍历”。

    将Bean定义信息映射成 BeanDefinition 的过程是由 XxxReader 来完成的，而 XmlResource 需要为 XxxReader 提供
    document 数据。关于如何处理 document 数据，接下来会有两种做法：
        1、一是像之前实现的那样，XmlResource 为 XxxReader 提供 document 对象，XxxReader 自己通过 for 循环遍历
        document下的所有数据节点并映射为 BeanDefinition。
        2、二就是我们目前使用的这种方法，让 Resource 接口继承 Iterator 接口，让它们的共同子类实现 next 和 hasNext
        方法，Resource 实现类同时对外暴漏 document 对象、next 方法和 hasNext 方法。这样 XxxReader 就可以不用使用
        for 循环，而是依赖于 Resource 中的 next 和 hasNext 方法来判断 XML 配置文件是否读完毕。
    这两种方法的区别是，
        方案一简化了 XmlResource 的逻辑，增加了 XxxReader 的逻辑；
        方案二简化了 XxxReader 的逻辑，增加了 XmlResource 的逻辑；

    具体使用哪种方案是自由的，Spring框架选择了方案二。 当然，如果你不喜欢这种方案，让 Resource 接口不继承 Iterator
    接口也是可以的。在 XxxReader 的代码中通过 for 循环的方式来获取配置文件中每个 bean 的信息也是完全OK的。
*/
