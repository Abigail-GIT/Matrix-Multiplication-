import java.io.*;
import java.util.*;
public class Matrix {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] inputs;
        if (args.length > 0) {
            inputs = args;
        } 
        else {
            System.out.println("Input two files to be read as arrays, or input an integer to indicate the new matrix size, which will generate two random matrices to be multiplied.");
            inputs = scanner.nextLine().split(" ");
        }
        if (inputs.length == 2) {
            multiplyFromFiles(inputs[0], inputs[1]);
        } 
        else {
            try {
                int size = Integer.parseInt(inputs[0]);
                generateAndMultiply(size);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
            }
        }
        scanner.close();
    }
    private static void multiplyFromFiles(String file1, String file2) {
        int[][] matrix1 = readMatrix(file1);
        int[][] matrix2 = readMatrix(file2);

        if (matrix1 != null && matrix2 != null && matrix1[0].length == matrix2.length) {
            int[][] result = multiplyMatrices(matrix1, matrix2);
            writeMatrix(result, "matrix3.txt");
            System.out.println("Successfully saved as matrix3.txt");
        } 
        else {
            System.out.println("Invalid matrices");
        }
    }
    private static void generateAndMultiply(int size) {
        int[][] matrix1 = generateRandomMatrix(size);
        int[][] matrix2 = generateRandomMatrix(size);

        writeMatrix(matrix1, "matrix1.txt");
        writeMatrix(matrix2, "matrix2.txt");
        System.out.println("Successfully saved as matrix.txt");

        int[][] result = multiplyMatrices(matrix1, matrix2);
        writeMatrix(result, "matrix3.txt");
        System.out.println("Result saved to matrix3.txt");
    }
    private static int[][] readMatrix(String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            List<int[]> matrixList = new ArrayList<>();
            while (scanner.hasNextLine()) {
                matrixList.add(Arrays.stream(scanner.nextLine().trim().split("\\s+"))
                        .mapToInt(Integer::parseInt).toArray());
            }
            return matrixList.toArray(new int[0][]);
        } catch (IOException e) {
            System.out.println("Invalid file");
            return null;
        }
    }
    private static void writeMatrix(int[][] matrix, String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (int[] row : matrix) {
                writer.println(Arrays.toString(row).replaceAll("[\\[\\],]", ""));
            }
        } catch (IOException e) {
            System.out.println("Invalid file");
        }
    }
    private static int[][] generateRandomMatrix(int size) {
        Random rand = new Random();
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) 
            for (int j = 0; j < size; j++) 
                matrix[i][j] = rand.nextInt(10);
        return matrix;
    }
    private static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        int rows = matrix1.length, cols = matrix2[0].length, common = matrix1[0].length;
        int[][] result = new int[rows][cols];

        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                for (int k = 0; k < common; k++)
                    result[i][j] += matrix1[i][k] * matrix2[k][j];

        return result;
    }
}
