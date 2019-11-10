package org.java.emlparser.parser.handle;


import org.java.emlparser.parser.EMLParser;

/**
 * Create by LuoChenXu on 2019/10/15
 */
public class ContentTypeAttrNameHandle implements Handle<Character, Character> {
    private final EMLParser parser;

    public ContentTypeAttrNameHandle(EMLParser parser) {
        this.parser = parser;
    }


    @Override
    public Character process(Character c) {
         if ('\r' == c || '\n' == c) {
            this.parser.memory().resetCurrent();
            this.parser.memory().setCurrentFieldName(null);
            this.parser.selectHandle().contentTypeNameHandle();
        }else if ('\t' == c || '\u0020' == c) {

        } else if ('=' == c) {
            this.parser.selectHandle().contentTypeAttrValHandle();
            this.parser.memory().setCurrentFieldName(this.parser.current());
            this.parser.memory().resetCurrent();
        }else{
            this.parser.append(c);
        }
        return c;
    }
}
