package com.baccredomatic;

import java.util.List;

/**
 * Interface a implementar para validadores de items particulares
 * @author rhernandezm
 *
 */
public interface ComplianceValidator {
	
	public List<String> validate(ValidationInput input);

}
