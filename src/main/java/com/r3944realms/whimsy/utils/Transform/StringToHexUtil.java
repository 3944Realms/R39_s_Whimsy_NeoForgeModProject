package com.r3944realms.whimsy.utils.Transform;

public class StringToHexUtil {
    /**
     * 形如
     * <code>0xB00000000A0A0A0A000A141E0000000000000065</code> -> <code>0xB0 0x00 0x00 0x00 0x0A 0x0A 0x0A 0x0A 0x00 0x0A 0x14 0x1E 0x00 0x00 0x00 0x00 0x00 0x00 0x00 0x65 </code>
     * <br/>
     * @param data 16进制标准的字符串
     * @param totalStringLen 实际的字符串长度
     * @return 含字符串的有效字节数长度的的字节数组
     */
     public static byte[] hexStringToByteArray(String data, int totalStringLen) {
        if(data.length() != totalStringLen) {
            throw new IllegalArgumentException("Invalid data length: " + data.length());
        }
        return getBytes(data.substring(2));//去掉前缀
    }

    private static byte[] getBytes(String data) {
        int len = data.length();
        byte[] result = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            result[i / 2] = (byte) ((Character.digit(data.charAt(i), 16) << 4)
                    + Character.digit(data.charAt(i + 1), 16));
        }
        return result;
    }
}
