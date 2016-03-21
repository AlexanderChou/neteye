package com.view.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.base.util.Constants;

/*
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
*/
public class GraphicLine {
	public static void graphic(String fileName,List lineList) throws Exception{
		File file = new File( Constants.webRealPath+"file/topo/topoHis/"+fileName+ "_sub.png"); 
		File imageFile=new File( Constants.webRealPath+"file/topo/topoHis/"+fileName+ ".png"); 
		if(imageFile.exists()){
			Image readImage = ImageIO.read(imageFile);
			String url= Constants.webRealPath+"file/topo/topoHis/"+fileName+ ".png";
			readImage = Toolkit.getDefaultToolkit().getImage(url); 
			MediaTracker tracker = new MediaTracker(null); 
			tracker.addImage(readImage, 0);
			ImageIcon icon = new ImageIcon(url);
			readImage = icon.getImage();
			
			int width = 1280; 
			int height = 1024;
			BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			Graphics2D graphic2D = image.createGraphics();
			image = graphic2D.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
			graphic2D.dispose();
			graphic2D = image.createGraphics();
			graphic2D.drawImage(readImage, 0, 0,null);	
			graphic2D.setPaint(Color.red);
			graphic2D.setStroke(new BasicStroke(3.0f));
			if(lineList!=null){
				for(int i=0;i<lineList.size();i++){
					int sourx=0;
					int soury=0;
					int descx=0;
					int descy=0;		
					graphic2D.draw(new Line2D.Double(sourx,soury,descx,descy));	
				}
			}
			graphic2D.dispose();
			ImageIO.write(image, "png", file);
		}
	}
}
