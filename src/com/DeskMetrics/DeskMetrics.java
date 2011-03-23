/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.DeskMetrics;

import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author herberth
 */
public class DeskMetrics {

    private static List<String> Events;

    public static void Start(String appID,String appVersion)
    {
        Hashtable<String,Object> startApp = new Hashtable<String,Object>();

        startApp.put("tp", "strApp");
        startApp.put("aver", "4.5");
        startApp.put("ID", getUserID());
        startApp.put("ss", getSessionID());
        
    }

    private static String getUserID()
    {
        return "";
    }

    private static String getSessionID()
    {
        return "";
    }

}
