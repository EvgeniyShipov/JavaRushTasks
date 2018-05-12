package com.javarush.task.task32.task3208;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/* 
RMI-2
*/
public class Solution {
    public static Registry registry;
    public static Thread CLIENT_THREAD = new Thread(Solution::clientRun);
    public static Thread SERVER_THREAD = new Thread(Solution::ServerRun);

    public static void main(String[] args) throws InterruptedException {
        SERVER_THREAD.start();
        Thread.sleep(1000);
        CLIENT_THREAD.start();
        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void clientRun() {
        try {
            for (String bindingName : registry.list()) {
                Animal service = (Animal) registry.lookup(bindingName);
                service.printName();
                service.say();
            }
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    private static void ServerRun() {
        try {
            Animal cat = new Cat("Murka");
            Animal dog = new Dog("Zhuchka");
            registry = LocateRegistry.createRegistry(2099);
            Remote remoteCat = UnicastRemoteObject.exportObject(cat, 0);
            Remote remoteDog = UnicastRemoteObject.exportObject(dog, 1);
            registry.bind("cat", remoteCat);
            registry.bind("dog", remoteDog);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}