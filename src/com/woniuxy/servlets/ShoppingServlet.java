package com.woniuxy.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.winiuxy.entitys.Shoppings;
import com.woniuxy.daos.ShoppingDao;

/**
 * Servlet implementation class ShoppingServlet
 */
@WebServlet({"/addShopping.do","/getShopping.do","/updateShoppingCount.do","/getSubShopping.do"})
public class ShoppingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String path = request.getServletPath();
		System.out.println(path);
		
		if (path.equals("/addShopping.do")) {
			//接受到时为
			int cusId = Integer.parseInt((String) request.getSession(false).getAttribute("cusId"));
			ShoppingDao sd = new ShoppingDao();
			
			String goodsId = request.getParameter("goodsId");
			
			Shoppings shoppings = new Shoppings(Integer.parseInt(goodsId),cusId);
			
			sd.addShopping(shoppings);
			
		}
		else if (path.equals("/getShopping.do")) {
			response.setContentType("text/html;charset=utf-8");
			int cusId = Integer.parseInt((String) request.getSession(false).getAttribute("cusId"));
			ShoppingDao sd = new ShoppingDao();
			
			List<Shoppings> list = sd.getGoodsByCusId(cusId);
			
			String cusName = (String) request.getSession(false).getAttribute("cusName");
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Shoppings", list);
			map.put("cusName", cusName);
			JSONObject js = new JSONObject(map);
			PrintWriter out = response.getWriter();
			out.print(js);
			out.flush();
			out.close();
		}
		else if (path.equals("/updateShoppingCount.do")) {
			response.setContentType("text/html;charset=utf-8");
			String shoppingId = request.getParameter("shoppingId");
			String shoppingCount = request.getParameter("shoppingCount");
			
			
			ShoppingDao sd = new ShoppingDao();
			boolean isTrue = sd.updateShoppingCount(Integer.parseInt(shoppingId), Integer.parseInt(shoppingCount));
			PrintWriter out = response.getWriter();
			out.print(isTrue);
			out.flush();
			out.close();
			
		}
		else if (path.equals("/getSubShopping.do")) {
			response.setContentType("text/html;charset=utf-8");
			
			String sids = request.getParameter("sids");
			ShoppingDao sd = new ShoppingDao();
			
			List<Shoppings> list = sd.getSubShoppings(sids);
			
			String cusName = (String)request.getSession().getAttribute("cusName");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("shoppings", list);
			map.put("cusName", cusName);
			
			JSONObject js = new JSONObject(map);
			PrintWriter out = response.getWriter();
			out.print(js);
			out.flush();
			out.close();
			
			
		}
		
		
		
		
		
		
		
	}

}
