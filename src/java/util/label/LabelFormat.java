package com.cap.util.label;
/*
 * Created on Dec 17, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/**
 * @author Owner
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
import com.lowagie.text.*;

public class LabelFormat {
	float tableWidth;
	float[] headerWidths;
	float[] bodyWidths;
	float bodyHeight;
	float headerHeight;
	int topMargin ;
	int bottomMargin ;
	int leftMargin;
	int rightMargin;
		
	// Font to show the To: word in the label's body
	Font toName_font = new Font(Font.HELVETICA, 12, Font.BOLD);
	// Font to show the destination address in the label's body
	Font toAddress_font = new Font(Font.HELVETICA, 8, Font.NORMAL);
	Font orderInfo_font = new Font(Font.COURIER, 8, Font.NORMAL);
	
	Font companyName_font = new Font(Font.HELVETICA, 9, Font.BOLD);    
	Font companyAddress_font = new Font(Font.HELVETICA, 8, Font.NORMAL);
	
	public LabelFormat(Rectangle pageSize)
	{
		if (pageSize == PageSize.LETTER) {
			this.tableWidth = 296f;					// 1" = 72 points
			this.headerWidths = new float[]{5f, 35f, 55f, 5f};
//			this.bodyWidths = new float[]{5f, 15f, 75f, 5f};
			this.bodyWidths = new float[]{4f, 8f, 69f, 15f, 4f};
			this.bodyHeight = 160f;
			this.headerHeight = 78f;
		}

		if (pageSize == PageSize.A4) {
			this.tableWidth = 298f;
			this.headerWidths = new float[]{5f, 35f, 55f, 5f};
			this.bodyWidths = new float[]{5f, 15f, 75f, 5f};
			this.bodyHeight = 133f;
			this.headerHeight = 133f;
		 
		}
//		default
		topMargin = 38;  
		bottomMargin = 38;  
		leftMargin = 5;
		rightMargin = 5;
	}

	float xPosition(Rectangle pageSize , int label_index){
	   float  xPos = 0f;
	   	if (pageSize == PageSize.LETTER) {
			switch ((label_index+1)%2){
			case 0: xPos = 5f;		break;              //based on margin 
			case 1: xPos = 311f; 	break;				// (612-5*4)/2+5*3
	   		}
	   	}
		if (pageSize == PageSize.A4) {
			switch ((label_index+1)%2){
			case 0: xPos = 5f;		break;
			case 1: xPos = 298f; 	break;
			}
		} 
	return xPos;
	}

	float yPosition(Rectangle pageSize, int label_index){
	   float  yPos = 0f;
	   	if (pageSize == PageSize.LETTER)  {
	   		switch (label_index) {
			case 1:
			case 2: yPos = 754f; break;
			case 3:
			case 4: yPos = 514f; break;
			case 5:
			case 6: yPos = 274f; break;
			}
	   	}
		if (pageSize == PageSize.A4)  {
			switch (label_index) {
			case 1:
			case 2: yPos = 819f; break;
			case 3:
			case 4: yPos = 553f; break;
			case 5:
			case 6: yPos = 287f; break;
			}
		}
	return yPos;
   } 

   float xPosition1(Rectangle pageSize , int label_index){
	  float  xPos = 0f;
	   if (pageSize == PageSize.LETTER) {
		   switch ((label_index+1)%2){
		   case 0: xPos = 0f;		break;
		   case 1: xPos = 306f; 	break;
		   }
	   }
	   if (pageSize == PageSize.A4) {
		   switch ((label_index+1)%2){
		   case 0: xPos = 0f;		break;
		   case 1: xPos = 298f; 	break;
		   }
	   } 
   return xPos;
   }

   float yPosition1(Rectangle pageSize, int label_index){
	  float  yPos = 0f;
	   if (pageSize == PageSize.LETTER)  {
		   switch (label_index) {
		   case 1:
		   case 2: yPos = 771f; break;
		   case 3:
		   case 4: yPos = 646f; break;
		   case 5:
		   case 6: yPos = 521f; break;
		   case 7:
		   case 8: yPos = 396f; break;
		   case 9:
		   case 10: yPos = 271f; break;
		   case 11:
		   case 12: yPos = 146f; break;
		   }
	   }
	   if (pageSize == PageSize.A4)  {
		   switch (label_index) {
		   case 1:
		   case 2: yPos = 819f; break;
		   case 3:
		   case 4: yPos = 686f; break;
		   case 5:
		   case 6: yPos = 553f; break;
		   case 7:
		   case 8: yPos = 420f; break;
		   case 9:
		   case 10: yPos = 287f; break;
		   case 11:
		   case 12: yPos = 154f; break;

		   }
	   }
   return yPos;
  } 
/*
	void setTopMargin(int topMargin){
		this.topMargin = topMargin;
	}
	void setBottomMargin(int bottomMargin){
		this.bottomMargin = bottomMargin;
	}
	void setRightMargin(int rightMargin){
		this.rightMargin = rightMargin;
	}
	void setLeftMargin(int leftMargin){
		this.leftMargin = leftMargin;
	}
*/

}
