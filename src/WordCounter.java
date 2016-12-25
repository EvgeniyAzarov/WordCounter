import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class WordCounter {
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.print("Введите полный или относительный путь к файлу: ");

        String filename = in.nextLine();

        long start = System.currentTimeMillis();

        String[] allWords = readToString(new File(filename)).split(" ");

        String del = "[\"',\\.;`:!?\\(\\)<>\\-_]";

        for (int i = 0; i < allWords.length; i++) {
            allWords[i] = allWords[i].replaceAll(del, "");
            if (allWords[i].length() == 1) allWords[i] = "";
        }

        Arrays.sort(allWords);

        int emptyStringNumber = 0;
        for (int i = 0; i < allWords.length; i++) {
            if (allWords[i].length() == 0) continue;

            emptyStringNumber = i;
            break;
        }

        String[] allWordsOptimise = new String[allWords.length - emptyStringNumber];

        for (int i = emptyStringNumber; i < allWords.length; i++) {
            allWordsOptimise[i - emptyStringNumber] = allWords[i];
        }

        String[] words = new String[10];
        int[] wordsCount = new int[words.length];

        for (int i = 0; i < allWordsOptimise.length; ) {
            String word = allWordsOptimise[i];

            if (word.length() == 1 || word.length() == 0) continue;

            int count = 0;
            while (allWordsOptimise[i].equals(word)) {
                count++;
                i++;
                if (i == allWordsOptimise.length) break;
            }

            quickSort(wordsCount, words, 0, wordsCount.length - 1);

            if (count > wordsCount[0]) {
                wordsCount[0] = count;
                words[0] = word;
            }
        }

        quickSort(wordsCount, words, 0, wordsCount.length - 1);

        long end = System.currentTimeMillis();

        System.out.println("Общее количество слов в файле: " + allWordsOptimise.length);

        System.out.println("Время работы программы: " + (end - start) + "ms");

        for (int i = words.length - 1; i >= 0; i--) {
            String word = words[i];

            if (word != null) {
                System.out.println(word + " - " + wordsCount[i]);
            }
        }
    }

    private static String readToString(File file) {
        BufferedReader inStream = null;

        try {
            inStream = new BufferedReader(new FileReader(file.getName()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        StringBuilder text = new StringBuilder();
        String line;

        try {
            while ((line = inStream.readLine()) != null) {
                if (!line.equals(""))
                    text.append(line.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text.toString();
    }

    private static void quickSort(int[] array, String[] strings, int start, int end) {
        if (start >= end) return;

        int i = start, j = end;
        int cur = i - (i - j) / 2;

        while (i < j) {
            while ((array[i] <= array[cur]) && i < cur) {
                i++;
            }
            while ((array[j] >= array[cur]) && j > cur) {
                j--;
            }
            if (i < j) {
                swap(array, strings, i, j);

                if (i == cur) {
                    cur = j;
                } else if (j == cur) {
                    cur = i;
                }
            }
        }
        quickSort(array, strings, start, cur);
        quickSort(array, strings, cur + 1, end);
    }

    private static void swap(int[] array, String[] strings, int i, int j) {
        int intBuff = array[i];
        array[i] = array[j];
        array[j] = intBuff;

        String stringBuff = strings[i];
        strings[i] = strings[j];
        strings[j] = stringBuff;
    }
}
