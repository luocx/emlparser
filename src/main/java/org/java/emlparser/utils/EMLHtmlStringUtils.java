package org.java.emlparser.utils;

import org.java.emlparser.parser.EMLContent;
import org.java.emlparser.parser.constants.FieldNameEnum;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Create by LuoChenXu on 2019/10/23
 */
public class EMLHtmlStringUtils {

    private static final String HEAD_ELEMENT_DEFAULT_TEMPLATE;

    static {
        StringBuilder builder = new StringBuilder();
        builder.append("<div style=\"border:solid #E1E1E1 1px;padding:3px;background-color: #f0f5f3;margin-bottom: 10px;\">\n");
        builder.append("    <h3>${" + FieldNameEnum.SUBJECT.getName() + "}</h3>\n");
        builder.append("    <div style=\"padding-left: 10px;\">\n");
        builder.append("        <div><span>发件人: </span><span>${" + FieldNameEnum.FROM.getName() + "}</span></div>\n");
        builder.append("        <div style=\"padding-top: 5px;\"><span>收件人: </span><span>${" + FieldNameEnum.TO.getName() + "}</span></div>\n");
        builder.append("        <div style=\"padding-top: 5px;\"><span>&nbsp;&nbsp;抄送: </span><span>${" + FieldNameEnum.CC.getName() + "}</span></div>\n");
        builder.append("        <div style=\"padding-top: 5px;\"><span>&nbsp;&nbsp;时间: </span><span>${" + FieldNameEnum.DATE.getName() + "}</span></div>\n");
        builder.append("    </div>\n");
        builder.append("</div>\n");
        HEAD_ELEMENT_DEFAULT_TEMPLATE = builder.toString();
    }

    /**
     * 输出默认样式的Html 文本，包含邮件完整内容
     *
     * @param content
     * @return
     */
    public static String outDefaultStyleHtmlString(EMLContent content) throws IOException {
        int bodyoff = content.outHtmlString().indexOf("<body");
        if (bodyoff < 0) {
            return content.outHtmlString();
        }
        int bodyIndex = content.outHtmlString().indexOf(">", bodyoff + 5);
        if (bodyIndex < 0) {
            return content.outHtmlString();
        }
        bodyIndex +=1;
        StringBuilder htmlStr = new StringBuilder();
        htmlStr.append(content.outHtmlString().substring(0, bodyIndex));
        htmlStr.append(getHeadHtmlElementString(content.header()));
        htmlStr.append(content.outHtmlString().substring(bodyIndex, content.outHtmlString().length()));
        return htmlStr.toString();
    }

    /**
     * 返回头信息html 文本
     * @param header
     * @return
     */
    public static String getHeadHtmlElementString(Map<String, String> header) {
        String loaclDateStr = noNullString(header.get(FieldNameEnum.DATE));

        return HEAD_ELEMENT_DEFAULT_TEMPLATE
                .replaceAll("\\$\\{" + FieldNameEnum.SUBJECT.getName() + "}", Matcher.quoteReplacement(noNullString(header.get(FieldNameEnum.SUBJECT.getName()))))
                .replaceAll("\\$\\{" + FieldNameEnum.FROM.getName() + "}", Matcher.quoteReplacement(parserEMail(noNullString(header.get(FieldNameEnum.FROM.getName())))))
                .replaceAll("\\$\\{" + FieldNameEnum.TO.getName() + "}", Matcher.quoteReplacement(parserEMail(noNullString(header.get(FieldNameEnum.TO.getName())))))
                .replaceAll("\\$\\{" + FieldNameEnum.CC.getName() + "}", Matcher.quoteReplacement(parserEMail(noNullString(header.get(FieldNameEnum.CC.getName())))))
                .replaceAll("\\$\\{" + FieldNameEnum.DATE.getName() + "}", Matcher.quoteReplacement(parserDateField(noNullString(header.get(FieldNameEnum.DATE.getName())))));

    }

    public static String parserEMail(String value) {
        final String template = "%s &lt;<a style=\"font-size: 14px;color: #717171;\" href=\"mailto:%s\">%s</a>&gt;";

        StringBuilder sb = new StringBuilder();
        String[] mails = value.split(",");
        for (String m : mails){
            if (m.trim().isEmpty()) {
                continue;
            }
            int offer = m.indexOf("<");
            int end = m.lastIndexOf(">");
            if (offer < 0 || end < 0 || end < offer) {
                sb.append(String.format(template, m, m, m));
                continue;
            }
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(String.format(template, m.substring(0, offer), m.substring(offer + 1, end), m.substring(offer + 1, end)));
        }
        return sb.toString();
    }

    public static String parserDateField(String dateStr) {
        Date date = null;
        if (dateStr != null && !dateStr.isEmpty()) {
            try {
                SimpleDateFormat usFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.US);
                date = usFormat.parse(dateStr.trim());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (date != null){
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                return String.format("%s年%s月%s日 (%s)  %s:%s"
                        , c.get(Calendar.YEAR)
                        , (c.get(Calendar.MONTH) + 1)
                        , c.get(Calendar.DAY_OF_MONTH)
                        , getCalendarWeekDay(c.get(Calendar.DAY_OF_WEEK))
                        , c.get(Calendar.HOUR_OF_DAY)
                        , (c.get(Calendar.MINUTE) < 10 ? "0" + c.get(Calendar.MINUTE) : c.get(Calendar.MINUTE)));
            }
        }
        return dateStr;
    }

    private static String getCalendarWeekDay(int day){
        switch (day){
            case 2: return "星期一";
            case 3: return "星期二";
            case 4: return "星期三";
            case 5: return "星期四";
            case 6: return "星期五";
            case 7: return "星期六";
            case 1: return "星期日";
        }
        return "";
    }

    private static String noNullString(String str) {
        return str == null ? "" : str;
    }

    private EMLHtmlStringUtils() {

    }
}
