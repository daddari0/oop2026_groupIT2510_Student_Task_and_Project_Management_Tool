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
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setGender(gender);
        setStatus(status);

    }
    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Name is null.");
        }
        this.name = name;
    }

    public String getSurname(){
        return surname;
    }

    public void setSurname(String surname){
        if(surname == null || surname.isEmpty()){
            throw new IllegalArgumentException("Surname is null.");
        }
        this.surname = surname;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        if(email == null || email.isEmpty()){
            throw new IllegalArgumentException("Email is null.");
        }
        this.email = email;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber){
        if(phoneNumber == null || phoneNumber.isEmpty()){
            throw new IllegalArgumentException("Phone number is null.");
        }
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender == null || gender.isEmpty()) {
            throw new IllegalArgumentException("Gender is null.");
        }
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        if (status == null || status.isEmpty()) {
            throw new IllegalArgumentException("Status is null.");
        }
        this.status = status;
    }
}
