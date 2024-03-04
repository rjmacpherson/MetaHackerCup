package _2022.A1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 *  Consecutive Cuts - Chapter 1
 *  There are N cards per test case
 *  They display integers between 1 to N
 *  Cutting the deck reshuffles order
 *  A is the initial order, B is the desired order
 *  We can make K cuts to reach B
 *  For each case work out if this is achievable
 *  <p>
 *  Key Parameters
 *   T - number of test cases
 *   N - number of cards
 *   K - times to cut the deck
 */
public class A1 {
    public static void main(String[] args) {
        List<TestCase> testCases = readFileInput();
        List<Boolean> results = processTestCases(testCases);
        writeResults(results);
    }

    private static List<TestCase> readFileInput() {
        // TODO: Update here to change input file
        String inputFile = "src/main/java/_2022/A1/A1Input.txt";
        Path inputPath = Paths.get(inputFile);
        List<TestCase> testCases = new ArrayList<>();
        try(BufferedReader reader = Files.newBufferedReader(inputPath)) {
            int T = Integer.parseInt(reader.readLine());
            for (int i = 0; i < T; i++) {
                String[] NandK = reader.readLine().split(" ");
                int N = Integer.parseInt(NandK[0]);
                int K = Integer.parseInt(NandK[1]);
                ArrayList<Integer> A = Arrays.stream(reader.readLine().split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toCollection(ArrayList::new));
                ArrayList<Integer> B = Arrays.stream(reader.readLine().split(" "))
                        .map(Integer::parseInt)
                        .collect(Collectors.toCollection(ArrayList::new));
                TestCase testCase = new TestCase(N, K, A, B);
                testCases.add(testCase);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return testCases;
    }

    private static List<TestCase> readConsoleInput() {
        Scanner scan = new Scanner(System.in);
        int T = scan.nextInt(); // Number of Test Cases
        List<TestCase> testCases = new ArrayList<>();
        for(int i = 0; i < T; i++) {
            int N = scan.nextInt();
            int K = scan.nextInt();
            List<Integer> A = new ArrayList<>();
            for(int j = 0; j < N; j++) {
                A.add(scan.nextInt());
            }
            List<Integer> B = new ArrayList<>();
            for(int j = 0; j < N; j++) {
                B.add(scan.nextInt());
            }
            TestCase testCase = new TestCase(N, K, A, B);
            testCases.add(testCase);
        }
        scan.close();
        return testCases;
    }

    private static List<Boolean> processTestCases(List<TestCase> testCases) {
        List<Boolean> results = new ArrayList<>();
        for (TestCase testCase : testCases) {
            boolean result = processTestCase(testCase);
            results.add(result);
        }
        return results;
    }

    private static boolean processTestCase(TestCase testCase) {
        int K = testCase.getK();
        List<Integer> initialOrder = testCase.getA();
        List<Integer> desiredOrder = testCase.getB();
        int N = initialOrder.size();
        // If you cannot cut, then must be equal to begin
        if(K == 0) {
            return initialOrder.equals(desiredOrder);
        }
        // If only single cut, must arrange at least once so cannot solve if equal
        if(K == 1 && initialOrder.equals(desiredOrder)) {
            return false;
        }
        // If only 2 cards
        if(N == 2) {
            // If they are ordered, must cut even times
            if(initialOrder.equals(desiredOrder) && K % 2 == 0) {
                return true;
            }
            // If they are not ordered, must cut odd times
            if (!initialOrder.equals(desiredOrder) && K % 2 == 1) {
                return true;
            }
        return false;
        }
        int cutPosition = desiredOrder.indexOf(initialOrder.getFirst());
        List<Integer> head = desiredOrder.subList(0, cutPosition);
        List<Integer> tail = desiredOrder.subList(cutPosition, desiredOrder.size());
        List<Integer> cutOrder = new ArrayList<>(tail);
        cutOrder.addAll(head);
        return initialOrder.equals(cutOrder);
    }

    private static void writeResults(List<Boolean> results) {
        try {
            String resultsFile = "src/main/java/_2022/A1/A1results.txt";
            PrintWriter writer = new PrintWriter(resultsFile, StandardCharsets.UTF_8);
            for (int i = 0; i < results.size(); i++) {
                String result = results.get(i) ? "YES" : "NO";
                writer.println("Case #" + (i + 1) + ": " + result);
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class TestCase {
        private final int N; // Number of Cards
        private final int K; // Number of Cuts
        private final List<Integer> A;
        private final List<Integer> B;

        public TestCase(int n, int k, List<Integer> a, List<Integer> b) {
            N = n;
            K = k;
            A = a;
            B = b;
        }

        public int getN() {
            return N;
        }

        public int getK() {
            return K;
        }

        public List<Integer> getA() {
            return A;
        }

        public List<Integer> getB() {
            return B;
        }

        @Override
        public String toString() {
            return "N:" + N +
                    "\nK:" + K +
                    "\nA:" + A +
                    "\nB:" + B +
                    "\n";
        }
    }
}
