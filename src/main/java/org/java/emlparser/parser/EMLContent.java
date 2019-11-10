package org.java.emlparser.parser;


import org.java.emlparser.parser.constants.FieldNameEnum;
import org.java.emlparser.parser.content.ApplicationStreamBody;
import org.java.emlparser.parser.content.Body;
import org.java.emlparser.parser.content.ImageBody;
import org.java.emlparser.parser.content.TextBody;
import org.java.emlparser.utils.EMLHtmlStringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Create by LuoChenXu on 2019/10/15
 */
public class EMLContent {

    private final Map<String, String> header;

    private Charset htmlCharset;

    private Body body;

    private StringBuilder htmlStr;

    public EMLContent() {
        this.header = new HashMap<String, String>();
        this.htmlStr = new StringBuilder();
    }

    void setBody(Body body) {
        this.body = body;
    }

    /**
     * 增加头信息
     *
     * @param name
     * @param value
     */
    public void addHeader(String name, String value) {
        if (FieldNameEnum.FROM.getName().equals(name)) {
            if (value.trim().startsWith("\"") && value.trim().endsWith("\"")) {
                value = value.substring(1, value.length() - 1);
            }else{
                String[] arr = value.trim().split(" ", 2);
                value = BodyUtils.translateString(arr[0]) + BodyUtils.translateString(arr[1]);
            }
        }
        this.header.put(name, value);
    }

    public Map header() {
        return new HashMap(this.header);
    }

    /**
     * 获取头信息
     *
     * @param name
     * @return
     */
    public String getHeader(String name) {
        return this.header.get(name);
    }

    public Set<String> getAllHeader() {
        return this.header.keySet();
    }

    /**
     * 获取邮件文本内容
     * 不包括附件、内嵌资源、超文本
     *
     * @return
     */
    public String getMessage() throws IOException {
        TextBody body = BodyUtils.getMessageTextBody(this.body);
        return body == null ? "" : BodyUtils.translateString(body.getContent(), body.getEncodeName(), body.getCharset());
    }

    /**
     * 获取附件
     *
     * @return
     */
    public List<ApplicationStreamBody> getAttachments() {
        return BodyUtils.getApplicationStreamBody(this.body);
    }

    /**
     * 将eml文件内容数据转为HTML
     * 邮件中的附件将转换为 <a href="file:id">{file name}</a> 标签
     *
     * @return
     */
    public String outHtmlString() throws IOException {
        if (htmlStr != null && htmlStr.length() != 0) {
            return htmlStr.toString();
        }
        return BodyUtils.outHtmlString(this.body, htmlStr);
    }

    /**
     * 获取html内容编码格式
     *
     * 该方法获取编码不准确，若要获取html字符编码请编写代码解析html文本
     * @return
     */
    @Deprecated
    public Charset getHtmlCharset() {
        if (this.htmlCharset == null) {
            TextBody html = BodyUtils.getAlternativeTextBody(body);
            if (html == null || html.getCharset() == null) {
                this.htmlCharset = Charset.defaultCharset();
            }else{
                this.htmlCharset = Charset.forName(html.getCharset());
            }
        }
        return this.htmlCharset;
    }

    /**
     * 返回头信息html文本
     * @return
     */
    public String outHeadHtmlString() {
        return EMLHtmlStringUtils.getHeadHtmlElementString(this.header);
    }

    /**
     * 返回内嵌图片
     * @return
     */
    public List<ImageBody> getRelatedImages() {
        return BodyUtils.getRelatedImageBody(this.body);
    }
}
