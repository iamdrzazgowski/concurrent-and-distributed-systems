package org.example.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface TaskObserver extends Remote {

    void updateTasks(List<Task> tasks)
            throws RemoteException;
}