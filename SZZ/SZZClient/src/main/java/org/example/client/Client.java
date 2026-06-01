package org.example.client;

import org.example.service.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Client
        extends UnicastRemoteObject
        implements TaskObserver {

    private final RemoteTaskManager manager;

    public Client() throws Exception {

        Registry registry =
                LocateRegistry.getRegistry(
                        "localhost",
                        1099);

        manager =
                (RemoteTaskManager)
                        registry.lookup(
                                "TaskManager");

        manager.addObserver(this);
    }

    @Override
    public void updateTasks(
            List<Task> tasks) {

        System.out.println(
                "\n=== AKTUALIZACJA ZADAŃ ===");

        tasks.forEach(
                System.out::println);
    }

    public void init() {

        Scanner sc =
                new Scanner(System.in);

        while (true) {

            System.out.println("""

                    1 Rejestracja
                    2 Logowanie
                    3 Dodaj zadanie
                    4 Lista zadań
                    5 Edytuj zadanie
                    6 Usuń zadanie
                    7 Wyjście
                    """);

            int choice =
                    Integer.parseInt(
                            sc.nextLine());

            try {

                switch (choice) {

                    case 1 -> {

                        System.out.print(
                                "Login: ");

                        String login =
                                sc.nextLine();

                        System.out.print(
                                "Hasło: ");

                        String pass =
                                sc.nextLine();

                        System.out.print(
                                "Email: ");

                        String email =
                                sc.nextLine();

                        boolean result =
                                manager.register(
                                        new User(
                                                login,
                                                pass,
                                                email));

                        System.out.println(
                                result
                                        ? "Dodano"
                                        : "Login istnieje");
                    }

                    case 2 -> {

                        System.out.print(
                                "Login: ");

                        String login =
                                sc.nextLine();

                        System.out.print(
                                "Hasło: ");

                        String pass =
                                sc.nextLine();

                        boolean result =
                                manager.login(
                                        login,
                                        pass);

                        System.out.println(
                                result
                                        ? "OK"
                                        : "Błędne dane");
                    }

                    case 3 -> {

                        System.out.print(
                                "Tytuł: ");

                        String title =
                                sc.nextLine();

                        System.out.print(
                                "Opis: ");

                        String desc =
                                sc.nextLine();

                        System.out.print(
                                "Priorytet LOW/MEDIUM/HIGH: ");

                        Priority priority =
                                Priority.valueOf(
                                        sc.nextLine()
                                                .toUpperCase());

                        System.out.print(
                                "Data YYYY-MM-DD: ");

                        LocalDate date =
                                LocalDate.parse(
                                        sc.nextLine());

                        manager.addTask(
                                new Task(
                                        0,
                                        title,
                                        desc,
                                        priority,
                                        date));
                    }

                    case 4 -> {

                        List<Task> tasks =
                                manager.getTasks();

                        tasks.forEach(
                                System.out::println);
                    }

                    case 5 -> {

                        System.out.print(
                                "ID: ");

                        int id =
                                Integer.parseInt(
                                        sc.nextLine());

                        System.out.print(
                                "Nowy tytuł: ");

                        String title =
                                sc.nextLine();

                        System.out.print(
                                "Opis: ");

                        String desc =
                                sc.nextLine();

                        System.out.print(
                                "Priorytet: ");

                        Priority priority =
                                Priority.valueOf(
                                        sc.nextLine()
                                                .toUpperCase());

                        System.out.print(
                                "Data: ");

                        LocalDate date =
                                LocalDate.parse(
                                        sc.nextLine());

                        manager.updateTask(
                                new Task(
                                        id,
                                        title,
                                        desc,
                                        priority,
                                        date));
                    }

                    case 6 -> {

                        System.out.print(
                                "ID zadania: ");

                        int id =
                                Integer.parseInt(
                                        sc.nextLine());

                        manager.deleteTask(id);
                    }

                    case 7 -> {

                        System.exit(0);
                    }
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
            throws Exception {

        Client client =
                new Client();

        client.init();
    }
}