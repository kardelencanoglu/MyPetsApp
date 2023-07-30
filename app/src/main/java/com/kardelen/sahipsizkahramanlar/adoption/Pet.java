package com.kardelen.sahipsizkahramanlar.adoption;

public class Pet {
    private String name;
    private String breed;
    private String species;
    private String  age;
    private String  gender;

    // Yapıcı metot
    public Pet(String name, String breed, String species, String age, String gender) {
        this.name = name;
        this.breed = breed;
        this.species = species;
        this.age = age;
        this.gender = gender;
    }

    // Getter ve Setter metotları
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
