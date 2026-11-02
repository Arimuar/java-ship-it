package ru.yandex.practicum.delivery;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {
    //стоимость
    @Test
    void standardParcelCost() {
        StandardParcel p = new StandardParcel("Коробка", 5, "Астрахань", 1);
        assertEquals(10, p.calculateDeliveryCost());
    }

    @Test
    void fragileParcelCost() {
        FragileParcel p = new FragileParcel("Ваза", 3, "Новосибирск", 1);
        assertEquals(12, p.calculateDeliveryCost());
    }

    @Test
    void perishableParcelCost() {
        PerishableParcel p = new PerishableParcel("Торт", 2, "Москва", 1, 3);
        assertEquals(6, p.calculateDeliveryCost());
    }

    @Test
    void costWithZeroWeight() {
        StandardParcel p = new StandardParcel("Пусто", 0, "Адрес", 1);
        assertEquals(0, p.calculateDeliveryCost());
    }

    @Test
    void perishableExpired() {
        PerishableParcel p = new PerishableParcel("Торт", 1, "Питер", 10, 3);
        assertTrue(p.isExpired(15));
    }

    @Test
    void perishableNotExpired() {
        PerishableParcel p = new PerishableParcel("Торт", 1, "Питер", 10, 3);
        assertFalse(p.isExpired(12));
    }

    @Test
    void perishableBoundaryNotExpired() {
        PerishableParcel p = new PerishableParcel("Торт", 1, "Питер", 10, 3);
        assertFalse(p.isExpired(13));
    }

    @Test
    void perishableBoundaryExpired() {
        PerishableParcel p = new PerishableParcel("Торт", 1, "Питер", 10, 3);
        assertTrue(p.isExpired(14));
    }

    @Test
    void addParcelWithinLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);
        StandardParcel p = new StandardParcel("Книга", 5, "Адрес", 1);
        assertTrue(box.addParcel(p));
        assertEquals(1, box.getAllParcels().size()); //меньше лимита
    }

    @Test
    void addParcelExceedsLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);
        StandardParcel p = new StandardParcel("Книга", 15, "Адрес", 1);
        assertFalse(box.addParcel(p));
        assertTrue(box.getAllParcels().isEmpty()); // больше лимита
    }

    @Test
    void addParcelExactlyAtLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);
        StandardParcel p = new StandardParcel("Книга", 10, "Адрес", 1);
        assertTrue(box.addParcel(p)); // ровно в лимит
    }

    @Test
    void addParcelAfterPartialFill() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);
        box.addParcel(new StandardParcel("A", 6, "B", 1));
        StandardParcel second = new StandardParcel("C", 5, "D", 1);
        assertFalse(box.addParcel(second));
        assertEquals(1, box.getAllParcels().size()); // добавить коробку превышающую лимит
    }

}