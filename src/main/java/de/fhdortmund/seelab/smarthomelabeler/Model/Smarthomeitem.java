package de.fhdortmund.seelab.smarthomelabeler.Model;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by jonas on 13.08.17.
 */
public class Smarthomeitem {
    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty link = new SimpleStringProperty("");
    private SimpleStringProperty label = new SimpleStringProperty("");
    private SimpleStringProperty type = new SimpleStringProperty("");

    public Smarthomeitem() {

    }

    public Smarthomeitem(String name,String label,  String link, String type) {
        this.name.set(name);
        this.link.set(link);
        this.label.set(label);
        this.type.set(type);
    }

    public String getName() {
        return name.get();
    }

    public String getLabel() {
        return label.get();
    }

    public void setLabel(String label) {
        this.label.set(label);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getLink() {
        return link.get();
    }

    public void setlink(String link) {
        this.link.set(link);
    }

    public String getType() {
       return this.type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }




}
