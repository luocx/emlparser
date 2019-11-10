package org.java.emlparser.parser;


import org.java.emlparser.parser.constants.ContentTypeEnum;
import org.java.emlparser.parser.content.*;
import org.java.emlparser.parser.handle.Handle;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

/**
 * Create by LuoChenXu on 2019/10/10
 */
public class EMLParser {

    // 处理控制器，
    private final HandleControl handleControl;

    private final EMLParserMemory memory;

    private Handle handle;

    private Handle preHandle;

    private Character lastChar;

    private EMLContent emlContent;

    private Body body;

    public EMLParser(){
        this.emlContent = new EMLContent();
        this.memory = new EMLParserMemory();
        this.handleControl = new HandleControl(this);
        this.handleControl.unknowHandle();
    }

    /**
     * 解析EML文件
     * @param input
     * @throws IOException
     */
    public void parserEML(InputStream input) throws IOException {
        InputStreamReader reader = new InputStreamReader(input);

        char[] chars = new char[1];
        try {
            while ((reader.read(chars)) != -1){
                handle.process(chars[0]);
                lastChar = chars[0];
            }
        } finally {
            reader.close();
        }
    }

    public EMLParserMemory memory(){
        return this.memory;
    }

    public EMLContent content(){
        return this.emlContent;
    }

    public String current(){
        return this.memory.current().toString();
    }

    public String currentFieldName(){
        return this.memory.currentFieldName();
    }

    public void append(Character c) {
        this.memory.current().append(c);
    }

    public void recover() {
        for (char c : this.memory.last().toCharArray()) {
            this.memory.current().append(c);
        }
    }

    public Character lastChar() {
        return this.lastChar;
    }
    /**
     * 追加内容到body中
     *
     * @param c
     */
    public void appendBody(Character c) {
        this.memory.currentBody().append(c);
    }

    public void flushBody(){
        for (char c : this.current().toCharArray()) {
            this.memory.currentBody().append(c);
        }
    }

    /**
     * 设置处理器
     * @param handle
     */
    protected void setHandle(Handle handle) {
        this.preHandle = this.handle;
        this.handle = handle;
    }

    public Handle lastHandle(){
        return this.preHandle;
    }

    public HandleControl selectHandle() {
        return this.handleControl;
    }


    /**
     * 刷新数据分段标识
     * 设置为currentBody boundary
     */
    public void refreshBoundary() {
        this.memory.setCurrentBoundary(this.memory.currentBody() == null ? null : this.memory.currentBody().boundary());
    }

    public void refreshBody() {
        if (this.memory.currentBoundary() != null) {
            Body cb = this.body;
            while (!this.memory.currentBoundary().equals(cb.boundary())) {
                cb = cb.childNode();
                if (cb.childNode() == null) {
                    break;
                }
            }
            this.memory.setCurrentBody(cb == null ? this.body : cb);
        }
    }

    public void flushBodyAttribute() {
        for (Map.Entry<String, String> entry : ((Map<String, String>)this.memory.cacheAttribute()).entrySet()) {
            this.memory.currentBody().setAttribute(entry.getKey(), entry.getValue());
        }
        this.memory.clearCacheAttribute();
    }

    /**
     * 创建新的Body对象
     * 如果当前boundary不为空，则父body设置为boundary关联的
     * @return
     */
    public Body createBody(ContentTypeEnum type) {
        Body parent = this.memory.currentBody();
        while (this.memory.currentBoundary() != null && !this.memory.currentBoundary().equals(parent.boundary())) {
            if (parent.parentNode() == null) {
                break;
            }
            parent = parent.parentNode();
        }
        Body body = null;
        switch (type) {
            case MULTIPART_MIXED:
                body = new MultipartMixedBody(parent);
                break;
            case MULTIPART_ALTERNATIVE:
                body = new MultipartAlternativeBody(parent);
                break;
            case MULTIPART_RELATED:
                body = new MultipartRelatedBody(parent);
                break;
            case IMAGE_JPEG:
            case IMAGE_PNG:
            case IMAGE_JPG:
                body = new ImageBody();
                if (parent != null) {
                    parent.addContentBody(body);
                }
                break;
            case TEXT_HTML:
                body = new TextBody();
                if (parent != null) {
                    parent.addContentBody(body);
                }
                break;
            case APPLICATION_OCTET_STREAM:
                body = new ApplicationStreamBody();
                if (parent != null) {
                    parent.addContentBody(body);
                }
                break;
            default:
                body = new TextBody();
                if (parent != null) {
                    parent.addContentBody(body);
                }
                break;
        }
        if (this.body == null) {
            this.body = body;
            this.content().setBody(this.body);
        }
        return body;
    }

}
