package org.atemsource.jcr.example;

import org.atemsource.atem.api.attribute.Attribute;
import org.atemsource.atem.utility.transform.api.AttributeNameConverter;

public class JcrAttributeNameConverter implements AttributeNameConverter {

	public String convert(Attribute<?, ?> attribute) {
		String code = attribute.getCode();
		if (code.equals("path")) {
			return "url";
		} else {
			return code;
		}
	}

}
