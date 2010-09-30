package org.sgodden.echo.ext20;

/**
 * A subclass of AutocompleteComboWithTrigger that uses a text area for
 * input instead of a text field
 * 
 * @author Lloyd Colling
 */
public class AutocompleteTextAreaWithTrigger extends AutocompleteComboWithTrigger {

    private Integer areaWidth;
    private Integer areaHeight;
    
    public AutocompleteTextAreaWithTrigger() {
        super();
        areaWidth = 300;
        areaHeight = 70;
        updateAutoCreate();
    }
    
    private void updateAutoCreate() {
        String autoCreate = "tag: 'textarea', style:'width:" + areaWidth + ";height:" + areaHeight + ";', autocomplete: 'off'";
        setAutoCreate(autoCreate);
    }

    public Integer getAreaWidth() {
        return areaWidth;
    }

    public void setAreaWidth(Integer areaWidth) {
        if (areaWidth == null) {
            throw new IllegalArgumentException("area width may not be null");
        }
        this.areaWidth = areaWidth;
        updateAutoCreate();
    }

    public Integer getAreaHeight() {
        return areaHeight;
    }

    public void setAreaHeight(Integer areaHeight) {
        if (areaHeight == null) {
            throw new IllegalArgumentException("area height may not be null");
        }
        this.areaHeight = areaHeight;
        updateAutoCreate();
    }
}
