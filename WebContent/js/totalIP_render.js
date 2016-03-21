function readNode(val, cellmeta, record, rowIndex, columnIndex, store){
		var engName = record.data["engName"];
		return "<a href='nodeIP.do?engName="+engName+"'>"+val+"</a>";
	}
	
	
function renderTotalNum(val){
		var width = val / 20000;
		
		return '<span style="color:#CC66CC; display:-moz-inline-box; display:inline-block; width:50px;">' + val + '</span><img src="images/common/_blank.gif" style= "width:' + width +
			   'px;height:12px;background:url(images/common/green_bar.jpg) no-repeat scroll 0 0;">' 
			   ;
}
function renderHourNum(val){
		var width = val / 75;
		
		return '<span  style=" color:#CC66CC;display:-moz-inline-box; display:inline-block; width:50px;">' + val + '</span><img src="images/common/_blank.gif" style= "width:' + width +
			   'px;height:12px;background:url(images/common/green_bar.jpg) no-repeat scroll 0 0;">' 
			   ;
}
function renderDayNum(val){
		var width = val / 400;
		
		return '<span style="color:#CC66CC; display:-moz-inline-box; display:inline-block; width:50px;">' + val + '</span><img src="images/common/_blank.gif" style= "width:' + width +
			   'px;height:12px;background:url(images/common/green_bar.jpg) no-repeat scroll 0 0;">' 
			   ;
	}
function renderWeekNum(val){
		var width = val / 3000;
		
		return '<span style="color:#CC66CC; display:-moz-inline-box; display:inline-block; width:50px;">' + val + '</span><img src="images/common/_blank.gif" style= "width:' + width +
			   'px;height:12px;background:url(images/common/green_bar.jpg) no-repeat scroll 0 0;">' 
			   ;
}
function renderMonthNum(val){
		var width = val / 10000;
		
		return '<span style="color:#CC66CC; display:-moz-inline-box; display:inline-block; width:50px;">' + val + '</span><img src="images/common/_blank.gif" style= "width:' + width +
			   'px;height:12px;background:url(images/common/green_bar.jpg) no-repeat scroll 0 0;">' 
			   ;
}