package hu.task.ow.mapper;

import hu.task.ow.model.dto.CustomerDto;
import hu.task.ow.model.dto.PaymentDto;
import hu.task.ow.model.enumeration.PaymentMethodType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DataReaderMapper {

  public CustomerDto mapToCustomer(List<String> element) {
    var customer = new CustomerDto();
    customer.setWebShopId(element.get(0));
    customer.setClientId(element.get(1));
    customer.setClientName(element.get(2));
    customer.setClientAddress(element.get(3));
    return customer;
  }

  public PaymentDto mapToPayment(List<String> element) {
    var customer = new PaymentDto();
    customer.setWebShopId(element.get(0));
    customer.setClientId(element.get(1));
    customer.setPaymentMethod(PaymentMethodType.valueOf(element.get(2)));
    customer.setAmount(new BigDecimal(element.get(3)));
    customer.setBankAccountNumber(element.get(4));
    customer.setCardNumber(element.get(5));
    customer.setDateOfPayment(
        LocalDate.parse(element.get(6), DateTimeFormatter.ofPattern("yyyy.MM.dd"))
    );
    return customer;
  }
}
