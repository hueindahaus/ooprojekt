package Controller;

import Model.Account;

public interface AdCreator {
    void createAd(String title, String description, int price, String location);
    void updateAdItems();
}
