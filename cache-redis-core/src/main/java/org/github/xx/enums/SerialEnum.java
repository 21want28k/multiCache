package org.github.xx.enums;

public enum SerialEnum {
    PROTOSTUFF("ProtostuffRedisSerializer"),
    KYRO("KryoRedisSerializer"),
    JACKSON("JacksonRedisSerializer"),

    JDK("JdkRedisSerializer");

    SerialEnum(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
