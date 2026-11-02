package ru.yandex.practicum.delivery;

public class FragileParcel extends Parcel implements Trackable {
    private static final int BASE_COST = 4;
    private static final String LOCATION = "Склад отправки";

    private String currentLocation = LOCATION;

    public FragileParcel(String description, int weight, String deliveryAddress, int sendDay) {
        super(description, weight, deliveryAddress, sendDay);
    }

    @Override
    public void packageItem() {
        System.out.println("Поссылка <<" + description + ">> обёрнута в защитную плёнку");
        super.packageItem();
    }

    @Override
    protected int getBaseCost() {
        return BASE_COST;
    }

    @Override
    public void reportStatus(String newLocation) {
        this.currentLocation = newLocation;
        System.out.println("Хрупкая посылка <<" + description
                + ">> изменила местоположение на " + newLocation);
    }

    public String getCurrentLocation() {
        return currentLocation;
    }
}
