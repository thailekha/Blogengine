package controllers;

import models.Page;
import play.mvc.Controller;

public class PageRender extends Controller {

	public static void index(String pageLink)
	{
		Page page = Page.find("byPageLink", pageLink).first();
		render(page);
	}
}
