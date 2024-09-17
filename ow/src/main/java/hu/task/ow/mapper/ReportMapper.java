package hu.task.ow.mapper;

import hu.task.ow.model.dto.CustomerDto;
import hu.task.ow.model.dto.PaymentDto;
import hu.task.ow.model.dto.PurchaseDto;
import hu.task.ow.model.dto.WebShopReportDto;
import hu.task.ow.model.enumeration.PaymentMethodType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReportMapper {

  public PurchaseDto mapToPurchase(CustomerDto customerDto, BigDecimal total) {
    var purchase = new PurchaseDto();
    purchase.setClientName(customerDto.getClientName());
    purchase.setClientAddress(customerDto.getClientAddress());
    purchase.setTotalPurchases(total);
    return purchase;
  }

  public List<List<String>> mapToCsvDataFromPurchases(List<PurchaseDto> purchaseDtoList) {
    List<List<String>> result = new ArrayList<>();
    for (var purchaseDto : purchaseDtoList) {
      result.add(mapToCsvDataFromPurchase(purchaseDto));
    }
    return result;
  }

  private List<String> mapToCsvDataFromPurchase(PurchaseDto purchaseDto) {
    List<String> result = new ArrayList<>();
    result.add(purchaseDto.getClientName());
    result.add(purchaseDto.getClientAddress());
    result.add(purchaseDto.getTotalPurchases().toString());
    return result;
  }

  public List<List<String>> mapToCsvDataFromWebShopReports(List<WebShopReportDto> webShopReportDtoList) {
    List<List<String>> result = new ArrayList<>();
    for (var webShopReportDto : webShopReportDtoList) {
      result.add(mapToCsvDataFromWebShopReport(webShopReportDto));
    }
    return result;
  }

  private List<String> mapToCsvDataFromWebShopReport(WebShopReportDto webShopReportDto) {
    List<String> result = new ArrayList<>();
    result.add(webShopReportDto.getWebShopId());
    result.add(webShopReportDto.getAmountOfCardPurchases().toString());
    result.add(webShopReportDto.getAmountOfTransferPurchases().toString());
    return result;
  }
  
  public WebShopReportDto mapToWebShopReportDto(String webShopId, List<PaymentDto> payments) {
    var result = new WebShopReportDto();
    result.setWebShopId(webShopId);
    result.setAmountOfTransferPurchases(
        payments.stream()
            .filter(payment -> payment.getPaymentMethod() == PaymentMethodType.transfer)
            .map(PaymentDto::getAmount).reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO)
    );
    result.setAmountOfCardPurchases(
        payments.stream()
            .filter(payment -> payment.getPaymentMethod() == PaymentMethodType.card)
            .map(PaymentDto::getAmount).reduce(BigDecimal::add)
            .orElse(BigDecimal.ZERO));
    return result;
  }

}
