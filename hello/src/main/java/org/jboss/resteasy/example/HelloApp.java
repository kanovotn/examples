package org.jboss.resteasy.example;

import org.w3c.dom.Document;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class HelloApp extends Application {

    @Path("/")
    public static class HelloResource {

        @Consumes("application/xml")
        @POST
        public String doPost(Document doc)
        {
            return "Hello, " + doc.getDocumentElement().getTextContent();
        }
    }
	
}
