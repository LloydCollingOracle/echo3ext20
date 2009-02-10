package org.sgodden.echo.ext20.grid;

import java.text.MessageFormat;

import org.sgodden.echo.ext20.util.InsertEntities;

import nextapp.echo.app.Component;
import nextapp.echo.app.Label;


/**
 * A grid cell renderer that may be subclassed to return values for images and
 * text content
 * 
 * @author Lloyd Colling
 */
public abstract class ImageLabelGridCellRenderer implements GridCellRenderer {

    private CellTemplate cellFormat = new CellTemplate();

    public final Component getGridCellRendererComponent(Component gridPanel,
            Object valueAt, int colIndex, int rowIndex) {
        String imageUrl = getImageUrl(gridPanel, valueAt, colIndex, rowIndex);
        String text = getText(gridPanel, valueAt, colIndex, rowIndex);
        String template = getCellTemplate(gridPanel, valueAt, colIndex,
                rowIndex);

        String cellContents = MessageFormat.format(template, imageUrl, text);
        return new Label(cellContents);
    }
    
    protected CellTemplate getCellFormat() {
        return cellFormat;
    }

    protected String getCellTemplate(Component gridPanel, Object valueAt,
            int colIndex, int rowIndex) {
        return cellFormat.toString();
    }

    protected String getImageUrl(Component gridPanel, Object valueAt,
            int colIndex, int rowIndex) {
        return null;
    }

    protected String getText(Component gridPanel, Object valueAt, int colIndex,
            int rowIndex) {
        return valueAt == null ? "" : InsertEntities.insertHTMLEntities(String
                .valueOf(valueAt));
    }

    public static class CellTemplate {
        protected Integer topImageMargin;
        protected Integer leftImageMargin;
        protected Integer bottomImageMargin;
        protected Integer rightImageMargin = Integer.valueOf(3);
        protected Integer topTextMargin;
        protected Integer leftTextMargin;
        protected Integer bottomTextMargin;
        protected Integer rightTextMargin;

        protected VerticalAlignment imageAlignment = VerticalAlignment.TEXT_BOTTOM;
        protected Integer imageHeight;
        protected Integer imageWidth;
        protected String imageCssStyle;
        protected String textCssStyle;

        public String toString() {
            StringBuffer ret = new StringBuffer();

            ret.append("<img src=\"{0}\"");
            if (imageHeight != null)
                ret.append(" height=\"" + imageHeight + "\"");
            if (imageWidth != null)
                ret.append(" width=\"" + imageWidth + "\"");
            if (imageCssStyle != null)
                ret.append(" class=\"" + imageCssStyle + "\"");

            if (imageAlignment != null || topImageMargin != null
                    || bottomImageMargin != null || leftImageMargin != null
                    || rightImageMargin != null) {
                ret.append(" style=\"");
            }
            if (imageAlignment != null)
                ret.append("vertical-align: "
                        + imageAlignment.name().toLowerCase().replaceAll("_",
                                "-") + ";");
            if (topImageMargin != null)
                ret.append("margin-top: " + topImageMargin + "px;");
            if (bottomImageMargin != null)
                ret.append("margin-bottom: " + bottomImageMargin + "px;");
            if (leftImageMargin != null)
                ret.append("margin-left: " + leftImageMargin + "px;");
            if (rightImageMargin != null)
                ret.append("margin-right: " + rightImageMargin + "px;");

            if (imageAlignment != null || topImageMargin != null
                    || bottomImageMargin != null || leftImageMargin != null
                    || rightImageMargin != null) {
                ret.append("\"");
            }

            ret.append("/><span");

            if (textCssStyle != null)
                ret.append(" class=\"" + textCssStyle + "\"");

            if (topTextMargin != null || bottomTextMargin != null
                    || leftTextMargin != null || rightTextMargin != null) {
                ret.append(" style=\"");
            }
            if (topTextMargin != null)
                ret.append("margin-top: " + topTextMargin + "px;");
            if (bottomTextMargin != null)
                ret.append("margin-bottom: " + bottomTextMargin + "px;");
            if (leftTextMargin != null)
                ret.append("margin-left: " + leftTextMargin + "px;");
            if (rightTextMargin != null)
                ret.append("margin-right: " + rightTextMargin + "px;");
            if (topTextMargin != null || bottomTextMargin != null
                    || leftTextMargin != null || rightTextMargin != null) {
                ret.append("\"");
            }

            ret.append(">{1}</span>");

            return ret.toString();
        }
    }

    static enum VerticalAlignment {
        TOP, MIDDLE, BOTTOM, BASELINE, SUB, SUPER, TEXT_TOP, TEXT_BOTTOM
    }
}
