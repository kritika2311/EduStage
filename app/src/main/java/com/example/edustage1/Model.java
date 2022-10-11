package com.example.edustage1;

public class Model {
    String name, image, experience, members;

    public Model() {
    }

    public Model(String name, String image, String experience, String members) {
        this.name = name;
        this.image = image;
        this.experience = experience;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }

}
