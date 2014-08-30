package org.atemsource.jcr.example;

import java.io.IOException;
import java.util.Collection;

import javax.inject.Inject;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;

import org.atemsource.atem.impl.dynamic.DynamicEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Scope("request")
@Controller
public class CategoryController {

	@Inject
	private RepositoryHelper repositoryHelper;

	@Inject
	private PageRenderer pageRenderer;

	@Inject
	private BreadcrumbRenderer breadcrumbRenderer;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView product(@PathVariable("id") String id)
			throws ValueFormatException, PathNotFoundException,
			RepositoryException, IOException {
		
		DynamicEntity category = repositoryHelper.get("/" + id);
		

		Collection<DynamicEntity> rows = (Collection<DynamicEntity>) category
				.getEntity("category").get("rows");
		if (rows != null) {
			for (DynamicEntity row : rows) {
				repositoryHelper.loadRefs(row, "columns", "jcr:product");
			}
		}
		
		breadcrumbRenderer.renderCategory( category);



		return pageRenderer.renderPage(category);

	}
}
