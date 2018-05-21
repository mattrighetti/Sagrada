package ingsw.view;

import java.util.Scanner;

public class LaunchCLI {
    public static void main(String[] args) {
        CLI cli = new CLI(new Scanner(System.in));
        cli.startCLI();
    }
}
