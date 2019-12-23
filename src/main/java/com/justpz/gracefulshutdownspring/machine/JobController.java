package com.justpz.gracefulshutdownspring.machine;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

  private static Logger logger = LoggerFactory.getLogger(JobController.class);

  @GetMapping("small")
  public String runSmallJob() throws InterruptedException {
    String uuid = UUID.randomUUID().toString();
    logger.debug("start small job {}", uuid);
    Thread.sleep(10_000);
    logger.debug("end small job {}", uuid);
    return String.format("small id [%d] uuid [%s]", Thread.currentThread().getId(), uuid);
  }

  @GetMapping("huge")
  public String runHugeJob() throws InterruptedException {
    String uuid = UUID.randomUUID().toString();
    logger.debug("start huge job {}", uuid);
    Thread.sleep(50_000);
    logger.debug("end huge job {}", uuid);
    return String.format("huge id [%d] uuid [%s]", Thread.currentThread().getId(), uuid);
  }

}
