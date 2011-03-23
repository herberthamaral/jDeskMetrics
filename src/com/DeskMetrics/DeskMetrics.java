/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.DeskMetrics;

import com.sun.management.OperatingSystemMXBean;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author herberth
 */
public class DeskMetrics {

    private static List<String> Events;
    private static long freeDiskSpace;
    private static long totalDiskSpace;

    public static void Start(String appID,String appVersion) throws IOException, NoSuchAlgorithmException
    {
        Hashtable<String,Object> startApp = new Hashtable<String,Object>();

        startApp.put("tp", "strApp");
        startApp.put("aver", "4.5");
        startApp.put("ID", getUserID());
        startApp.put("ss", getSessionID());
        startApp.put("ts", getCurrentTimeStamp());
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

        
        Events.add(getJSONFromHashtable(startApp));
    }

    private static String getUserID() throws IOException, NoSuchAlgorithmException
    {
        String filename = System.getProperty("user.home")+System.getProperty("file.separator")+".deskmetrics";
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
            userID = getMD5(String.valueOf(getCurrentTimeStamp()));
            fileWriter.write(userID);
        }

        return userID;
    }

    private static String getSessionID()
    {
        return getMD5(String.valueOf(getCurrentTimeStamp()));
    }

    private static int getCurrentTimeStamp()
    {
        return (int) (new Date().getTime() / 1000);
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

    private static String getMD5(String toEncode)
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

    private static String getJSONFromHashtable(Hashtable hash)
    {
        return "";
    }
    
}
