package com.iber.portal.vo.pile;

import java.util.List;

public class PileContDetailVo {
			//今天或本月
			private List<PileCountDataVo> currList;
			//昨天或上月
			private List<PileCountDataVo> lastList;
			
			public List<PileCountDataVo> getCurrList() {
				return currList;
			}
			public void setCurrList(List<PileCountDataVo> currList) {
				this.currList = currList;
			}
			public List<PileCountDataVo> getLastList() {
				return lastList;
			}
			public void setLastList(List<PileCountDataVo> lastList) {
				this.lastList = lastList;
			}
}
