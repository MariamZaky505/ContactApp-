package application;

import java.sql.Date;

public class contactData {

private Integer contactId;
private String firstName;
private String lastName;
private String gender;
private String phone;
private String email;
private Date date;
private String image;

public contactData(Integer contactId, String firstName, String lastName, String gender, String phone, String email, String image,  Date date){
    this.contactId = contactId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
    this.phone = phone;
    this.date = date;
    this.image= image;
}    
public contactData(Integer employeeId, String firstName, String lastName,String email){
    this.contactId = employeeId;
    this.firstName = firstName;
    this.lastName = lastName;;
    this.email= email;
}

public Integer getContactId(){
    return contactId;
}
public String getFirstName(){
    return firstName;
}
public String getLastName(){
    return lastName;
}
public String getGender(){
    return gender;
}
public String getPhone(){
    return phone;
}    

public Date getDate(){
    return date;
}
public String getEmail(){
    return email;
}
public String getImage(){
    return image;
}
}

