<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<div style="height: 500px;" id="canvas"/>
</div>
<script src="http://demo.qunee.com/lib/qunee-min.js"></script>
<script src="js/json2.js"></script>
<script language="JavaScript" type="text/javascript">
var graph = new Q.Graph("canvas");
var edges = [];
var movingEdges = [];
var directions = [];
var edgeInfo = {};

var index = 0;
var timer = setInterval(function(){
    index++;
    index = index%25;
    for (var i = 0; i < movingEdges.length; i++)
    {
    	if (directions[i] == true)
    		movingEdges[i].setStyle(Q.Styles.ARROW_TO_OFFSET, -1 + 0.04 * index);
    	else
    		movingEdges[i].setStyle(Q.Styles.ARROW_TO_OFFSET, -0.04 * index);		
    }
}, 150);

function destroy(){
	movingEdges = [];
	directions = [];
	document.getElementById("src").value = "";
	document.getElementById("dst").value = "";
    clearInterval(timer);
    
}

function request(url, params, callback, callbackError) {
	try {
		var req = new XMLHttpRequest();
		req.open('GET', encodeURI(url));
		req.onreadystatechange = function(e) {
			if (req.readyState != 4) {
				return;
		    }
			if (200 == req.status) {
				var code = req.responseText;
				if (code && callback) {
					callback(code);
				}
				return;
			}else{
				if (callbackError) {
				callbackError();
			}
		}
	};
	req.send(params);
	} catch (error) {
		if (callbackError) {
			callbackError();
		}
	}
}

function pathGetted(txt)
{
	var json = JSON.parse(txt);
	if(json.edges){
		Q.forEach(json.edges, function(data){
			for (var i = 0; i < edges.length; i++)
			{
				if (edges[i].from.name == String(data.from) && edges[i].to.name == String(data.to))
				{
					edges[i].setStyle(Q.Styles.ARROW_TO, Q.Consts.SHAPE_CIRCLE);
					edges[i].setStyle(Q.Styles.ARROW_TO_SIZE, 8);
					edges[i].setStyle(Q.Styles.EDGE_OUTLINE, 1);
					movingEdges.push(edges[i]);
					directions.push(true);
				}
				else if (edges[i].to.name == String(data.from) && edges[i].from.name == String(data.to))
				{
					edges[i].setStyle(Q.Styles.ARROW_TO, Q.Consts.SHAPE_CIRCLE);
					edges[i].setStyle(Q.Styles.ARROW_TO_SIZE, 8);
					edges[i].setStyle(Q.Styles.EDGE_OUTLINE, 1);
					movingEdges.push(edges[i]);
					directions.push(false);
				}
			}
		});
	}
}

function getInput()
{
	var src = document.getElementById("src").value;
	var dst = document.getElementById("dst").value;
	
	if (src == "" || dst == "")
	{
		alter("Wrong Input!");
		return;
	}
	else if (src == dst)
	{
		alter("Wrong Input!");
		return;
	}
	var srcRouter = "";
	var srcPrefix = "";
	var dstPrefix = "";
	for (var i = 0; i < edges.length; i++)
	{
		if (edges[i].from.id == Number(src))
		{
			edges[i].setStyle(Q.Styles.ARROW_TO, Q.Consts.SHAPE_CIRCLE);
			edges[i].setStyle(Q.Styles.ARROW_TO_SIZE, 8);
			edges[i].setStyle(Q.Styles.EDGE_OUTLINE, 1);
			movingEdges.push(edges[i]);
			directions.push(true);
			srcRouter = edges[i].to.name;
			srcPrefix = edgeInfo[edges[i].from.id];
		}
		else if (edges[i].from.id == Number(dst))
		{
			edges[i].setStyle(Q.Styles.ARROW_TO, Q.Consts.SHAPE_CIRCLE);
			edges[i].setStyle(Q.Styles.ARROW_TO_SIZE, 8);
			edges[i].setStyle(Q.Styles.EDGE_OUTLINE, 1);
			movingEdges.push(edges[i]);
			directions.push(false);
			dstPrefix = edgeInfo[edges[i].from.id];
		}
	}
	url = "json/getRoutingPath.do?srcRouter=" + srcRouter + "&dstPrefix=" + dstPrefix + "&srcPrefix=" + srcPrefix;
	request(url, "", pathGetted);
}

function translateToQuneeElements(json){
	var map = {};
	if(json.nodes){
		Q.forEach(json.nodes, function(data){
			var node = graph.createNode(data.name);
			if (Number(data.id) < 0)
				node.image = Q.Graphs.cloud;
			else
				node.image = Q.Graphs.exchanger2;
			node.set("data", data);
			map[data.id] = node;
		});
	}
	if(json.edges){
		Q.forEach(json.edges, function(data){
		var from = map[data.from];
		var to = map[data.to];
		if(!from || !to){
			return;
		}
		var edge = graph.createEdge(data.name, from, to);
		edges.push(edge);
		edgeInfo[Number(edge.from.id)] = String(data.fromprefix);
		var leftLabel = new Q.LabelUI();
		leftLabel.position = Q.Position.LEFT_BOTTOM;
		leftLabel.anchorPosition = Q.Position.LEFT_BOTTOM;
		leftLabel.offsetX = 10;
		var rightLabel = new Q.LabelUI();
		rightLabel.position = Q.Position.RIGHT_BOTTOM;
		rightLabel.anchorPosition = Q.Position.RIGHT_BOTTOM;
		rightLabel.offsetX = 10;
		edge.addUI(leftLabel, {bindingProperty : "data",
		    property : "leftLabel",
		    propertyType : Q.Consts.PROPERTY_TYPE_STYLE});
		edge.addUI(rightLabel, {bindingProperty : "data",
		    property : "rightLabel",
		    propertyType : Q.Consts.PROPERTY_TYPE_STYLE});
		edge.setStyle("leftLabel", String(data.fromprefix));
		edge.setStyle("rightLabel", String(data.toprefix));
		edge.setStyle(Q.Styles.ARROW_TO, false);
		edge.set("data", data);
		}, graph);
	}
}

function onDataCollected(txt){
	var json = JSON.parse(txt);
	translateToQuneeElements(json);
	graph.moveToCenter();
	var layouter = new Q.SpringLayouter(graph);
	layouter.repulsion = 100;
	layouter.attractive = 0.2;
	layouter.elastic = 0;
	layouter.start();
}

request("json/getLink.do", "", onDataCollected);

graph.ondblclick = function(evt){
	var node = graph.getElementByMouseEvent(evt);
	window.open("routingtable.do?routername="+node.name, "_blank");
};

graph.onclick = function(evt){
	var node = graph.getElementByMouseEvent(evt);
	if(node){
		if (document.getElementById("src").value == "")
			document.getElementById("src").value = node.id;
		else if (document.getElementById("dst").value != "")
		{
			document.getElementById("src").value = node.id;
			document.getElementById("dst").value = "";
		}
		else
			document.getElementById("dst").value = node.id;
	}
};
</script>
<div>
src:<input type="text" id = "src" name = "src"/>
dst:<input type="text" id = "dst" name = "dst"/>
<input type="submit" value="Submit" onclick="getInput()"/>
<input type="submit" value="Reset" onclick="destroy()">
</div>
</body>
</html>