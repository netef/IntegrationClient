package com.Integration.client.Boundaries;

public class Creator {

    String email;
    String smartspace;

    public Creator(){

    }

    public Creator(String email, String smartspace){
        this.email = email;
        this.smartspace = smartspace;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSmartspace() {
        return smartspace;
    }

    public void setSmartspace(String smartspace) {
        this.smartspace = smartspace;
    }
}
