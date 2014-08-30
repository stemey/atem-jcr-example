package org.atemsource.jcr.example;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;

import org.atemsource.atem.impl.dynamic.DynamicEntity;
import org.atemsource.atem.impl.dynamic.DynamicEntityImpl;
import org.springframework.stereotype.Component;

@Component
public class BreadcrumbRenderer {

	@Inject
	private RepositoryHelper repositoryHelper;

	public void renderProduct(String categoryPath, DynamicEntity product)
			throws PathNotFoundException, RepositoryException {
		DynamicEntity category = repositoryHelper.get(categoryPath);
		DynamicEntity home = repositoryHelper.get("/index.html");
		DynamicEntity outer = product.getEntity("outer");

		List<DynamicEntity> breadcrumbs = new ArrayList<DynamicEntity>(3);
		breadcrumbs.add(home);
		breadcrumbs.add(category);
		breadcrumbs.add(product);
		
		((DynamicEntity) outer.get("breadcrumb")).put("breadcrumbs",
				breadcrumbs);
	}

	public void renderCategory(DynamicEntity category)
			throws PathNotFoundException, RepositoryException {
		DynamicEntity home = repositoryHelper.get("/index.html");

		List<DynamicEntity> breadcrumbs = new ArrayList<DynamicEntity>(3);
		breadcrumbs.add(home);
		breadcrumbs.add(category);
		DynamicEntity breadcrumb = new DynamicEntityImpl();
		breadcrumb.put("breadcrumbs", breadcrumbs);

		category.getEntity("outer").getEntity("breadcrumb").put("breadcrumbs", breadcrumbs);

	}
}
