/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.DeskMetrics;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author herberth
 */
public class Util {
    protected static String getMD5(String toEncode)
    {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("MD5");
            md.update(toEncode.getBytes());
            BigInteger hash = new BigInteger(1, md.digest());
            return hash.toString(16);
        } catch (NoSuchAlgorithmException ex) {
            return "";
        }
    }

    protected static int getCurrentTimeStamp()
    {
        return (int) (new Date().getTime() / 1000);
    }

    protected static String getJSONFromHashtable(Hashtable hash)
    {
        Enumeration elements = hash.elements();
        String json = "{";

        while(elements.hasMoreElements())
        {
            Object e = elements.nextElement();
            if (hash.get(e) == null)
            {
                json += "\""+e.toString()+":null,";
                continue;
            }

            try
            {
                Long valueOf = Long.valueOf(hash.get(e).toString());
                json += "\""+e.toString()+"\":"+String.valueOf(valueOf)+",";
            }
            catch(NumberFormatException ex)
            {
                //it is not an integer, but a string.
                json += "\""+e.toString()+"\":\""+hash.get(e).toString()+"\",";
            }
        }

        json = json.substring(0, json.length()-1);
        json += "}";

        return json;
    }
}
