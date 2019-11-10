package org.java.emlparser.parser.content;


import org.java.emlparser.parser.constants.ContentTypeAttributeEnum;
import org.java.emlparser.parser.constants.FieldNameEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by LuoChenXu on 2019/10/16
 */
public abstract class AbstractBody<T> implements Body<T> {

    //MIME类型
    protected String contentType;
    //编码字符格式
    protected String charset;
    //编码方式
    protected String encodeName;
    //内容标识符
    protected String contentId;
    //描述
    protected String dispiostion;

    protected final Body parentNode;

    protected Body childNode;

    protected final List<Body> contentBodyList;

    public AbstractBody(Body parent) {
        this.parentNode = parent;
        if (this.parentNode != null) {
            this.parentNode.setChildNode(this);
        }
        this.contentBodyList = new ArrayList<Body>();
    }

    @Override
    public Body parentNode() {
        return this.parentNode;
    }

    @Override
    public Body childNode() {
        return this.childNode;
    }

    @Override
    public void setChildNode(Body body) {
        this.childNode = body;
    }

    @Override
    public void addContentBody(Body body) {
        this.contentBodyList.add(body);
    }

    @Override
    public List<Body> getContentBodyList() {
        return this.contentBodyList;
    }

    @Override
    public abstract void setContent(T t);

    /**
     * 增加内容
     *
     * @param c
     */
    @Override
    public abstract void append(char c);

    /**
     * 设置内容属性
     *
     * @param name
     * @param value
     */
    @Override
    public void setAttribute(String name, String value) {
        if (FieldNameEnum.CONTENT_TYPE.getName().equals(name)) {
            this.contentType = value;
        } else if (FieldNameEnum.CONTENT_ID.getName().equals(name)) {
            if (value.startsWith("<") && value.endsWith(">")) {
                value = value.substring(1, value.length() - 1);
            }
            this.contentId = value;
        } else if (FieldNameEnum.CONTENT_TRANSFER_ENCODING.getName().equals(name)) {
            this.encodeName = value;
        } else if (ContentTypeAttributeEnum.CHARSET.getName().equals(name)) {
            this.charset = value;
        } else if (FieldNameEnum.CONTENT_DISPOSTION.getName().equals(name)) {
            this.dispiostion = value;
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
    public String boundary() {
        return null;
    }

    public String getContentType() {
        return contentType;
    }

    public String getCharset() {
        return charset;
    }

    public String getEncodeName() {
        return encodeName;
    }

    public String getContentId() {
        return contentId;
    }

    public String getDispiostion() {
        return dispiostion;
    }
}
