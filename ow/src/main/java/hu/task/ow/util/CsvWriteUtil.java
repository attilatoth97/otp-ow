package hu.task.ow.util;

import static hu.task.ow.constant.WebShopConstant.COMMA_DELIMITER;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CsvWriteUtil {

  private CsvWriteUtil() {
  }

  public static void convertToCSV(List<List<String>> data, String filePath) {
    List<String> lines = new ArrayList<>();
    for (var dataLine : data) {
      lines.add(String.join(COMMA_DELIMITER, dataLine));
    }

    writeCsv(lines, filePath);
  }

  private static void writeCsv(List<String> lines, String filePath) {
    File csvOutputFile = new File(filePath);
    try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
      lines.forEach(pw::println);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

}
