package Model;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {

    @Test
    void makeAccount() {
    Account account = new Account("username","password");

    assertEquals("username", account.getUsername());
    assertEquals("password", account.getPassword());

    Account account1 = new Account("Username1", "Password2");
    assertEquals("Username1", account1.getUsername());
    assertEquals("Password2", account1.getPassword());


    }



}