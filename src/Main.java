import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static String[][] table;
    private static int count;

    static int checkInput(String x) throws IllegalInput {
        boolean is_string = false;
        int factor = 1;
        for (int i = 0; i < x.length(); i++) {
            if (i == 0 && x.charAt(0) == '-') {
                x = x.substring(1);
                factor = -1;
            } else if (!Character.isDigit(x.charAt(i))) {
                is_string = true;
                break;
            }
        }
        if (is_string) {
            throw new IllegalInput("invalid input value!(input must be digit)");
        } else
            return factor * Integer.parseInt(x);
    }

    static int check_input_range(String input) throws IllegalInput, IllegalOutOfBound {
        int inp = checkInput(input);
        if (inp > 9 || inp < 1) {
            throw new IllegalOutOfBound("input is out out of range!(1,9)");
        } else
            return inp;
    }

    static void check_input_exist(int i, int j, int input) throws ExistNumberInRow, ExistNumberInColumn, ExistNumberInSquare {
        for (int m = 0; m < table.length; m++) {
            if (m == j)
                continue;
            if (input == Integer.parseInt(table[i][m])) {
                throw new ExistNumberInRow("already input has existed!(in row)");
            }
        }
        for (int k = 0; k < table.length; k++) {
            if (k == i)
                continue;
            if (input == Integer.parseInt(table[k][j])) {
                throw new ExistNumberInColumn("already input has existed!(in column)");
            }
        }
        int x_index = 0, y_index = 0, x_lim = 0, y_lim = 0;

        if (i >= 0 && i <= 2) {
            x_index = 0;
            x_lim = 2;
        } else if (i >= 3 && i <= 5) {
            x_index = 3;
            x_lim = 5;
        } else if (i >= 6 && i <= 8) {
            x_index = 6;
            x_lim = 8;
        }

        if (j >= 0 && j <= 2) {
            y_index = 0;
            y_lim = 2;
        } else if (j >= 3 && j <= 5) {
            y_index = 3;
            y_lim = 5;
        } else if (j >= 6 && j <= 8) {
            y_index = 6;
            y_lim = 8;
        }

        for (int k = x_index; k <= x_lim; k++) {
            for (int l = y_index; l <= y_lim; l++) {
                if (Integer.parseInt(table[k][l]) == input) {
                    throw new ExistNumberInSquare("already input has existed!(in square)");
                }
            }
        }

    }

    public static void main(String[] args) {
        table = new String[9][9];
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < table.length; i++) {
            Arrays.fill(table[i], "0");
        }
        boolean is_running = true;
        while (is_running) {
            System.out.println("1)showChart 2)put number");
            int input = scanner.nextInt();
            switch (input) {
                case 1:
                    showChart(table);
                    break;
                case 2:
                    try {
                        System.out.print("line: ");
                        int i = check_input_range(scanner.next()) - 1;
                        System.out.print("column: ");
                        int j = check_input_range(scanner.next()) - 1;
                        System.out.print("value: ");
                        int val = check_input_range(scanner.next());
                        check_input_exist(i, j, val);
                        numbering(i, j, val);
                        if (count == 81) {
                            System.out.println("game was finished");
                            is_running = false;
                        }
                    } catch (IllegalInput | IllegalOutOfBound | ExistNumberInColumn | ExistNumberInRow | ExistNumberInSquare ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                default:
                    System.out.println("invalid input!");
            }
        }
    }

    private static void numbering(int i, int j, int value) {
        table[i][j] = String.valueOf(value);
        count++;
    }

    private static void showChart(String[][] table) {
        System.out.print("   ");
        for (int i = 1; i <= table.length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.print("   ");
        for (int i = 1; i <= table.length; i++) {
            System.out.print("- ");
        }
        System.out.println();
        for (int i = 0; i < table.length; i++) {
            System.out.print((i + 1) + "| ");
            for (int j = 0; j < table[i].length; j++) {
                System.out.print(table[i][j] + " ");
            }
            System.out.println();
        }
    }
}

class IllegalOutOfBound extends Exception {
    IllegalOutOfBound(String s) {
        super(s);
    }
}

class IllegalInput extends Exception {
    public IllegalInput(String s) {
        super(s);
    }
}

class ExistNumberInRow extends Exception {
    ExistNumberInRow(String s) {
        super(s);
    }
}

class ExistNumberInColumn extends Exception {
    ExistNumberInColumn(String s) {
        super(s);
    }
}

class ExistNumberInSquare extends Exception {
    ExistNumberInSquare(String s) {
        super(s);
    }
}