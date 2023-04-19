package Classes;

import java.util.Random;

import java.io.IOException;

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


    public static void randomQueue36Cards(SimpleLinkedList<Integer> koloda) throws SimpleLinkedList.SimpleLinkedListException {
        //метод для рандомного заполнения листа 36 картами
        // вида 1 1 1 1 2 2 2 2 3 3 3 3
        // там же по 4 карты одинаковые
        Random rand = new Random();
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= 4; j++) {
                koloda.addLast(i);
            }
        }
        for (int i = 0; i < 100; i++) { // 100 iterations to ensure no duplicates
            int index1 = rand.nextInt(koloda.count());
            int index2 = rand.nextInt(koloda.count());
            int temp = koloda.get(index1);
            koloda.set(index1, koloda.get(index2));
            koloda.set(index2, temp);
        }
    }

    public static String nameOfTheCards(int nomber) { // переводит цифры от 1 до 36 в карты (стр)
        switch (nomber) {
            case 1:
                return "Шестерка";
            case 2:
                return "Семёрка";
            case 3:
                return "Восьмёрка";
            case 4:
                return "Девятка";
            case 5:
                return "Десятка";
            case 6:
                return "Валет";
            case 7:
                return "Дама";
            case 8:
                return "Король";
            case 9:
                return "Туз";
            default:
                return "Некорректное значение";
        }
    }
}
