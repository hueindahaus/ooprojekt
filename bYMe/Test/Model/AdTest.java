package Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdTest {

    Ad testad = new Ad("Title1", 10, "Description", "Chalmers", "idtest");

    @Test
    void getTitle() {
        assertEquals("Title1", testad.getTitle());
    }

    @Test
    void getPrice() {
        assertEquals(10, testad.getPrice());
    }

    @Test
    void getDescription() {
        assertEquals("Description", testad.getDescription());
    }

    @Test
    void getLocation() {
        assertEquals("Chalmers", testad.getLocation());
    }

    @Test
    void getAdId() {
        assertEquals("idtest", testad.getAdId());
    }

    @Test
    void setPrice() {
    testad.setPrice(1);
    assertEquals(1, testad.getPrice());
    }

    @Test
    void setTitle() {
        testad.setTitle("changedTitle");
        assertEquals("changedTitle", testad.getTitle());

    }

    @Test
    void setDescription() {
       testad.setDescription("changedDescription");
        assertEquals("changedDescription", testad.getDescription());
    }

    @Test
    void setLocation() {
        testad.setLocation("changedLocation");
        assertEquals("changedLocation",testad.getLocation());
    }
}