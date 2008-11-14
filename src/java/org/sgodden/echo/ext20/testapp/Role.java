package org.sgodden.echo.ext20.testapp;

/**
 * A role object, for testing list components.
 * @author sgodden
 *
 */
public class Role {
    
    private String description;
    
    public static final Role[] ROLES = new Role[]{
        new Role("Administrator"),
        new Role("User"),
        new Role("Technical Manager"),
        new Role("Supplier"),
        new Role("Site")
    };
    
    public String getDescription() {
        return description;
    }

    public Role(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return super.toString() + "{description=" + description + "}";
    }

}
