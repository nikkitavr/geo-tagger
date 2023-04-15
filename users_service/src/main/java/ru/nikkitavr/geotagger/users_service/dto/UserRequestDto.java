package ru.nikkitavr.geotagger.users_service.dto;

public class UserRequestDto {
     String name;
     String login;
     String email;
     String phoneNumber;

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public String getLogin() {
          return login;
     }

     public void setLogin(String login) {
          this.login = login;
     }

     public String getEmail() {
          return email;
     }

     public void setEmail(String email) {
          this.email = email;
     }

     public String getPhoneNumber() {
          return phoneNumber;
     }

     public void setPhoneNumber(String phoneNumber) {
          this.phoneNumber = phoneNumber;
     }
}
