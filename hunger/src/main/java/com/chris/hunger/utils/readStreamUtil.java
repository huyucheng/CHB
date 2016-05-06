package com.chris.hunger.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by dell on 2016/3/11.
 */
public class readStreamUtil {
    public static byte[] readStream(InputStream is) throws Exception{
        byte[] bytes = new byte[1024];
        int leng;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while((leng=is.read(bytes)) != -1){
            baos.write(bytes, 0, leng);
        }
        return baos.toByteArray();
    }
}
