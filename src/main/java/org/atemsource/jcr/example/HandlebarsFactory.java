package org.atemsource.jcr.example;

import java.io.IOException;

import javax.inject.Inject;

import org.atemsource.dynamic.RestService;
import org.springframework.stereotype.Component;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.io.URLTemplateLoader;

@Component
public class HandlebarsFactory {
	
	@Inject
	private RestService restService;
	
	public Handlebars createHandleBarsWithOuter( String viewName) {
		// Creates a new template loader.
		URLTemplateLoader templateLoader = createTemplateLoader(
				viewName);

		// Creates a new handlebars object.
		Handlebars handlebars = new Handlebars(templateLoader);

		// set delete partial after merge
		handlebars.setDeletePartialAfterMerge(false);

		registerHelpers(handlebars);

		return handlebars;
	}

	public Handlebars createHandleBars() {
		return createHandleBarsWithOuter(null);
	}
	private void registerHelpers(Handlebars handlebars) {
		handlebars.registerHelper("gt", new Helper() {

			public CharSequence apply(Object context, Options options)
					throws IOException {
				boolean gt = ((Number) context).intValue() > ((Number) options.param(0)).intValue();
				if (gt) {
					return options.fn(context);
				} else {
					return null;
				}
			}
		});
		handlebars.registerHelper("gte", new Helper() {

			public CharSequence apply(Object context, Options options)
					throws IOException {
				boolean gte =  ((Number) context).intValue() > ((Number) options.param(0)).intValue();
				if (gte) {
					return options.fn(context);
				} else {
					return null;
				}
			}
		});
		handlebars.registerHelper("equals", new Helper() {

			public CharSequence apply(Object context, Options options)
					throws IOException {
				if (options.params.length!=1) {
					return "";
				}
				boolean equal = context.equals(options.param(0));
				if (equal) {
					return options.fn(context);
				} else {
					return null;
				}
			}
		});
		handlebars.registerHelper("link", new Helper() {

			public CharSequence apply(Object context, Options options)
					throws IOException {	
				return "http://localhost:8080/site"+context;
			}
		});
	}
	
	/**
	 * Creates a new template loader.
	 * 
	 * @param context
	 *            The application's context.
	 * @return A new template loader.
	 */
	protected URLTemplateLoader createTemplateLoader(
			String viewName) {
		JcrTemplateLoader templateLoader = new JcrTemplateLoader(restService);
		templateLoader.addAlias("rating", "components/rating");
		templateLoader.addAlias("carousel", "components/product-carousel");
		templateLoader.addAlias("breadcrumb", "components/breadcrumb");
		templateLoader.addAlias("category", "components/product-grid");
		templateLoader.addAlias("navbar", "components/navbar");
		
		if (viewName!=null) {
		templateLoader.addAlias("inner", viewName);
		}
		// Override prefix and suffix.
		templateLoader.setPrefix("");
		templateLoader.setSuffix("");
		return templateLoader;
	}
}
