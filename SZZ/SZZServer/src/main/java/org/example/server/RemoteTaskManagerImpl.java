package org.example.server;

import org.example.service.*;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RemoteTaskManagerImpl
        extends UnicastRemoteObject
        implements RemoteTaskManager {

    private final List<User> users =
            Collections.synchronizedList(
                    new ArrayList<>());

    private final List<Task> tasks =
            Collections.synchronizedList(
                    new ArrayList<>());

    private final List<TaskObserver> observers =
            Collections.synchronizedList(
                    new ArrayList<>());

    private int nextTaskId = 1;

    public RemoteTaskManagerImpl()
            throws RemoteException {

        super();

        List<Task> loadedTasks =
                (List<Task>) SaveManager.load("tasks.dat");

        if (loadedTasks != null) {
            tasks.addAll(loadedTasks);
        }

        List<User> loadedUsers =
                (List<User>) SaveManager.load("users.dat");

        if (loadedUsers != null) {
            users.addAll(loadedUsers);
        }
    }

    @Override
    public boolean register(User user)
            throws RemoteException {

        for (User u : users) {

            if (u.getLogin().equals(user.getLogin())) {
                return false;
            }
        }

        user.setPassword(
                PasswordUtil.hash(
                        user.getPassword()
                ));

        users.add(user);

        SaveManager.save(users, "users.dat");

        return true;
    }

    @Override
    public boolean login(String login,
                         String password)
            throws RemoteException {

        String hashed =
                PasswordUtil.hash(password);

        for (User u : users) {

            if (u.getLogin().equals(login)
                    && u.getPassword().equals(hashed)) {

                return true;
            }
        }

        return false;
    }

    @Override
    public synchronized void addTask(Task task)
            throws RemoteException {

        task.setId(nextTaskId++);

        tasks.add(task);

        SaveManager.save(tasks,
                "tasks.dat");

        notifyObservers();
    }

    @Override
    public List<Task> getTasks()
            throws RemoteException {

        return tasks;
    }

    @Override
    public void updateTask(Task task)
            throws RemoteException {

        for (int i = 0; i < tasks.size(); i++) {

            if (tasks.get(i).getId()
                    == task.getId()) {

                tasks.set(i, task);

                SaveManager.save(tasks,
                        "tasks.dat");

                notifyObservers();
            }
        }
    }

    @Override
    public void deleteTask(int taskId)
            throws RemoteException {

        tasks.removeIf(
                task -> task.getId() == taskId
        );

        SaveManager.save(tasks,
                "tasks.dat");

        notifyObservers();
    }

    @Override
    public void addObserver(
            TaskObserver observer)
            throws RemoteException {

        observers.add(observer);
    }

    @Override
    public void removeObserver(
            TaskObserver observer)
            throws RemoteException {

        observers.remove(observer);
    }

    private void notifyObservers() {

        for (TaskObserver observer
                : observers) {

            try {

                observer.updateTasks(tasks);

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }
}