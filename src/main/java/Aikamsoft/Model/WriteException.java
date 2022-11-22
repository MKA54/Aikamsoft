package Aikamsoft.Model;

import Aikamsoft.Converter.MyExceptionToMyExceptionDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class WriteException {
    public void writeException(ObjectMapper objectMapper, String message, String defaultPath) throws IOException {
        MyException exception = new MyException(message);

        objectMapper.writeValue(new File(defaultPath),
                new MyExceptionToMyExceptionDTO().convert(exception));
    }
}
