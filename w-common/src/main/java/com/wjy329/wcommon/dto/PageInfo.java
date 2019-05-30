package com.wjy329.wcommon.dto;
/**
* @author wjy329
* @Date 2018/12/6
*/
public class PageInfo {
	
	//限制的条数
	private Integer limit;
	
	//设定offset
	private Integer offset = 0;
	
	//排序的字段
	private String  orderField;
	
	//排序方式 asc desc
	private String order;

	//页面数
	private Integer pageCnt ;
	
	//界面的总条数
	private Integer total = 0;
	
	//当前页
	private Integer pageNow;
	
	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getPageCnt() {
		return pageCnt;
	}

	public void setPageCnt(Integer pageCnt) {
		this.pageCnt = pageCnt;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPageNow() {
		return pageNow;
	}

	public void setPageNow(Integer pageNow) {
		this.pageNow = pageNow;
	}
}
