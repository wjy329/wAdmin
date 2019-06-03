package com.wjy329.filter;



import com.wjy329.wcommon.dto.PageInfo;
import com.wjy329.wcommon.dto.SystemPageContext;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
* @description 分页过滤器
* @author wjy329
* @Date 2018/12/7
*/
@WebFilter(filterName="pageFilter",urlPatterns="/*")
public class PageFilter implements Filter {
	
	//默认界面大小
	private int pageSize = 10;
	
	//开始的位置
	private int offset = 0;
	
	private int pageNow =1;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {


		String page = request.getParameter("page");
		String rows = request.getParameter("rows");
		//排序的字段
		String sort = request.getParameter("sort");
		//顺序
		String order = request.getParameter("order");
		//获取到页面信息
		try {
			PageInfo pageInfo = SystemPageContext.getPageInfo();
			
			//设定界面大小
			if(this.isNotEmpty(rows)){
				//设定界面大小
				pageSize = Integer.parseInt(rows);
			}
			pageInfo.setLimit(pageSize);
			
			//设定offset 
			if(this.isNotEmpty(page)){
				//当前所在的位置
				pageNow= Integer.parseInt(page);
				if(pageNow > 1){
					offset = (pageNow -1) * pageSize ;
				}else{
					offset = 0;
				}
			}
			
			//设定当前页
			pageInfo.setPageNow(pageNow);
			//设定offset
			pageInfo.setOffset(offset);
			
			//设定排序的顺寻 desc asc
			if(this.isNotEmpty(order)){
				pageInfo.setOrder(order);
			}
			
			//设定排序的字段
			if(this.isNotEmpty(sort)){
				pageInfo.setOrderField(sort);
			}
			
			//设定到ThreadLoacl中
			SystemPageContext.setPageInfo(pageInfo);
			//继续执行
			chain.doFilter(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//移除分页的信息
			SystemPageContext.remove();
		}
	}

	private boolean isNotEmpty(String str){
		if(str != null && !"".equals(str.trim())){
			return true;
		}
		return false;
	}

	@Override
	public void destroy() {
		
	}

}
