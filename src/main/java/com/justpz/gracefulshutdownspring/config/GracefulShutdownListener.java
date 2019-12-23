package com.justpz.gracefulshutdownspring.config;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class GracefulShutdownListener implements ApplicationListener<ContextClosedEvent> {

  private static Logger logger = LoggerFactory.getLogger(GracefulShutdownListener.class);

  private final GracefulShutdownTomcatConnector gracefulShutdownTomcatConnector;

  public GracefulShutdownListener(
      GracefulShutdownTomcatConnector gracefulShutdownTomcatConnector) {
    this.gracefulShutdownTomcatConnector = gracefulShutdownTomcatConnector;
  }

  @Override
  public void onApplicationEvent(ContextClosedEvent event) {
    gracefulShutdownTomcatConnector.pauseConnector();
    shutdownApp();
  }

  private void shutdownApp() {
    ThreadPoolExecutor threadPoolExecutor = gracefulShutdownTomcatConnector.getThreadPoolExecutor()
        .orElseThrow(IllegalStateException::new);
    threadPoolExecutor.shutdown();
    try {
      if (threadPoolExecutor.awaitTermination(30, TimeUnit.SECONDS)) {
        logger.info("Graceful shutdown.");
      } else {
        logger.error("Not graceful shutdown. Active tasks {}", threadPoolExecutor.getActiveCount());
      }
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }
}
