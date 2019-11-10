package org.java.emlparser.parser.content;



/**
 * Create by LuoChenXu on 2019/10/16
 */
public class MultipartRelatedBody extends AbstractMultipartBody<Body>{

    Body contentBody;

    public MultipartRelatedBody(Body parent) {
        super(parent);
    }

    @Override
    public void setContent(Body body) {
        this.contentBody = body;
    }

    /**
     * 增加内容
     *
     * @param c
     */
    @Override
    public void append(char c) {}

    /**
     * 返回内容
     *
     * @return
     */
    @Override
    public Body getContent() {
        return contentBody;
    }
}
