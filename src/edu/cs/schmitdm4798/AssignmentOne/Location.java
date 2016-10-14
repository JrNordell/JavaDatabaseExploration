package edu.cs.schmitdm4798.AssignmentOne;

/**
 * Created by Dave and Jack.
 */
public class Location {

    private String name;
    private int onHand;

    public Location (String name, int onHand){
        this.name = name;
        this.onHand = onHand;
    }

    public String getName() {
        return name;
    }

    public int getOnHand() {
        return onHand;
    }
}
