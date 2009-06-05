package org.sgodden.echo.ext20;

import org.sgodden.echo.ext20.command.ScrollIntoViewCommand;

import nextapp.echo.app.Command;
import nextapp.echo.app.Component;

/**
 * Abstract subclass of component that introduces behavioural options
 * that are specific to ext components.
 * @author Lloyd Colling
 *
 */
@SuppressWarnings("serial")
public abstract class ExtComponent extends Component {

    /**
     * Sets whether the tab close icon is visible when this 
     * component is added to a tabbed pane
     */
    public static final String CLOSABLE_PROPERTY = "closable";

    /**
     * An optional extra CSS class that will be added to this component's Element (defaults to '').
     */
    public static final String CSS_CLASS = "cssClass";
    
    /**
     * An optional extra CSS class that will be added to this component's container (defaults to '').
     */
    public static final String CONTAINER_CSS_CLASS = "containerCssClass";
    
    /**
     * The effect to apply when the component is added to it's parent container
     */
    public static final String CONTAINER_ADD_EFFECTS = "containerAddFx";
    
    /**
     * The effect to apply when the component will be removed from it's parent container
     */
    public static final String CONTAINER_REMOVE_EFFECTS = "containerRemoveFx";
    
    /**
     * The tooltip to apply to any ext component.
     */
    public static final String TOOL_TIP = "toolTip";
    
    AddComponentFx addEffect = null;
    RemoveComponentFx removeEffect = null;
    
    public ExtComponent() {
        super();
    }
    
    /**
     * Sets an optional extra CSS class that will be added to this component's Element (defaults to '').
     * @param cls the extra CSS class
     */
    public void setCssClass(String cls) {
        set(CSS_CLASS,cls);
    }
    
    public String getCssClass() {
        return (String)get(CSS_CLASS);
    }
    
    /**
     * Returns whether the component is closable.
     * A closable component can be removed from a tabbed pane.
     * @return
     */
    public boolean getClosable() {
        Object val = get(CLOSABLE_PROPERTY);
        return val == null ? Boolean.FALSE : (Boolean)val;
    }
    
    public void setClosable(boolean closable) {
        set(CLOSABLE_PROPERTY, Boolean.valueOf(closable));
    }
    
    /**
     * An optional extra CSS class that will be added to this component's container (defaults to '').
     * @param cls the extra CSS class
     */
    public void setContainerCssClass(String cls) {
        set(CONTAINER_CSS_CLASS,cls);
    }
    
    public String getContainerCssClass() {
        return (String)get(CONTAINER_CSS_CLASS);
    }
    
    public void scrollIntoView() {
        Component parent = getParent();
        boolean isScrollable;
        while(parent != null){
            if(parent instanceof Panel){
                isScrollable = ((Panel) parent).getAutoScroll();
                if(isScrollable){
                    Command command = new ScrollIntoViewCommand(this, parent);
                    nextapp.echo.app.Window.getActive().enqueueCommand(command);
                    ((ExtComponent)parent).scrollIntoView();
                    break;
                }
            }
            parent = parent.getParent();
        }
    }
    
    public AddComponentFx getAddEffect() {
        return addEffect;
    }
    
    public void setAddEffect(AddComponentFx effect) {
        addEffect = effect;
        set(CONTAINER_ADD_EFFECTS, addEffect.toString());
    }
    
    public RemoveComponentFx getRemoveEffect() {
        return removeEffect;
    }
    
    public void setRemoveEffect(RemoveComponentFx effect) {
        removeEffect = effect;
        set(CONTAINER_REMOVE_EFFECTS, removeEffect.toString());
    }
    
    public void setToolTip(String toolTip){
    	set(TOOL_TIP, toolTip);
    }
}
