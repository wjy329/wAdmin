package com.wjy329.wcommon.dto;

/**
* @author wjy329
* @Date 2018/12/6
*/
public class SystemPageContext {

	public static ThreadLocal<PageInfo> pagerThreadLocal = new ThreadLocal<PageInfo>();

	/**
	* @description  获取分页对象
	* @author wjy329
	* @Date 2018/12/6
	*/
	public static PageInfo getPageInfo() {
		return pagerThreadLocal.get() == null ? new PageInfo() : pagerThreadLocal.get();
	}

	/**
	 * @description  设定page信息
	 * @author wjy329
	 * @Date 2018/12/6
	 */
	public static void setPageInfo(PageInfo pageInfo) {
		pagerThreadLocal.set(pageInfo);
	}
	

	public static void setTotal(Integer total){
		PageInfo page = pagerThreadLocal.get();
		
		if(total == null) {
			page.setPageCnt(0);
			page.setTotal(0);
			pagerThreadLocal.set(page);
			return;
		}
		//获取
		int pageSize = (total - 1) /page.getLimit() +1;
		//设定界面的页数
		page.setPageCnt(pageSize);
		
		//界面的总条数
		page.setTotal(total);
		
		pagerThreadLocal.set(page);
	}

	public static void remove() {
		pagerThreadLocal.remove();
	}
}
