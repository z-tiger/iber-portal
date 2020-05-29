package com.iber.portal.model.employee;

public class CityManagerEmployeeInfo extends EmployeeInfo{
    private String sex;
    private Integer currentTask;
    private Integer todayCompleteTask;
    private Integer processTask;
    private Integer totalCompleteTask;
    private Integer identifyLabel;
    private String cityName;
    private String remark;
	private String todayScore;
	private String monthScore;
	private String totalScore;
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getCurrentTask() {
		return currentTask;
	}
	public void setCurrentTask(Integer currentTask) {
		this.currentTask = currentTask;
	}
	public Integer getTodayCompleteTask() {
		return todayCompleteTask;
	}
	public void setTodayCompleteTask(Integer todayCompleteTask) {
		this.todayCompleteTask = todayCompleteTask;
	}
	public Integer getProcessTask() {
		return processTask;
	}
	public void setProcessTask(Integer processTask) {
		this.processTask = processTask;
	}
	public Integer getTotalCompleteTask() {
		return totalCompleteTask;
	}
	public void setTotalCompleteTask(Integer totalCompleteTask) {
		this.totalCompleteTask = totalCompleteTask;
	}
	public Integer getIdentifyLabel() {
		return identifyLabel;
	}
	public void setIdentifyLabel(Integer identifyLabel) {
		this.identifyLabel = identifyLabel;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTodayScore() {
		return todayScore;
	}

	public void setTodayScore(String todayScore) {
		this.todayScore = todayScore;
	}

	public String getMonthScore() {
		return monthScore;
	}

	public void setMonthScore(String monthScore) {
		this.monthScore = monthScore;
	}

	public String getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}
}
