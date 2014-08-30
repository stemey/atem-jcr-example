package org.atemsource.jcr.example;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.atemsource.dynamic.RestService;
import org.codehaus.jackson.node.ObjectNode;

import com.github.jknack.handlebars.io.StringTemplateSource;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.github.jknack.handlebars.io.TemplateSource;
import com.github.jknack.handlebars.io.URLTemplateLoader;

public class JcrTemplateLoader extends URLTemplateLoader {

	private RestService restService;
	
	private Map<String,String> aliases = new HashMap<String, String>();

	public JcrTemplateLoader(RestService restService) {
		super();
		this.restService = restService;
	}

	public void setRestService(RestService restService) {
		this.restService = restService;
	}

	public TemplateSource sourceAt(String location) throws IOException {
		if (aliases.containsKey(location)) {
			location=aliases.get(location);	
		}
		ObjectNode schema = restService.getSchema(location);
		if (schema==null) {
			throw new IllegalArgumentException("cannot find schema "+location);
		}
		return new StringTemplateSource(location, schema.get("sourceCode")
				.getTextValue());
	}

	public String resolve(String location) {
		return location;
	}

	private String prefix;

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	private String suffix;

	@Override
	protected URL getResource(String location) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	public void addAlias(String alias, String path) {
		aliases.put(alias,path);
		
	}

}
