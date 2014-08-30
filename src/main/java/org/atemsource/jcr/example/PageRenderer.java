package org.atemsource.jcr.example;

import java.io.IOException;

import javax.inject.Inject;
import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.RepositoryException;

import org.atemsource.atem.impl.dynamic.DynamicEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class PageRenderer {

	@Inject
	private NavbarRenderer navbarRenderer;

	public ModelAndView renderPage(DynamicEntity page) throws LoginException, NoSuchWorkspaceException, IOException, RepositoryException {
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.setViewName(page.getTypeCode().substring(
				"map::jcr:".length()));

		modelAndView.addAllObjects(page.getEntity("outer"));
		modelAndView.addObject("inner", page);

		String navbarHtml = navbarRenderer.renderNavbar(page);
		modelAndView.addObject("navbar", navbarHtml);
		
		return modelAndView;
	}
}
