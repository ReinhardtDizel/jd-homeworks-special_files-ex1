import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class Main {
    public static void main(String... args) {
        String[] columnMapping = {"id", "firstName", "lastName", "country", "age"};
        String fileName = "src/main/resources/data.csv";
        List<Employee> list = parseCSV(columnMapping, fileName);
        writeString(listToJson(list), "src/main/resources/output.txt");
    }

    public static List<Employee> parseCSV(String[] columns, String fileName) {

        try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
            ColumnPositionMappingStrategy<Employee> ms = new ColumnPositionMappingStrategy<>();
            ms.setType(Employee.class);
            ms.setColumnMapping(columns);
            CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Employee.class)
                    .withMappingStrategy(ms)
                    .build();
            return csvToBean.parse();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return null;
    }

    public static String listToJson(List<Employee> employeeList) {
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        Gson gson = new Gson();
        return gson.toJson(employeeList, listType);
    }

    public static void writeString(String resource, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write(resource);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}