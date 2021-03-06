package com.portfolio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.portfolio.model.dao.GoodsRepository;
import com.portfolio.model.dao.UserRepository;
import com.portfolio.model.entity.Goods;
import com.portfolio.model.entity.User;
import com.portfolio.model.form.GoodsForm;
import com.portfolio.model.form.LoginForm;

@Controller
@RequestMapping("/ecsite/admin")
public class AdminController {
	
	@Autowired
	private UserRepository userRepos;
	
	@Autowired
	private GoodsRepository goodsRepos;
	
	@RequestMapping("/")
	public String index() {
		return "adminindex";
	}
	
	//ログイン処理
	@PostMapping("/welcome")
	public String welcome(LoginForm form, Model m) {
		List<User> users= userRepos.findByUserNameAndPassword(form.getUserName(),form.getPassword());
		
		if (users != null && users.size() > 0) {
			boolean isAdmin = users.get(0).getIsAdmin() != 0;
			if(isAdmin) {
				List<Goods> goods = goodsRepos.findAll();
				m.addAttribute("userName", users.get(0).getUserName());
				m.addAttribute("password",users.get(0).getPassword());
				m.addAttribute("goods", goods);
			}
		}
		return "welcome";
	}
	
	//商品登録
	@RequestMapping("/goodsMst")
	public String goodsMst(LoginForm loginForm, Model m) {
		m.addAttribute("userName", loginForm.getUserName());
		m.addAttribute("password",loginForm.getPassword());
		
		return "goodsmst";
	}
	
	//登録内容をDBに反映
	//-------------------------------変更中--------------------------------
	//１．画像をフォルダに格納
	//２．フォルダのパスor名前をDBに保存
	@RequestMapping("/addGoods")
	public String addGoods(GoodsForm goodsForm, LoginForm loginForm, Model m) {
		m.addAttribute("userName", loginForm.getUserName());
		m.addAttribute("password",loginForm.getPassword());
		
		Goods goods = new Goods();
		goods.setGoodsName(goodsForm.getGoodsName());
		goods.setPrice(goodsForm.getPrice());
		goodsRepos.saveAndFlush(goods);
		
		return "forward:/ecsite/admin/welcome";
	}
	
	//商品削除
	@ResponseBody
	@PostMapping("/api/deleteGoods")
	public String deleteApi(@RequestBody GoodsForm goodsForm, Model m) {
		try {
			goodsRepos.deleteById(goodsForm.getId());
		} catch (IllegalArgumentException e) {
			return "-1";
		}
		
		return "1";
	}
}
