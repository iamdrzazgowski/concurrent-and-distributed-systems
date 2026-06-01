package org.example.server;

import org.example.service.RemoteTaskManager;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    public static void main(String[] args) {

        try {

            Registry registry =
                    LocateRegistry.createRegistry(1099);

            RemoteTaskManager manager =
                    new RemoteTaskManagerImpl();

            registry.rebind(
                    "TaskManager",
                    manager
            );

            System.out.println(
                    "=== SERWER URUCHOMIONY ==="
            );

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}