package org.jboss.resteasy.example;

import org.junit.Test;

/**
 * Test for simple ZapClientApiExample.
 */
public class ZapClientApiExampleTest {

    private static String ZAP_ADDRESS = "localhost";
    private static int ZAP_PORT = 8083;
    private static String ZAP_API_KEY = "er17cpj56974vspilvjadtr0sk"; // Change this if you have set the apikey in ZAP via Options / API
    private static String TARGET = "http://localhost:8080/exceptions/";

    @Test
    public void testApp() {

        TARGET = System.getProperty("target", TARGET);
        String result = new ZapClientApiExample(ZAP_ADDRESS, ZAP_PORT, TARGET, ZAP_API_KEY).runScan();
        System.out.println(result);
    }
}
