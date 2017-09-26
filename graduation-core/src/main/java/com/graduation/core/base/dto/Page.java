package com.graduation.core.base.dto;

import java.util.List;
import java.util.Map;

/**
 * 核心-基础-服务器到客户端的分页数据封装
 * @param <T> 分页中存储的数据类型。
 * @author Liu jun
 */
public class Page<T> {

	/**
	 * 分页查询开始记录位置
	 */
	private int begin;

	/**
	 * 分页查看下结束位置
	 */
	private int end;

	/**
	 * 每页显示记录数
	 */
	private int length;

	/**
	 * 查询结果总页数
	 */
	private int count;

	/**
	 * 当前页码
	 */
	private int current;

	/**
	 * 查询结果总记录数
	 */
	private long total;

	/** jqgrid使用属性 start */
	/**
	 * 查询结果总页数
	 */
	private long totalpage;

	/**
	 * 查询结果总记录数
	 */
	private long records;
	/** jqgrid使用属性 end */

	/**
	 * 数据集合
	 */
	private List<T> rows;

	/**
	 * 合计行
	 */
	private List<Map<String, String>> footer;

	/**
	 * 构造函数
	 * @param current	当前页码
	 * @param length	每页显示记录数
	 */
	public Page(int current, int length) {
		this.current = current;
		this.length = length;
		this.begin = (this.current - 1) * this.length;
		this.end = this.begin + this.length;
	}

	/**
	 * 构造函数
	 * @param begin		初始记录数
	 * @param length	每页显示记录数
	 * @param count		总页数
	 */
	public Page(int begin, int length, int count) {
		this(begin, length);
		this.count = count;
		this.totalpage = (count + this.length - 1) / this.length;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
		this.setRecords(total);
	}

	public long getRecords() {
		return records;
	}

	public void setRecords(long records) {
		this.records = records;
		this.totalpage = (records + this.length - 1) / this.length;
	}

	public long getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(long totalpage) {
		this.totalpage = totalpage;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public List<Map<String, String>> getFooter() {
		return footer;
	}

	public void setFooter(List<Map<String, String>> footer) {
		this.footer = footer;
	}

	@Override
	public String toString() {
		return "Pager [begin=" + begin + ", end=" + end + ", length="
				+ length + ", count=" + count + ", current=" + current
				+ ", total=" + total + ", records=" + records + ", totalpage="
				+ totalpage + ", rows=" + rows + ", footer=" + footer + "]";
	}
}