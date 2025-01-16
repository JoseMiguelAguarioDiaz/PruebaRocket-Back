package com.example.demo.kernel;

public class Validations {
    public boolean isInvalidEmail(String email){
        return !email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
    }
    public boolean isInvalidPhone(Long phone){
        return !String.valueOf(phone).matches("^[0-9]{1,10}$");
    }
}
