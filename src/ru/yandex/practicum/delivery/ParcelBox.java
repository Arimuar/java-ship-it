package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;

public class ParcelBox<T extends Parcel> {
    private final int maxWeight;
    private final List<T> parcels = new ArrayList<>();
    private int currentWeight = 0;

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public boolean addParcel(T parcel) {
        if (currentWeight + parcel.getWeight() > maxWeight) {
            System.out.println("Нельзя добавить посылку <<" + parcel.getDescription() + ">> так как превыщен максимальный вес коробки (" + maxWeight + ").");
            return false;
        }
        parcels.add(parcel);
        currentWeight += parcel.getWeight();
        return true;
    }

    public List<T> getAllParcels() {
        return parcels;
    }

    public void clear() {
        parcels.clear();
        currentWeight = 0;
    }

    public int getMaxWeight() {
        return maxWeight;
    }
}
