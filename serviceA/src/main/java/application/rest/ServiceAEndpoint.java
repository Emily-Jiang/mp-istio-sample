package application.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Retry;

@Path("/")
@RequestScoped
public class ServiceAEndpoint extends Application {

    @Inject
    @ConfigProperty(name = "SERVICEB_HOST", defaultValue = "serviceB-service")
    private String serviceBHost;

    @Inject
    @ConfigProperty(name = "SERVICEB_SERVICE_SERVICE_PORT", defaultValue = "9080")
    private String serviceBPort;

    @Inject
    @ConfigProperty(name = "prop1", defaultValue = "def1")
    private String prop1;

    @Inject
    @ConfigProperty(name = "prop2", defaultValue = "def2")
    private String prop2;

    @GET
    @Path("/serviceA")
    @Retry
    @Fallback(fallbackMethod="serviceAFallback")
    @Produces(MediaType.TEXT_PLAIN)
    public String callServiceB() {
        StringBuilder url = new StringBuilder();
        
        url.append("http://")
            .append(serviceBHost)
            .append(":")
            .append(serviceBPort)
            .append("/serviceB");

        return "Hello from serviceA\n" + callService(url) + formatConfigProperties();
    }

    public String serviceAFallback() {

        return "Hello from serviceAFallback\n" + formatConfigProperties();
    }

    private String callService(StringBuilder url) {

        StringBuilder sb = new StringBuilder();

        sb.append("Calling service at: ")
            .append(url);

        System.out.println(sb.toString());

        sb.append("\n")
            .append(ClientBuilder.newClient()
                              .target(url.toString())
                              .request(MediaType.TEXT_PLAIN)
                              .get(String.class));

        return sb.toString();
    }

    private String formatConfigProperties() {
      return "\nprop1=" + prop1 + "\nprop2=" + prop2 + "\n";
    }
}
