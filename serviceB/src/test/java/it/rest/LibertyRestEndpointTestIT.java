package it.rest;

import it.EndpointTest;

import org.junit.Test;

public class LibertyRestEndpointTestIT extends EndpointTest {

    @Test
    public void testDeployment() {
        testEndpoint("/serviceB", "Hello from serviceB");
    }
}
