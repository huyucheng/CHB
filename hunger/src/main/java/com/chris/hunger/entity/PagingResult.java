
package com.chris.hunger.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: PagingResult
 * @Description: 分页结果
 * @author shensuoyao
 * @date 2014年7月10日 下午4:10:36
 */
public class PagingResult<T> {
	
	// 当前页
	private int currentPage;
	
	//总页数
	private int totalPage;
	
	// 总共记录条数
	private int totalSize;
	
	//每页显示数量
	private int pageSize;
	
	// 结果集
	private List<T> resultList = new ArrayList<T>();
	
	
	/** 
	* @Fields params : 查询条件
	*/ 
	private Map<String, Object> params;

	/**
	 * @return currentPage
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage
	 *            要设置的 currentPage
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return totalSize
	 */
	public int getTotalSize() {
		return totalSize;
	}

	/**
	 * @param totalSize
	 *            要设置的 totalSize
	 */
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	/**
	 * @return resultList
	 */
	public List<T> getResultList() {
		return resultList;
	}

	/**
	 * @param resultList
	 *            要设置的 resultList
	 */
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}

	/** 
	 * @return totalPage
	 */
	public int getTotalPage() {
		if(this.totalPage > 0) {
			return this.totalPage;
		}
		this.totalPage = this.totalSize / this.pageSize;
		if((this.totalSize % this.pageSize) > 0) {
			this.totalPage += 1;
		}
		return totalPage;
	}

	/** 
	 * @param totalPage 要设置的 totalPage
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/** 
	 * @return pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/** 
	 * @param pageSize 要设置的 pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/** 
	* @Title: prevPage
	* @Description: 上一页页码
	* @author shensuoyao
	* @date 2014年7月11日 上午10:52:17
	* @return int
	* @throws 
	*/ 
	public int getPrevPage() {
		int p = this.currentPage - 1;
		if(p > 0) {
			return p;
		}
		return this.currentPage;
	}
	
	/** 
	* @Title: nextPage
	* @Description: 下一页页码
	* @author shensuoyao
	* @date 2014年7月11日 上午10:49:15
	* @return int
	* @throws 
	*/ 
	public int getNextPage() {
		int p = this.currentPage + 1;
		if(p > this.getTotalPage()) {
			return this.currentPage;
		}
		return p;
	}
	
	/** 
	* @Title: hasPrevPage
	* @Description: 是否有上一页
	* @author shensuoyao
	* @date 2014年7月11日 上午10:53:24
	* @return boolean
	* @throws 
	*/ 
	public boolean hasPrevPage() {
		return getPrevPage() < this.currentPage;
	}
	
	/** 
	* @Title: hasNext
	* @Description: 是否有下一页
	* @author shensuoyao
	* @date 2014年7月11日 上午10:50:27
	* @return boolean
	* @throws 
	*/ 
	public boolean hasNextPage() {
		return getNextPage() > this.currentPage;
	}
	
    /** get page no **/
    public long getFirstPage() {
        if ((currentPage - 1) > 0) {
            return 1;
        } else {
            return 0;
        }
    }
    
    public long getLastPage() {
        if ((currentPage + 1) <= getTotalPage()) {
            return (getTotalPage());
        } else {
            return 0;
        }
    }
	
	/** 
	* @Title: getResultSize
	* @Description: 获取结果集数量
	* @author shensuoyao
	* @date 2014年7月11日 上午11:23:04
	* @return int
	* @throws 
	*/ 
	public int getResultSize() {
		return this.resultList.size();
	}

	/** 
	 * @return params
	 */
	public Map<String, Object> getParams() {
		return params;
	}

	/** 
	 * @param params 要设置的 params
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public List<Integer> getPageList() {
		List<Integer> numberList = new ArrayList<Integer>();
		if(this.getTotalPage() <= pageSize){
	    	for(int i = 0; i < this.getTotalPage(); i++){
	    		numberList.add(i+1);
	    	}
	    }else {
	    	if(this.getCurrentPage() <= (pageSize/2)){
	        	for(int i = 0; i < pageSize; i++){
	        		numberList.add(i+1);
	        	}
	    	}else if(this.getCurrentPage() > (this.getTotalPage() - (pageSize/2))){
	    		for(int i = (int) this.getTotalPage()-10; i< this.getTotalPage(); i++){
	    			numberList.add(i+1);
	    		}
	    	}else{
	    		for(int i = (int) this.getCurrentPage()-(pageSize/2); i< this.getCurrentPage()+(pageSize/2); i++){
	    			numberList.add(i+1);
	    		}
	    	}
	    }
		
		return numberList;
	}


}
