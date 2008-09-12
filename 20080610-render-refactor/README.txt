This project contains echo3 components and rendering peers for the Ext 2.0 widget set.

Explanation of project layout
-----------------------------
It's a pretty standard webapp layout.  In the 'contrib' directory, you will find the results of running "ant dist" in the svn checkout of the echo3 trunk.  The "Echo3_App.jar" and "Echo3_WebContainer.jar" have been extracted from there and put into src/webapp/WEB-INF/lib.

If you want to get source attachments to the echo3 classes, then you need to extract that tar.gz where it is.

BUILDING
--------
1. Copy build.properties.sample to build.properties and alter the two properties in there to point to the correct locations for the servlet jar and 
your tomcat deployment directoy.
2. Run ant.  The war file will be produced in the 'dist' directory and copied to the deployment directory.

NOTES
-----

Integration between echo and ext is broken because we are not creating all the children during renderAdd.
We need to ensure that children are created in the renderAdd phase, but that creation of ext components
is deferred to renderDisplay.

What happens when server update is received:
RemoteClient#_processSyncResponse
  create new Echo.RemoteClient.ServerMessage
  tell it to process

RemoteClient.ServerMessage#process
  doLibs
  _processPostLibraryLoad
    for each group, create processor and tell it to process

Processors are all added down the bottom of RemoteClient.js
'CSyncUp' is processor Echo.RemoteClient.ComponentSyncUpdateProcessor

CSUP#process(element)
_processUpdate(element)
  creates new children using Echo.Serial#loadComponent
    which uses Echo.ComponentFactory#newInstance
  adds children to relevant component (causing app to be notified?)

Component#add
  register app on the component
  this.application#notifyComponentUpdate(this, "children")

Component#register
  this.application#_registerComponent(this)

Application#notifyComponentUpdate
  Echo.Update.Manager#_processComponentUpdate

Echo.Update.Manager#_processComponentUpdate
  this._processComponentAdd(parent, newChild)
Echo.Update.Manger#_processComponentAdd
  this._createComponentUpdate
Echo.Update.Manager#_createComponentUpdate
  add new Echo.Update.ComponentUpdate(this, parent)

----

In Echo.Render.processUpdates(client)
 if (updates[i].renderContext.displayRequired)
