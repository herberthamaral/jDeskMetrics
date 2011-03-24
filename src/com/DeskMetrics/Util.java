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
import java.util.List;

/**
 *
 * @author Herberth Amaral (http://github.com/herberthamaral)
 */
public class Util {
    protected static String getMD5(String toEncode)
    {
        try
        {
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
        Enumeration keys = hash.keys();
        String json = "{";

        while(keys.hasMoreElements())
        {
            Object e = keys.nextElement();
            
            if (hash.get(e) == null)
            {
                json += "\""+e.toString()+"\":null,";
                continue;
            }

            try
            {
                Long valueOf = Long.valueOf(hash.get(e).toString());
                json += "\""+e.toString()+"\":"+String.valueOf(valueOf)+",";
            }
            catch(NumberFormatException ex)
            {
                if (hash.get(e).toString()=="null")
                {
                    json += "\""+e.toString()+"\":null,";
                }
                else
                {
                //it is not an integer, but a string.
                json += "\""+e.toString()+"\":\""+hash.get(e).toString()+"\",";
                }
            }
        }

        json = json.substring(0, json.length()-1);
        json += "}";

        return json;
    }

    protected static String getJSONFromJSONList(List<String> jsonList)
    {
        String json = "[";
        
        for(String s:jsonList)
        {
            json += s+",";
        }

        json = json.substring(0, json.length()-1);
        json += "]";
        return json;
    }
}
