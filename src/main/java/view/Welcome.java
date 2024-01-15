package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

import DAO.userDAO;
import model.User;
import service.SendOTPService;
import service.generateOTP;
import service.userService;

public class Welcome {
    public void welcomescreen() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Welcome to the application");
        System.out.println("Press 1 to login");
        System.out.println("Press 2 to signUp");
        System.out.println("Press 0 to exit");
        int choice = 0;
        try {
            choice = Integer.parseInt(br.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                signUp();
                break;
            case 0:
                System.exit(0);
        }

    }

    private void login() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your email address");
        String email = sc.nextLine();
        try {
            if (userDAO.isExists(email)) {
                String genOTP = generateOTP.getOTP();
                SendOTPService.sendOTP(email, genOTP);
                System.out.println("Enter the OTP");
                String otp = sc.nextLine();
                if (otp.equals(genOTP)) {
                    new userView(email).home();
                } else {
                    System.out.println("Invalid OTP");
                }
            } else {
                System.out.println("User not found");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your name");
        String name = sc.nextLine();
        System.out.println("Enter your email address");
        String email = sc.nextLine();
        String genOTP = generateOTP.getOTP();
        SendOTPService.sendOTP(email, genOTP);
        System.out.println("Enter the OTP");
        String otp = sc.nextLine();
        if (otp.equals(genOTP)) {
            User user = new User(name, email);
            int response = userService.saveUser(user);
            switch (response) {
                case 0:
                    System.out.println("User is registered successfully");

                case 1:
                    System.out.println("User already exists");

            }
        }

    }

}
