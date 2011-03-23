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
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException
    {
        DeskMetrics.start("4d47c012d9340b116a000000", "0.5");
        System.out.println("Application started");
        DeskMetrics.trackEvent("jDeskMetrics", "automatedTest1");
        DeskMetrics.trackEvent("jDeskMetrics", "automatedTest2");
        DeskMetrics.trackEvent("jDeskMetrics", "automatedTest3");
        System.out.println("Added some events");
        DeskMetrics.stop();
        System.out.println("Application finished");
    }
}
