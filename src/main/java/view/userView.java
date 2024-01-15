package view;

import DAO.dataDAO;
import model.Data;
import java.util.Scanner;
import java.sql.SQLException;
import java.util.List;
import java.io.File;
import java.io.IOException;

public class userView {
    private String email;

    userView(String email) {
        this.email = email;
    }

    public void home() {
        do {
            System.out.println("Welcome " + this.email);
            System.out.println("Press 1 to show hidden files");
            System.out.println("Press 2 to hide a new file");
            System.out.println("Press 3 to unhide a file");
            System.out.println("Press 0 to exit");
            Scanner sc = new Scanner(System.in);
            int ch = Integer.parseInt(sc.nextLine());
            switch (ch) {
                case 1:
                    try {
                        System.out.println("\nHidden Files:\n");
                        List<Data> files = dataDAO.getAllFile(this.email);
                        System.out.println("Id- File Name");
                        for (Data file : files) {
                            System.out.print(file.getId() + " - " + file.getFileName());
                        }
                    } catch (SQLException e) {
                        System.out.println("Error in retrieving the list of files.");
                        e.printStackTrace();
                    }
                    break;
                // show hidden files
                case 2:
                    System.out.print("Enter the path of the file you want to hide: ");
                    String path = sc.nextLine();
                    File f = new File(path);
                    Data file = new Data(0, f.getName(), path, this.email);
                    try {
                        dataDAO.hideFile(file);
                    } catch (SQLException e) {
                        System.out.println("Failed to add the file to your hidden files.\n");
                    } catch (IOException e) {
                        System.out.println("The file does not exist or cannot be accessed by the system");
                    }
                    break;
                case 3: {
                    List<Data> files = null;
                    try {
                        files = dataDAO.getAllFile(this.email);
                        System.out.println("Id- File Name");
                        for (Data fi : files) {
                            System.out.print(fi.getId() + " - " + fi.getFileName());
                        }
                        System.out.print("\n Please enter the id of the file you want to unhide");
                        int id = Integer.parseInt(sc.nextLine());
                        boolean isValidId = false;
                        for (Data d : files) {
                            if (d.getId() == id) {
                                isValidId = true;
                                break;
                            }
                        }
                        if (isValidId) {
                            dataDAO.unhide(id);
                        } else {
                            System.out.println("Invalid ID!");
                        }
                    } catch (SQLException e) {
                        System.err.println("Database error occurred while trying to get user's file list");
                        e.printStackTrace();
                    } catch (IOException e) {
                        System.out.println("An IO exception has occurred.");
                        e.printStackTrace();
                    }
                }
                    break;
                case 0: {
                    System.exit(0);
                }
            }
        } while (true);
    }
}