package org.java.emlparser.parser.content;

import org.java.emlparser.parser.BodyUtils;
import org.java.emlparser.parser.constants.ContentTypeAttributeEnum;

import java.io.IOException;

/**
 * Create by LuoChenXu on 2019/10/15
 */
public class ApplicationStreamBody extends AbstractBody<byte[]> {

    private String name;

    private String filename;

    private StringBuilder enctryStr;

    private byte[] filebyte;

    public ApplicationStreamBody(Body parent) {
        super(parent);
        this.enctryStr = new StringBuilder();
    }

    public ApplicationStreamBody() {
        this(null);
    }

    @Override
    public void setContent(byte[] b) {
        this.filebyte = b;
    }

    @Override
    public void setAttribute(String name, String value) {
        if (ContentTypeAttributeEnum.NAME.getName().equals(name)) {
            this.name = value;
        } else if (ContentTypeAttributeEnum.FILENAME.getName().equals(name)) {
            this.filename = value;
        } else {
            super.setAttribute(name, value);
        }
    }

    /**
     * 增加内容
     *
     * @param c
     */
    @Override
    public void append(char c) {
        this.enctryStr.append(c);
    }

    public String getName() {
        return this.name;
    }

    public String getFilename() {
        return this.filename;
    }

    /**
     * 返回内容
     *
     * @return
     */
    @Override
    public byte[] getContent() {
        return null;
    }

    public byte[] getBytes() throws IOException {
        return BodyUtils.translateString(enctryStr.toString(), getEncodeName(), getCharset()).getBytes();
    }
}
