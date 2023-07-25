package com.kardelen.sahipsizkahramanlar.adoption;

public class Pet {
    private String name;
    private String breed;
    private String species;
    private String  age;

    // Yapıcı metot
    public Pet(String name, String breed, String species, String age) {
        this.name = name;
        this.breed = breed;
        this.species = species;
        this.age = age;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
