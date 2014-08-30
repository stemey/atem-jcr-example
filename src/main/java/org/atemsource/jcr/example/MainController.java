package org.atemsource.jcr.example;

import java.io.IOException;

import javax.inject.Inject;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.ValueFormatException;

import org.atemsource.atem.impl.dynamic.DynamicEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Scope("request")
@Controller
public class MainController {

	@Inject
	private RepositoryHelper repositoryHelper;

	@Inject
	private PageRenderer pageRenderer;

	@RequestMapping(value = "/index.html", method = RequestMethod.GET)
	public ModelAndView product()
			throws ValueFormatException, PathNotFoundException,
			RepositoryException, IOException {

		DynamicEntity page = repositoryHelper.get("/index.html");

		return pageRenderer.renderPage(page);
		
	}
}
