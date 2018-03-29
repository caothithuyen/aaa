package webhoithao.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import webhoithao.model.Account;
import webhoithao.model.News;
import webhoithao.service.AccountService;
import webhoithao.service.NewsService;

@Controller

public class AdminController {
	@Autowired
	private NewsService newsService;
	private AccountService accountService;
	@GetMapping("/index")
	public String AllNews(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		//if (username == null) {
			//response.sendRedirect("/login");
		//}
		request.setAttribute("lst_news", newsService.findAll());
		return "index";
	}
	
	@GetMapping("/create-news")
	public String CreateNews(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		if (username == null) {
			response.sendRedirect("/login");
		}
		request.setAttribute("message", null);
		return "create-new";
	}
	@RequestMapping(value = "/save-news", method = RequestMethod.POST)
	public String SaveNews(@ModelAttribute News news, BindingResult bindingResult, HttpServletRequest request) {
		String announce = null;
		if(news.getId()==0) {
			news.setDate(java.time.LocalDate.now().toString());
			Account acc = accountService.GetAccountSession(request);
			
			news.setIduser(acc.getIduser());
			announce = "Add";
		}
		else {
			announce = "Update";}
		/*
		 * System.out.println("id: " + news.getId()); System.out.println("title: " +
		 * news.getTitle()); System.out.println("content: " + news.getContent());
		 * System.out.println("date: " + news.getDate_created());
		 * System.out.println("username: " + news.getUsername());
		 */
		try {
			newsService.save(news);
			request.setAttribute("message", announce + " successfully");
			System.out.println(announce);
		} catch (Exception ex) {
			request.setAttribute("message", "Error!");
		}

		request.setAttribute("lst_news", newsService.findAll());
		return "index";
	}
	@GetMapping("/update-news")
	public String UpdateNews(@RequestParam int id, HttpServletRequest request) {
		request.setAttribute("message", null);
		request.setAttribute("news", newsService.findNews(id));
		// request.setAttribute("mode", "MODE_NEW");
		return "update-news";
	}

	//
	@GetMapping("/delete-news")
	public String DeleteNews(@RequestParam int id, HttpServletRequest request) {
		try {
			newsService.delete(id);
			request.setAttribute("message", "Deleted successfully");
		} catch (Exception ex) {
			request.setAttribute("message", "!Error! - Can not delete");
		}
		// request.setAttribute("mode", "MODE_NEW");
		request.setAttribute("lst_news", newsService.findAll());
		return "index";
	}

}
