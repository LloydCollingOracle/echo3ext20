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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.deployer.WebAppDeployer;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.handler.RequestLogHandler;

/**
 * An embedded Jetty server which can be used to start the test app.
 * @author sgodden
 *
 */
public class JettyServer {

    private static final Log log = LogFactory.getLog(JettyServer.class);

    public static void main(String[] args) {

        BasicConfigurator.configure();
        Logger l = Logger.getLogger("org.hibernate");
        l.setLevel(Level.INFO);
        l = Logger.getLogger("org.sgodden");
        l.setLevel(Level.DEBUG);
        l = Logger.getLogger("org.mortbay");
        l.setLevel(Level.DEBUG);


        final Server server = new Server(8081);
        server.setStopAtShutdown(true);

        server.addHandler(new RequestLogHandler());

        ContextHandlerCollection contexts = new ContextHandlerCollection();
        server.setHandler(contexts);

        WebAppDeployer deployer = new WebAppDeployer();
        deployer.setContexts(contexts);
        deployer.setWebAppDir("./build/webapps");
        deployer.setExtract(true); // necessary
        server.addLifeCycle(deployer);

        try {
            server.start();
        } catch (Exception e) {
            log.error("Error starting jetty server", e);
            System.exit(-1);
        }
    }
}
