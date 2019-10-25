package ViewController;

import Model.Ad;

/**
 * @Author Alexander Huang
 * Used by: DetailViewController & MenuController
 */
public interface DetailViewToggler {
    void toggleDetailView(boolean value, Ad ad);
}
