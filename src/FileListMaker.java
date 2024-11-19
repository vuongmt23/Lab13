import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileListMaker {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<String> list = new ArrayList<>();
    private static boolean needsToBeSaved = false;
    private static String currentFileName = null;

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            displayMenu();
            String choice = scanner.nextLine().trim().toUpperCase();
            switch (choice) {
                case "A":
                    addItem();
                    break;
                case "D":
                    deleteItem();
                    break;
                case "I":
                    insertItem();
                    break;
                case "V":
                    viewList();
                    break;
                case "M":
                    moveItem();
                    break;
                case "O":
                    openFile();
                    break;
                case "S":
                    saveFile();
                    break;
                case "C":
                    clearList();
                    break;
                case "Q":
                    running = quitProgram();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("A - Add an item");
        System.out.println("D - Delete an item");
        System.out.println("I - Insert an item");
        System.out.println("V - View the list");
        System.out.println("M - Move an item");
        System.out.println("O - Open a list file");
        System.out.println("S - Save the current list");
        System.out.println("C - Clear the list");
        System.out.println("Q - Quit");
        System.out.print("Enter your choice: ");
    }

    private static void addItem() {
        System.out.print("Enter the item to add: ");
        String item = scanner.nextLine();
        list.add(item);
        needsToBeSaved = true;
        System.out.println("Item added.");
    }

    private static void deleteItem() {
        viewList();
        System.out.print("Enter the index of the item to delete: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index >= 0 && index < list.size()) {
            list.remove(index);
            needsToBeSaved = true;
            System.out.println("Item deleted.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void insertItem() {
        System.out.print("Enter the index to insert at: ");
        int index = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the item to insert: ");
        String item = scanner.nextLine();
        if (index >= 0 && index <= list.size()) {
            list.add(index, item);
            needsToBeSaved = true;
            System.out.println("Item inserted.");
        } else {
            System.out.println("Invalid index.");
        }
    }

    private static void viewList() {
        System.out.println("\nCurrent List:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + ": " + list.get(i));
        }
    }

    private static void moveItem() {
        viewList();
        System.out.print("Enter the index of the item to move: ");
        int fromIndex = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter the index to move the item to: ");
        int toIndex = Integer.parseInt(scanner.nextLine());
        if (fromIndex >= 0 && fromIndex < list.size() && toIndex >= 0 && toIndex <= list.size()) {
            String item = list.remove(fromIndex);
            list.add(toIndex, item);
            needsToBeSaved = true;
            System.out.println("Item moved.");
        } else {
            System.out.println("Invalid indices.");
        }
    }

    private static void openFile() {
        if (needsToBeSaved) {
            promptSave();
        }
        System.out.print("Enter the filename to open: ");
        currentFileName = scanner.nextLine();
        try {
            list = Files.readAllLines(Paths.get(currentFileName));
            needsToBeSaved = false;
            System.out.println("File loaded.");
        } catch (IOException e) {
            System.out.println("Error loading file: " + e.getMessage());
        }
    }

    private static void saveFile() {
        if (currentFileName == null) {
            System.out.print("Enter the filename to save as: ");
            currentFileName = scanner.nextLine();
        }
        try {
            Files.write(Paths.get(currentFileName), list);
            needsToBeSaved = false;
            System.out.println("File saved.");
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static void clearList() {
        list.clear();
        needsToBeSaved = true;
        System.out.println("List cleared.");
    }

    private static boolean quitProgram() {
        if (needsToBeSaved) {
            promptSave();
        }
        System.out.println("Goodbye!");
        return false;
    }

    private static void promptSave() {
        System.out.print("You have unsaved changes. Save now? (Y/N): ");
        String choice = scanner.nextLine().trim().toUpperCase();
        if ("Y".equals(choice)) {
            saveFile();
        }
    }
}
