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
            // XML prolog / DOCTYPE / comment?
            if(mr.group().startsWith("<?xml") ||
                    mr.group().startsWith("<!DOCTYPE")) {
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

            if(mr.group(3).equals("")) { // Not an empty element:
                if(mr.group(1).equals("/")) { // Closing element:
                    indentLevel--;
                } else if(!mr.group(1).equals("!")) { // Not a comment / DOCTYPE:
                    indentLevel++;
                }
            }
            if(!prevItrClosing && mr.group(1).equals("/")) { // Both previous and current element closing ones:
                sb.append(NL);
                for(int i=0; i < indentLevel; i++) {
                    sb.append(indent);
                }    
            }

            // Mark closing element flag for next iteration:
            if(mr.group(1).equals("/")) {
                prevItrClosing = true;
            } else {
                prevItrClosing = false;
            }

            // O/p the formatted element:
            sb.append("<").
                append(mr.group(1)).
                append(mr.group(2)).
                append(mr.group(3)).
                append(">").
                append(NL);
            
            // Spacing for next iteration:
            for(int i=0; i < indentLevel-1; i++) {
                sb.append(indent);
            }
            return sb.toString();
        });
        
        return str;
    }

    public static String fmt(String xmls, int indent) {
        if(indent <= 0) {
            throw new IllegalArgumentException("Indent cannot be 0 or less.");
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<indent; i++) {
            sb.append(" ");
        }
        return new XMLFmt().formatXML(xmls, sb.toString());
    }

    public static String fmt(String xmls) {
        return fmt(xmls, 2);
    }
}
