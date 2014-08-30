package org.atemsource.jcr.example;

import java.io.IOException;
import java.util.Iterator;

import org.atemsource.atem.api.infrastructure.exception.TechnicalException;
import org.atemsource.atem.impl.dynamic.DynamicEntity;
import org.atemsource.atem.impl.dynamic.DynamicEntityImpl;
import org.atemsource.atem.impl.json.JsonUtils;
import org.atemsource.atem.utility.transform.api.JavaConverter;
import org.atemsource.atem.utility.transform.api.TransformationContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

public class ObjectNodeToMapConverter implements JavaConverter<String,DynamicEntity> {
	
	private ObjectMapper objectMapper=new ObjectMapper();

	public DynamicEntity convertAB(String a, TransformationContext ctx) {
		if (a==null) {
			return null;
		}
		DynamicEntity value = new DynamicEntityImpl();
		ObjectNode node;
		try {
			node = (ObjectNode) objectMapper.readTree(a);
			Iterator<String> fieldNames = node.getFieldNames();
			for (;fieldNames.hasNext();) {
				String key = fieldNames.next();
				JsonNode jsonNode = node.get(key);
				value.put(key,JsonUtils.convertToJava(jsonNode));
			}
			return value;
		} catch (JsonProcessingException e) {
			throw new TechnicalException("cannot red json",e);
		} catch (IOException e) {
			throw new TechnicalException("cannot red json",e);
		}
		
	}

	public String convertBA(DynamicEntity b, TransformationContext ctx) {
		throw new UnsupportedOperationException("not implemented yet");
	}

}
