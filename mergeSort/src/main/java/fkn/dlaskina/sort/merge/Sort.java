package fkn.dlaskina.sort.merge;

import java.io.*;
import java.util.*;

import static fkn.dlaskina.sort.comparators.SortMap.CHARSET;

/**
 * Definition of the Sort class
 * @author VLaskin
 * @since 02.05.2016.
 */
public class Sort {

    private final String inputFileName;
    private final String outputFileName;
    private final List<TempSortFile> chunks = new ArrayList<>();

    public Sort(
        String inputFileName,
        String outputFileName
    ) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
    }

    public void run() {
        createTempFiles();
        mergeTempFiles();
        deleteTempFiles();
    }

    /**
     * удаляем временные файлы.
     */
    private void deleteTempFiles() {
        final File[] tempFiles = Launch.TEMP_DIR.listFiles();
        if (tempFiles != null) {
            for (File file : tempFiles) {
                if (!file.delete()) {
                    System.out.println("Не могу удалить файл - " + file.getName());
                }
            }
        }
    }

    /**
     * Постепенное слияние всех временных файлов.
     */
    private void mergeTempFiles() {
        while (chunks.size() > 2) {
            final TempSortFile sf1 = chunks.remove(0);
            final TempSortFile sf2 = chunks.remove(0);
            final TempSortFile sf3 = TempSortFile.createTemp();
            merge(sf1, sf2, sf3);
            chunks.add(sf3);
        }
        final TempSortFile sf1 = chunks.remove(0);
        final TempSortFile sf2 = chunks.remove(0);
        final SortFile sf3 = SortFile.create(outputFileName);
        merge(sf1, sf2, sf3);
    }

    /**
     * Процедура слияния двух отсортированных файлов в третий
     * @param inp1 первый входной
     * @param inp2 второй входной
     * @param out выходной для слияния
     */
    private void merge(final SortFile inp1, final SortFile inp2, final SortFile out) {
        try (
            final BufferedReader reader1 = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(inp1.getFile()), CHARSET
                )
            );
            final BufferedReader reader2 = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(inp2.getFile()), CHARSET
                )
            );
            final Writer writer = new OutputStreamWriter(
                new FileOutputStream(out.getFile(), false), CHARSET
            );
        ) {
            IndexLine line1 = IndexLine.create(reader1.readLine());
            IndexLine line2 = IndexLine.create(reader2.readLine());
            do {
                int res = 0;
                if (line1 != null && (res = line1.compareTo(line2)) < 0) {
                    writer.write(line1.getLine());
                    writer.write("\r\n");
                    line1 = IndexLine.create(reader1.readLine());
                } else if (line2 != null && (line1 == null ? 1 : -res) < 0) {
                    writer.write(line2.getLine());
                    writer.write("\r\n");
                    line2 = IndexLine.create(reader2.readLine());
                } else {
                    if (line1 != null) {
                        writer.write(line1.getLine());
                        writer.write("\r\n");
                        line1 = IndexLine.create(reader1.readLine());
                    }
                    if (line2 != null) {
                        writer.write(line2.getLine());
                        writer.write("\r\n");
                        line2 = IndexLine.create(reader2.readLine());
                    }
                }
            } while (line1 != null || line2 != null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Создаём много временных файлов с отсортированными строками.
     * Файлы складываем в chunks
     */
    private void createTempFiles() {
        List<IndexLine> list = new ArrayList<>();
        try (
            final BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(inputFileName), CHARSET
                )
            );
        ) {
            String line;
            long size = 0;
            while ((line = reader.readLine()) != null) {
                final IndexLine xLine = new IndexLine(line);
                list.add(xLine);
                size += line.length();
                if (size > Launch.SIZE_CHUNK) {
                    saveChunk(list);
                    size = 0;
                }
            }
            saveChunk(list);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Записываем порцию во временный файл
     * @param list индексированные линии
     */
    private void saveChunk(List<IndexLine> list) {
        final TempSortFile sortFile = TempSortFile.createTemp();
        if (sortFile != null) {
            try (
                final Writer writer = new OutputStreamWriter(
                    new FileOutputStream(sortFile.getFile(), false), CHARSET
                );
            ) {
                chunks.add(sortFile);
                Collections.sort(list);
                for (IndexLine xLine : list) {
                    writer.write(xLine.getLine());
                    writer.write("\r\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        list.clear();
    }

    private void saveOut(List<IndexLine> list) {
        Collections.sort(list);
        try (
            final Writer writer = new OutputStreamWriter(
                new FileOutputStream(outputFileName, false), CHARSET
            );
        ) {
            for (IndexLine xLine : list) {
                writer.write(xLine.getLine());
                writer.write("\r\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
