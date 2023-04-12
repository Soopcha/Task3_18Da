package Classes;

import java.util.LinkedList;
import java.util.Random;

import java.io.IOException;
import java.util.regex.Pattern;

public class MainLogic {
    //public static int[] getAnswer(int[] arr){
//    int n = arr.length;
//    for (int i = 1; i < n; ++i) {
//        int key = arr[i];
//        int j = i - 1;
//
//        while (j >= 0 && arr[j] > key) {
//            arr[j + 1] = arr[j];
//            j = j - 1;
//        }
//        arr[j + 1] = key;
//    }
//    return arr;
//}
    //    .\input.txt .\output.txt
    public static String massivVStroki(SimpleLinkedList<Integer> list) {
        StringBuilder answer = new StringBuilder();
        for (int mas : list) {
            answer.append(mas);
            answer.append(" ");
        }
        return answer.toString();
    }

    public static void readAndWriteMethod(InputArgs inputArgs) throws IOException {
        SimpleLinkedList<Integer> arr = ClassesForInAndOut.readFile(inputArgs.getInputFile()); //создали наш лист односвязанный
        arr.getAnswer();
        String answer = MainLogic.massivVStroki(arr);
        ClassesForInAndOut.writeFile(inputArgs.getOutputFile(), answer);
    }

    public static void printSuccessMessage(int num) {
        if (num == 0) {
            System.out.println("Основная программа выполнена.");
        } else {
            System.out.println("Тест " + num + " выполнен успешно.");
        }
        System.out.println();
    }


    public static void randomQueue(SimpleLinkedList<Integer> koloda) throws SimpleLinkedList.SimpleLinkedListException {
        Random rand = new Random();
        for (int i = 2; i <= 19; i++) {
            koloda.addLast(i);
        }
        for (int i = 0; i < 100; i++) { // 100 iterations to ensure no duplicates
            int index1 = rand.nextInt(koloda.count());
            int index2 = rand.nextInt(koloda.count());
            int temp = koloda.get(index1);
            koloda.set(index1, koloda.get(index2));
            koloda.set(index2, temp);
        }
    }


}
