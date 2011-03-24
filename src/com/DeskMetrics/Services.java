package com.DeskMetrics;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


/**
 *
 * @author Herberth Amaral (http://github.com/herberthamaral)
 */
public class Services {

    protected static void sendDataToUrl(String data, String toUrl) throws Exception
    {
        try {
            // Construct data
            String _data = URLEncoder.encode(data,"UTF-8");

            // Send data
            URL url = new URL(toUrl);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            // Get the response
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;

            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }

            wr.close();
            rd.close();
        }
        catch (Exception e) {
            System.out.println("rá");
        }
    }
}
