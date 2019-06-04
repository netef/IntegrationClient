package com.Integration.client.Boundaries;

public class ElementKey {
    String id;
    String smartspace;

    public ElementKey(){

    }

    public ElementKey( String smartspace) {
        this.id = "";
        this.smartspace = smartspace;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSmartspace() {
        return smartspace;
    }

    public void setSmartspace(String smartspace) {
        this.smartspace = smartspace;
    }
}
