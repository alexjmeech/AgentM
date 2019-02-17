package com.hackdfw.clientm;

public class Player {
    private String instanceName;
    private boolean isDead;
    private String instanceClass;

    public Player(String instanceName, boolean isDead, String instanceClass) {
        this.instanceName = instanceName;
        this.isDead = isDead;
        this.instanceClass = instanceClass;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setInstanceClass(String instanceClass) {
        this.instanceClass = instanceClass;
    }

    public String getInstanceClass() {
        return instanceClass;
    }
}
