package hu.task.ow.model.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class WebShopReportDto {

  private String webShopId;
  private BigDecimal amountOfCardPurchases;
  private BigDecimal amountOfTransferPurchases;

}
