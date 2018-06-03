package ingsw.view;

public class LaunchCLI {
    public static void main(String[] args) {
        CLI cli = new CLI(args[0]);
        cli.startCLI();
    }
}
