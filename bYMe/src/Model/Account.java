package Model;

class Account {
    private final String username;
    private String password;

    protected Account(String username, String password){
        this.username = username;
        this.password = password;
    }

    protected String getUsername(){
        return this.username;
    }

    protected String getPassword(){
        return this.password;
    }
}
