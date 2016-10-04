package org.jboss.resteasy.example;

import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ClientApi;

/**
 * A simple example showing how to use the API to spider and active scan a site and then retrieve and print out the alerts.
 * <p>
 * ZAP must be running on the specified host and port for this script to work
 */
public class ZapClientApiExample {

    private static String ZAP_ADDRESS = "localhost";
    private static int ZAP_PORT = 8083;
    private static String ZAP_API_KEY = null; // Change this if you have set the apikey in ZAP via Options / API

    private static String TARGET = "http://localhost:8080/exceptions/";

    public ZapClientApiExample(String address, int port, String target, String apiKey) {
        this.ZAP_ADDRESS = address;
        this.ZAP_PORT = port;
        this.TARGET = target;
        this.ZAP_API_KEY = apiKey;
    }

    public String runScan() {
        ClientApi api = new ClientApi(ZAP_ADDRESS, ZAP_PORT);
        String result = null;

        try {
            // Must be here to initially access target URL via proxy
            api.accessUrl(TARGET);
            // Start spidering the target
            System.out.println("Spider : " + TARGET);
            ApiResponse resp = api.spider.scan(ZAP_API_KEY, TARGET, null, null, null, null);
            String scanid;
            int progress;

            // The scan now returns a scan id to support concurrent scanning
            scanid = ((ApiResponseElement) resp).getValue();

            // Poll the status until it completes
            while (true) {
                Thread.sleep(1000);
                progress = Integer.parseInt(((ApiResponseElement) api.spider.status(scanid)).getValue());
                System.out.println("Spider progress : " + progress + "%");
                if (progress >= 100) {
                    break;
                }
            }
            System.out.println("Spider complete");

            // Give the passive scanner a chance to complete
            Thread.sleep(2000);

            System.out.println("Active scan : " + TARGET);
            resp = api.ascan.scan(ZAP_API_KEY, TARGET, "True", "False", null, null, null);

            // The scan now returns a scan id to support concurrent scanning
            scanid = ((ApiResponseElement) resp).getValue();

            // Poll the status until it completes
            while (true) {
                Thread.sleep(5000);
                progress = Integer.parseInt(((ApiResponseElement) api.ascan.status(scanid)).getValue());
                System.out.println("Active Scan progress : " + progress + "%");
                if (progress >= 100) {
                    break;
                }
            }
            System.out.println("Active Scan complete");
            
            result = new String(api.core.xmlreport(ZAP_API_KEY));

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
