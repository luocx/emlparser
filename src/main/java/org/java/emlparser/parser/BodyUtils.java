package org.java.emlparser.parser;



import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QPDecoderStream;
import org.apache.commons.codec.binary.Base64;
import org.java.emlparser.parser.constants.ContentTypeEnum;
import org.java.emlparser.parser.content.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by LuoChenXu on 2019/10/18
 */
public class BodyUtils {
    private static final String ENCODE_NAME_QP = "quoted-printable";

    private static final String ENCODE_NAME_BASE64 = "base64";

    private static final String ENCODE_STR_START_PREFIX = "=?";

    private static final String ENCODE_STR_END_PREFIX = "?=";

    private BodyUtils() {

    }

    /**
     * 获取文本内容
     * @param body
     * @return
     */
    static TextBody getMessageTextBody(Body body) {
        if (ContentTypeEnum.TEXT_PLAIN.getName().equals(((AbstractBody) body).getContentType())) {
            return (TextBody) body;
        }
        Body cb = body;
        while (cb != null) {
            if (cb.childNode() == null) {
                break;
            }
            cb = cb.childNode();
        }
        List<Body> contentList = cb.getContentBodyList();
        for (Body c : contentList) {
            if (ContentTypeEnum.TEXT_PLAIN.getName().equals(((AbstractBody) c).getContentType())) {
                return (TextBody) c;
            }
        }
        return null;
    }

    /**
     * 返回HTML超文本内容
     * 注意：邮箱客户端不同，生成的文本格式不一定均符合W3C标准
     * @param body
     * @return
     */
    static TextBody getAlternativeTextBody(Body body) {
        if (ContentTypeEnum.TEXT_HTML.getName().equals(((AbstractBody) body).getContentType())) {
            return (TextBody) body;
        }
        Body cb = body;
        while (cb != null) {
            if (cb.childNode() == null) {
                break;
            }
            cb = cb.childNode();
        }
        List<Body> contentList = cb.getContentBodyList();
        for (Body c : contentList) {
            if (ContentTypeEnum.TEXT_HTML.getName().equals(((AbstractBody) c).getContentType())) {
                return (TextBody) c;
            }
        }
        return null;
    }

    /**
     * 获取附件内容
     * @param body
     * @return
     */
    static List<ApplicationStreamBody> getApplicationStreamBody(Body body) {
        Body cb = body;
        while (cb != null) {
            if (cb.childNode() == null) {
                break;
            }
            if ((ContentTypeEnum.MULTIPART_MIXED.getName().equals(((AbstractBody) cb).getContentType()))) {
                return cb.getContentBodyList();
            }
            cb = cb.childNode();
        }
        return new ArrayList();
    }

    /**
     * 获取图片内容
     * @param body
     * @return
     */
    static List<ImageBody> getRelatedImageBody(Body body) {
        Body cb = body;
        while (cb != null) {
            if (cb.childNode() == null) {
                break;
            }
            if ((ContentTypeEnum.MULTIPART_RELATED.getName().equals(((AbstractBody) cb).getContentType()))) {
                return cb.getContentBodyList();
            }
            cb = cb.childNode();
        }
        return new ArrayList();
    }

    /**
     * 翻译加密文本
     * @param text
     * @param encode
     * @return
     */
    public static String translateString(String text, String encode, String charsetName) throws IOException {
        if (encode != null){
            if (charsetName == null) {
                charsetName = Charset.defaultCharset().name();
            }
            if (ENCODE_NAME_QP.equals(encode)) {
                return new String(decodeQuotedPrintable(text.getBytes()), charsetName);
            } else if (ENCODE_NAME_BASE64.equals(encode)) {
                return new String(Base64.decodeBase64(text), charsetName);
            }
        }
        return text;
    }

    /**
     * 翻译编码字符串
     * @param text
     * @return
     * @throws IOException
     */
    public static String translateString(String text){
        String encode = null;
        String charsetName = null;
        if (text != null && text.trim().startsWith(ENCODE_STR_START_PREFIX) && text.trim().endsWith(ENCODE_STR_END_PREFIX)) {
            String tmpText = text.trim().substring(ENCODE_STR_START_PREFIX.length(), text.length() - ENCODE_STR_END_PREFIX.length());
            String[] heads = tmpText.split("\\?", 3);
            if (heads.length == 3) {
                charsetName = heads[0];
                try {
                    if ("B".equals(heads[1])) {
                        encode = ENCODE_NAME_BASE64;
                        return translateString(heads[2], encode, charsetName);
                    } else if ("Q".equals(heads[1])) {
                        encode = ENCODE_NAME_QP;
                        return translateString(heads[2], encode, charsetName);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return text;
    }

    public static String outHtmlString(Body body) throws IOException {
        StringBuilder htmlString = new StringBuilder();
        return outHtmlString(body, htmlString);
    }

    /**
     * 输出Html文本
     * @param body
     * @return
     */
    public static String outHtmlString(Body body, StringBuilder htmlString) throws IOException {
        TextBody html = getAlternativeTextBody(body);

        if (html != null) {
            htmlString.append(translateString(html.getContent(), html.getEncodeName(), html.getCharset()));
            List<ImageBody> imgList = getRelatedImageBody(body);
            if (!imgList.isEmpty()) {
                for (ImageBody img : imgList) {
                    String str = String.format("cid:%s", img.getContentId());
                    int fromIndex = 0;
                    while ((fromIndex = htmlString.indexOf(str, fromIndex)) != -1) {
                        htmlString.replace(fromIndex, fromIndex + str.length(), String.format("data:%s;base64,%s",  img.getContentType(), img.getContent()));
                    }
                }
            }
        }
        return htmlString.toString();
    }

    /**
     * Quoted Printable 解码
     * @param qp
     * @return
     */
    public static byte[] decodeQuotedPrintable(byte[] qp) throws IOException {
        InputStream is = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            is = new QPDecoderStream(new ByteArrayInputStream(qp));
            byte[] tmp = new byte[1024];
            int len = 0;
            while ((len = is.read(tmp)) != -1){
                bos.write(tmp, 0, len);
            }
        } finally {
            if (is != null){
                is.close();
            }
        }
        return bos.toByteArray();
    }


    /**
     * @param qp  Byte array to decode
     * @param charsetName The character encoding of the returned string
     * @return The decoded string.
     */
    public static String decodeQuotedPrintable(byte[] qp, String charsetName) throws IOException {
        return new String(decodeQuotedPrintable(qp), charsetName);
    }
}
