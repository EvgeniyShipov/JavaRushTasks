package com.javarush.task.task32.task3207;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/* 
К серверу по RMI
*/
public class Solution {
    public static final String UNIC_BINDING_NAME = "double.string";
    public static Registry registry;

    public static Thread CLIENT_THREAD = new Thread(() -> {
        try {
            DoubleString service = (DoubleString) registry.lookup(UNIC_BINDING_NAME);
            String result = service.doubleString("tic-tac ");
            System.out.println(result);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    });

    public static void main(String[] args) throws InterruptedException {
        Remote stub = null;
        try {
            registry = LocateRegistry.createRegistry(2099);
            final DoubleStringImpl service = new DoubleStringImpl();
            stub = UnicastRemoteObject.exportObject(service, 0);
            registry.bind(UNIC_BINDING_NAME, stub);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
        CLIENT_THREAD.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}