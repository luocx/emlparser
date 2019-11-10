package org.java.emlparser.parser.handle;


import org.java.emlparser.parser.EMLParser;

/**
 * Create by LuoChenXu on 2019/10/15
 */
public class BodyHandle implements Handle<Character, Character>{
    private final EMLParser parser;

    public BodyHandle(EMLParser parser) {
        this.parser = parser;
    }

    @Override
    public Character process(Character c) {
        if ('\r' == c || '\n' == c) {
            if (isStart()) {
                this.parser.refreshBoundary();
                this.parser.selectHandle().contentTypeNameHandle();
            } else if (isNewStart()) {
                this.parser.refreshBody();
                this.parser.refreshBoundary();
                this.parser.selectHandle().contentTypeNameHandle();
            }else if (isEnd()) {
                this.parser.refreshBody();
                if (this.parser.memory().currentBody().parentNode() != null) {
                    this.parser.memory().setCurrentBody(this.parser.memory().currentBody().parentNode());
                }
                this.parser.refreshBoundary();
                this.parser.selectHandle().contentTypeNameHandle();
            }else if (isCurrentBody()){
                this.parser.append(c);
                this.parser.flushBody();
            }
            this.parser.memory().resetCurrent();
        } else {
            this.parser.append(c);
        }
        return c;
    }

    public boolean isStart(){
        return this.parser.memory().currentBody().boundary() != null
                && this.parser.current().equals("--" + this.parser.memory().currentBody().boundary());
    }

    public boolean isNewStart() {
        return this.parser.memory().currentBody().boundary() == null
                && this.parser.current().equals("--" + this.parser.memory().currentBoundary());
    }

    public boolean isCurrentBody(){
        return (this.parser.memory().currentBody().boundary() != null
                && this.parser.memory().currentBoundary() != null
                && this.parser.memory().currentBoundary().equals(this.parser.memory().currentBody().boundary()))
                || (this.parser.memory().currentBody().boundary() == null);
    }

    public boolean isEnd() {
        return  this.parser.current().equals("--" + this.parser.memory().currentBoundary() + "--");
    }
}
