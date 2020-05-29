package com.iber.portal.model.member;
import java.util.List;
public class MemberCountDetailVo {
	//今天或本月
		private List<MemberCountDetail> currList;
		//昨天或上月
		private List<MemberCountDetail> lastList;
		
		public List<MemberCountDetail> getCurrList() {
			return currList;
		}
		public void setCurrList(List<MemberCountDetail> currList) {
			this.currList = currList;
		}
		public List<MemberCountDetail> getLastList() {
			return lastList;
		}
		public void setLastList(List<MemberCountDetail> lastList) {
			this.lastList = lastList;
		}
		
		
		
}

