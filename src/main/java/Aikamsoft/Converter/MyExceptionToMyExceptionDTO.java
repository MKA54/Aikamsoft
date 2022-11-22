package Aikamsoft.Converter;

import Aikamsoft.DTO.MyExceptionDTO;
import Aikamsoft.Model.MyException;

public class MyExceptionToMyExceptionDTO {
    public MyExceptionDTO convert(MyException exception) {
        MyExceptionDTO myExceptionDTO = new MyExceptionDTO();

        myExceptionDTO.setType(exception.getType());
        myExceptionDTO.setMessage(exception.getMessage());

        return myExceptionDTO;
    }
}