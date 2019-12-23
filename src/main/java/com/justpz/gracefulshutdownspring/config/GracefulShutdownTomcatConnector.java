package com.justpz.gracefulshutdownspring.config;

import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.stereotype.Component;

@Component
public class GracefulShutdownTomcatConnector implements TomcatConnectorCustomizer {

  private Connector connector;

  @Override
  public void customize(Connector connector) {
    this.connector = connector;
  }

  public void pauseConnector() {
    connector.pause();
  }

  public Optional<ThreadPoolExecutor> getThreadPoolExecutor() {
    return Optional.of(connector.getProtocolHandler().getExecutor())
        .filter(ThreadPoolExecutor.class::isInstance).map(ThreadPoolExecutor.class::cast);
  }
}
