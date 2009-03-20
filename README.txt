This project contains echo3 components and rendering peers for the Ext 2.2.1 widget set.


EXT LICENSING
=============
NOTE THAT IT IS YOUR RESPONSIBILITY TO READ AND UNDERSTAND THE EXT-2.1.1 LICENSE AND TO
ENSURE THAT YOU ARE COMPLYING WITH IT.  IF YOUR PROJECT IS GPL, YOU MAY USE IT FREELY.
IF YOUR PROJECT IS NOT GPL, YOU MUST PURCHASE A COMMERCIAL LICENSE.

PROJECT LAYOUT
==============
It's a pretty standard webapp layout.  In the 'contrib' directory, you will find the results 
of running "ant dist" in the svn checkout of the echo3 trunk.  The "Echo3_App.jar" and "Echo3_WebContainer.jar" 
have been extracted from there and put into src/webapp/WEB-INF/lib.

If you want to get source attachments to the echo3 classes, then you need to extract that tar.gz where it is.


BUILDING
========

1. Install the ext-2.2.1.zip
============================
The license of Ext precludes us from including it within this source tree.  Download it from http://extjs.com
and place it into the contrib directory.

2. Build this application
=========================
1. Copy build.properties.sample to build.properties and alter the two properties in there to point to the correct locations 
for the servlet jar and your tomcat deployment directoy.
2. Run ant.  The war file will be produced in the 'dist' directory and copied to the deployment directory.

That's it!


KNOWN PROBLEMS
==============
1) The application does not run on Java 6 - there seems to be a XML parsing problem which we have not investigated yet.

2) It is not possible to nest form components within ext layouts within echo layouts.  Something just doesn't work, and I have not had the time
to understand the ext layout pipeline enough to find the problem. So you can use echo3 layouts within ext layouts, to e.g. put a row of images within an ext panel, but you can't then
put another ext layout inside that containing form components.  You will get all sorts of rendering issues.


FOR DEVELOPER
=============
If you want to make some changes to this project, you can set the environment like below:
1) Use eclipse to open the project.
2) Run "ant war" at the first time.
3) Run "ant -DisDev=true run-server" at console.
4) Change some codes.
5) Press Ctrl+C at the console.
6) Run "ant -DisDev=true run-server" at console again
7) View your changes
8) Repeat step 4 to 7. 
Welcome to contribute to the project. ;)  

OTHER INFORMATION
=================

Customised Echo3 Package
========================

This library uses a customised version of the Echo3 framework. Currently, this is built by checking out svn revision 1602 of Echo3 and applying
the following patches in order:

1. echo3ext20/contrib/patches/Echo3/1602/ComplexPropertiesAndCss.diff
2. echo3ext20/contrib/patches/Echo3/1602/CSSStyleSheetService.diff
3. http://bugs.nextapp.com/mantis/view.php?id=368
4. http://bugs.nextapp.com/mantis/view.php?id=370
5. http://bugs.nextapp.com/mantis/view.php?id=369
6. echo3ext20contrib/patches/Echo3/1602/ComplexClientProperties.diff
7. http://bugs.nextapp.com/mantis/view.php?id=367

There are no guarantees made as to when the project will update the version of Echo3.