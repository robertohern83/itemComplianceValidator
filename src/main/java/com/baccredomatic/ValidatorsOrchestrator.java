package com.baccredomatic;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidatorsOrchestrator {

	List<ComplianceValidator> validators;
	
	@Autowired
	public ValidatorsOrchestrator(List<ComplianceValidator> pValidators) {
		this.validators = pValidators;
	}
	
	public void showValidators(){
		System.out.println("Actualmente hay " + this.validators.size() + " validadores de código implementados");
		this.validators.stream().forEach(v -> System.out.println(v));
	}
	
	public List<String> executeValidators(InputStream inputStream ){
		return this.validators.stream().map(v -> v.validate(inputStream)).flatMap(x -> x.stream()).collect(Collectors.toList());
	}
}
