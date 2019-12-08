package zqit.chat.base.pulgins.pageHelper;

import org.apache.commons.lang3.StringUtils;

import com.github.pagehelper.PageHelper;

/**
 * 分页查询类 在 PageQuery 初始化时，需要设置 T 超类, 然后通过 getT() 中 调用 PageHelper.startPage(..) 方法在线程中 重写mybatis的sql
 * 注： 因为PageHelper是在 Thread.currentThread(), 即当前线程获取 mapper的查询线程进行修改，因此调用时，必须在mapper的方法传参使用 getT()
 * @author mc
 *
 * @param <T>
 */
public class PageQuery<T>{

	int pageNum = 1;
	int pageSize = 10;
	String orderBy;
	
	T t;
	
	public T getT() {
		return startPage();
	}
	public void setT(T t) {
		this.t = t;
	}
	
	/**
	 * 排序参数 必须为 T 中的字段, 例如 "id"/"id desc"
	 * @param orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	/**
	 * 页码 默认为 1
	 * @param pageNum
	 */
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	/**
	 * 每页的行数 默认为10
	 * @param pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * 注入pagehelper
	 * @return
	 */
	private T startPage(){
		if(!StringUtils.isEmpty(orderBy))
			PageHelper.orderBy(orderBy);
		PageHelper.startPage(pageNum, pageSize);
		/**
		 * 需要什么参数在此返回
		 */
		return t;
	}
	
	
}
