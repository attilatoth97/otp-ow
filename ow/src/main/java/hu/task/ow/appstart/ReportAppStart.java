package hu.task.ow.appstart;

import hu.task.ow.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportAppStart implements ApplicationListener<ApplicationStartedEvent> {

  private final ReportService reportService;

  @Override
  public void onApplicationEvent(ApplicationStartedEvent event) {
    reportService.createReports();
    System.exit(0);
  }
}
