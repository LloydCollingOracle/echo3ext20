package org.sgodden.echo.ext20.util;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Inserts HTML entities such as &quot; into a file replacing the single
 * character equivalents.
 */
public class InsertEntities {

    public static String insertHTMLEntities(String text) {
        return StringEscapeUtils.escapeHtml(text);
    }

    public static String insertXMLEntities(String text) {
        return StringEscapeUtils.escapeXml(text);
    }
}
