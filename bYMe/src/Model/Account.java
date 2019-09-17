package Model;

class Account {
    private final String username;
    private String password;

    Account(String username, String password){
        this.username = username;
        this.password = password;
    }

    String getUsername(){
        return this.username;
    }

    String getPassword(){
        return this.password;
    }
}
