package online.lucianofelix.yahoo;

import java.io.IOException;
import java.math.BigDecimal;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class SingleStock {

	public SingleStock() {

	}

	public static void main(String[] args) {

		try {
			Stock stock = YahooFinance.get("TSLA");
			BigDecimal price = stock.getQuote().getPrice();
			BigDecimal change = stock.getQuote().getChangeInPercent();
			BigDecimal peg = stock.getStats().getPeg();
			BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();
			stock.print();
			System.out.println("preço " + price);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/**
		 * <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.jr/jackson-jr-objects API veloz de JSON-->
		<dependency>
		    <groupId>com.fasterxml.jackson.jr</groupId>
		    <artifactId>jackson-jr-objects</artifactId>
		    <version>2.10.2</version>
		<!-- Exclui biblioteca em conflito com a api do yahoo finance-->	    
	    <exclusions>
	      <exclusion> 
	        <groupId>org.slf4j</groupId>
	        <artifactId>slf4j-log4j12</artifactId>
	      </exclusion>
	      <exclusion> 
	        <groupId>log4j</groupId>
	        <artifactId>log4j</artifactId>
	      </exclusion>
    	</exclusions>
		</dependency>
		<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.jr/jackson-jr-objects API veloz de JSON-->
		<dependency>
    		<groupId>com.fasterxml.jackson.core</groupId>
    		<artifactId>jackson-annotations</artifactId>
    		<version>2.10.2</version>
		</dependency>
		<dependency>
    		<groupId>com.fasterxml.jackson.core</groupId>
    		<artifactId>jackson-databind</artifactId>
    		<version>2.10.2</version>
		</dependency>
		 */
	}

}
