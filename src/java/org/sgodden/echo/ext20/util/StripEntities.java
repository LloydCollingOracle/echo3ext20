package org.sgodden.echo.ext20.util;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * Strips HTML entities such as &quot; from a file, replacing them with their
 * Unicode equivalents.
 */
public class StripEntities {

    /**
     * Converts HTML to text converting entities such as &amp;quot; back to " and
     * &amp;lt; back to &lt;
     */
    public static String stripHTMLEntities(String text) {
        return StringEscapeUtils.unescapeHtml(text);
    }

    /**
     * Converts XML to text converting entities such as &amp;quot; back to &quot; and
     * &amp;lt; back to &lt;
     */
    public static String stripXMLEntities(String text) {
        return StringEscapeUtils.unescapeXml(text);
    }
}
