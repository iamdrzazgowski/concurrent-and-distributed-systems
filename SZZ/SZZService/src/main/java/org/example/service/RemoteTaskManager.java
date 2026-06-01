package org.example.service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RemoteTaskManager extends Remote {

    boolean register(User user)
            throws RemoteException;

    boolean login(String login,
                  String password)
            throws RemoteException;

    void addTask(Task task)
            throws RemoteException;

    List<Task> getTasks()
            throws RemoteException;

    void updateTask(Task task)
            throws RemoteException;

    void deleteTask(int taskId)
            throws RemoteException;

    void addObserver(TaskObserver observer)
            throws RemoteException;

    void removeObserver(TaskObserver observer)
            throws RemoteException;
}
