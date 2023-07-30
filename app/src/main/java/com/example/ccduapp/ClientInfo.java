package com.example.ccduapp;

public class ClientInfo{
    private final String client_username;
    private final String client_problems;
    private final int client_displayPicture;
    public ClientInfo(String client_username, String client_problems, int client_displayPicture){
        this.client_username = client_username;
        this.client_problems = client_problems;
        this.client_displayPicture = client_displayPicture;
    }
    public String getClient_username(){
        return client_username;
    }
    public String getClient_problems(){
        return client_problems;
    }
    public int getClient_displayPicture(){
        return client_displayPicture;
    }
}
