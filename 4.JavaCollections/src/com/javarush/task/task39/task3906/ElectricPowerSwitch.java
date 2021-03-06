package com.javarush.task.task39.task3906;

public class ElectricPowerSwitch {
    private Switchable system;

    public ElectricPowerSwitch(Switchable system) {
        this.system = system;
    }

    public void press() {
        System.out.println("Pressed the power switch.");
        if (system.isOn()) {
            system.turnOff();
        } else {
            system.turnOn();
        }
    }
}
