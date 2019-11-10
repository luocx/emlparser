package org.java.emlparser.parser.handle;


import org.java.emlparser.parser.EMLParser;
import org.java.emlparser.parser.constants.FieldNameEnum;

/**
 * Create by LuoChenXu on 2019/10/10
 */
public class FieldNameHandle implements Handle<Character, Character> {

    private final EMLParser parser;

    public FieldNameHandle(EMLParser parser) {
        this.parser = parser;
    }

    @Override
    public Character process(Character c) {
        if ('\r' == c || '\n' == c) {
            this.parser.memory().resetCurrent();
            this.parser.selectHandle().unknowHandle();
        } else if (':' == c) {
            this.parser.memory().setCurrentFieldName(this.parser.current());
            this.parser.memory().resetCurrent();
            if (FieldNameEnum.CONTENT_TYPE.getName().equals(this.parser.currentFieldName())) {
                this.parser.selectHandle().contentTypeValHandle();
            }else{
                this.parser.selectHandle().fieldValueHandle();
            }
        } else {
            this.parser.append(c);
        }
        return c;
    }
}
