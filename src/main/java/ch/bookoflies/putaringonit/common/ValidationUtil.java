package ch.bookoflies.putaringonit.common;

public abstract class ValidationUtil {

    public static void rejectEmpty(String s, String attributeName) {
        if (s == null || s.isEmpty()) {
            throw ErrorResponse.Unprocessable(String.format("%s required", attributeName)).get();
        }
    }

    public static void rejectNull(Object o, String attributeName) {
        if (o == null) {
            throw ErrorResponse.Unprocessable(String.format("%s required", attributeName)).get();
        }
    }

    public static void rejectNegative(Double d, String attributeName) {
        if (d == null || d <= 0) {
            throw ErrorResponse.Unprocessable(String.format("%s required greater or equal 0", attributeName)).get();
        }
    }

    public static void rejectNonPositive(Double d, String attributeName) {
        if (d == null || d <= 0) {
            throw ErrorResponse.Unprocessable(String.format("%s required greater than 0", attributeName)).get();
        }
    }
}
