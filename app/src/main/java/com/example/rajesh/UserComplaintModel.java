package com.example.rajesh;

public class UserComplaintModel {
    private String complaintid;
    private String imageURL;
    private String date;
    private String description;
    private String status;
    private String type;
    public UserComplaintModel(){

    }
    public UserComplaintModel(String complaintid, String imageURL, String date, String description, String status, String type) {
        this.complaintid = complaintid;
        this.imageURL = imageURL;
        this.date = date;
        this.description = description;
        this.status = status;
        this.type = type;
    }

    public String getComplaintid() {
        return complaintid;
    }

    public void setComplaintid(String complaintid) {
        this.complaintid = complaintid;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
