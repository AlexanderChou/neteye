<%--
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
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
    <head>
    	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>网络节点综合信息显示</title>
        <link rel="stylesheet" type="text/css" href="css/topoInit.css" />
        <link rel="stylesheet" type="text/css" href="css/ext-all.css" />
    <script type="text/javascript" src="js/ext-base.js">
    </script>
    <script type="text/javascript" src="js/ext-all.js">
    </script>
    <script type="text/javascript" src="js/ext-all-debug.js"></script>
    <script type="text/javascript" src="js/Portal.js"></script>
    <script type="text/javascript" src="js/PortalColumn.js"></script>
    <script type="text/javascript" src="js/Portlet.js"></script>
     <script type="text/javascript" src="js/router_switch_info.js"></script>
     <script type="text/javascript" src="js/fixTime.js"></script>
        <style type="text/css">
            html, body {
                margin: 0;
                padding: 0;
                border: 0 none;
                overflow: hidden;
                height: 100%;
            }
            p {
                margin: 5px;
            }
            .settings {
                background-image: url("images/folder_wrench.png");
            }
            .nav {
                background-image: url("images/folder_go.png");
            }
            h1 {
            	text-align:center;
            	font: bold 60px verdana;
            	color: #0060A8
            }
            h2 {
            	font: bold 20px verdana;
            	display: inline;
            	color: #0060A8
            }
            h3 {
            	font: bold 15px verdana;
            	display: inline;
            	color: #0060A8
            }
        </style>
    </head>
    <body>
        <!-- EXAMPLES -->
        <div id="south">
            <p>
                目前节点状况良好
            </p>
        </div>
        
        <div id="tmp">
        
        </div>
        
        <div id="responseDiv">
        	
        </div>
        
    </body>
</html>