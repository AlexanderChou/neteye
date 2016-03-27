/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.savi.cernet.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author guoq
 */
public class NasCoderUtil {

/**
 * MD5
 * @param stringSource
 * @return MD5
 */
    public static String stringToMD5(String stringSource){
        String stringMD5 = null;
        try {
          MessageDigest md = MessageDigest.getInstance("MD5");
          md.update(stringSource.getBytes());
          stringMD5 = byte2hex(md.digest());
        }
        catch (NoSuchAlgorithmException e) {
          e.printStackTrace();
        }
        return stringMD5;
    }

    private static char[] HEXCHAR = { '0', '1', '2', '3', '4', '5', '6', '7',
        '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    //
    private static String byte2hex(byte[] b){
        String hs = "";
        String stmp = "";
        for(int n = 0; n < b.length; n++){
            stmp = ( java.lang.Integer.toHexString(b[n] & 0XFF) );
            if(stmp.length()==1){
                hs = hs + "0" + stmp;
            }
            else{
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

}
