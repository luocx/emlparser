package org.java.emlparser.parser.content;

import java.util.List;

/**
 * Create by LuoChenXu on 2019/10/15
 */
public interface Body<T> {
    /**
     * 返回父内容
     * EML文件中有固定的内容分层，不同内容直接属于包含关系
     * @return
     */
    public Body parentNode();

    public Body childNode();

    public void setChildNode(Body body);

    public void addContentBody(Body body);

    public List<Body> getContentBodyList();
    /**
     * 增加内容
     * @param c
     */
    public void append(char c);

    /**
     * 设置内容属性
     * @param name
     * @param value
     */
    public void setAttribute(String name, String value);

    public void setContent(T t);

    /**
     * 返回内容
     * @return
     */
    public T getContent();

    /**
     * 数据分段标识
     * multipart类型的数据必须有的值，其他类型数据返回为空字符
     * @return
     */
    public String boundary();
}
