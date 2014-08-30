package org.atemsource.jcr.example;

import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;
import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;

import org.atemsource.atem.impl.dynamic.DynamicEntity;
import org.springframework.stereotype.Component;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;

@Component
public class NavbarRenderer {

	@Inject
	private HandlebarsFactory handlebarsFactory;

	@Inject
	private RepositoryHelper repositoryHelper;

	public String renderNavbar(Map<String, Object> ctx) throws IOException,
			LoginException, NoSuchWorkspaceException, RepositoryException {

		Handlebars handlebars = handlebarsFactory.createHandleBars();

		Template template = handlebars.compile("components/navbar");

		DynamicEntity navbar = repositoryHelper.get("/components/navbar");

		repositoryHelper.loadRefs(navbar, "items", "jcr:main/category");

		navbar.putAll(ctx);

		return template.apply(navbar);

	}

}
