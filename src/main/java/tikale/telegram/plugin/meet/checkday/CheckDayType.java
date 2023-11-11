package tikale.telegram.plugin.meet.checkday;

public enum CheckDayType {

    WORK(0),
    HOLIDAY(1),
    ERROR_IN_DATE(100),
    DATA_NOT_FOUND(101),
    UNKWOWN(-1);

    private int code;

    private CheckDayType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static CheckDayType find(int code) {
        for (CheckDayType type : values()) {
            if (code == type.getCode()) {
                return type;
            }
        }

        return CheckDayType.UNKWOWN;
    }
}
