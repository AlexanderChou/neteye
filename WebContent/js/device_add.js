/****************************************
** Copyright (c) 2008, 2009, 2010
**      The Regents of the Tsinghua University, PRC.  All rights reserved.
**
** Redistribution and use in source and binary forms, with or without  modification, are permitted provided that the following conditions are met:
** 1. Redistributions of source code must retain the above copyright  notice, this list of conditions and the following disclaimer.
** 2. Redistributions in binary form must reproduce the above copyright  notice, this list of conditions and the following disclaimer in the  documentation and/or other materials provided with the distribution.
** 3. All advertising materials mentioning features or use of this software  must display the following acknowledgement:
**  This product includes software (iNetBoss) developed by Tsinghua University, PRC and its contributors.
** THIS SOFTWARE IS PROVIDED BY THE REGENTS AND CONTRIBUTORS ``AS IS'' AND
** ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
** IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
** ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE
** FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
** DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
** OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
** HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
** LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
** OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
** SUCH DAMAGE.
*
******************************************/
//添加设备
function addDevice(viewName){
	var treeDiv = Ext.get("tree");
	if(treeDiv==null){
		var addDIV = document.createElement("div");
		addDIV.id = "tree";
		addDIV.style.width = "300px";
        addDIV.style.height = "450px";
        document.body.appendChild(addDIV);
	}
	var chosedId = new Array();
	for(i = 0; i < stations.length; i++){
		var s = stations[i];
		chosedId.push((s.id).replace("s",""));
	}
	
	var Tree = Ext.tree;
    var tree = new Tree.TreePanel({
        el:'tree',
        animate:true, 
        autoScroll:true,
        loader: new Tree.TreeLoader({dataUrl:'tochose.do?chosedId='+chosedId.toString()}),
        enableDD:true,
        containerScroll: true
    });
    
    new Tree.TreeSorter(tree, {folderSort:true});
    var root = new Tree.AsyncTreeNode({
        text: '设备列表', 
        draggable:false, // disable root node dragging
        checked:false,
        id:'source'
    });
    tree.setRootNode(root);
    tree.render(); // render the tree
    root.expand(true, true);
    tree.on('checkchange', function(node, checked) {        
			mytoggleChecked(node);
	}, tree);
    function mytoggleChecked(node){
    	if(node.hasChildNodes){
    		node.eachChild(function(child){
    			child.getUI().toggleCheck(node.attributes.checked);
    			child.attributes.checked = node.attributes.checked;
    			child.on("checkchange",function(sub){
    				mytoggleChecked(sub);
    			});
    			mytoggleChecked(child);
    		});
    	}
    };
	
	var win = new Ext.Window({
        title: '添加设备',
        contentEl:"tree",
        width: 330,
        autoScroll:true,
        resizable:false,
        x:270,
        y:0,
        plain: true,
        bodyStyle:'padding:5px;color:black;',
        buttonAlign:'center',
        buttons: [{
            text: '添加',
            handler  : function(){
            	var checkId = new Array();
			    var checkName = new Array();
			    var b = tree.getChecked();
			    for(var i=0;i<b.length;i++){
					if(b[i].leaf){
						checkId.push(b[i].id);
						checkName.push(b[i].text);
					}
				}
				
				//æ°éæ©çèç¹ = checkId - chosedId
				var temp =  ","+chosedId+",";
				for(var i=0;i<checkId.length;i++){
					//将checkId以下划线隔开
					var words = checkId[i].split("_");
					if(temp.indexOf(","+words[0]+",")==-1){
						createDiv(words[0],checkName[i],words[1]);
					}
				}
				win.close();
           }
        },{
            text: '取消',
            handler  : function(){
            	win.close();
            }
        }]
    });
    win.show();
}