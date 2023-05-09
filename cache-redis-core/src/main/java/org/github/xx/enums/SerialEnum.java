package org.github.xx.enums;

public enum SerialEnum {
    Protostuff("ProtostuffRedisSerializer"),
    Kyro("KryoRedisSerializer"),
    Jackson("JacksonRedisSerializer"),

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
