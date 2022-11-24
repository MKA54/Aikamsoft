package Aikamsoft;

import Aikamsoft.Model.MyException;
import Aikamsoft.Model.WriteException;
import Aikamsoft.Service.Search;
import Aikamsoft.Service.Stat;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        String defaultPath = "D:\\Aikamsoft\\src\\main\\java\\error.json";
        ObjectMapper objectMapper = new ObjectMapper();
        WriteException writeException = new WriteException();

        try {
            if (args.length < 3) {
                throw new MyException("Менее 3 аргументов: " + args.length);
            }

            if (args.length > 3) {
                throw new MyException("Более 3 аргументов: " + args.length);
            }

            String typeTransaction = args[0];
            String inputPath = args[1];
            String outputPath = args[2];

            if (typeTransaction.equals("search")) {
                Search search = new Search(inputPath, defaultPath, objectMapper);

                if (search.isSuccessfully()) {
                    objectMapper.writeValue(new File(outputPath),
                            search.getResult());
                }

                return;
            }

            if (typeTransaction.equals("stat")) {
                Stat stat = new Stat(inputPath, defaultPath, objectMapper);

                if (stat.isSuccessfully()) {
                    objectMapper.writeValue(new File(outputPath),
                            stat.getStatResults());
                }

                return;
            }

            throw new MyException("Не корректная команда поиска: " + args[0]);
        } catch (
                IOException e) {
            writeException.writeException(objectMapper, "Ошибка пути", defaultPath);
        } catch (MyException e) {
            writeException.writeException(objectMapper, e.getMessage(), defaultPath);
        }
    }
}