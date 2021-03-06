<%@page import="com.winiuxy.entitys.Goods"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改商品</title>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>

</head>
<body>
	<% Goods goods = (Goods)request.getAttribute("goods"); %>
	<form class="form-horizontal" role="form" style="width: 550px;margin: 10px auto;" action="<%=request.getContextPath()%>/UpdGoodsSave.do" method="post" enctype="multipart/form-data">
					   <div class="form-group">
		    <label for="goodsCode" class="col-sm-3 control-label">商品编号:</label>
			    <div class="col-sm-9">
			      <input type="text" class="form-control" id="goodsCode" value="<%=goods.getGoodsCode()%>" placeholder="请输入商品编号" name="goodsCode">
			    </div>
 		    </div>
 		    <div class="form-group">
			    <label for="goodsName" class="col-sm-3 control-label">商品名称:</label>
			    <div class="col-sm-9">
			      <input type="text" class="form-control" id="goodsName" value="<%=goods.getGoodsName()%>" placeholder="请输入商品名称" name="goodsName">
			    </div>
		    </div>
 					   
 		    <div class="form-group">
			   <label for="goodsPrice" class="col-sm-3 control-label">商品价格:</label>
			    <div class="col-sm-9">
			      <input type="text" class="form-control" id="goodsPrice" value="<%=goods.getGoodsPrice()%>" placeholder="请输入商品价格" name="goodsPrice" >
			    </div>
 		    </div>
 					   
 		   <div class="form-group">
		    <label for="goodsType" class="col-sm-3 control-label">类型编号:</label>
			    <div class="col-sm-9">
			      <input type="text" class="form-control" id="goodsType" value="<%=goods.getTypeId()%>" placeholder="请输入商品类型" name="goodsType">
			    </div>
 		  </div>
 					   
 		   <div class="form-group">
			   <label for="goodsSupplier" class="col-sm-3 control-label">供应商编号:</label>
			    <div class="col-sm-9">
			     <input type="text" class="form-control" id="goodsSupplier" value="<%=goods.getSupplierId()%>" placeholder="请输入商品供应商" name="goodsSupplier">
			    </div>
 		   </div>
 					   
 		   <div class="form-group">
			    <label for="goodsSupplier" class="col-sm-3 control-label" va<%= goods.getGoodsImg() %>>商品图片:</label>
			    <div class="col-sm-9">
			      <input type="file"  name="goodsImg">
			    </div>
 			   </div>
 			   
 		   <div class="form-group">
			    <label for="inputEmail3" class="col-sm-3 control-label"></label>
			    <div class="col-sm-9">
			    	<input type="hidden" name="goodsId" value="<%=goods.getGoodsId() %>">
			       <input type="submit" class="btn btn-info col-sm-12" value="保存" />
			    </div>
 		   </div>
 	</form>
 					
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.12.3.min.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
</body>
</html>