package org.wiztools.jxmlfmt;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] arg) throws IOException {
        try(Scanner s = new Scanner(System.in)) {
            s.useDelimiter("\\A");
            String xmlInput = s.hasNext() ? s.next() : "";
            System.out.println(XMLFmt.fmt(xmlInput, 2));
        }
    }
}