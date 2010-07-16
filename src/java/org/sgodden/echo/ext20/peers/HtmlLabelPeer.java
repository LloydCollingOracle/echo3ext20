package org.sgodden.echo.ext20.peers;

import org.sgodden.echo.ext20.HtmlLabel;
import org.sgodden.echo.ext20.Label;

public class HtmlLabelPeer extends ExtComponentPeer {

    public HtmlLabelPeer() {
        super();
    }

    @Override
    public Class<HtmlLabel> getComponentClass() {
        return HtmlLabel.class;
    }

    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2L" : "Ext20Label";
    }

}
