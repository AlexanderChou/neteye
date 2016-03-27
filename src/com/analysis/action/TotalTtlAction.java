package com.analysis.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.analysis.bpo.AnalysisBPO;
import com.analysis.dto.TopNData;
import com.analysis.dto.TopNTotalData;
import com.base.util.Constants;
import com.opensymphony.xwork2.ActionSupport;


public class TotalTtlAction extends ActionSupport{
	private static final long serialVersionUID = 1L;	
	private List<TopNTotalData> totalResult = new ArrayList<TopNTotalData>();
	private List<TopNData> inputresult =new ArrayList<TopNData>();    

	public String execute() throws Exception{
		AnalysisBPO bpo = new AnalysisBPO();
		String fileName = "total.xml";
		File dir=new File(Constants.webRealPath+"file/analysis/downLoadFile/");
		bpo.getTotalFile(dir, fileName);
		totalResult = bpo.createTotalResult("ttl", dir+File.separator+fileName,"Packet Number","ttlnum","value");
		return SUCCESS;
	}

   public static void main(String[] args) throws Exception {
	   	TotalTtlAction test = new TotalTtlAction();
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

