package application.rest;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("serviceB")
public class ServiceBEndpoint {

    @GET
    public String hello() {
        String hostname;

        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch(java.net.UnknownHostException e) {
            hostname = e.getMessage();
        }
        return "Hello from serviceB at " + new Date() + " on " + hostname;
    }

}
