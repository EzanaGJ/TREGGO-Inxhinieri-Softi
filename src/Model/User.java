package Model;

public class User {
    private int userId;
    private String name;
    private String password;
    private String roleType;
    private String email;

    public User(int userId, String name, String password, String roleType, String email) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.roleType = roleType;
        this.email = email;
    }

    public User(String name, String password, String roleType,String email) {
        this.name = name;
        this.password = password;
        this.roleType = roleType;
        this.email = email;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public String getRoleType() {
        return this.roleType;
    }

    public String getEmail() {
        return this.email;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoleType(String roleType) {
    }

    public void setEmail(String email) {
        this.email = email;
    }

}