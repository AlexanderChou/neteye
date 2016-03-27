
	function showHistory(picPath){
		temp = picPath;
		picName = temp.replace(/^(\S+\/)*/,"");
		win2 = new Ext.Window({
			title:picName,
			width:550,
			height:250,
			layout:"fit",
			closable:true,
			modal:true,
			resizable:true,
			items:[{id: 'pic',
					height: 250,
					html:flowChartTpl.apply({pic:picPath})
				}]
		});
		win2.show();
	}
	
	var flowChartTpl = new Ext.Template(
						'<div style="padding:10px;">',
							'<img src="{pic}">',
						'</div>'
	);			
	

// 入口函数
Ext.onReady(function() {
	
	
	function change(val,p){ 
		
        if(val >= 7000){
        	p.css='x-grid-back-1';
        	sp = "<span style=\"color:#ffffff;text-decoration:none;\">" + val + "</span></a>";
        } else if((val >= 5000) && (val < 7000)){
        	p.css='x-grid-back-2';
        	sp = "<span style=\"color:#ffffff;text-decoration:none;\">" + val + "</span></a>";
        } else if( (val >= 3000 ) && (val < 5000 )){
        	p.css='x-grid-back-3';
        	sp = "<span style=\"color:#ffffff;text-decoration:none;\">" + val + "</span></a>";
        } else if( (val >= 2000 ) && (val < 3000)){
        	p.css='x-grid-back-4';
        	sp = "<span style=\"color:#ffffff;text-decoration:none;\">" + val + "</span></a>";
        } else if((val >= 1000) && (val < 2000 )){
        	p.css='x-grid-back-5';
        	sp = "<span style=\"color:#000000;text-decoration:none;\">" + val + "</span></a>";
        } else if((val >= 500 ) && (val < 1000)){
        	p.css='x-grid-back-6';
        	sp = "<span style=\"color:#000000;text-decoration:none;\">" + val + "</span></a>";
        } else if( (val >= 100) && (val < 500)){
        	p.css='x-grid-back-7';
        	sp = "<span style=\"color:#000000;text-decoration:none;\">" + val + "</span></a>";
        } else if( (val >= 50) && (val < 100)){
        	p.css='x-grid-back-8';
        	sp = "<span style=\"color:#000000;text-decoration:none;\">" + val + "</span></a>";
        } else if((val >= 10) && (val < 50)){
        	p.css='x-grid-back-9';
        	sp = "<span style=\"color:#000000;text-decoration:none;\">" + val + "</span></a>";
        } else if( (val >= 1) && (val < 10 )){
        	p.css='x-grid-back-10';
        	sp = "<span style=\"color:#000000;text-decoration:none;\">" + val + "</span></a>";
        } else if( val < 1){
        	p.css='x-grid-back-11';
        	sp = "<span style=\"color:#000000;text-decoration:none;\">" + val + "</span></a>";
        } else {
        	p.css='x-grid-back-12';
        	sp = "<span style=\"color:#000000;text-decoration:none;\">" + val + "</span>";
        }
		
        return sp;
	} 
	
	function getValueFromTM(from, to, tM) {
		for (var i = 0; i < tM.length; i++) {
			if (tM[i][1] == from && tM[i][2] == to) {
				return tM[i][0];
			}
		}
	}
	// 定义用来接收流量矩阵值的 store
	var TMstore = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : 'json/getMatrixInfoInRegion.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'tmList',
							fields : [{
										name : "srcAs",
										mapping : "srcAs"
									}, {
										name : "dstAs",
										mapping : "dstAs"
									}, {
										name : "bytes",
										mapping : "bytes"
									}]
						})
			});

	// 动态构建gird对象
	// 第一步构建gird的 cloumnModel 对象

	var Tmatric = new Array();
	var FromNode = new Array();
	var len;
	TMstore.load({
				callback : function(records, options, success) {
					// 把store当中的值存在数组中
					if (!success) {
						Ext.Msg.alert("Error", "载入数据时出错");
						mk.hide();
						return;
					}
					for (var j = 0; j < TMstore.getTotalCount(); j++) {
						Tmatric[j] = [TMstore.getAt(j).data.bytes,
								TMstore.getAt(j).data.srcAs,
								TMstore.getAt(j).data.dstAs];
					}
				    len = Math.sqrt(TMstore.getTotalCount());
					for (var k = 0; k < len ; k++) {
						FromNode[k] = TMstore.getAt(k).data.dstAs;
					}

					var records = [];// 每一条记录
					var field = [{
								name : 'names'
							}];// 列值的属性
					var cloumn = [{
								id : 'names',
								header : "源AS\\目标AS",
								width : 90,
								sortable : true,
								dataIndex : 'names'
							}];// 列标签名

					// 把值记录到records中
					for (var i = 0; i < len; i++) {
						var oneRec = [FromNode[i]];
						for (var j = 0; j < len; j++) {
							// var value = getValueFromTM(FromNode[i],
							// FromNode[j], Tmatric);
							var value = Tmatric[i * len + j][0];
							oneRec.push(value);
						}
						records.push(oneRec);
					};// for
					// 构造column 模型

					for (var fi = 0; fi < len; fi++) {
						field.push({
									name : fi
								});
						cloumn.push({
									header : FromNode[fi],
									width : 55,
									renderer: change,
									sortable : true,
									dataIndex : fi
								});
					};// end for

					var store = new Ext.data.SimpleStore({
								fields : field
							});
					store.loadData(records);// 定义表中的数据表示
					// create the Grid
					var grid = new Ext.grid.GridPanel({
								store : store,
								columns : cloumn,
								stripeRows : true, // 隔行换色
								trackMouseOver : true, // 鼠标特效
								height : document.body.clientHeight * 0.9 + 5,
								width : 1200,
								title : '流量矩阵(域间)视图显示'
							});
							
				    grid.addListener('cellclick',cellclickFn);			
					function cellclickFn (viewPanel, rowIndex, columnIndex) {
					  if(columnIndex == 0){
					     Ext.Msg.alert("提示","选择的单元格无效");
					  }
					  else{
					     Ext.Ajax.request({
							url:"json/viewHistoryRegion.do?row="+(rowIndex+1)+"&col="+(columnIndex),
							success:function(response, request){
								var url = Ext.decode(response.responseText).url;
								showHistory(url);
							}
					});
					  
					  
					  }
					  
					}	
					grid.render('showGrid');
					mk.hide();

				}
			});

});