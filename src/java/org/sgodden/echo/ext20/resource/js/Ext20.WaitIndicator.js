/**
 * @class Default wait indicator implementation.
 */
EchoExt20.WaitIndicator = Core.extend(Echo.Client.WaitIndicator, {

    $static: {
    
        appWaitIndicator: null,
        boot: function(client) {
            client.setWaitIndicator(new EchoExt20.WaitIndicator());
            client._preWaitIndicatorDelay = 5;
            client._waitIndicatorRunnable = new Core.Web.Scheduler.MethodRunnable(Core.method(client, client._waitIndicatorActivate), 
            client._preWaitIndicatorDelay, false);
        }
    },
    
    $load: function() {
        Echo.Boot.addInitMethod(this.boot);
    },
    
    loadMask: null,
    dialogMask: null,
    
    activate: function() {
        var waitEl = EchoExt20.WaitIndicator.appWaitIndicator;
        if (waitEl != null) {
            waitEl.firstChild.src = EchoExt20.waitIconUrl;
            var bdy = Ext.get("body");
            if (bdy != null) bdy.applyStyles("cursor:wait");
            return;
        }
        this._applyLoadMask();
    },
    
    deactivate: function() {
        var waitEl = EchoExt20.WaitIndicator.appWaitIndicator;
        if (waitEl != null) {
            waitEl.firstChild.src = EchoExt20.noWaitIconUrl;
            var bdy = Ext.get("body");
            if (bdy != null) bdy.applyStyles("cursor:default");
        }
        this._removeLoadMask();	
    },
    _applyLoadMask: function() {
        if(this.loadMask == null) {
            this.loadMask = new Ext.LoadMask(document.getElementById("approot"), {msg:"Loading..."});
        }
        if (Ext.WindowMgr.getActive() != null) {
            if (this.dialogMask != null) {
                this.dialogMask.disable();
                this.dialogMask.hide();
            }
            this.dialogMask = new Ext.LoadMask(Ext.WindowMgr.getActive().getEl(), {msg:"Loading..."});
            this.dialogMask.enable();
            this.dialogMask.show();
        } else {
            this.loadMask.enable();
            this.loadMask.show();
        }
    },
    _removeLoadMask: function() {
        if(this.loadMask != null) {
            this.loadMask.disable();
            this.loadMask.hide();
        }
        if (this.dialogMask != null) {
            this.dialogMask.disable();
            this.dialogMask.hide();
        }
    }
});