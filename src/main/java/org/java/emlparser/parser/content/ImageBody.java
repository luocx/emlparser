package org.java.emlparser.parser.content;


import org.java.emlparser.parser.BodyUtils;
import org.java.emlparser.parser.constants.ContentTypeAttributeEnum;

import java.io.IOException;

/**
 * Create by LuoChenXu on 2019/10/15
 */
public class ImageBody extends AbstractBody<String> {

    private String name;

    private StringBuilder enctryStr;

    public ImageBody(Body parent) {
        super(parent);
        this.enctryStr = new StringBuilder();
    }

    @Override
    public void setContent(String s) {
        enctryStr.setLength(0);
        enctryStr.append(s);
    }

    @Override
    public void setAttribute(String name, String value) {
        if (ContentTypeAttributeEnum.NAME.getName().equals(name)) {
            this.name = value;
        } else {
            super.setAttribute(name, value);
        }
    }

    public ImageBody() {
        this(null);
    }

    /**
     * 增加内容
     *
     * @param c
     */
    @Override
    public void append(char c) {
        enctryStr.append(c);
    }

    public String getName() {
        return this.name;
    }

    /**
     * 返回内容
     *
     * @return
     */
    @Override
    public String getContent() {
        return this.enctryStr.toString();
    }

    public byte[] getBytes() throws IOException {
        return BodyUtils.translateString(this.enctryStr.toString(), getEncodeName(), getCharset()).getBytes();
    }
}
