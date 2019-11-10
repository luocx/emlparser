package org.java.emlparser.parser.constants;

/**
 * Create by LuoChenXu on 2019/10/18
 */
public enum  ContentTypeEnum {
    MULTIPART("multipart/*"),
    MULTIPART_MIXED("multipart/mixed"),
    MULTIPART_RELATED("multipart/related"),
    MULTIPART_ALTERNATIVE("multipart/alternative"),
    IMAGE("image/*"),
    IMAGE_PNG("image/png"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_JPG("image/jpg"),
    TEXT("text/*"),
    TEXT_PLAIN("text/plain"),
    TEXT_HTML("text/html"),
    APPLICATION_OCTET_STREAM("application/octet-stream"),
    NONE("");

    String name;

    ContentTypeEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ContentTypeEnum get(String name){
        for (ContentTypeEnum c : ContentTypeEnum.values()) {
            if (c.name.equals(name)) {
                return c;
            }
        }
        return NONE;
    }
}
