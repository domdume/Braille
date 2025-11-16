package service;

import model.Greeting;

public class GreetingService {
    public Greeting createGreeting(String name) {
        String safeName = (name == null || name.trim().isEmpty()) ? "Mundo" : name.trim();
        return new Greeting("Hola, " + safeName + "!");
    }
}

