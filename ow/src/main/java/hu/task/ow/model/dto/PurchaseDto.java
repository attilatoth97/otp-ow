package hu.task.ow.model.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PurchaseDto {

  private String clientName;
  private String clientAddress;
  private BigDecimal totalPurchases;

}
