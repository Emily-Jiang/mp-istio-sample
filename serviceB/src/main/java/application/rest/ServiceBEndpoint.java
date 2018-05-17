package application.rest;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("serviceB")
@RequestScoped
public class ServiceBEndpoint {

  @Inject
  @ConfigProperty(name = "succeedFrequency", defaultValue = "0")
  private int succeedFrequency;

  static int callCount = 0;

  @GET
  public String hello() throws Exception {
    String hostname;

    ++callCount;

    if (succeedFrequency > 0 && callCount % succeedFrequency != 0) {
      throw new Exception("ServiceB deliberately caused to fail. SucceedFrequency is " + succeedFrequency);
    }

    try {
      hostname = InetAddress.getLocalHost().getHostName();
    } catch(java.net.UnknownHostException e) {
      hostname = e.getMessage();
    }

    return "Hello from serviceB at " + new Date() + " on " + hostname + " (ServiceB call count: " + callCount + ", succeedFrequency: " + succeedFrequency + ")";
  }
}
