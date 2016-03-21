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
(function init() {

	/* 高亮显示左导航菜单的当前菜单项  */
	var href = window.location.href;
	var u = document.getElementById("leftMenu");
	var es = u.getElementsByTagName("li");
	for (var index in es ){
		if (index != "length") {
			var url = es[index].childNodes[0].href;
			if (href.indexOf(url) != -1) {
				es[index].style.background='url(images/rr/tabbg02.gif)';
				break;
			}
		}
	}
	
	/* 对页面添加监听事件，当按 F5 键时延缓刷新页面*/
	document.onkeydown = function() {
		var e  = event || window.event;
		if (e.keyCode == 122) {
			setTimeout("document.location.reload()", 1000);
		}
	}
	
	/*  控制左边导航菜单的高度    */
	var menuDiv = document.getElementById("menuDiv");
	var height = document.body.clientHeight * 0.95;
	menuDiv.style.height = height;
	
})();