package org.java.emlparser.parser.content;


/**
 * Create by LuoChenXu on 2019/10/15
 */
public class TextBody extends AbstractBody<String> {
    //内容
    private final StringBuilder text;

    public TextBody(Body parent) {
        super(parent);
        this.text = new StringBuilder();
    }

    @Override
    public void setContent(String s) {
        text.setLength(0);
        text.append(s);
    }

    public TextBody() {
        this(null);
    }

    /**
     * 增加内容
     * @param c
     */
    @Override
    public void append(char c) {
        text.append(c);
    }

    /**
     * 返回内容
     *
     * @return
     */
    @Override
    public String getContent() {
        return this.text.toString();
    }
}
