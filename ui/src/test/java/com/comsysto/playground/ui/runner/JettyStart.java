package com.comsysto.playground.ui.runner;

public class JettyStart {

    public static void main(final String[] args) {
        if (args.length < 1) {
            System.out.println("JettyStart <httpport>");
            return;
        }

        //System.setProperty("spring.profiles.active", "default");

        JettyStarterApplication jettyStarter = new JettyStarterApplication(Integer.valueOf(args[0]));
        jettyStarter.startServer();
    }
}
