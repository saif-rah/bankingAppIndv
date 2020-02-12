package com.example.simplemongo;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.server.TServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.servlet.Servlet;

@SpringBootApplication
public class SimpleMongoApplication{

	public static void main(String[] args) {
		SpringApplication.run(SimpleMongoApplication.class, args);

	}

	@Autowired
	private ApplicationContext applicationContext;

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		TProcessor processor =
				new BankingService.Processor<BankingServiceHandler>(applicationContext.getBean(BankingServiceHandler.class));
		TProtocolFactory protoFactory = new TJSONProtocol.Factory();
		Servlet bankingServlet = new TServlet(processor, protoFactory);
		return new ServletRegistrationBean(bankingServlet, "/banking/*");
	}

}
