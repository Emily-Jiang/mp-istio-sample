package it;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class ServiceAEndpointTest {

    @Test
    public void testDeployment() {
        try {
          testEndpoint("/mp-istio-sample/serviceA", 200, "serviceAFallback");
        } catch (Exception e) {
          e.printStackTrace();
        }
    }

    private void testEndpoint(String endpoint, int expectedResponseCode, String expectedOutput) {
        String port = System.getProperty("liberty.test.port");
        String url = "http://localhost:" + port + endpoint;
        System.out.println("Testing " + url);
        Response response = sendRequest(url, "GET");
        int responseCode = response.getStatus();
        assertTrue("Incorrect response code: " + responseCode,
                   responseCode == expectedResponseCode);

        String responseString = response.readEntity(String.class);
        response.close();
        assertTrue("Incorrect response, response is " + responseString, responseString.contains(expectedOutput));
    }

    private Response sendRequest(String url, String requestType) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(url);
        Invocation.Builder invoBuild = target.request();
        Response response = invoBuild.build(requestType).invoke();
        return response;
    }
}
