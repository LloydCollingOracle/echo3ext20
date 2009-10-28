/**
 * Component rendering peer: Span.
 * This class should not be extended by developers, the implementation is subject to change.
 */
EchoExt20.Span = Core.extend(Echo.Component, {
    
    $load: function() {
        Echo.ComponentFactory.registerType("Ext20Span", this);
        Echo.ComponentFactory.registerType("E2SP", this);
    },
    
    focusable: false,

    componentType: "Ext20Span"
    
});
EchoExt20.SpanSync = Core.extend(Echo.Render.ComponentSync, {

    
    $load: function() {
        Echo.Render.registerPeer("Ext20Span", this);
    },
    
    /**
     * Outer Span.
     * @type Element
     */
    div: null,
    
    /**
     * Renders the content (e.g. text and/or icon) of the button.
     * Appends rendered content to bounding element (<code>this.div</code>).
     */
    renderContent: function() {
        var text = this.component.render("html");
        this.div.innerHTML = text;
    },
    
    /** @see Echo.Render.ComponentSync#getFocusFlags */ 
    getFocusFlags: function() {
        return Echo.Render.ComponentSync.FOCUS_PERMIT_ARROW_ALL;
    },
    
    /** @see Echo.Render.ComponentSync#renderAdd */
    renderAdd: function(update, parentElement) {
        this.enabled = this.component.isRenderEnabled();
        
        this.div = document.createElement('SPAN'); 
        this.div.id = this.component.renderId;
        var text = this.component.render("html");
        this.div.innerHTML = text;
        
        parentElement.appendChild(this.div);
    },
    
    /** @see Echo.Render.ComponentSync#renderDispose */
    renderDispose: function(update) {

        Core.Web.Event.removeAll(this.div);
        
        this.div = null;
    },
    
    /** @see Echo.Render.ComponentSync#renderUpdate */
    renderUpdate: function(update) {
        var element = this.div;
        var containerElement = element.parentNode;
        this.renderDispose(update);
        containerElement.removeChild(element);
        this.renderAdd(update, containerElement);
        return false; // Child elements not supported: safe to return false.
    }
});
