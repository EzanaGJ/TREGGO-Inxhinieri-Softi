package Model;

class User {
    final int userId;
    String name;
    String email;
    String passwordHash;
    RoleType role;
    List<Address> addresses = new ArrayList<>();
    List<Integer> favorites = new ArrayList<>();

    User(int id, String name, String email, String passwordPlain, RoleType role) {
        this.userId = id;
        this.name = name;
        this.email = email;
        this.passwordHash = PasswordUtil.hash(passwordPlain);
        this.role = role;
    }

    @Override public String toString() {
        return String.format("User{id=%d,name='%s',email='%s',role=%s}", userId,name,email,role);
    }
}