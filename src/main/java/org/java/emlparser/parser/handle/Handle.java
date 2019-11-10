package org.java.emlparser.parser.handle;

/**
 * Create by LuoChenXu on 2019/10/10
 */
public interface Handle<I, O> {

    public O process(I input);
}
