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
public class ProductController {

	@Inject
	private RepositoryHelper repositoryHelper;

	@Inject
	private PageRenderer pageRenderer;

	@Inject
	private BreadcrumbRenderer breadcrumbRenderer;

	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
	public ModelAndView product(@PathVariable("id") String id)
			throws ValueFormatException, PathNotFoundException,
			RepositoryException, IOException {

		DynamicEntity product = repositoryHelper.get("/product/" + id);

		Collection<DynamicEntity> rows = (Collection<DynamicEntity>) ((DynamicEntity)product
				.get("carousel")).get("rows");
		if (rows != null) {
			for (DynamicEntity row : rows) {
				repositoryHelper.loadRefs(row, "columns", "jcr:product");
			}
		}
		
		
		breadcrumbRenderer.renderProduct("/toys",product);
		

		return pageRenderer.renderPage(product);
	}
}
