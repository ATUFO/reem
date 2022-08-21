package top.taofoo.backboot.enums;

public enum LockRecordStatusEnum {
    PRE_LOCK("未锁门"),
    LOCKED("已锁门");

    LockRecordStatusEnum(String description) {
        this.description = description;
    }

    private String description;

    public static String getDescription(int ord) {
        LockRecordStatusEnum[] values = LockRecordStatusEnum.values();
        if (ord < 0 || ord >= values.length) {
            return null;
        }
        return values[ord].description;
    }
}
