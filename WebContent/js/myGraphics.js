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
//从(x1,y1)向(x2,y2)画箭头,delta决定箭头大小
function drawArrow(drawer,x1,y1,x2,y2,delta) {
	//drawer.setColor("maroon");
	var x3,y3,x4,y4
	if(x1==x2) {//垂直情况
		x3 = x2 + delta;
		x4 = x2 - delta;
		var d = 1;	//箭头方向
		if(y1 > y2) {
			d = -1;
		}
		y3 = y2 - d * delta;
		y4 = y2 - d * delta;
	}
	else if(y1==y2) {//水平情况
		y3 = y2 + delta;
		y4 = y2 - delta;
		var d = 1;	//箭头方向
		if(x1 > x2) {
			d = -1;
		}
		x3 = x2 - d * delta;
		x4 = x2 - d * delta;
	}else {	//倾斜情况
		var k = (y2-y1)/(x2-x1);
		var tx,ty;
		var d = 1;
		if(x1 > x2) {
			d = -1;
		}
		tx = x2 - d * delta/Math.sqrt(1+k*k);
		ty = y2 - d * k * delta/Math.sqrt(1+k*k);
		var kk = 1/k;
		x3 = tx - delta/Math.sqrt(1+kk*kk);
		y3 = ty + kk* delta/Math.sqrt(1+kk*kk);
		x4 = tx + delta/Math.sqrt(1+kk*kk);
		y4 = ty - kk* delta/Math.sqrt(1+kk*kk);
	}
	//drawer.setColor("#80bb80");
	//drawer.setStroke(4);
	drawer.drawLine(x1,y1,x2,y2);
	drawer.drawLine(x2,y2,x3,y3);
	drawer.drawLine(x2,y2,x4,y4);
	drawer.paint();
}

//因为两根对顶的箭头接在一起不好看，所以需要隔开一定距离
function drawTwoArrowOneLine(drawer,x1,y1,x2,y2,delta,color1,color2) 
{
	var middlex = (x1 + x2) /2;
	var middley = (y1 + y2) /2;
	var t1x,t1y;
	var t2x,t2y;
	var offset = 4;	//
	
	if(x1==x2) {//垂直情况
		t1x = t2x = x1;
		var d = 1;	//箭头方向
		if(y1 > y2) {
			d = -1;
		}
		t1y = middley - d * offset;
		t2y = middley + d * offset;
	}
	else if(y1==y2) {//水平情况
		t1y = t2y = y1;
		var d = 1;	//箭头方向
		if(x1 > x2) {
			d = -1;
		}
		t1x = middlex - d * offset;
		t2x = middlex + d * offset;
	}else {	//倾斜情况
		var k = (y2-y1)/(x2-x1);
		var tx,ty;
		var d = 1;
		if(x1 > x2) {
			d = -1;
		}
		t1x = middlex - d * offset/Math.sqrt(1+k*k);
		t1y = middley - d * k * offset/Math.sqrt(1+k*k);
		t2x = middlex + d * offset/Math.sqrt(1+k*k);
		t2y = middley + d * k * offset/Math.sqrt(1+k*k);
	}
	if(!color1) {
		drawer.setColor("#8080ff");
	}
	else {
		drawer.setColor(color1);
	}
	drawArrow(drawer,x1,y1,t1x,t1y,delta);
	if(!color2) {
		drawer.setColor("#80bb80");
	}
	else {
		drawer.setColor(color2);
	}
	drawArrow(drawer,x2,y2,t2x,t2y,delta);
}

function drawTwoLine(drawer,x1,y1,x2,y2,delta,color1,color2){
	var tx,ty;
	//先排除一下小偏差
	if(Math.abs((x1-x2))<delta) {
		x2 = x1;
	}else if(Math.abs(y1-y2)<delta) {
		y2 = y1;
	}
	
	if(x1==x2) {//垂直情况
		tx = x1;
		ty = (y1+y2)/2;
	}
	else if(y1==y2) {//水平情况
		ty = y1;
		tx = (x1+x2)/2;
	}else {	//倾斜情况
		tx = x1;
		ty = y2;
	}
	drawer.setColor("#8080ff");
	drawer.drawLine(x1,y1,tx,ty);
	drawer.setColor("#80bb80");
	drawer.drawLine(tx,ty,x2,y2);
}

function drawTwoLineOutside(drawer,x1,y1,x2,y2,radius,color1,color2) {
	var tx1,ty1,tx2,ty2;
	var angle;
	if(x1==x2) {	//垂直情况
		
	}else {
		angle = Math.atan((y2-y1)/(x2-x1));
		if(x2<x1) {
			angle = angle + Math.PI;
		}
		tx1 = x1 + radius * Math.cos(angle);
		ty1 = y1 + radius * Math.sin(angle);
		tx2 = x2 - radius * Math.cos(angle);
		ty2 = y2 - radius * Math.sin(angle);
	}
	drawTwoLine(drawer,tx1,ty1,tx2,ty2,0,color1,color2);
}


function drawTwoLine2(drawer,x1,y1,x2,y2,color1,color2,horizontalFirst/*为true则横向优先*/){
	var tx,ty;
	var mx,my;
	
	if(x1==x2) {//垂直情况
		tx = x1;
		ty = (y1+y2)/2;
		drawer.setColor(color1);
		drawer.drawLine(x1,y1,tx,ty);
		drawer.setColor(color2);
		drawer.drawLine(tx,ty,x2,y2);
	}
	else if(y1==y2) {//水平情况
		ty = y1;
		tx = (x1+x2)/2;
		drawer.setColor(color1);
		drawer.drawLine(x1,y1,tx,ty);
		drawer.setColor(color2);
		drawer.drawLine(tx,ty,x2,y2);
	}else {	//倾斜情况
		if(horizontalFirst) {//先纵向再横向
			tx = x1;
			ty = y2;
			var xlen = Math.abs(x2-x1);
			var ylen = Math.abs(y2-y1);
			if(xlen>ylen) {
				my = y2;
				var leftLen = (xlen-ylen)/2;
				if(x2>x1) {
					mx = x1 + leftLen;
				}else {
					mx = x1 - leftLen;
				}
				drawer.setColor(color1);
				drawer.drawLine(x1,y1,tx,ty);
				drawer.drawLine(tx,ty,mx,my);
				drawer.setColor(color2);
				drawer.drawLine(mx,my,x2,y2);
			}else {
				mx = x1;
				var halfLen = (xlen+ylen)/2;
				if(y2>y1) {
					my = y1 + halfLen;
				}else {
					my = y1 - halfLen;
				}
				drawer.setColor(color1);
				drawer.drawLine(x1,y1,mx,my);
				drawer.setColor(color2);
				drawer.drawLine(mx,my,tx,ty);
				drawer.drawLine(tx,ty,x2,y2);
			}
		}else {
			tx = x2;
			ty = y1;
			var xlen = Math.abs(x2-x1);
			var ylen = Math.abs(y2-y1);
			if(xlen>ylen) {
				my = y1;
				var halfLen = (xlen+ylen)/2;
				if(x2>x1) {
					mx = x1 + halfLen;
				}else {
					mx = x1 - halfLen;
				}
				drawer.setColor(color1);
				drawer.drawLine(x1,y1,mx,my);
				drawer.setColor(color2);
				drawer.drawLine(mx,my,tx,ty);
				drawer.drawLine(tx,ty,x2,y2);
			}else {
				mx = x2;
				var leftLen = (ylen-xlen)/2;
				if(y2>y1) {
					my = y1 + leftLen;
				}else {
					my = y1 - leftLen;
				}
				drawer.setColor(color1);
				drawer.drawLine(x1,y1,tx,ty);
				drawer.drawLine(tx,ty,mx,my);
				drawer.setColor(color2);
				drawer.drawLine(mx,my,x2,y2);
			}
		}
	}
}

//默认两点间的距离> 2* radius
function drawPolyline(drawer,x1,y1,x2,y2,radius,color1,color2,horizontalFirst) {
	//alert("color1="+color1+" color2="+color2);
	var tx1,ty1,tx2,ty2;
	var angle;
	if(x1==x2) {	//垂直情况
		tx1 = x1;
		tx2 = x2;
		if(y1<y2) {
			ty1 = y1 + radius;
			ty2 = y2 - radius;
		}else {
			ty1 = y1 - radius;
			ty2 = y2 + radius;
		}
	}else {
		angle = Math.atan((y2-y1)/(x2-x1));
		if(x2<x1) {
			angle = angle + Math.PI;
		}
		tx1 = x1 + radius * Math.cos(angle);
		ty1 = y1 + radius * Math.sin(angle);
		tx2 = x2 - radius * Math.cos(angle);
		ty2 = y2 - radius * Math.sin(angle);
	}
	//drawTwoLine2(drawer,tx1,ty1,tx2,ty2,color1,color2,horizontalFirst);
	drawArc(drawer,tx1,ty1,tx2,ty2,color1,color2,horizontalFirst);
}


function drawSmallPolyline(drawer,x1,y1,x2,y2,radius,color1,color2) {
	//alert("color1="+color1+" color2="+color2);
	var tx1,ty1,tx2,ty2;
	var angle;
	if(x1==x2) {	//垂直情况
		tx1 = x1;
		tx2 = x2;
		if(y1<y2) {
			ty1 = y1 + radius;
			ty2 = y2 - radius;
		}else {
			ty1 = y1 - radius;
			ty2 = y2 + radius;
		}
	}else {
		angle = Math.atan((y2-y1)/(x2-x1));
		if(x2<x1) {
			angle = angle + Math.PI;
		}
		tx1 = x1 + radius * Math.cos(angle);
		ty1 = y1 + radius * Math.sin(angle);
		tx2 = x2 - radius * Math.cos(angle);
		ty2 = y2 - radius * Math.sin(angle);
	}
	
	//drawTwoLine2(drawer,tx1,ty1,tx2,ty2,color1,color2,horizontalFirst);
	drawArcSmall(drawer,tx1,ty1,tx2,ty2,color1,color2);
}
function drawArcSmall(drawer,x1,y1,x2,y2,color1,color2){	
	var tx,ty;
	var circleX,circleY;//圆心坐标
	var firstX,firstY,secondX,secondY;
	var circleR = 10;//圆弧半径
	
	if(x1==x2) {//垂直情况
		tx = x1;
		ty = (y1+y2)/2;
		drawer.setColor(color1);
		drawer.drawLine(x1,y1,tx,ty);
		drawer.setColor(color2);
		drawer.drawLine(tx,ty,x2,y2);
	}
	else if(y1==y2) {//水平情况
		ty = y1;
		tx = (x1+x2)/2;
		drawer.setColor(color1);
		drawer.drawLine(x1,y1,tx,ty);
		drawer.setColor(color2);
		drawer.drawLine(tx,ty,x2,y2);
	}else {	//倾斜情况	
			drawer.setColor(color1);
			tx = x1;
			ty = y2;
			var xlen = Math.abs(x2-x1);
			var ylen = Math.abs(y2-y1);		
			
			if(y2>y1){
				circleY = y2 - circleR;					
			}else{
				circleY = y2 + circleR;					
			}
			if(x2>x1){
				circleX = x1 + circleR;					
			}else{
				circleX = x1 - circleR;					
			}
			firstX = x1;
			firstY = circleY;
			secondX = circleX;
			secondY = y2;
        	drawer.drawLine(x1,y1,firstX,firstY);
			//画弧
			//drawer.fillArc(circleX, circleY, leftLen, leftLen, 270, 360);	
			if(x1>x2 && y1<y2){
				smallArc(drawer,circleX,circleY,circleR,0,90);
			}else if(x1>x2 && y1>y2){
				smallArc(drawer,circleX,circleY,circleR,270,360);
			}else if(x1<x2 && y1<y2){
				smallArc(drawer,circleX,circleY,circleR,90,180);
			}else if(x1<x2 && y1>y2){
				smallArc(drawer,circleX,circleY,circleR,180,270);
			}				
			drawer.drawLine(secondX,secondY,x2,y2);		
	}
}
function drawArc(drawer,x1,y1,x2,y2,color1,color2,horizontalFirst){	
	var tx,ty;
	var circleX,circleY;//圆心坐标
	var firstX,firstY,secondX,secondY;
	var circleR = 10;//圆弧半径
	
	if(x1==x2) {//垂直情况
		tx = x1;
		ty = (y1+y2)/2;
		drawer.setColor(color1);
		drawer.drawLine(x1,y1,tx,ty);
		drawer.setColor(color2);
		drawer.drawLine(tx,ty,x2,y2);
	}
	else if(y1==y2) {//水平情况
		ty = y1;
		tx = (x1+x2)/2;
		drawer.setColor(color1);
		drawer.drawLine(x1,y1,tx,ty);
		drawer.setColor(color2);
		drawer.drawLine(tx,ty,x2,y2);
	}else {	//倾斜情况	
			drawer.setColor(color1);
			tx = x1;
			ty = y2;
			var xlen = Math.abs(x2-x1);
			var ylen = Math.abs(y2-y1);				
			if(y2>y1){
				circleY = y2 - circleR;					
			}else{
				circleY = y2 + circleR;					
			}
			if(x2>x1){
				circleX = x1 + circleR;					
			}else{
				circleX = x1 - circleR;					
			}
			firstX = x1;
			firstY = circleY;
			secondX = circleX;
			secondY = y2;

			drawer.drawLine(x1,y1,firstX,firstY);
			//画弧
			//drawer.fillArc(circleX, circleY, leftLen, leftLen, 270, 360);	
			if(x1>x2 && y1<y2){
				Arc(drawer,circleX,circleY,circleR,0,90);
			}else if(x1>x2 && y1>y2){
				Arc(drawer,circleX,circleY,circleR,270,360);
			}else if(x1<x2 && y1<y2){
				Arc(drawer,circleX,circleY,circleR,90,180);
			}else if(x1<x2 && y1>y2){
				Arc(drawer,circleX,circleY,circleR,180,270);
			}				
			drawer.drawLine(secondX,secondY,x2,y2);		
	}
}

function   Arc(drawer,x,y,r,a1,a2)   {	
	var  rad = Math.PI/180;
    for(i=a1;i <=a2;i++)   {		
		drawer.drawLine(r*Math.cos(i*rad)+x,r*Math.sin(i*rad)+y,r*Math.cos(i*rad)+x,r*Math.sin(i*rad)+y);	
	} 
 }
function   smallArc(drawer,x,y,r,a1,a2)   {	
	alert("x="+x+" y="+y);
	var  rad = Math.PI/180;
    for(i=a1;i <=a2;i++)   {		
		drawer.drawLine(r*Math.cos(i*rad)+x,r*Math.sin(i*rad)+y,r*Math.cos(i*rad)+x,r*Math.sin(i*rad)+y);	
	} 
 }