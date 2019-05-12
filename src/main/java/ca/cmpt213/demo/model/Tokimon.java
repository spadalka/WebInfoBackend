package ca.cmpt213.demo.model;

public class Tokimon {

    private long id;
    private String name;
    private double weight;
    private double height;
    private String ability;
    private double strength;
    private String color;

    public Tokimon()
    {

    }

    public Tokimon(long id, String name, double weight, double height, String ability, double strength, String color) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.ability = ability;
        this.strength = strength;
        this.color = color;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
