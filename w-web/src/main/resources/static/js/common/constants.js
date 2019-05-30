//--------------------------------------------------
//共同js
//--------------------------------------------------
var resultData = {};

//正常处理
resultData.OK = "1";
//结果错误
resultData.ERROR = "2";

//ton查询的最大条数
resultData.TOPN_MAX_CNT=1000;

resultData.requestOk = function(result){
	
	//当结果为空的情况
	if(resultData.isEmpty(result.code )){
		return false;
	}
	
	//当code为空
	return result.code == resultData.OK;
}

//--------------------------------------------------
//判断字符串是否为空
//--------------------------------------------------
resultData.isEmpty = function(varStr){
	
	//将符号转化为字符串，不然有时候是 数字的时候，报错
	varStr = varStr+"";
	if(varStr == null || varStr ==undefined || varStr == ""){
	    return true;
	}
	
	if(varStr.replace(/(^\s*)|(\s*$)/g, "") != ""){
	    return false;
	}
	return true;
}
//--------------------------------------------------
//获取前一月的日期
//比如 当前日期  2018-05 月   ,获取前一个月  resultData.getPreMonth()  或者resultData.getPreMonth(1)
// 					         获取前2个月  resultData.getPreMonth(2)
//--------------------------------------------------
resultData.getPreMonth = function (preMonth){
	
	//当前preMonth 没有指定的情况，直接认为是前一个月
	if(preMonth == null || preMonth == undefined){
		preMonth = 1;
	}
	
	var day1 = new Date();
	day1.setTime(day1.getTime()-24*60*60*1000);
	day1.setMonth(day1.getMonth()-preMonth);
	var month = (day1.getMonth()+1);
	var s1 = null;
	if(month < 10){
		s1 = day1.getFullYear()+"-0" + month ;
	}else{
		s1 = day1.getFullYear()+"-" + month ;
		
	}
	return s1;
}
//--------------------------------------------------
//获取前一天的日期
//比如今天是 2018-06-04 返回  2018-06-03
//--------------------------------------------------
resultData.getPreDay = function (){
	var day1 = new Date();
	day1.setTime(day1.getTime()-24*60*60*1000);
	var month = (day1.getMonth()+1);
	return day1.getFullYear()+"-" + formatDayOrMonth(month) + "-" + formatDayOrMonth(day1.getDate());
}

function formatDayOrMonth(day){
	if(day < 10){
		return "0"+day; 
	}
	return 0 ;
}
