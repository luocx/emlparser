package org.java.emlparser.parser.content;

/**
 * Create by LuoChenXu on 2019/10/16
 */
public class MultipartMixedBody extends AbstractMultipartBody<Body>{

    Body contentBody;

    public MultipartMixedBody(Body parent) {
        super(parent);
    }

    @Override
    public void setContent(Body body) {
        this.contentBody = body;
    }

    @Override
    public void append(char c) {

    }

    @Override
    public Body getContent() {
        return contentBody;
    }
}
