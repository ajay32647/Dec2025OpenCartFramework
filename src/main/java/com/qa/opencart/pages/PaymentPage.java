package com.qa.opencart.pages;

import org.openqa.selenium.By;

public class PaymentPage {

	
	private By loc= By.id("payment");
	
	public void getPayment() {
		System.out.println("Payment is successfull...."+loc);
	}
}
