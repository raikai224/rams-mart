package main.java.com.ramsmart.entities;

import java.io.Serializable;

public class FeePaid implements Serializable {

    private static final long serialVersionUID = 1L;

    private double feePaid = 20.00;

    public double getFeePaid() {
        return feePaid;
    }

    public void setFeePaid(double feePaid) {
        this.feePaid = feePaid;
    }
}
