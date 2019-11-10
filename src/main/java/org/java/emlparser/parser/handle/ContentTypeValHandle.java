package org.java.emlparser.parser.handle;

import org.java.emlparser.parser.EMLParser;
import org.java.emlparser.parser.constants.ContentTypeEnum;
import org.java.emlparser.parser.constants.FieldNameEnum;

/**
 * Create by LuoChenXu on 2019/10/10
 */
public class ContentTypeValHandle implements Handle<Character, Character> {

    private final EMLParser parser;

    public ContentTypeValHandle(EMLParser parser) {
        this.parser = parser;
    }

    @Override
    public Character process(Character c) {
        switch (c) {
            case ' ':
            case '\t':
                break;
            case '\r':
            case '\n':
                if (FieldNameEnum.CONTENT_TYPE.getName().equals(this.parser.currentFieldName())) {
                    this.parser.memory().setCurrentBody(this.parser.createBody(ContentTypeEnum.get(this.parser.current())));
                }
                this.parser.memory().setAttribute(this.parser.currentFieldName(), this.parser.current().trim());
                this.parser.memory().resetCurrent();
                this.parser.selectHandle().contentTypeNameHandle();
                break;
            case ';':
                if (FieldNameEnum.CONTENT_TYPE.getName().equals(this.parser.currentFieldName())) {
                    this.parser.memory().setCurrentBody(this.parser.createBody(ContentTypeEnum.get(this.parser.current())));
                }
                this.parser.memory().setAttribute(this.parser.currentFieldName(), this.parser.current().trim());
                this.parser.selectHandle().contentTypeAttrNameHandle();
                this.parser.memory().resetCurrent();
                break;
            default:
                this.parser.append(c);
                break;
        }
        return c;
    }
}
