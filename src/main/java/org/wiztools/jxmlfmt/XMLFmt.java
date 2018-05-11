package org.wiztools.jxmlfmt;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLFmt {
    private XMLFmt(){}; // Private constructor!

    private static final Pattern RE = Pattern.compile("<([/!]?)([^>]+?)(/?)>");
    private static final String NL = "\n";

    private int indentLevel = 0;
    private boolean prevItrClosing = false;
    private boolean firstEntry = true;

    private String formatXML(String xmls, String indent) {
        String src = xmls.replaceAll(">\\s+<", "><");
        Matcher m = RE.matcher(src);
        
        String str = m.replaceAll((MatchResult mr) -> {
            // XML prolog?
            if(mr.group().startsWith("<?xml")) {
                return mr.group() + NL;
            }

            // Elements:
            StringBuilder sb = new StringBuilder();
            if(!firstEntry) {
                if(prevItrClosing && !mr.group(1).equals("/")) {
                    sb.append(indent);
                }
                if(!prevItrClosing) {
                    sb.append(indent);
                }
            } else {
                firstEntry = false;
            }

            if(mr.group().startsWith("<!--")) {
                return mr.group() + NL;
            }
            if(mr.group(3).equals("")) {
                if(mr.group(1).equals("/")) {
                    indentLevel--;
                } else if(!mr.group(1).equals("!")) {
                    indentLevel++;
                }
            }
            if(!prevItrClosing && mr.group(1).equals("/")) {
                sb.append(NL);
                for(int i=0; i < indentLevel; i++) {
                    sb.append(indent);
                }    
            }
            if(mr.group(1).equals("/")) {
                prevItrClosing = true;
            } else {
                prevItrClosing = false;
            }
            sb.append("<").
                append(mr.group(1)).
                append(mr.group(2)).
                append(mr.group(3)).
                append(">").
                append(NL);
            
            for(int i=0; i < indentLevel-1; i++) {
                sb.append(indent);
            }
            return sb.toString();
        });
        
        return str;
    }

    public static String fmtXML(String xmls, String indent) {
        return new XMLFmt().formatXML(xmls, indent);
    }
}
