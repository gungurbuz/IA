package businesslayer;

public class Member {
    private String idmember;
    private String username;
    private String passHash;
    private String phoneNumber;
    private String email;
    
    public String getIdmember() {
        return idmember;
    }
    
    public void setIdmember(String idmember) {
        this.idmember = idmember;
    }
    public Member(String idmember, String username, String passHash) {
        this.idmember = idmember;
        this.username = username;
        this.passHash = passHash;
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
