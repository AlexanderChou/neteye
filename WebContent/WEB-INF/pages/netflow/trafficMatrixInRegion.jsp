<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="css/ext-all.css" />
<link rel="StyleSheet" href="css/topoInit.css" type="text/css" />
<script type="text/javascript" src="js/ext-base.js"></script>
<script type="text/javascript" src="js/ext-all.js"></script>
<script type="text/javascript" src="js/trafficMatrixInRegion.js"></script>
<title>流量矩阵显示</title>

<style>
        .x-grid-back-1 { background: #200080; }
	</style>
	<style>
        .x-grid-back-2 { background: #2000c0; }
	</style>
	<style>
        .x-grid-back-3 { background: #2040ff; }
	</style>
	<style>
        .x-grid-back-4 { background: #2080ff; }
	</style>
	<style>
        .x-grid-back-5 { background: #40c0ff; }
	</style>
	<style>
        .x-grid-back-6 { background: #20ffff; }
	</style>
	<style>
        .x-grid-back-7 { background: #66ff66; }
	</style>
	<style>
        .x-grid-back-8 { background: #ccff66; }
	</style>
	<style>
        .x-grid-back-9 { background: #ffff99; }
	</style>
	<style>
        .x-grid-back-10 { background: #ffffaa; } 
	</style>
	<style>
        .x-grid-back-11 { background: #ffff00; } //黄色
	</style>
	<style>
        .x-grid-back-12 { background: #ffffff; } //白色
    </style>
    <style>
        .x-grid-back-13 { background: #00ff00; } //绿色
    </style>
    <style>
        .x-grid-back-14 { background: #ff0000; } //红色
	</style>

</head>
<body>
<script type="text/javascript">
	var mk = null;
	mk = new Ext.LoadMask(Ext.getBody(), {
		msg: '正在加载数据，请稍候……',
		removeMask: true //完成后移除
	});
	mk.show();
</script>
     
<center>
<table  border="1" cellPadding=0 cellSpacing=0>
  <tr>
    <td height="6" width="70" bgcolor="#ffffaa">
      <div align="center">&lt; 10 </div>
    </td>
    <td height="6" width="80" bgcolor="#ffff99">
      <div align="center">10--50 </div>
    </td>
    <td height="6" width="90" bgcolor="#ccff66">
      <div align="center">50--100 </div>
    </td>
    <td height="6" width="90" bgcolor="#66ff66">
      <div align="center" >100--500 </div>
    </td>
    <td height="6" width="100" bgcolor="#20ffff">
      <div align="center">500--1000 </div>
    </td>
    <td height="6" width="100" bgcolor="#40c0ff">
      <div align="center">1000--2000 </div>
    </td>
    <td height="6"  width="100" bgcolor="#2080ff">
      <div align="center"><span style="color:#ffffff">2000--3000 </span></div>
    </td>
    <td height="6"  width="100" bgcolor="#2040ff">
      <div align="center"><span style="color:#ffffff">3000--5000 </span></div>
    </td>
    <td height="6" width="100" bgcolor="#2000c0">
      <div align="center"><span style="color:#ffffff">5000--7000 </span></div>
    </td>
    <td height="6" width="100" bgcolor="#200080">
      <div align="center"><span style="color:#ffffff">&gt; 7000 </span></div>
    </td>

  </tr>
</table>
</center>
    
     <div id='showGrid' style="margin:20px 10px 20px 30px;"></div>

</body>
</html>