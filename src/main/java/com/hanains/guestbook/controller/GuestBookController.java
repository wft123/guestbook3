package com.hanains.guestbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hanains.guestbook.dao.GuestBookDao;
import com.hanains.guestbook.vo.GuestBookVo;

@Controller
public class GuestBookController {
	
	@Autowired
	private GuestBookDao dao;
	
	@RequestMapping("/")
	public String index(Model model){
		List<GuestBookVo> list = dao.getGuestBook();
		model.addAttribute("list",list);
		return "/WEB-INF/views/index.jsp";
	}

	@RequestMapping(value="/add", method=RequestMethod.POST )
	public String add(@ModelAttribute GuestBookVo vo){
		dao.add(vo);
		return "redirect:/";
	}
	
	@RequestMapping("/deleteform")
	public String deleteform(Model model, @RequestParam("no") int no){
		model.addAttribute("no", no);
		return "/WEB-INF/views/deleteform.jsp";
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST )
	public String delete(Model model,@RequestParam("no") int no, @RequestParam("password") String password){
		int result = dao.delete(no, password);
		if(result>0){
			return "redirect:/";
		}
		model.addAttribute("no",no);
		model.addAttribute("msg","true");
		return "/WEB-INF/views/deleteform.jsp";
	}
	
}
