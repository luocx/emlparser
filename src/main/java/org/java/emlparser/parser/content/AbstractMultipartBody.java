package org.java.emlparser.parser.content;

import org.java.emlparser.parser.constants.ContentTypeAttributeEnum;

/**
 * Create by LuoChenXu on 2019/10/16
 */
public abstract class AbstractMultipartBody<T> extends AbstractBody<T>{

    private String boundary;

    public AbstractMultipartBody(Body parent) {
        super(parent);
    }

    /**
     * 增加内容
     *
     * @param c
     */
    @Override
    public abstract void append(char c);

    /**
     * 解析Body属性值抽象方法
     * 除去固定的一些属性，其余都交给具体body实现类去解析
     *
     * @param name
     * @param value
     */
    @Override
    public void setAttribute(String name, String value) {
        if (ContentTypeAttributeEnum.BOUNDARY.getName().equals(name)) {
            this.boundary = value;
        }else{
            super.setAttribute(name, value);
        }
    }

    /**
     * 返回内容
     *
     * @return
     */
    @Override
    public abstract T getContent();

    @Override
    public String boundary(){
        return this.boundary;
    }
}
