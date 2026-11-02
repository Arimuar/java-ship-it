package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Parcel> allParcels = new ArrayList<>();
    private static final List<FragileParcel> fragileTracked = new ArrayList<>();

    private static final ParcelBox<StandardParcel> standardBox = new ParcelBox<>(50);
    private static final ParcelBox<FragileParcel> fragileBox = new ParcelBox<>(30);
    private static final ParcelBox<PerishableParcel> perishableBox = new ParcelBox<>(20);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    showTrackers();
                    break;
                case 5:
                    updateTrackerState();
                    break;
                case 6:
                    showBoxContents();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Показать трекер"); // немного увеличила функцию трекера
        System.out.println("5 — Изменить статус трекера");
        System.out.println("6 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }


    private static void addParcel() {
        System.out.println("Выберите тип посылки:");
        System.out.println("1 - Стандартная");
        System.out.println("2 - Хрупкая");
        System.out.println("3 - Скоропортящаяся");
        int type = Integer.parseInt(scanner.nextLine());

        System.out.println("Введите описание: ");
        String description = scanner.nextLine();

        System.out.println("Введите вес (целое число): ");
        int weight = Integer.parseInt(scanner.nextLine());

        System.out.println("Введите адрес назначения: ");
        String address = scanner.nextLine();

        System.out.println("Введите день отправки (от 1 до 31): ");
        int sendDay = Integer.parseInt(scanner.nextLine());

        switch (type) {
            case 1: {
                StandardParcel parcel = new StandardParcel(description, weight, address, sendDay);
                allParcels.add(parcel);
                standardBox.addParcel(parcel);
                break;
            }
            case 2: {
                FragileParcel parcel = new FragileParcel(description, weight, address, sendDay);
                allParcels.add(parcel);
                fragileTracked.add(parcel);
                fragileBox.addParcel(parcel);
                break;
            }
            case 3: {
                System.out.println("Введите срок хранения (дней): ");
                int timeToLeave = Integer.parseInt(scanner.nextLine());
                PerishableParcel parcel = new PerishableParcel(description, weight, address, sendDay, timeToLeave);
                allParcels.add(parcel);
                perishableBox.addParcel(parcel);
                break;
            }
            default:
                System.out.println("Неверный тип посылки.");
        }
    }

    private static void sendParcels() {
        if (allParcels.isEmpty()) {
            System.out.println("Нет посылок для отправки.");
            return;
        }
        for (Parcel parcel : allParcels) {
            parcel.packageItem();
            parcel.deliver();
        }

        allParcels.clear();
        fragileTracked.clear();
        standardBox.clear();
        fragileBox.clear();
        perishableBox.clear();

        System.out.println("Все посылки отправлены. Склад очищен.");
    }

    private static void calculateCosts() {
        if (allParcels.isEmpty()) {// Посчитать общую стоимость всех доставок и вывести на экран
            System.out.println("Нет посылок для расчёта стоимости.");
            return;
        }
        int total = 0;
        for (Parcel parcel : allParcels) {
            total += parcel.calculateDeliveryCost();
        }
        System.out.println("Общая стоимость доставки: " + total);
    }

    private static void showTrackers() {
        if (fragileTracked.isEmpty()) {
            System.out.println("Нет посылок с трекингом.");
            return;
        }
        System.out.println("Текущие местоположения ");
        for (FragileParcel parcel : fragileTracked) {
            System.out.println(" - <<" + parcel.getDescription() + ">>: " + parcel.getCurrentLocation());
        }
    }

    private static void updateTrackerState() {
        if (fragileTracked.isEmpty()) {
            System.out.println("Нет отправлений с поддержкой трекинга.");
            return;
        }

        System.out.println("Доступные хрупкие посылки:");
        for (FragileParcel p : fragileTracked) {
            System.out.println(" - " + p.getDescription() + " (" + p.getCurrentLocation() + ")");
        }
        System.out.println("Введите описание посылки: ");
        String name = scanner.nextLine();

        System.out.println("Введите новое местоположение: ");
        String newLocation = scanner.nextLine();

        boolean updated = false;
        for (FragileParcel p : fragileTracked) {
            if (p.getDescription().equals(name)) {
                p.reportStatus(newLocation);
                updated = true;
                break;
            }
        }

        if (!updated) {
            System.out.println("Посылка с таким описанием не найдена. ");
        } else {
            System.out.println("Статус обновлён.");
        }
    }

    private static void showBoxContents() {
        boolean running = true;
        while (running) {
            System.out.println("Выберите коробку: ");
            System.out.println("1 - Стандартные посылки ");
            System.out.println("2 - Хрупкие посылки ");
            System.out.println("3 - Скоропортящиеся посылки");
            System.out.println("0 - Вернуться ");
            int type = Integer.parseInt(scanner.nextLine());

            switch (type) {
                case 1:
                    printBox(standardBox);
                    break;
                case 2:
                    printBox(fragileBox);
                    break;
                case 3:
                    printBox(perishableBox);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неизвестная команда.");
                    break;
            }
        }
    }

    private static <T extends Parcel> void printBox(ParcelBox<T> box) {
        List<T> parcels = box.getAllParcels();
        if (parcels.isEmpty()) {
            System.out.println("Коробка пуста");
            return;
        }
        System.out.println("Содержимое коробки: ");
        for (T parcel : parcels) {
            System.out.println(" - " + parcel.getDescription() + " (вес: " + parcel.getWeight() + ").");
        }
    }
}

