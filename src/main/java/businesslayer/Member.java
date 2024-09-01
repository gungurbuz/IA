package businesslayer;

public class Member {

    private String username;
    private String passHash;
    private String phoneNumber;
    private String email;

    public Member(String username, String passHash) {
        this.username = username;
        this.passHash = passHash;
    }

    public Member(String username, String passHash, String phoneNumber) {
        this.username = username;
        this.passHash = passHash;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassHash() {
        return passHash;
    }

    public String getUsername() {
        return username;
    }

}
