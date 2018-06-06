package com.baccredomatic;

import java.io.InputStream;
import java.util.List;

public interface ComplianceValidator {
	
	public List<String> validate(InputStream data);

}
