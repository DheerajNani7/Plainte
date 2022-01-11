package com.example.rajesh;

public class AdminComplaintModel {

    private String type;
    private String imageURL;
    private String date;
    private String description;
    private String status;
    private String complaintid;
    private String username;

    public AdminComplaintModel() {
    }

    public AdminComplaintModel(String type, String imageURL, String date, String description, String status, String complaintid, String username) {
        this.type = type;
        this.imageURL = imageURL;
        this.date = date;
        this.description = description;
        this.status = status;
        this.complaintid = complaintid;
        this.username = username;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComplaintid() {
        return complaintid;
    }

    public void setComplaintid(String complaintid) {
        this.complaintid = complaintid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



}
