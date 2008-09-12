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

import nextapp.echo.app.ApplicationInstance;
import nextapp.echo.app.Window;

/**
 * The application instance for the test application.
 * 
 * @author goddens
 *
 */
public class AppInstance extends ApplicationInstance {

    private static final long serialVersionUID = 20080103L;

    public Window init() {
        Window ret = new Window();
        
        ret.setTitle("Echo3 and Ext2.0 test application");
        ret.setContent(new ApplicationContentPane());

        return ret;
    }
}
