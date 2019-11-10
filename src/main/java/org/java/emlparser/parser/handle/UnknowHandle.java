package org.java.emlparser.parser.handle;


import org.java.emlparser.parser.EMLParser;

/**
 * Create by LuoChenXu on 2019/10/11
 */
public class UnknowHandle implements Handle<Character, Character> {

    private final EMLParser parser;

    public UnknowHandle(EMLParser parser) {
        this.parser = parser;
    }

    @Override
    public Character process(Character c) {
        if ('\t' == c || '\u0020' == c) {
            if (this.parser.lastHandle() != null) {
                String lineSeparator = System.getProperty("line.separator");
                for (char ch : lineSeparator.toCharArray()) {
                    this.parser.append(ch);
                }
                this.parser.selectHandle().setHandle(this.parser.lastHandle());
            }
            this.parser.append(c);
        } else if ('\r' == c || '\n' == c){

        }else {
            this.parser.selectHandle().fieldNameHandle();
            this.parser.memory().resetCurrent();
            this.parser.append(c);
        }
        return c;
    }
}
