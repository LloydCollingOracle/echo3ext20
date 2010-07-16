package org.sgodden.echo.ext20.peers;

import org.sgodden.echo.ext20.Label;

public class LabelPeer extends ExtComponentPeer {

    public LabelPeer() {
        super();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class getComponentClass() {
        return Label.class;
    }

    public String getClientComponentType(boolean shortType) {
        return shortType ? "E2L" : "Ext20Label";
    }

}
