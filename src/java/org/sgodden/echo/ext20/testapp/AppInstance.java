/* =================================================================
# This library is free software; you can redistribute it and/or
# modify it under the terms of the GNU Lesser General Public
# License as published by the Free Software Foundation; either
# version 2.1 of the License, or (at your option) any later version.
#
# This library is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
# Lesser General Public License for more details.
#
# You should have received a copy of the GNU Lesser General Public
# License along with this library; if not, write to the Free Software
# Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
#
# ================================================================= */
package org.sgodden.echo.ext20.testapp;

import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.StyleSheet;
import nextapp.echo.app.Window;
import nextapp.echo.app.serial.SerialException;
import nextapp.echo.app.serial.StyleSheetLoader;

/**
 * The application instance for the test application.
 * 
 * @author goddens
 *
 */
@SuppressWarnings("serial")
public class AppInstance extends ApplicationInstance {

    private static final StyleSheet DEFAULT_STYLE_SHEET;


    static {
        try {

            DEFAULT_STYLE_SHEET = StyleSheetLoader.load(
                    "default-stylesheet.xml", Thread.currentThread().getContextClassLoader());

        } catch (SerialException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private static GroovyScriptEngine groovyScriptEngine;

    static {
        try {
            groovyScriptEngine = new GroovyScriptEngine(
                    new URL[]{new URL(System.getProperty("groovy.script.url"))});
        } catch (Exception e) {
            throw new Error("Error initialsing groovy script engine", e);
        }
    }

    public Window init() {
        setStyleSheet(DEFAULT_STYLE_SHEET);

        Window ret = new Window();

        ret.setTitle("Echo3 and Ext2.0 test application");
        ret.setContent(new ApplicationContentPane());

        return ret;
    }

    /**
     * Returns a new instance of the class defined in the specified
     * groovy script.
     * @param scriptName the groovy script name.
     * @return a new instance of the class defined in the script.
     */
    public Object getGroovyObjectInstance(String scriptName) {
        Object ret = null;
        try {
            Class clazz = groovyScriptEngine.loadScriptByName(scriptName);
            ret = clazz.newInstance();
        } catch (Exception e) {
            throw new Error("Error creating groovy object instance for" +
                    " script name: " + scriptName, e);
        }

        return ret;
    }
}
