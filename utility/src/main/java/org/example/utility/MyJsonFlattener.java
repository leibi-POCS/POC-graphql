package org.example.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class MyJsonFlattener {
  static ArrayList<Map<String, JsonNode>> line = new ArrayList<>();
  static Set<String> headers = new LinkedHashSet<>();

  public static void main(String[] args) throws IOException {
    Path path = new File(MyJsonFlattener.class.getResource("/test.json").getPath()).toPath();

    String jsonString = new String(Files.readAllBytes(path));
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode jsonNode = objectMapper.readTree(jsonString);
    org.springframework.util.StopWatch springStopWatch = new org.springframework.util.StopWatch();
    springStopWatch.start();
    Map<String, JsonNode> hashMap = walkElement(jsonNode, "", new HashMap<>());
    if (!hashMap.isEmpty()) {
      line.add(hashMap);
    }
    springStopWatch.stop();
    System.out.println(
        "Time taken to flatten the JSON: " + springStopWatch.prettyPrint(TimeUnit.SECONDS));

    write2Excel(path.getParent().toAbsolutePath() + "/output.xlsx");
    write2csv(path.getParent().toAbsolutePath() + "/output.csv");
  }

  private static void write2csv(String s) throws IOException {
    PrintWriter writer = new PrintWriter(new File(s), StandardCharsets.UTF_8);
    String headerString = String.join(",", headers);
    writer.println(headerString);
    for (Map<String, JsonNode> valuesToPrint : line) {

      String lineString =
          headers.stream()
              .map(
                  e -> {
                    if (valuesToPrint.containsKey(e)) {
                      return String.join(",", valuesToPrint.get(e).asText());
                    } else {
                      return "";
                    }
                  })
              .collect(Collectors.joining(","));
      writer.println(lineString);
    }
    writer.close();
  }

  private static Map<String, JsonNode> walkElement(
      JsonNode node, String header, HashMap<String, JsonNode> map) {
    if (node.isValueNode()) {
      handleValueNode(header, node, map);
    } else if (node.isObject()) {
      Iterator<Entry<String, JsonNode>> fields = node.fields();

      List<Entry<String, JsonNode>> entries =
          StreamSupport.stream(
                  Spliterators.spliteratorUnknownSize(fields, Spliterator.ORDERED), false)
              .sorted(
                  Entry.comparingByValue(
                      Comparator.comparing(JsonNode::isValueNode)
                          .thenComparing(JsonNode::isObject)
                          .reversed()))
              .toList();
      for (Entry<String, JsonNode> entry : entries) {
        walkElement(entry.getValue(), header + "_" + entry.getKey().replace('_', '.'), map);
      }
    } else if (node.isArray()) {
      Iterator<JsonNode> elements = node.elements();
      while (elements.hasNext()) {
        line.add(walkElement(elements.next(), header, new HashMap<>(map)));
      }
    }
    return map;
  }

  private static void handleValueNode(String key, JsonNode element, HashMap<String, JsonNode> map) {
    map.put(key, element);
    headers.add(key);
  }

  public static void write2Excel(String destination) throws IOException {

    // Create a Workbook
    try (SXSSFWorkbook workbook = new SXSSFWorkbook(SXSSFWorkbook.DEFAULT_WINDOW_SIZE)) {

      // Create cell styles
      CellStyle cellStyleNumber = workbook.createCellStyle();
      cellStyleNumber.setDataFormat(workbook.createDataFormat().getFormat("0.00"));

      // Create a Sheet
      Sheet sheet = workbook.createSheet();

      // Create header row
      List<String> headers = MyJsonFlattener.headers.stream().toList();
      int cellIndex = 0;
      Row headerRow = sheet.createRow(0);
      for (String s : headers) {
        Cell cell = headerRow.createCell(cellIndex++);
        cell.setCellValue(s);
      }

      for (int i = 0; i < line.size(); i++) {
        cellIndex = 0;
        Row row = sheet.createRow(i + 1);
        for (String header : headers) {
          Cell cell = row.createCell(cellIndex++);
          JsonNode jsonNode = line.get(i).get(header);
          if (jsonNode == null) {
            continue;
          }
          if (jsonNode.isNumber()) {
            cell.setCellStyle(cellStyleNumber);
            cell.setCellValue(jsonNode.asDouble());
          } else if (jsonNode.isBoolean()) {
            cell.setCellValue(jsonNode.asBoolean());
          } else {
            cell.setCellValue(jsonNode.asText());
          }
        }
      }

      // Write the workbook content to a file
      try {
        Path excelfile = Files.createFile(Paths.get(destination));
        FileOutputStream outputStream = new FileOutputStream(excelfile.toFile());
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

        System.out.println("Excel file exported successfully!");

      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
