<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN "   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta HTTP-EQUIV="refresh" Content="300">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>二维路由COST</title>
	<link rel='StyleSheet' href="css/topoInit.css" type="text/css" />
   	<link href="js/mini/miniui/themes/default/miniui.css" rel="stylesheet" type="text/css" />
    <script src="js/mini/jquery-1.6.2.min.js" type="text/javascript"></script>    
    <script src="js/mini/miniui/miniui.js" type="text/javascript"></script>
    <script src="js/mini/boot.js" type="text/javascript"></script>
</head>

<body>
    <h1>二维路由COST</h1>

    <div style="width:800px;">
        <div class="mini-toolbar" style="border-bottom:0;padding:2px;">
            <table style="width:100%;">
                <tr>
                    <td style="width:100%;">
                        <a class="mini-button" iconCls="icon-add" onclick="addRow()" plain="true" tooltip="增加...">增加</a>
                        <a class="mini-button" iconCls="icon-remove" onclick="removeRow()" plain="true">删除</a>
                        <span class="separator"></span>
                        <a class="mini-button" iconCls="icon-save" onclick="saveData()" plain="true">保存</a>            
                    </td>
                </tr>
            </table>           
        </div>
    </div>
    <div id="datagrid1" class="mini-datagrid" style="width:800px;height:280px;" 
        url="json/getTwodCost.do?routername=R2" idField="id" 
        allowResize="true" pageSize="20" 
        allowCellEdit="true" allowCellSelect="true" multiSelect="true" 
        enterNextCell="true"         
        allowMoveColumn="true" editNextOnEnterKey="true"
        _showPagerButtonIcon="false" _showPagerButtonText="true" skipReadOnlyCell="true"
    >
        <div property="columns">
            <div type="indexcolumn"></div>
            <div type="checkcolumn"></div>            
            <div name="DstPrefix"  field="dstprefix" headerAlign="center" allowSort="true" width="150" showCellTip="false">目的地址前缀
                <input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
            </div>
            <div field="srcprefix" headerAlign="center" allowSort="true" width="150" showCellTip="false">源地址前缀
                <input property="editor" class="mini-textbox" style="width:100%;" minWidth="200" />
            </div>
            <div field="cost" width="100" allowSort="true">COST
                <input property="editor" class="mini-spinner"  minValue="0" maxValue="200" value="25" style="width:100%;"/>
            </div>                                                         
        </div>
    </div>
	
    <script type="text/javascript">         
    var rname='';
    function QueryString()
    { 
        var name,value,i; 
        var str=location.href;
        var num=str.indexOf("?"); 
        str=str.substr(num+1);
        var arrtmp=str.split("&");
        for(i=0;i < arrtmp.length;i++)
        { 
            num=arrtmp[i].indexOf("="); 
            if(num>0)
            { 
                name=arrtmp[i].substring(0,num);
                value=arrtmp[i].substr(num+1);
                this[name]=value; 
           } 
        }
        return this;
    }
    var query = QueryString();
    rname = query.routername;
    
    requestUrl = "json/getTwodCost.do?routername="+rname;
	document.getElementById('datagrid1').setAttribute('url',requestUrl);
    
	mini.parse();
    var grid = mini.get("datagrid1");
    grid.load();
        

        //////////////////////////////////////////////////////

        function addRow() {          
            var newRow = { name: "New Row" };
            grid.addRow(newRow, 0);

            grid.beginEditCell(newRow, "DstPrefix");
        }
        function removeRow() {
            var rows = grid.getSelecteds();
            if (rows.length > 0) {
                grid.removeRows(rows, true);
            }
        }
        function saveData() {
            
            var data = grid.getChanges();
            var json = mini.encode(data);
	
            
            grid.loading("保存中，请稍后......");
            $.ajax({
                url: "json/saveCostChanges.do?routername="+rname,
                data: { change: json },
                type: "get",
                success: function (text) {
                    grid.reload();
                },
                error: function (jqXHR, textStatus, errorThrown) {
                    alert(jqXHR.responseText);
                }
            });
        }

//        grid.on("headercellclick", function (e) {
//            alert(e.column.header);
//        });
        
    </script>

</body>
</html>