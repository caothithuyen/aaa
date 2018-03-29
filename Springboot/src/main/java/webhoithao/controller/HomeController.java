package webhoithao.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import webhoithao.model.News;
import webhoithao.service.NewsService;

@Controller

public class HomeController {
	@Autowired
	private NewsService newsService;
	
	@GetMapping("/")
    public String home(HttpServletRequest request) {
	request.setAttribute("lst_news", newsService.findAll());
	return "Home";
	}
	@GetMapping("/keynote")
    public String speaker(HttpServletRequest request) {
	request.setAttribute("lst_news", newsService.findAll());
	return "KeyNote";
    }
	@GetMapping("/contact")
    public String contact(HttpServletRequest request) {
	request.setAttribute("lst_news", newsService.findAll());
	return "Contact";
    }
	@GetMapping("/venue-hotel")
    public String venue_hotel(HttpServletRequest request) {
	request.setAttribute("lst_news", newsService.findAll());
	return "VenueAndHotel";
    }
	@GetMapping("/about")
    public String About(HttpServletRequest request) {
	request.setAttribute("lst_news", newsService.findAll());
	return "About";
    }
	@GetMapping("/news")
    public String News(@RequestParam int id, HttpServletRequest request) {
	request.setAttribute("currnews", newsService.findNews(id));
	request.setAttribute("lst_news", newsService.findAll());
	return "Home1";
    }
	
	

}
