package com.woniuxy.servlets;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CheckCodeServlet
 */
@WebServlet("/checkCode.do")
public class CheckCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	  private Random random=new Random();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckCodeServlet() {
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
		
		  //内存图片对象(TYPE_INT_BGR 选择图片模式RGB模式)
        BufferedImage image = new BufferedImage(90,30,BufferedImage.TYPE_INT_BGR);
         //得到画笔
        Graphics graphics = image.getGraphics();
        //画之前要设置颜色，设置画笔颜色
        graphics.setColor(Color.yellow);
        //填充矩形区域（指定要画的区域设置区）
        graphics.fillRect(0,0,90,30);
        //为了防止黑客软件通过扫描软件识别验证码。要在验证码图片上加干扰线
        //给两个点连一条线graphics.drawLine();
        //生成干扰线
        for (int i=0;i<20;i++){
            //颜色也要随机（设置每条线随机颜色）
            graphics.setColor(getRandomColor());
            int x1=random.nextInt(90);
            int y1=random.nextInt(30);
            int x2=random.nextInt(90);
            int y2=random.nextInt(30);
            graphics.drawLine(x1,y1,x2,y2);
        } 
        
        //拼接4个验证码，画到图片上
        //字符数组中的字符就是可能会出现在图片中的字符
        char [] arrays={'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        //builder即将存放验证码中的字符
        StringBuilder builder = new StringBuilder();
        //得到四个验证码
        for(int i=0;i<4;i++){
               //设置字符的颜色
               //获得了一个随机的下标位置
                int index=random.nextInt(arrays.length);
                 //从数组中拿到对应的字母追加到字符串中
                builder.append(arrays[index]);
          }
        
        
          //创建session对象将生成的验证码字符串以名字为checkCode保存在session中
          
        request.getSession().setAttribute("checkCode",builder.toString());
          //将4个字符画到图片上graphics.drawString(str ,x,y);一个字符一个字符画
        
        
        //将四个字符画到图片上
        graphics.setFont(new Font("微软雅黑", Font.BOLD, 25));
        //每循环一次，画一个字符
        for (int i=0;i<builder.toString().length();i++)
        {
            graphics.setColor(getRandomColor());
          
            char item=builder.toString().charAt(i);
            //最后一个参数指文字的基线的纵坐标，基线大概在文字的四分之三的位置。
            graphics.drawString(item+"",5+(i*22),25); 
            
        }
  
  
        //输出内存图片到输出流。，最终输出到客户端浏览器上
        ImageIO.write(image,"png",response.getOutputStream());
		
	}
	

	private Color getRandomColor(){
		int r=random.nextInt(256);
		int g=random.nextInt(256);
		int b=random.nextInt(256);
		return new Color(r,g,b); 
		
	}
}

