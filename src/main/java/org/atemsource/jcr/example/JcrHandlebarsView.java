package org.atemsource.jcr.example;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractTemplateView;

import com.github.jknack.handlebars.Context;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.ValueResolver;

/**
 * A handlebars view implementation.
 * 
 * @author edgar.espina
 * @since 0.1.0
 */
public class JcrHandlebarsView extends AbstractTemplateView {

	/**
	 * The compiled template.
	 */
	private Template template;

	/**
	 * The value's resolvers.
	 */
	private ValueResolver[] valueResolvers;

	/**
	 * Merge model into the view. {@inheritDoc}
	 */
	@Override
	protected void renderMergedTemplateModel(final Map<String, Object> model,
			final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		response.setCharacterEncoding("UTF-8");
		Context context = Context.newBuilder(model).resolver(valueResolvers)
				.build();
		try {
			template.apply(context, response.getWriter());
		} finally {
			context.destroy();
		}
	}

	/**
	 * @return The underlying template for this view.
	 */
	public Template getTemplate() {
		return template;
	}

	@Override
	public boolean checkResource(final Locale locale) {
		return template != null;
	}

	/**
	 * Set the compiled template.
	 * 
	 * @param template
	 *            The compiled template. Required.
	 */
	void setTemplate(final Template template) {
		this.template = notNull(template, "A handlebars template is required.");
	}

	/**
	 * Set the value resolvers.
	 * 
	 * @param valueResolvers
	 *            The value resolvers. Required.
	 */
	void setValueResolver(final ValueResolver... valueResolvers) {
		this.valueResolvers = notEmpty(valueResolvers,
				"At least one value-resolver must be present.");
	}

	@Override
	protected boolean isContextRequired() {
		return false;
	}
}
