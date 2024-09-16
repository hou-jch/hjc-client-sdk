package com.hjc.hjjcclientsdk.util;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * File Description: SignUtils
 * Author: hou-jch
 * Date: 2024/9/5
 */
public class SignUtils {
    public static String getSion(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String context = body + '.' + secretKey;
        return md5.digestHex(context);
    }
}
