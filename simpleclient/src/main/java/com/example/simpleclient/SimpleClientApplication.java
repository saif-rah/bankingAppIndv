package com.example.simpleclient;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimpleClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleClientApplication.class, args);
	}

}
