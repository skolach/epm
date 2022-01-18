package com.epam.training.xmldemo;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.epam.training.xmldemo.entity.order.Currency;
import com.epam.training.xmldemo.entity.order.Order;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonDemo {

	public static void main(String[] args) throws Exception {
//		Orders orders = null;
		Order order = new Order();
		order.setNo(new BigInteger("1"));
		Order.TotalPrice total = new Order.TotalPrice();
		total.setCurrency(Currency.UAH);
		total.setValue(new BigDecimal(50));
		order.setTotalPrice(total);
		
//		orders.getOrder().add(order);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.writeValue(new File("order.json"), order);
	}
}
