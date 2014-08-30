package org.atemsource.jcr.example;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.atemsource.dynamic.RestService;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Helper;
import com.github.jknack.handlebars.JsonNodeValueResolver;
import com.github.jknack.handlebars.Options;
import com.github.jknack.handlebars.context.JavaBeanValueResolver;
import com.github.jknack.handlebars.context.MapValueResolver;
import com.github.jknack.handlebars.io.URLTemplateLoader;

@Component
public class JcrHandlebarsViewResolver extends AbstractTemplateViewResolver {

	
	@Inject
	private HandlebarsFactory handlebarsFactory;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AbstractUrlBasedView buildView(final String viewName)
			throws Exception {
		JcrHandlebarsView view = (JcrHandlebarsView) super.buildView(viewName);
		Handlebars handlebars = createHandleBars( viewName);
		try {
			view.setTemplate(handlebars.compile("components/outer"));
			view.setValueResolver(MapValueResolver.INSTANCE,
					JavaBeanValueResolver.INSTANCE,
					JsonNodeValueResolver.INSTANCE);
			return view;
		} catch (IOException ex) {
			throw ex;
		}

	}

	

	@Override
	protected Class getViewClass() {
		return requiredViewClass();
	}



	@Override
	protected Class requiredViewClass() {
		return JcrHandlebarsView.class;
	}

	public Handlebars createHandleBars( String viewName) {
		return handlebarsFactory.createHandleBarsWithOuter( viewName);
	}

}
