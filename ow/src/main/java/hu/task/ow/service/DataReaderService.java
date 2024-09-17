package hu.task.ow.service;

import static hu.task.ow.constant.WebShopConstant.COMMA_DELIMITER;
import static hu.task.ow.constant.WebShopConstant.CUSTOMER_FILE;
import static hu.task.ow.constant.WebShopConstant.PAYMENTS_FILE;

import hu.task.ow.mapper.DataReaderMapper;
import hu.task.ow.model.dto.CustomerDto;
import hu.task.ow.model.dto.PaymentDto;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataReaderService {

  private final DataReaderMapper dataReaderMapper;

  List<CustomerDto> readCustomer() {
    List<CustomerDto> result = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(
        new InputStreamReader(new ClassPathResource(CUSTOMER_FILE).getInputStream()))) {
      String line;
      while ((line = br.readLine()) != null) {
        List<String> values = Arrays.stream(line.split(COMMA_DELIMITER)).toList();

        try {
          result.add(dataReaderMapper.mapToCustomer(values));
        } catch (Exception e) {
          log.error(e.toString());
          log.error(line);
        }
      }
      return result;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  List<PaymentDto> readPayments() {
    List<PaymentDto> result = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(
        new InputStreamReader(new ClassPathResource(PAYMENTS_FILE).getInputStream()))) {
      String line;
      while ((line = br.readLine()) != null) {
        List<String> values = Arrays.stream(line.split(COMMA_DELIMITER)).toList();

        try {
          result.add(dataReaderMapper.mapToPayment(values));
        } catch (Exception e) {
          log.error(e.toString());
          log.error(line);
        }
      }
      return result;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}
