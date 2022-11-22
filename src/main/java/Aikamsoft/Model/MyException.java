package Aikamsoft.Model;

public class MyException extends Throwable {
    private String type;
    private String message;

    public MyException(String message) {
        type = "error";
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.type = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}