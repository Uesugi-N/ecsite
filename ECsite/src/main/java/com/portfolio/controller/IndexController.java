package com.portfolio.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.portfolio.model.dao.GoodsRepository;
import com.portfolio.model.dao.PurchaseRepository;
import com.portfolio.model.dao.RegisterRepository;
import com.portfolio.model.dao.UserRepository;
import com.portfolio.model.dto.HistoryDto;
import com.portfolio.model.dto.LoginDto;
import com.portfolio.model.entity.Goods;
import com.portfolio.model.entity.Purchase;
import com.portfolio.model.entity.User;
import com.portfolio.model.form.CartForm;
import com.portfolio.model.form.HistoryForm;
import com.portfolio.model.form.LoginForm;
import com.portfolio.model.form.UserForm;

@Controller
@RequestMapping("/ecsite")
public class IndexController {
	
	@Autowired
	private GoodsRepository goodsRepos;

	@Autowired
	private UserRepository userRepos;
	
	@Autowired
	private RegisterRepository registerRepos;
	
	@Autowired
	private PurchaseRepository purchaseRepos;
	
	private Gson gson = new Gson();
	
	@RequestMapping("/")
	public String index(Model m) {
		List<Goods> goods = goodsRepos.findAll();
		m.addAttribute("goods", goods);
		
		return "index";
	}
	
	@ResponseBody
	@PostMapping("/api/login")
	public String loginApi(@RequestBody LoginForm loginForm) {
		List<User> users = userRepos.findByUserNameAndPassword(loginForm.getUserName(),loginForm.getPassword());
		
		LoginDto dto = new LoginDto(0, null, null, "ƒQƒXƒg");
		if(users.size() > 0) {
			dto = new LoginDto(users.get(0)); 
		}
		
		return gson.toJson(dto);
		
	}
	
	@RequestMapping("/register")
	public String register() {
		return "register";
	}
	
	@RequestMapping("/addUser")
	public String addUser(UserForm userForm) {
		User user = new User();
		user.setUserName(userForm.getUserName());
		user.setPassword(userForm.getPassword());
		user.setFullName(userForm.getFullName());
		registerRepos.saveAndFlush(user);
		
		return "forward:/ecsite/";
	}
	
	@ResponseBody
	@PostMapping("/api/purchase")
	public String purchaseApi(@RequestBody CartForm cartForm) {
		
		cartForm.getCartList().forEach((c) -> {
		long total = c.getPrice() * c.getCount();
		purchaseRepos.persist(cartForm.getUserId(),c.getId(),c.getGoodsName(),c.getCount(),total);
		});
		
		return String.valueOf(cartForm.getCartList().size());
		
	}
	
	@ResponseBody
	@PostMapping("/api/history")
	public String historyApi(@RequestBody HistoryForm historyForm) {
		String userId = historyForm.getUserId();
		List<Purchase> history = purchaseRepos.findHistory(Long.parseLong(userId));
		List<HistoryDto> historyDtoList = new ArrayList<>();
		history.forEach((v) -> {
			HistoryDto dto = new HistoryDto(v);
			historyDtoList.add(dto);
		});
		
		return gson.toJson(historyDtoList);
	}
	
}
	

