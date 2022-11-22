package Aikamsoft.Converter;

import Aikamsoft.Model.MyException;
import Aikamsoft.Model.StatisticsDates;
import Aikamsoft.Model.WriteException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StatDateDTOToStatDate {
    private final String path;
    private final String defaultPath;
    private StatisticsDates statisticsDates;
    private final ObjectMapper objectMapper;
    private final WriteException writeException = new WriteException();
    private boolean isException = false;

    public StatDateDTOToStatDate(String path, String defaultPath, ObjectMapper objectMapper) throws IOException {
        this.path = path;
        this.defaultPath = defaultPath;
        this.objectMapper = objectMapper;

        loadDate();
    }

    public boolean isException() {
        return isException;
    }

    public void setException(boolean exception) {
        isException = exception;
    }

    public StatisticsDates getStatisticsDates() {
        return statisticsDates;
    }

    public void setStatisticsDates(StatisticsDates statisticsDates) {
        this.statisticsDates = statisticsDates;
    }

    private void loadDate() throws IOException {
        try {
            statisticsDates = objectMapper.readValue(new File
                    (path), StatisticsDates.class);

            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Date startDate = format.parse(statisticsDates.getStartDate());
            Date endDate = format.parse(statisticsDates.getEndDate());

            if (startDate.after(endDate)) {
                isException = true;
                throw new MyException("Не корректное значение startDate: startDate > endDate");
            }
        } catch (ParseException e) {
            isException = true;
            writeException.writeException(objectMapper, "Не верный формат даты", defaultPath);
        } catch (MyException e) {
            writeException.writeException(objectMapper, e.getMessage(), defaultPath);
        } catch (IOException e) {
            writeException.writeException(objectMapper, "Не верный путь входного файла", defaultPath);
        }
    }
}