package application.rest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

@Health
@ApplicationScoped
public class HealthEndpoint implements HealthCheck {
  @Inject
  @ConfigProperty(name = "health", defaultValue = "health config prop not available")
  private String health;

  @Override
  public HealthCheckResponse call() {
    HealthCheckResponse hcr;

    if ("up".equals(health)) {
      hcr = HealthCheckResponse.named("serviceB")
                                .withData("health", health)
                                .up().build();
    } else {
      hcr = HealthCheckResponse.named("serviceB")
                                .withData("health", health)
                                .down().build();
    }

    System.out.println("Health endpoint called: " + hcr);
    return hcr;
  }
}
