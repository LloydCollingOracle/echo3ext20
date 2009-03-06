This project contains echo3 components and rendering peers for the Ext 2.0 widget set.

Explanation of project layout
=============================
It's a pretty standard webapp layout.  In the 'contrib' directory, you will find the results of running "ant dist" in the svn checkout of the echo3 trunk.  The "Echo3_App.jar" and "Echo3_WebContainer.jar" have been extracted from there and put into src/webapp/WEB-INF/lib.

If you want to get source attachments to the echo3 classes, then you need to extract that tar.gz where it is.

BUILDING
========

1. Build the patched ext-2.0.2 distribution (optional)
======================================================
Note that this is optional - you can go with vanilla ext-2.0.2, but you won't be able to make table layouts
occupy full width, or to set the alignment of table cells.

If you decide to go for it, here's the process:

1. Download ext-2.0.2.zip from somewhere.  I can't redistribute it here.
2. Apply the patches in 'contrib/patches/ext-2.0.2' to the ext source.  These make table layout behave reasonably.
3. Download the ext SVN builder from 'http://extjs.com/deploy/builder-0.11.zip'
4. Rename the 'ext-2.0.2/source' folder to 'ext-2.0.2/src' (why oh why?)
5. Run the ext SVN builder to produce output somewhere, e.g.
    'java -jar builder.jar -s /home/me/ext-src/ext-2.0.2/ -o /home/me/ext-output/ext-2.0.2 -d'
6. Zip up that output folder to a zip called ext-2.0.2.zip, e.g.
    'cd /home/me/ext-output'
    'zip -r ext-2.0.2.zip ext-2.0.2'
7. Place that zip into the contrib folder.

2. Build this application
=========================
1. Copy build.properties.sample to build.properties and alter the two properties in there to point to the correct locations for the servlet jar and 
your tomcat deployment directoy.
2. Run ant.  The war file will be produced in the 'dist' directory and copied to the deployment directory.

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

There are no guarantees mad as to when the project will update the version of Echo3.

LIMITATIONS
===========
It is not possible to nest form components within ext layouts within echo layouts.  Something just doesn't work, and I have not had the time
to understand the ext layout pipeline enough to find the problem.

So you can use echo3 layouts within ext layouts, to e.g. put a row of images within an ext panel, but you can't then
put another ext layout inside that containing form components.  You will get all sorts of rendering issues.

TO DO
=====
Implement MultiSelect / ItemSelector extension from: http://extjs.com/forum/showthread.php?t=20071
Implement TreePanel
Implement arbitrary drag and drop