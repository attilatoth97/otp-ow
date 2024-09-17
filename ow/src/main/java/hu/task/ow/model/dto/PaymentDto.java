package hu.task.ow.model.dto;

import hu.task.ow.model.enumeration.PaymentMethodType;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class PaymentDto {

  private String webShopId;
  private String clientId;
  private PaymentMethodType paymentMethod;
  private BigDecimal amount;
  private String bankAccountNumber;
  private String cardNumber;
  private LocalDate dateOfPayment;

  public String getPaymentUniqueId() {
    return webShopId + clientId;
  }
}
