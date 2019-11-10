package org.java.emlparser.parser.constants;

/**
 * Create by LuoChenXu on 2019/10/11
 */
public enum MultiPartTypeEnum {
    MIXED("multipart/mixed"), RELATED("multipart/related"), ALTERNATIVE("multipart/alternative"), NONE("");

    String name;

    MultiPartTypeEnum(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static MultiPartTypeEnum get(String name){
        if (name.equals(MIXED.name)){
            return MIXED;
        }
        if (name.equals(RELATED.name)){
            return RELATED;
        }
        if (name.equals(ALTERNATIVE.name)){
            return ALTERNATIVE;
        }
        return NONE;
    }
}
