package org.sgodden.echo.ext20;

import org.sgodden.echo.ext20.models.RemoteAutocompleteModel;

/**
 * <p>A ComboBox that allows for auto-completion by a remote-loading model.</p>
 *
 * <p>This should be used when you want a combo or auto-completion field that has
 * a large number of entries.</p>
 *
 * @author Lloyd Colling
 */
public class AutocompleteCombo extends ExtComponent {

    private static final long serialVersionUID = 1L;

    public static final String PROPERTY_REMOTE_MODEL = "remoteModel";
    public static final String PROPERTY_VALUE = "value";
    public static final String PROPERTY_WIDTH = "width";
    public static final String PROPERTY_MIN_CHARS = "minChars";
    public static final String PROPERTY_HIDE_TRIGGER = "hideTrigger";
    public static final String PROPERTY_ALLOW_BLANK = "allowBlank";
    public static final String PROPERTY_BLANK_TEXT = "blankText";
    public static final String PROPERTY_FORCE_SELECTION = "forceSelection";
    public static final String PROPERTY_LIST_CSS_CLASS = "listCssClass";
    public static final String PROPERTY_LIST_WIDTH = "listWidth";
    public static final String PROPERTY_LOADING_TEXT = "loadingText";
    public static final String PROPERTY_LIST_MAX_HEIGHT = "listMaxHeight";
    public static final String PROPERTY_MIN_LIST_WIDTH = "minListWidth";
    public static final String PROPERTY_LIST_PAGE_SIZE = "listPageSize";
    public static final String PROPERTY_QUERY_DELAY = "queryDelay";
    public static final String PROPERTY_LIST_TITLE = "listTitle";
    public static final String PROPERTY_TRIGGER_CLASS = "triggerClass";
    public static final String PROPERTY_AUTOCREATE = "autoCreate";
    public static final String PROPERTY_TYPE_AHEAD = "typeAhead";

    public AutocompleteCombo() {
        super();
        setMinChars(2);
        setHideTrigger(true);
        setAllowBlank(true);
        setForceSelection(true);
        setTypeAhead(false);
    }

    @Override
    public void validate() {
        super.validate();
        if (getRemoteModel() == null) {
            throw new IllegalStateException("Model must be specified on an AutocompleteCombo");
        }
    }

    /**
     * Method that should be used by subclasses to set the autoCreate option, e.g.
     * <pre>tag: 'textarea', style:'width:300;height:60;', autocomplete: 'off'</pre>
     * @param javascript
     */
    protected void setAutoCreate(String javascript) {
        set(PROPERTY_AUTOCREATE, javascript);
    }

    public RemoteAutocompleteModel getRemoteModel() {
        return (RemoteAutocompleteModel)get(PROPERTY_REMOTE_MODEL);
    }
    public void setRemoteModel(RemoteAutocompleteModel remoteModel) {
        set(PROPERTY_REMOTE_MODEL, remoteModel);
    }
    public String getValue() {
        return (String)get(PROPERTY_VALUE);
    }
    public void setValue(String value) {
        set(PROPERTY_VALUE, value);
    }
    public Integer getWidth() {
        return (Integer)get(PROPERTY_WIDTH);
    }
    public void setWidth(Integer width) {
        set(PROPERTY_WIDTH, width);
    }

    public Integer getMinChars() {
        return (Integer)get(PROPERTY_MIN_CHARS);
    }
    public void setMinChars(Integer minChars) {
        set(PROPERTY_MIN_CHARS, minChars);
    }

    public Boolean getHideTrigger() {
        return (Boolean)get(PROPERTY_HIDE_TRIGGER);
    }

    public void setHideTrigger(Boolean hideTrigger) {
        set(PROPERTY_HIDE_TRIGGER, hideTrigger);
    }

    public Boolean getAllowBlank() {
        return (Boolean)get(PROPERTY_ALLOW_BLANK);
    }

    public void setAllowBlank(Boolean allowBlank) {
        set(PROPERTY_ALLOW_BLANK, allowBlank);
    }

    public String getBlankText() {
        return (String)get(PROPERTY_BLANK_TEXT);
    }

    public void setBlankText(String blankText) {
        set(PROPERTY_BLANK_TEXT, blankText);
    }

    public Boolean getForceSelection() {
        return (Boolean)get(PROPERTY_FORCE_SELECTION);
    }

    public void setForceSelection(Boolean forceSelection) {
        set(PROPERTY_FORCE_SELECTION, forceSelection);
    }

    public String getListCssClass() {
        return (String)get(PROPERTY_LIST_CSS_CLASS);
    }

    public void setListCssClass(String listCssClass) {
        set(PROPERTY_LIST_CSS_CLASS, listCssClass);
    }

    public Integer getListWidth() {
        return (Integer)get(PROPERTY_LIST_WIDTH);
    }

    public void setListWidth(Integer listWidth) {
        set(PROPERTY_LIST_WIDTH, listWidth);
    }

    public String getLoadingText() {
        return (String)get(PROPERTY_LOADING_TEXT);
    }

    public void setLoadingText(String loadingText) {
        set(PROPERTY_LOADING_TEXT, loadingText);
    }

    public Integer getListMaxHeight() {
        return (Integer)get(PROPERTY_LIST_MAX_HEIGHT);
    }

    public void setListMaxHeight(Integer listMaxHeight) {
        set(PROPERTY_LIST_MAX_HEIGHT, listMaxHeight);
    }

    public Integer getListMinWidth() {
        return (Integer)get(PROPERTY_MIN_LIST_WIDTH);
    }

    public void setListMinWidth(Integer listMinWidth) {
        set(PROPERTY_MIN_LIST_WIDTH, listMinWidth);
    }

    public Integer getListPageSize() {
        return (Integer)get(PROPERTY_LIST_PAGE_SIZE);
    }

    public void setListPageSize(Integer listPageSize) {
        set(PROPERTY_LIST_PAGE_SIZE, listPageSize);
    }

    public Integer getQueryDelay() {
        return (Integer)get(PROPERTY_QUERY_DELAY);
    }

    public void setQueryDelay(Integer queryDelay) {
        set(PROPERTY_QUERY_DELAY, queryDelay);
    }

    public String getListTitle() {
        return (String)get(PROPERTY_LIST_TITLE);
    }

    public void setListTitle(String listTitle) {
        set(PROPERTY_LIST_TITLE, listTitle);
    }

    public String getTriggerClass() {
        return (String)get(PROPERTY_TRIGGER_CLASS);
    }

    public void setTriggerClass(String triggerClass) {
        set(PROPERTY_TRIGGER_CLASS, triggerClass);
    }
    
    /**
     * Sets whether type ahead should be enabled.
     * N.B. typeAhead defaults to false when creating a 
     * new instance of AutoCompleteCombo
     *
     * @param typeAhead
     *            whether type ahead should be enabled.
     */
    public void setTypeAhead(boolean typeAhead) {
        set(PROPERTY_TYPE_AHEAD, typeAhead);
    }
}
