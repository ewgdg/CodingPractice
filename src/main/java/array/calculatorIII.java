package array;

import java.util.Stack;

public class calculatorIII {
    // add prio 0
    // mul priority 1
    // bracket priority +2
    public static double cal(double a, double b, char op) {
        if (op == '+')
            return a + b;
        else if (op == '-')
            return a - b;
        else if (op == '*')
            return a * b;
        else if (op == '/')
            return a / b;
        else
            return a + b;
    }

    public static double solution(String expr) {
        int i = 0;
        Stack<Double> nums = new Stack<>();
        Stack<Integer> prios = new Stack<>();
        Stack<Character> ops = new Stack<>();

        int n = expr.length();
        int prio = 0;
        while (i < n) {
            char c = expr.charAt(i);
            if (Character.isDigit(c)) {
                double num = 0;
                while (i < n && Character.isDigit((c = expr.charAt(i)))) {
                    num = num * 10 + c - '0';
                    i++;
                }
                nums.push(num);
                i--;// in case i>=n
            }

            else if (c == '(')
                prio += 2;
            else if (c == ')')
                prio -= 2;

            else if (c == '+' || c == '-' || c == '*' || c == '/') {// map.containskey(c)
                int curP = prio + ((c == '*' || c == '/') ? 1 : 0);
                while (!prios.isEmpty() && prios.peek() >= curP) {
                    prios.pop();
                    char op = ops.pop();
                    double b = nums.pop();
                    double a = nums.pop();

                    nums.push(cal(a, b, op));

                }
                prios.push(prio);
                ops.push(c);

            }
            i++;

        }
        while (!prios.isEmpty()) {
            prios.pop();
            char op = ops.pop();
            double b = nums.pop();
            double a = nums.pop();

            nums.push(cal(a, b, op));

        }
        return nums.pop();
    }

    public static void main(String[] args) {
        System.out.println(solution("2+(2+3)*2/3"));
    }

}
