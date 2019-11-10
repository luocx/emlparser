package org.java.emlparser.parser.constants;

/**
 * Create by LuoChenXu on 2019/10/11
 */
public enum FieldNameEnum {
    MIME_VERSION("Mime-Version")
    , CC("Cc")
    , FROM("From")
    , TO("To")
    , SUBJECT("Subject")
    , DATE("Date")
    , RETURN_PATH("Return-Path")
    , RECEIVED("Received")
    , MESSAGE_ID("Message-ID")
    , CONTENT_TYPE("Content-Type")
    , CONTENT_ID("Content-ID")
    , CONTENT_DISPOSTION("Content-Disposition")
    , CONTENT_TRANSFER_ENCODING("Content-Transfer-Encoding");

    private String name;

    FieldNameEnum(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }

    public static FieldNameEnum get(String name){
        for (FieldNameEnum f : FieldNameEnum.values()){
            if (f.getName().equals(name)){
                return f;
            }
        }
        return null;
    }
}
