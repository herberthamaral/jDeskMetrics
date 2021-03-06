package com.DeskMetrics;

import java.awt.GraphicsEnvironment;
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
import java.util.Vector;

/**
 *
 * @author Herberth Amaral (http://github.com/herberthamaral)
 */
public class DeskMetrics {

    private  List<String> Events = new Vector<String>();;
    private  long freeDiskSpace;
    private  long totalDiskSpace;
    private  String appID,appVersion;
    private  String sessionID;
    private  int flow=0;
    private  String url = "";

    private static DeskMetrics instance;

    private Hashtable<String,Integer> languageCodes;

    private DeskMetrics()
    {
        languageCodes = new Hashtable<String, Integer>();

        languageCodes.put("ja", 1041);
        languageCodes.put("es", 1034);
        languageCodes.put("en", 1033);
        languageCodes.put("sr", 2074); //TODO: make distinction from latin and cyrilic
        languageCodes.put("mk", 1071);
        languageCodes.put("mk", 1071);
        languageCodes.put("no", 1044);
        languageCodes.put("sq", 1052);
        languageCodes.put("sq", 1052);
        languageCodes.put("bg", 1026);
        languageCodes.put("hu", 1038);
        languageCodes.put("pt", 1036);
        languageCodes.put("pt", 1036);
        languageCodes.put("sv", 1053);
        languageCodes.put("de", 1031);
        languageCodes.put("fi", 1035);
        languageCodes.put("is", 1039);
        languageCodes.put("cs", 1029);
        languageCodes.put("sl", 1060);
        languageCodes.put("sk", 1051);
        languageCodes.put("it", 1040);
        languageCodes.put("tr", 1055);
        languageCodes.put("th", 1054);
        languageCodes.put("ro", 2072);
        languageCodes.put("fr", 1036);
        languageCodes.put("ko", 1042);
        languageCodes.put("et", 1061);
        languageCodes.put("ru", 1049);
        languageCodes.put("lv", 1062);
        languageCodes.put("lv", 1062);
        languageCodes.put("hr", 1050);
        languageCodes.put("hi", 1081);
        languageCodes.put("be", 1059);
        languageCodes.put("ca", 1027);
        languageCodes.put("uk", 1058);
        languageCodes.put("pl", 1045);
        languageCodes.put("vi", 1066);
        languageCodes.put("mt", 1082);
        languageCodes.put("ms", 1086);
        languageCodes.put("da", 1030);
    }

    public static DeskMetrics getInstance()
    {
        if (instance==null)
            instance = new DeskMetrics();

        return instance;
    }

    public  void start(String appID,String appVersion) throws IOException, NoSuchAlgorithmException
    {
        this.appID = appID;
        this.appVersion = appVersion;
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
        startApp.put("oslng", languageCodes.get(java.util.Locale.getDefault().getLanguage()).toString());
        startApp.put("osscn", getScreenResolution());
        startApp.put("ccr", Runtime.getRuntime().availableProcessors());
        startApp.put("cbr", "null");
        startApp.put("cnm", "null");
        startApp.put("car", "null");

        com.sun.management.OperatingSystemMXBean osbean = (com.sun.management.OperatingSystemMXBean) java.lang.management.ManagementFactory.getOperatingSystemMXBean();

        startApp.put("mtt", osbean.getTotalPhysicalMemorySize());
        startApp.put("mfr", osbean.getFreePhysicalMemorySize());
        startApp.put("dtt", totalDiskSpace);
        startApp.put("dfr", freeDiskSpace);

        
        Events.add(Util.getJSONFromHashtable(startApp));

        url = "http://"+appID+".api.deskmetrics.com/sendData";
    }

    public void trackEvent(String category,String name)
    {
        Hashtable<String,Object> hash = new Hashtable<String, Object>();

        hash.put("tp", "ev");
        hash.put("ca", category);
        hash.put("nm", name);
        hash.put("ts", Util.getCurrentTimeStamp());
        hash.put("ss", getSessionID());
        hash.put("fl", flow);
        flow ++;
        String json = Util.getJSONFromHashtable(hash);
        Events.add(json);
    }

    public  void trackEventValue(String category, String name, String value)
    {
        Hashtable<String,Object> hash = new Hashtable<String, Object>();

        hash.put("tp", "evV");
        hash.put("ca", category);
        hash.put("nm", name);
        hash.put("vl", value);
        hash.put("ts", Util.getCurrentTimeStamp());
        hash.put("ss", getSessionID());
        hash.put("fl", flow);
        flow ++;
        String json = Util.getJSONFromHashtable(hash);
        Events.add(json);
    }

    public  void trackCustomData(String name, String value)
    {
        Hashtable<String,Object> hash = new Hashtable<String, Object>();

        hash.put("tp", "ctD");
        hash.put("nm", name);
        hash.put("vl", value);
        hash.put("ts", Util.getCurrentTimeStamp());
        hash.put("ss", getSessionID());
        hash.put("fl", flow);
        flow ++;
        String json = Util.getJSONFromHashtable(hash);
        Events.add(json);
    }

    public  void trackCustomDataR(String name, String value) throws Exception
    {
        Hashtable<String,Object> hash = new Hashtable<String, Object>();

        hash.put("tp", "ctDR");
        hash.put("nm", name);
        hash.put("vl", value);
        hash.put("aver", appVersion);
        hash.put("ID", getUserID());
        hash.put("ts", Util.getCurrentTimeStamp());
        hash.put("ss", getSessionID());
        hash.put("fl", flow);
        flow ++;
        String json = Util.getJSONFromHashtable(hash);

        Services.sendDataToUrl(json, url);
    }

    public  void trackInstall(String version,String appID) throws Exception
    {
        Hashtable<String,Object> hash = new Hashtable<String, Object>();
        hash.put("tp", "ist");
        hash.put("ID", getUserID());
        hash.put("aver", version);
        hash.put("ts", Util.getCurrentTimeStamp());
        hash.put("ss", getSessionID());
        String json = Util.getJSONFromHashtable(hash);

        Services.sendDataToUrl(json, "http://"+appID+".api.deskmetrics.com/sendData");
    }

    public  void trackUninstall(String version,String appID) throws Exception
    {
        Hashtable<String,Object> hash = new Hashtable<String, Object>();
        hash.put("tp", "ust");
        hash.put("ID", getUserID());
        hash.put("aver", version);
        hash.put("ts", Util.getCurrentTimeStamp());
        hash.put("ss", getSessionID());
        String json = Util.getJSONFromHashtable(hash);

        Services.sendDataToUrl(json, "http://"+appID+".api.deskmetrics.com/sendData");
    }

    public void trackLog(String message)
    {
        Hashtable<String,Object> hash = new Hashtable<String, Object>();
        hash.put("tp", "lg");
        hash.put("ms", message);
        hash.put("ts", Util.getCurrentTimeStamp());
        hash.put("ss", getSessionID());
        hash.put("fl", flow);
        flow ++;
        String json = Util.getJSONFromHashtable(hash);
        Events.add(json);
    }

    public  void trackEventTimed(String category, String name, int time, boolean finished)
    {
        Hashtable<String,Object> hash = new Hashtable<String, Object>();

        hash.put("tp", "evP");
        hash.put("ca", category);
        hash.put("nm", name);
        hash.put("ec", finished?"1":"0");
        hash.put("tm", String.valueOf(time));
        hash.put("ts", Util.getCurrentTimeStamp());
        hash.put("ss", getSessionID());
        hash.put("fl", flow);
        flow ++;
        String json = Util.getJSONFromHashtable(hash);
        Events.add(json);
    }

    public  void trackException(Exception e)
    {
        Hashtable<String,Object> hash = new Hashtable<String, Object>();

        hash.put("tp", "exC");
        hash.put("msg", e.getMessage().replaceAll("\n", " ").replaceAll("\r", " "));
        
        String stackTrace = "";
        for (StackTraceElement element:e.getStackTrace())
        {
            stackTrace += element.toString().replaceAll("\n", " ").replace("\r", " ");
        }

        hash.put("stk", stackTrace);
        hash.put("ts", Util.getCurrentTimeStamp());
        hash.put("ss", getSessionID());
        hash.put("fl", flow);
        flow ++;
        String json = Util.getJSONFromHashtable(hash);
        Events.add(json);
    }

    public  void stop() throws Exception
    {
        Hashtable stApp = new Hashtable();
        stApp.put("tp", "stApp");
        stApp.put("ts", String.valueOf(Util.getCurrentTimeStamp()));
        stApp.put("ss", getSessionID());

        Events.add(Util.getJSONFromHashtable(stApp));

        Services.sendDataToUrl(Util.getJSONFromJSONList(Events), url);
    }

    private  String getUserID() throws IOException,NoSuchAlgorithmException
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
            fileWriter.close();
        }

        return userID;
    }

    private  String getSessionID()
    {
        if (sessionID==null)
            sessionID = Util.getMD5(String.valueOf(Util.getCurrentTimeStamp()));
        return sessionID;
    }

    private  String getDotNetVersion()
    {
        return "null";
    }

    private  String getDotNetServicePack()
    {
        return "null";
    }

    private  String getScreenResolution()
    {
        Toolkit t = null;//Toolkit.getDefaultToolkit();
        try
        {
            t = Toolkit.getDefaultToolkit();
        }catch(Exception e){}
        
        String width = String.valueOf(t.getScreenSize().getWidth());
        String height = String.valueOf(t.getScreenSize().getHeight());

        width = width.substring(0, width.indexOf("."));
        height = height.substring(0, height.indexOf("."));

        
        return width+"x"+height;
    }
    
}
