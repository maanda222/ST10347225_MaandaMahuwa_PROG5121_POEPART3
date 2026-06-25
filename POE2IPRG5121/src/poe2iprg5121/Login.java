/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package poe2iprg5121;

/**
 *
 * @author lab_services_student
 */
public class Login {

    private String username;
    private String password;
    private String cellPhone;

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    // Username validation
    public boolean checkUserName() {
        return username.contains("_") && username.length() <= 5;
    }

    // Password validation
    public boolean checkPasswordComplexity() {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");
    }

    // SA cellphone validation
    public boolean checkCellPhoneNumber() {
        return cellPhone.matches("^\\+27\\d{9}$");
    }

    // Registration validation
    public String registerUser() {

        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        return "User registered successfully.";
    }

    // Login validation
    public boolean loginUser(String enteredUsername, String enteredPassword) {

        return username.equals(enteredUsername)
                && password.equals(enteredPassword);
    }

    // Login message
    public String returnLoginStatus(String firstName, String lastName, boolean success) {

        if (success) {
            return "Welcome " + firstName + ", " + lastName + " it is great to see you again.";
        } else {
            return "Username or password incorrect, please try again.";
        }
    }
}
    

