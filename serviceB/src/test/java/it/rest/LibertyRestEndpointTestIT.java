package it.rest;

import it.EndpointTest;

import org.junit.Test;

public class LibertyRestEndpointTestIT extends EndpointTest {

//    @Test
    public void testDeployment() {
        testEndpoint("/mp-istio-sample/serviceB", "Hello from serviceB");
    }
}
