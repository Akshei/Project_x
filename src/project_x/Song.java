/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project_x;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Maciek
 */
public class Song {
    private final StringProperty path;
    private final StringProperty name;
    
    
     /**
     * Default constructor.
     */
    public Song() {
        this(null, null);
    }
    
      /**
     * Constructor
     * 
     * @param path_
     * @param name_
     */
    public Song(String name_, String path_) {
        this.path = new SimpleStringProperty(path_);
        this.name = new SimpleStringProperty(name_);

    }
    
       public String getPath() {
        return path.get();
    }

    public void setPath(String path_) {
        this.path.set(path_);
    }
    public StringProperty propertyPath(){
        return path;
}
    
       public String getName() {
        return name.get();
    }

    public void setName(String name_) {
        this.name.set(name_);
    }

    public StringProperty propertyName(){
        return name;
}

    
}
