package org.java.emlparser.parser.handle;


import org.java.emlparser.parser.EMLParser;

/**
 * Create by LuoChenXu on 2019/10/10
 */
public class ContentTypeNameHandle implements Handle<Character, Character> {

    private final EMLParser parser;

    public ContentTypeNameHandle(EMLParser parser) {
        this.parser = parser;
    }

    @Override
    public Character process(Character c) {
        if (('\r' == c && this.parser.lastChar() == '\n')
                || ('\r' == c && '\r' == this.parser.lastChar())
                || ('\n' == c && '\n' == this.parser.lastChar())) {
            this.parser.flushBodyAttribute();
            this.parser.selectHandle().bodyHandle();
        } else if ('\r' == c || '\n' == c) {

        } else if ((' ' == c || '\t' == c) && (this.parser.lastChar() == '\n' || this.parser.lastChar() == '\r')){
            this.parser.selectHandle().setHandle(this.parser.lastHandle());
            this.parser.recover();
            this.parser.append(c);
        }else if (':' == c) {
            this.parser.memory().setCurrentFieldName(this.parser.current());
            this.parser.memory().resetCurrent();
            this.parser.selectHandle().contentTypeValHandle();
        } else {
            this.parser.append(c);
        }
        return c;
    }
}
