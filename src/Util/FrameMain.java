package Util;

import Classes.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;

//import static Classes.ClassesForInAndOut.getString;
import static Classes.MainLogic.*;


public class FrameMain extends JFrame {
    private JButton readFileButton;
    private JButton writeFileBtn;


    private JPanel panelMain;
    private JTextArea textArea2Player;
    private JTextArea textArea1Player;
    private JButton buttonMove;
    private JTextArea textAreaResult;
    private JButton randomButton;
    private JButton restartButton;


    static InputArgs inputArgs = new InputArgs();

    public static void runTest() throws IOException {
        Tests test = new Tests();
        //первый тест
        String[] pathsTest1 = {test.testPath1In, test.testPath1Out};
        runSolutionTest(pathsTest1, 1);

        //второй тест
        String[] pathsTest2 = {test.testPath2In, test.testPath2Out};
        runSolutionTest(pathsTest2, 2);

        //третий тест
        String[] pathsTest3 = {test.testPath3In, test.testPath3Out};
        runSolutionTest(pathsTest3, 3);

        //четвёртый тест
        String[] pathsTest4 = {test.testPath4In, test.testPath4Out};
        runSolutionTest(pathsTest4, 4);

        //пятый тест
        String[] pathsTest5 = {test.testPath5In, test.testPath5Out};
        runSolutionTest(pathsTest5, 5);
    }

    public static void runSolutionTest(String[] pathArgs, int num) throws IOException {
        inputArgs.setInputFile(pathArgs[0]);
        inputArgs.setOutputFile(pathArgs[1]);
        readAndWriteMethod(inputArgs);
        printSuccessMessage(num);
    }

    public FrameMain() throws IOException {

        runTest();

        this.setTitle("Основная программа");
        this.setContentPane(panelMain);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();

        JFileChooser fileChooserOpen;
        JFileChooser fileChooserSave;

        fileChooserOpen = new JFileChooser();
        fileChooserSave = new JFileChooser();
        fileChooserOpen.setCurrentDirectory(new File("."));
        fileChooserSave.setCurrentDirectory(new File("."));
        FileFilter filter = new FileNameExtensionFilter("Text files", "txt");
        fileChooserOpen.addChoosableFileFilter(filter);
        fileChooserSave.addChoosableFileFilter(filter);

        fileChooserSave.setAcceptAllFileFilterUsed(false);
        fileChooserSave.setDialogType(JFileChooser.SAVE_DIALOG);
        fileChooserSave.setApproveButtonText("Save");


        SimpleQueue<Integer> koloda1Player = new SimpleLinkedList<>();
        SimpleQueue<Integer> koloda2Player = new SimpleLinkedList<>();

        JFileChooser finalFileChooserOpen = fileChooserOpen;

        readFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    if (finalFileChooserOpen.showOpenDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                        SimpleLinkedList<Integer> ans = ClassesForInAndOut.readFile(finalFileChooserOpen.getSelectedFile().getPath());
                        for (int i = 0; i < 36; i++) {
                            if (i < 18) {
                                koloda1Player.addLast(ans.removeFirst());//в очередь тут совать
                            } else {
                                koloda2Player.addLast(ans.removeFirst());//в очередь тут совать
                            }
                        }
                    }
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });

        randomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    SimpleLinkedList<Integer> list = new SimpleLinkedList<>();
                    SimpleLinkedList<Integer> list2 = new SimpleLinkedList<>();
                    MainLogic.randomQueue(list); // в наш list положили рандомные картишки
                    MainLogic.randomQueue(list2); // в наш list2 положили рандомные картишки

                    //итератором тут
                } catch (Exception e) {
                    SwingUtils.showErrorMessageBox(e);
                }
            }
        });


        JFileChooser finalFileChooserSave = fileChooserOpen;
        buttonMove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!koloda1Player.empty() && !koloda2Player.empty()) {

                        int karta1 = koloda1Player.removeFirst();
                        int karta2 = koloda2Player.removeFirst();

                        textArea1Player.setText(String.valueOf(karta1));
                        textArea2Player.setText(String.valueOf(karta2));

                        if (karta1 > karta2) {
                            textAreaResult.setText("Раунд выиграл игрок 1");
                            koloda1Player.addLast(karta2);
                            koloda1Player.addLast(karta1);
                        } else if (karta1 < karta2) {
                            textAreaResult.setText("Раунд выиграл игрок 2");
                            koloda2Player.addLast(karta1);
                            koloda2Player.addLast(karta2);
                        } else {
                            while (karta1 == karta2) {
                                if (!koloda1Player.empty()) {
                                    karta1 = koloda1Player.removeFirst();
                                }
                                if (!koloda2Player.empty()) {
                                    karta2 = koloda2Player.removeFirst();
                                }

                                textArea1Player.append(" " + String.valueOf(karta1));
                                textArea2Player.append(" " + String.valueOf(karta2));
                            }
                            if (!koloda1Player.empty() && !koloda2Player.empty()) {
                                if (karta1 > karta2) {
                                    textAreaResult.setText("Раунд выиграл игрок 1");
                                    koloda1Player.addLast(karta2);
                                    koloda1Player.addLast(karta1);
                                } else if (karta1 < karta2) {
                                    textAreaResult.setText("Раунд выиграл игрок 2");
                                    koloda2Player.addLast(karta1);
                                    koloda2Player.addLast(karta2);
                                }
                            }
                        }
                    }
                    if (koloda1Player.empty()) {
                        textAreaResult.setText("Игру выиграл игрок 2");
                    } else if (koloda2Player.empty()) {
                        textAreaResult.setText("Игру выиграл игрок 1");
                    }



                    /*if (finalFileChooserSave.showSaveDialog(panelMain) == JFileChooser.APPROVE_OPTION) {
                        String str = textArea1Player.getText();
                        String[] strNew = str.split(" ");
                        SimpleLinkedList<Integer> ans = new SimpleLinkedList<>();
                        for (int i = strNew.length - 1;i >= 0; i--){
                            ans.addFirst(Integer.parseInt(strNew[i]));
                        }
                        ans.getAnswer();
                        textArea3.setText(massivVStroki(ans));
                        textField1.setText("Ответ:");
                        String path = finalFileChooserSave.getSelectedFile().getPath();
                        File file = new File(path);
                        PrintWriter pw = new PrintWriter(file);
                        pw.println(textArea3.getText());
                        pw.close();
                    }

                     */
                } catch (Exception ex) {
                    SwingUtils.showErrorMessageBox(ex);
                }
            }
        });
        textArea1Player.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
            }
        });

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}