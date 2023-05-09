package org.github.xx.util;

/**
 * 序列化工具类
 */
public abstract class SerializationUtils {

    public static final byte[] EMPTY_ARRAY = new byte[0];

    /**
     * byte[]数组判空
     */
    public static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }
}
