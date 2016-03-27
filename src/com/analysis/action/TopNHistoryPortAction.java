package com.analysis.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.analysis.bpo.AnalysisBPO;
import com.analysis.dto.TopNData;
import com.analysis.dto.TopNTotalData;
import com.base.util.Constants;
import com.opensymphony.xwork2.ActionSupport;


public class TopNHistoryPortAction extends ActionSupport{
	private static final long serialVersionUID = 1L;	
	private List<TopNTotalData> totalResult = new ArrayList<TopNTotalData>();
	private List<TopNData> inputresult =new ArrayList<TopNData>();    

	public String execute() throws Exception{
		AnalysisBPO bpo = new AnalysisBPO();
		String fileName = "topNHistory.xml";
		File dir=new File(Constants.webRealPath+"file/analysis/downLoadFile/");
		bpo.getHistoryFile(dir, fileName);
		totalResult = bpo.createHistoryResult("topnport", dir+File.separator+fileName,"MB");
		return SUCCESS;
	}

   public static void main(String[] args) throws Exception {
	   	TopNHistoryPortAction test = new TopNHistoryPortAction();
	   	test.execute();
   }

  
   public List<TopNData> getInputresult() {
		return inputresult;
	}
   
   
   public void setInputresult(List<TopNData> inputresult) {
		this.inputresult = inputresult;
	}   
   
   
	public List<TopNTotalData> getTotalResult() {
		return totalResult;
	}
	
	public void setTotalResult(List<TopNTotalData> totalResult) {
		this.totalResult = totalResult;
	}


}

