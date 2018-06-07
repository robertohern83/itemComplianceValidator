package com.baccredomatic;

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
	
	public List<String> executeValidators(ValidationInput input ){
		return this.validators.stream().map(v -> v.validate(input)).flatMap(x -> x.stream()).collect(Collectors.toList());
	}
}
