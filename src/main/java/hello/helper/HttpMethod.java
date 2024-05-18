package hello.helper;

public enum HttpMethod {
    GET(200, "GET"),
    POST(201, "POST"),
    PUT(202, "PUT"),
    DELETE(203, "DELETE");

    final int code;
    final String string;

    HttpMethod(int code, String string) {
        this.code = code;
        this.string = string;
    }

    public int getCode() {
        return code;
    }

    public String getString() {
        return string;
    }
}