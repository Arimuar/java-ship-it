package ru.yandex.practicum.delivery;

public class PerishableParcel extends Parcel {
    private static final int BASE_COST = 3;
    private final int timeToLeave;

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLeave) {
        super(description, weight, deliveryAddress, sendDay);
        this.timeToLeave = timeToLeave;
    }

    @Override
    protected int getBaseCost() {
        return BASE_COST;
    }

    public boolean isExpired(int currentDay) {
        if (sendDay + timeToLeave >= currentDay) {
            return false;
        } else {
            return true;
        }
    }

    public int getTimeToLeave() {
        return timeToLeave;
    }
}
