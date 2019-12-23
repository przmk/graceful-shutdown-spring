package com.justpz.gracefulshutdownspring.machine;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

  @GetMapping("small")
  public String runSmallJob() throws InterruptedException {
    Thread.sleep(10_000);
    return String.format("small id [%d]", Thread.currentThread().getId());
  }

  @GetMapping("huge")
  public String runHugeJob() throws InterruptedException {
    Thread.sleep(50_000);
    return String.format("huge id [%d]", Thread.currentThread().getId());
  }

}
