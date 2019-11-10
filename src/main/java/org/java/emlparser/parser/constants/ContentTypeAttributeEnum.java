package org.java.emlparser.parser.constants;

/**
 * Create by LuoChenXu on 2019/10/16
 */
public enum ContentTypeAttributeEnum {
    BOUNDARY("boundary")
    ,CHARSET("charset")
    ,NAME("name")
    ,FILENAME("filename");

    private String name;

    ContentTypeAttributeEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
