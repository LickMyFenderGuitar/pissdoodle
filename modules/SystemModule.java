package modules;

public class SystemModule {
    public void write(String message) {
        System.out.println(message);
    }

    public void input(String prompt) {
        System.out.print(prompt);
        try (java.util.Scanner scanner = new java.util.Scanner(System.in)) {
            scanner.nextLine();
        }
    }

    public void exit() {
        System.exit(0);
    }
}
