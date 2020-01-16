package com.woniuxy.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.winiuxy.entitys.Orders;
import com.woniuxy.tools.ConnectionManager;

public class OrdersDao {

	public String addOrders(Orders order,String sids) {
		Connection conn = ConnectionManager.getConnection();
		
		/**
		 * 关闭自动提交事务的模式
		 * */
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		String orderId = getOrderId();
		
		String sql = "";
		
		sql = sql + "insert into orders(orders_id,cus_id,orders_time,orders_price,orders_status,orders_remark) value(?,?,now(),?,'未支付',?)";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, orderId);
			ps.setInt(2, order.getCusId());
			ps.setFloat(3, order.getOrdersPrice());
			ps.setString(4, order.getOrdersRemark());
			
			
			
			String sql2="insert into ordersdetail(orders_id,goods_id,ordersDetail_price,ordersDetail_count)"
					+"select "+orderId+",shoppings.goods_id,goods_price,shopping_count from shoppings" 
					+" LEFT JOIN goods on shoppings.goods_id=goods.goods_id where shopping_id in ("+sids+")";
			ps=conn.prepareStatement(sql2);
			
			
			ps = conn.prepareStatement("delete from orders where cus_id in ("+ sids +")");
			
			ps.executeUpdate();
			
			conn.commit();//提交事务
			
			return orderId;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}finally {
			ConnectionManager.closeConnection(conn);
		}
	}
	
	public String getOrderId() {
		Date time = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String orderIdTime = sdf.format(time);
		Random random = new Random();
		int randomNumber = random.nextInt(10000);
		return orderIdTime+randomNumber;
	}
}
