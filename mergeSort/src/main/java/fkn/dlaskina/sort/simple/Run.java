package fkn.dlaskina.sort.simple;

import java.util.*;
import java.io.*;

/**
 * Created by Laskina Daria on 23.04.2016.
 *
 */
public class Run {

    public static final String CHARSET = "UTF-8";
    public static final String TEMP_DIR = "C:\\Prj\\mergeSort\\temp\\";

    public static String SORT_RULES;
    public static final Map<Character, Integer> SORT_MAP = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {
        createSortMap();
        Writer writer1 = null;
        try {
            writer1 = new OutputStreamWriter(new FileOutputStream("time20.txt", false), CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        long  total=0;
        for (int i = 0; i<11;i++) {
            long startTime = System.currentTimeMillis();
            final Run run = new Run();
            try {
                run.run(args[0], args[1]);
            }catch (final ArrayIndexOutOfBoundsException e)
            {
                System.out.println("Oh, no! Incorrect number of parameters!");
            }
            long timeSpent = System.currentTimeMillis() - startTime;
            try {

                writer1.append(Long.toString(timeSpent)+" ");
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println(timeSpent);
            total+=timeSpent;
        }
        System.out.println("программа выполнялась в среднем " + total/11 + " миллисекунд");
        try {
            writer1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createSortMap() {
        try(
            final InputStream in = Run.class.getClassLoader().getResourceAsStream("SORT_RULES.txt");
            final BufferedReader reader = new BufferedReader(new InputStreamReader(in, CHARSET));
        ) {
            String line = reader.readLine();
            if (line != null) {
                SORT_RULES = line;
            }
        } catch (IOException e){
            System.out.println("Incorrect resource name");
        }

        //final String SORT_RULE =
        for (int i = 0; i < SORT_RULES.length(); i++) {
            SORT_MAP.put(SORT_RULES.charAt(i), i);
        }
    }

    public final Comparator<Map.Entry<Character, Count>> comparator = new Comparator<Map.Entry<Character, Count>>() {
        @Override
        public int compare(Map.Entry<Character, Count> o1, Map.Entry<Character, Count> o2) {
            final Integer ind1 = SORT_MAP.get(o1.getKey());
            final Integer ind2 = SORT_MAP.get(o2.getKey());
            if (ind1 != null) if (ind2 != null) return ind1 - ind2;
            else return -1;
            else if (ind2 != null) return 1;
            else return o1.getKey().compareTo(o2.getKey());
        }
    };

    /**
     * Программа читает из файла input текст и сортирует его в порядке:
      1. Знаки в порядке ASCII кода
      2. Цифры в порядке возрастания значения целого числа ( от 48 до 57)
      3. Буквы русского алфавита (сначала строчные, потом прописные)(192 до 223; от 224 до 255) - не работает
      4. Буквы латинского алфавита (аналогично)

     * @param input  - имя входного файла
     * @param output - имя выходного файла
     */
    private void run(String input, String output) {
        final Map<Character, Count> map = readInputFile(input);

        final List<Map.Entry<Character, Count>> sortList = new ArrayList<>(map.entrySet());
        Collections.sort(sortList, comparator); // сортировка тут!!!
        writeResult(output, sortList);

    }

    /**
     * Читает входной файл и записываем статистику количества символов в map
     * @param input имя файла
     * @return выходной map со статистикой
     */
    private Map<Character, Count> readInputFile(String input) {
        final Map<Character, Count> map = new HashMap<>();

        /* ВРЕМЯ ДЛЯ ЧТЕНИЯ ФАЙЛА */
        BufferedReader reader;
        try {
            //reader = new BufferedReader(new FileReader(new File("C:\\project\\VV_lab_2\\big_1000.txt")));
            for (int j = 0; j < 10; j++){
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(input)), CHARSET));
                String line;
                while ((line = reader.readLine()) != null) {
                    for (int i = 0; i < line.length(); i++) {
                        final char chr = line.charAt(i);
                        final Count count = map.get(chr);

                        if (count == null) {
                            map.put(chr, new Count());
                        } else {
                            count.increment();
                        }
                    }
                }

                reader.close();
            }

        } catch (IOException e) {
            System.out.println("Oh, no! Incorrect input file name!");
        }
        return map;
    }

    /**
     *
     * @param output - имя выходного файл
     * @param sortList  - отсортированный map со статистикой
     */
    private void writeResult(String output, List<Map.Entry<Character, Count>> sortList) {
        Writer writer = null;
        try  {
            writer = new OutputStreamWriter(new FileOutputStream(output, false), CHARSET);
            for (final Map.Entry<Character, Count> entry : sortList) {
                for (long i = 0; i < entry.getValue().getValue(); i++) {
                    writer.append(entry.getKey());
                }

            }

        } catch (IOException ex) {
            System.out.println("Oh, no!Incorrect output file name!");
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}