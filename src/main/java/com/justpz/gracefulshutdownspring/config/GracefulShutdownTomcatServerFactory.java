package com.justpz.gracefulshutdownspring.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class GracefulShutdownTomcatServerFactory implements
    WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

  private final GracefulShutdownTomcatConnector gracefulShutdownTomcatConnector;

  public GracefulShutdownTomcatServerFactory(
      GracefulShutdownTomcatConnector gracefulShutdownTomcatConnector) {
    this.gracefulShutdownTomcatConnector = gracefulShutdownTomcatConnector;
  }

  @Override
  public void customize(TomcatServletWebServerFactory factory) {
    factory.addConnectorCustomizers(gracefulShutdownTomcatConnector);
  }
}
