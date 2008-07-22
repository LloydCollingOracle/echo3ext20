package org.sgodden.echo.ext20.testapp;

import groovy.lang.GroovyClassLoader;

import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.Introspector;

import org.sgodden.echo.ext20.Button;
import org.sgodden.echo.ext20.Panel;

/**
 * A quick test of embedding groovy.
 * @author sgodden
 */
public class GroovyTestMain {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        try {
            
            BeanInfo info = Introspector.getBeanInfo(Button.class);
            for (EventSetDescriptor desc : info.getEventSetDescriptors()) {
                System.out.println(desc.getDisplayName());
            }
            
            GroovyClassLoader gcl = new GroovyClassLoader();
            Class clazz = gcl.parseClass(GroovyTestMain.class.getClassLoader().getResourceAsStream("org/sgodden/echo/ext20/testapp/groovy/TestPanel.groovy"));
            Panel p = (Panel)clazz.newInstance();
            //System.out.println(gb.getClass());
            //Panel p = (Panel) gb.build();
            System.out.println(p.getTitle());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
