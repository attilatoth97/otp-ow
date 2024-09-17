package hu.task.ow.service;

import static hu.task.ow.constant.WebShopConstant.REPORT_01_FILE_NAME;
import static hu.task.ow.constant.WebShopConstant.REPORT_02_FILE_NAME;
import static hu.task.ow.constant.WebShopConstant.TOP_FILE_NAME;

import hu.task.ow.mapper.ReportMapper;
import hu.task.ow.model.dto.PaymentDto;
import hu.task.ow.model.dto.PurchaseDto;
import hu.task.ow.model.dto.WebShopReportDto;
import hu.task.ow.util.CsvWriteUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

  private final ReportMapper reportMapper;
  private final DataReaderService dataReaderService;

  public void createReports() {
    var customers = dataReaderService.readCustomer();
    var payments = dataReaderService.readPayments();

    List<PurchaseDto> purchases = new ArrayList<>();
    for (var customer : customers) {
      var totalAmount = payments.stream()
          .filter(payment -> payment.getPaymentUniqueId().equals(customer.getCustomerUniqueId()))
          .map(PaymentDto::getAmount).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

      purchases.add(reportMapper.mapToPurchase(customer, totalAmount));
    }
    CsvWriteUtil.convertToCSV(
        reportMapper.mapToCsvDataFromPurchases(purchases), REPORT_01_FILE_NAME);

    List<PurchaseDto> purchaseTopNumberTwo = purchases.stream().sorted(
        Comparator.comparing(PurchaseDto::getTotalPurchases).reversed()).limit(2).toList();
    CsvWriteUtil.convertToCSV(
        reportMapper.mapToCsvDataFromPurchases(purchaseTopNumberTwo), TOP_FILE_NAME);

    Map<String, List<PaymentDto>> paymentsGroupByWebShop = payments.stream()
        .collect(Collectors.groupingBy(PaymentDto::getWebShopId));

    List<WebShopReportDto> webShopReports = new ArrayList<>();
    for (Entry<String, List<PaymentDto>> entry : paymentsGroupByWebShop.entrySet()) {
      String key = entry.getKey();
      List<PaymentDto> value = entry.getValue();

      webShopReports.add(reportMapper.mapToWebShopReportDto(key, value));
    }
    CsvWriteUtil.convertToCSV(
        reportMapper.mapToCsvDataFromWebShopReports(webShopReports), REPORT_02_FILE_NAME);

  }


}
