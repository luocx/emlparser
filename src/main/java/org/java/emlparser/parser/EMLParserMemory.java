package org.java.emlparser.parser;


import org.java.emlparser.parser.content.Body;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by LuoChenXu on 2019/10/10
 */
public class EMLParserMemory {

    private String currentFieldName;

    private final StringBuilder current;

    private String currentBoundary;

    private String lastContent;

    private Body currentBody;

    private Map<String, String> cacheAtta;

    public EMLParserMemory() {
        current = new StringBuilder();
        cacheAtta = new HashMap<String, String>();
    }

    public void setCurrentFieldName(String fieldName) {
        this.currentFieldName = fieldName;
    }

    /**
     * 返回当前段落
     * @return
     */
    public StringBuilder current(){
        return this.current;
    }

    public Map cacheAttribute(){
        return this.cacheAtta;
    }

    public void clearCacheAttribute(){
       this.cacheAtta.clear();
    }

    public void setAttribute(String name, String val) {
        cacheAtta.put(name, val);
    }

    public String last(){
        return this.lastContent;
    }

    /**
     * 返回当前处理内容
     *
     * @return
     */
    public Body currentBody(){
        return this.currentBody;
    }


    public String currentBoundary(){
        return this.currentBoundary;
    }

    /**
     * 设置处理内容，解析到Content-type域时创建
     * @param body
     */
    public void setCurrentBody(Body body) {
        this.currentBody = body;
    }


    public String currentFieldName(){
        return this.currentFieldName;
    }


    public void resetCurrent() {
        this.lastContent = this.current.toString();
        this.current.setLength(0);
    }

    public void setCurrentBoundary(String currentBoundary) {
        this.currentBoundary = currentBoundary;
    }
}
