package test.jackson;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
 
public class Orgtest6Lion extends Orgtest6Animal {
 
    private String name;
 
    @JsonCreator
    public Orgtest6Lion(@JsonProperty("name") String name) {
        this.name = name;
    }
 
    public String getName() {
        return name;
    }
 
    public String getSound() {
        return "Roar";
    }
 
    public String getType() {
        return "carnivorous";
    }
 
    public boolean isEndangered() {
        return true;
    }
 
    @Override
    public String toString() {
        return "Lion [name=" + name + ", getName()=" + getName() + ", getSound()=" + getSound() + ", getType()=" + getType() + ", isEndangered()="
                + isEndangered() + "]";
    }
 
}