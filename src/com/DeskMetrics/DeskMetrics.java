/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.DeskMetrics;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Hashtable;
import java.util.List;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 * @author herberth
 */
public class DeskMetrics {

    private static List<String> Events;
    private static long freeDiskSpace;
    private static long totalDiskSpace;
    private static String appID;
    private static String sessionID;

    public static void Start(String appID,String appVersion) throws IOException, NoSuchAlgorithmException
    {
        DeskMetrics.appID = appID;
        Hashtable<String,Object> startApp = new Hashtable<String,Object>();

        startApp.put("tp", "strApp");
        startApp.put("aver", appVersion);
        startApp.put("ID", getUserID());
        startApp.put("ss", getSessionID());
        startApp.put("ts", Util.getCurrentTimeStamp());
        startApp.put("osv", System.getProperty("os.name"));
        startApp.put("ossp", "null");
        startApp.put("osar", System.getProperty("os.arch"));
        startApp.put("osjv", System.getProperty("java.version"));
        startApp.put("osnet", getDotNetVersion());
        startApp.put("oslng", 1046);
        startApp.put("osscn", getScreenResolution());
        startApp.put("ccr", Runtime.getRuntime().availableProcessors());
        startApp.put("cbr", null);
        startApp.put("cnm", null);
        startApp.put("car", null);

        com.sun.management.OperatingSystemMXBean osbean = (com.sun.management.OperatingSystemMXBean) java.lang.management.ManagementFactory.getOperatingSystemMXBean();

        startApp.put("mtt", osbean.getTotalPhysicalMemorySize());
        startApp.put("mfr", osbean.getFreePhysicalMemorySize());
        startApp.put("dtt", totalDiskSpace);
        startApp.put("dfr", freeDiskSpace);

        
        Events.add(Util.getJSONFromHashtable(startApp));
    }

    public static void Stop()
    {
        Hashtable stApp = new Hashtable();
        stApp.put("tp", "stApp");
        stApp.put("ts", String.valueOf(Util.getCurrentTimeStamp()));
        stApp.put("ss", getSessionID());

        Events.add(Util.getJSONFromHashtable(stApp));
    }

    private static String getUserID() throws IOException,NoSuchAlgorithmException
    {
        String filename = System.getProperty("user.home")+
                System.getProperty("file.separator")+".deskmetrics";
        File file = new File(filename);

        String userID = "";
        freeDiskSpace = file.getFreeSpace();
        totalDiskSpace = file.getTotalSpace();
        
        if (file.exists())
        {
            BufferedReader fileReader =  new BufferedReader(new FileReader(file));
            userID = fileReader.readLine();
        }
        else
        {
            file.createNewFile();
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            userID = Util.getMD5(String.valueOf(Util.getCurrentTimeStamp()));
            fileWriter.write(userID);
        }

        return userID;
    }

    private static String getSessionID()
    {
        if (sessionID==null)
            sessionID = Util.getMD5(String.valueOf(Util.getCurrentTimeStamp()));
        return sessionID;
    }

    private static String getDotNetVersion()
    {
        return null;
    }

    private static String getDotNetServicePack()
    {
        return null;
    }

    private static String getScreenResolution()
    {
        Toolkit t = Toolkit.getDefaultToolkit();
        String width = String.valueOf(t.getScreenSize().getWidth()).split(".")[0];
        String height = String.valueOf(t.getScreenSize().getHeight()).split(".")[0];
        return width+"x"+height;
    }


}
