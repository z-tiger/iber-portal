package com.iber.portal.model.dispatcher;
/**
 * 网格和网点的关联对象，一个网格可以有多个网点
 * @author Administrator
 *
 */
public class GridParkRelation {
	private Integer id;
	private Integer gridId;
	private Integer parkId;
	private Integer isBuildByElecGrid;//是否通过电网格创建关联关系
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGridId() {
		return gridId;
	}
	public void setGridId(Integer gridId) {
		this.gridId = gridId;
	}
	public Integer getParkId() {
		return parkId;
	}
	public void setParkId(Integer parkId) {
		this.parkId = parkId;
	}
	public Integer getIsBuildByElecGrid() {
		return isBuildByElecGrid;
	}
	public void setIsBuildByElecGrid(Integer isBuildByElecGrid) {
		this.isBuildByElecGrid = isBuildByElecGrid;
	}
	
}
