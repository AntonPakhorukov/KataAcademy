import java.util.*;

public class Main {
    private static final Scanner SCAN = new Scanner(System.in);
    private static final List<String> ROMAN = new ArrayList<>(List.of("I", "IV", "V", "IX", "X", "XL", "L", "XC", "C"));
    private static final List<Integer> ARABIC = new ArrayList<>(List.of(1, 4, 5, 9, 10, 40, 50, 90, 100));

    public static void main(String[] args) throws Exception {
        System.out.println("Введите данные для расчета:");
        String input = SCAN.nextLine();
        System.out.println("Результат операции: " + calc(input));
    }

    public static String calc(String input) throws Exception {
        List<String> userInput = List.of(input.split(" "));
        String result = null;
        if (Character.isDigit(userInput.get(0).charAt(0))) {
            checkLength(userInput);
            if (!Character.isDigit(userInput.get(2).charAt(0))) {
                throw new Exception("используются одновременно разные системы счисления");
            } else {
                checkDigit(Integer.parseInt(userInput.get(0)));
                checkDigit(Integer.parseInt(userInput.get(2)));
                result = arabDigit(userInput);
            }
        } else {
            checkLength(userInput);
            if (Character.isDigit(userInput.get(2).charAt(0))) {
                throw new Exception("используются одновременно разные системы счисления");
            } else {
                List<String> toArab = romeToArab(userInput);
                String resultArab = arabDigit(toArab);
                if (Integer.parseInt(resultArab) < 1) {
                    throw new Exception("в римской системе нет отрицательных чисел");
                }
                result = arabToRome(resultArab);
            }
        }
        return result;
    }

    public static String arabToRome(String resultArab) {
        int num = Integer.parseInt(resultArab);
        StringBuilder sb = new StringBuilder();
        int i = ROMAN.size() - 1;
        while (num > 0) {
            if (num - ARABIC.get(i) >= 0) {
                sb.append(ROMAN.get(i));
                num -= ARABIC.get(i);
            } else {
                i--;
            }
        }
        return sb.toString();
    }

    public static List<String> romeToArab(List<String> input) throws Exception {
        Map<String, Integer> change = new HashMap<>(Map.of("I", 1, "II", 2, "III", 3, "IV", 4, "V", 5, "VI", 6, "VII", 7, "VIII", 8, "IX", 9, "X", 10));
        if (!change.containsKey(input.get(0)) || !change.containsKey(input.get(2))) {
            throw new Exception("не корректные данные, по условиям нельзя использовать один из операндов");
        }
        return new ArrayList<>(List.of(change.get(input.get(0)).toString(), input.get(1), change.get(input.get(2)).toString()));
    }

    public static void checkLength(List<String> input) throws Exception {
        if (input.size() > 3) {
            throw new Exception("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        } else if (input.size() < 3) {
            throw new Exception("строка не является математической операцией");
        }
    }

    public static void checkDigit(int num) throws Exception {
        if (num < 1 || num > 10) {
            throw new Exception("не корректные данные, по условиям нельзя использовать " + num);
        }
    }

    public static String arabDigit(List<String> input) {
        String result = null;
        switch (input.get(1)) {
            case "+":
                result = String.valueOf(Integer.parseInt(input.get(0)) + Integer.parseInt(input.get(2)));
                break;
            case "-":
                result = String.valueOf(Integer.parseInt(input.get(0)) - Integer.parseInt(input.get(2)));
                break;
            case "*":
                result = String.valueOf(Integer.parseInt(input.get(0)) * Integer.parseInt(input.get(2)));
                break;
            case "/":
                result = String.valueOf(Integer.parseInt(input.get(0)) / Integer.parseInt(input.get(2)));
                break;
        }
        return result;
    }
}
