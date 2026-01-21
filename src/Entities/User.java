package Entities;

public class User {
    private int id;
    private static int idgen;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String gender;
    private String status;

    public User(int id, String name, String surname, String email, String phoneNumber, String gender, String status){
        this.id = idgen++;
        setName(name);
        setSurname(surname);
        setEmail(email)

    }
}
