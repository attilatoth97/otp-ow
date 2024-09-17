package hu.task.ow.model.dto;

import lombok.Data;

@Data
public class CustomerDto {

  private String webShopId;
  private String clientId;
  private String clientName;
  private String clientAddress;

  public String getCustomerUniqueId() {
    return webShopId + clientId;
  }
}
