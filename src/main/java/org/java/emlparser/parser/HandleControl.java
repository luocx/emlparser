package org.java.emlparser.parser;


import org.java.emlparser.parser.handle.*;

/**
 * Create by LuoChenXu on 2019/10/10
 * 解析处理器控制中心
 * 用于控制切换处理器状态
 */
public class HandleControl {

    private final EMLParser parser;

    private final Handle fieldNameHandle;
    private final Handle fieldValueHandle;
    private final Handle unknowHandle;
    private final Handle contentTypeNameHandle;
    private final Handle contentTypeValHandle;
    private final Handle bodyHandle;
    private final Handle contentTypeAttrNameHandle;
    private final Handle contentTypeAttrValHandle;

    public HandleControl(EMLParser parser) {
        this.parser = parser;
        this.unknowHandle = new UnknowHandle(parser);
        this.fieldNameHandle = new FieldNameHandle(parser);
        this.fieldValueHandle = new FieldValueHandle(parser);
        this.contentTypeNameHandle = new ContentTypeNameHandle(parser);
        this.contentTypeValHandle = new ContentTypeValHandle(parser);
        this.bodyHandle = new BodyHandle(parser);
        this.contentTypeAttrNameHandle = new ContentTypeAttrNameHandle(parser);
        this.contentTypeAttrValHandle = new ContentTypeAttrValHandle(parser);
    }

    /**
     * 设置为域名处理器
     */
    public void fieldNameHandle(){
        this.parser.setHandle(fieldNameHandle);
    }

    /**
     * 设置为域值处理器
     */
    public void fieldValueHandle(){
        this.parser.setHandle(fieldValueHandle);
    }

    public void unknowHandle(){
        this.parser.setHandle(unknowHandle);
    }

    public void contentTypeNameHandle(){
        this.parser.setHandle(contentTypeNameHandle);
    }

    public void contentTypeValHandle(){
        this.parser.setHandle(contentTypeValHandle);
    }

    public void bodyHandle(){
        this.parser.setHandle(bodyHandle);
    }

    public void contentTypeAttrNameHandle(){
        this.parser.setHandle(contentTypeAttrNameHandle);
    }

    public void contentTypeAttrValHandle(){
        this.parser.setHandle(contentTypeAttrValHandle);
    }

    public void setHandle(Handle handle) {
        this.parser.setHandle(handle);
    }
}
