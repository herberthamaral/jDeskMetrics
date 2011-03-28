/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.DeskMetrics.IntegrationTests;

import com.DeskMetrics.DeskMetrics;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author herberth
 */
public class Main {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, Exception
    {
        DeskMetrics deskmetrics = DeskMetrics.getInstance();

        deskmetrics.start("4d47c012d9340b116a000000", "0.5");
        System.out.println("Application started");
        deskmetrics.trackEvent("jDeskMetrics", "automatedTest1");
        deskmetrics.trackEvent("jDeskMetrics", "automatedTest2");
        deskmetrics.trackEvent("jDeskMetrics", "automatedTest3");
        System.out.println("Added some events");

        deskmetrics.trackCustomData("CustomJavaData", "CustomDataValue");
        deskmetrics.trackCustomDataR("CustomJavaDataR", "CustomDataValueR");

        deskmetrics.trackInstall("4.2","4d47c012d9340b116a000000");
        deskmetrics.trackUninstall("4.2","4d47c012d9340b116a000000");

        deskmetrics.trackEventTimed("jDeskMetrics", "jTimed", 30, true);
        deskmetrics.trackLog("jDeskMetrics send a log ;)");
        deskmetrics.trackException(new Exception("Oops, got an error in jDeskMetrics"));

        deskmetrics.stop();
        System.out.println("Application finished");
    }
}
