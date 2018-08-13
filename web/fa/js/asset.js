//***************************************************
// validate asset add/update 
//***************************************************
function asset_show(form)
{
	form._message.value = "list";
	form.page.value = "1";
	form.submit();
}
//***************************************************
// validate asset add/update 
//***************************************************
function asset_submit(form)
{
	if(form._message.value == 'list' || form._message.value == 'lookup')
		return true;
	if (form.assetName.value.length == 0)
	{
		form.assetName.focus();
		form.assetName.select();
		alert(a_jsMsg.error1.value);
		return false;
	}
	//check manuf
	if(form.manuId.value == '' && form.manuName.value != '')
	{
		form.manuName.select();
		form.manuName.focus();
		alert(a_jsMsg.error2.value);
		return false;
	}
	//check vendor
	if(form.vendId.value == '' && form.vendName.value != '')
	{
		form.vendName.select();
		form.vendName.focus();
		alert(a_jsMsg.error3.value);
		return false;
	}
	//check Date
	if (form.acqDate.value.length != 0 && !isValidDate(form.acqDate))
	{
		form.acqDate.focus();
		form.acqDate.select();
		alert(a_jsMsg.error4.value);
		return false;
	}
	if (form.warranty.value.length != 0)
	{
		if(!isValidDate(form.warranty))
		{
			form.warranty.focus();
			form.warranty.select();
			alert(a_jsMsg.error5.value);
			return false;
		}
		if(form.acqDate.value.length != 0 && cmpDate(form.acqDate, form.warranty) > 0)
		{
			form.warranty.focus();
			form.warranty.select();
			alert(a_jsMsg.error6.value);
			return false;
		}
	}
	if(form._message.value == 'add' || (form._message.value == 'update' && form.status.value != "RG"))
	{
		//check price and currency
		if (form.price.value.length != 0 && form.price.value*1 != 0 && !isValidNumber216(form.price))
		{
			form.price.focus();
			form.price.select();
			alert(a_jsMsg.error7.value);
			return false;
		}
		if (form.price.value.length != 0 && form.price.value*1 != 0 && form.currency.selectedIndex == 0)
		{
			form.currency.focus();
			alert(a_jsMsg.error8.value);
			return false;
		}
		//check cost center
	  	if(form.empCustId0.value == " ©©© ©©© ©©©0©©©0©©©" && form.empCustIdText0.value != "")
	  	{
	        form.empCustIdText0.focus();
	        form.empCustIdText0.select();
			alert(a_jsMsg.error9.value);
			return false;
	  	}	  
	  	if(form.empCustId0.value != " ©©© ©©© ©©©0©©©0©©©" && form.acqDate.value.length ==0)
	  	{
	  		form.acqDate.focus();
	  		form.acqDate.select();
	  		alert(a_jsMsg.error10.value);
	  		return false;
	  	}
		//check employee
		if((form.empId.value == '' || form.empId.value == '0') && form.empName.value != '')
		{
			form.empName.focus();
			form.empName.select();
			alert(a_jsMsg.error19.value);
			return false;
		}
		//check salvageAmount
//		if (form.salvage.value.length != 0 && form.salvage.value*1 != 0 && !isValidNumber216(form.salvage))
		if (form.salvage.value.length != 0  && !isValidNumber216(form.salvage))
		{
			form.salvage.focus();
			form.salvage.select();
			alert(a_jsMsg.error11.value);
			return false;
		}
		//check GL Account
		if(form.level0.value == "" && form.levelText0.value != "")
		{
			form.levelText0.select();
			form.levelText0.focus();
			alert(a_jsMsg.error12.value);
			return false;
		}
		if(form.level3.value == "" && form.levelText3.value != "")
		{
			form.levelText3.select();
			form.levelText3.focus();
			alert(a_jsMsg.error13.value);
			return false;
		}
		if(form.level1.value == "" && form.levelText1.value != "")
		{
			form.levelText1.select();
			form.levelText1.focus();
			alert(a_jsMsg.error14.value);
			return false;
		}
		if(form.level2.value == "" && form.levelText2.value != "")
		{
			form.levelText2.select();
			form.levelText2.focus();
			alert(a_jsMsg.error17.value);
			return false;
		}
	}
	return true;
}

function deprun_submit(form)
{
	if(form._message.value == 'listrun')
		return true;

	if (form.runDate.value.length == 0 || !isValidDate(form.runDate))
	{
		form.runDate.focus();
		form.runDate.select();
		alert(a_jsMsg.error20.value);
		return false;
	}

	if (cmpDate(form.lastRunDate, form.runDate) >=0)
	{
		form.runDate.focus();
		alert(a_jsMsg.error21.value);		
		return false;
	}
	
  	 if(confirm(a_jsMsg.error22.value) == false)
  	 {
  	 	return false;
	 }

	return true;	
}

function relocate_submit(form)
{
	if(form._message.value == 'list')
		return true;
		
	if (form.locDate.value.length == 0 || !isValidDate(form.locDate))
	{
		form.locDate.focus();
		form.locDate.select();
		alert(a_jsMsg.error23.value);
		return false;
	}
	if (cmpDate(form.preDate, form.locDate) >=0)
	{
		form.locDate.focus();
		alert(a_jsMsg.error24.value);
		return false;
	}
	//check cost center
  	if(form.empCustId0.value == " ©©© ©©© ©©©0©©©0©©©" || form.empCustIdText0.value == "")
  	{
        form.empCustIdText0.focus();
        form.empCustIdText0.select();
		alert(a_jsMsg.error9.value);
		return false;
  	}	  

	return true;	
}

//***************************************************
// validate asset add/update 
//***************************************************
function asset_register(form)
{
	if(form._message.value == 'list')
		return true;

	if (form.assetName.value.length == 0)
	{
		form.assetName.focus();
		form.assetName.select();
		alert(a_jsMsg.error1.value);
		return false;
	}
	//check manuf
	if(form.manuId.value == '' && form.manuName.value != '')
	{
		form.manuName.select();
		form.manuName.focus();
		alert(a_jsMsg.error2.value);
		return false;
	}
	//check vendor
	if(form.vendId.value == '' && form.vendName.value != '')
	{
		form.vendName.select();
		form.vendName.focus();
		alert(a_jsMsg.error3.value);
		return false;
	}
	//check Date
	if (form.acqDate.value.length == 0 || !isValidDate(form.acqDate))
	{
		form.acqDate.focus();
		form.acqDate.select();
		alert(a_jsMsg.error4.value);
		return false;
	}
	if (form.warranty.value.length != 0)
	{
		if(!isValidDate(form.warranty))
		{
			form.warranty.focus();
			form.warranty.select();
			alert(a_jsMsg.error5.value);
			return false;
		}
		if(cmpDate(form.acqDate, form.warranty) > 0)
		{
			form.warranty.focus();
			form.warranty.select();
			alert(a_jsMsg.error6.value);
			return false;
		}
	}
	//check price and currency
	if (form.price.value.length == 0 || form.price.value*1 ==0 || !isValidNumber216(form.price))
	{
		form.price.focus();
		form.price.select();
		alert(a_jsMsg.error7.value);
		return false;
	}
	if (form.currency.selectedIndex == 0)
	{
		form.currency.focus();
		alert(a_jsMsg.error8.value);
		return false;
	}
	if(form.status.value != "RG")
	{
		//check cost center
	  	if(form.empCustId0.value == " ©©© ©©© ©©©0©©©0©©©" || form.empCustId0.value == "")
	  	{
	        form.empCustIdText0.focus();
	        form.empCustIdText0.select();
			alert(a_jsMsg.error9.value);
			return false;
	  	}	  
	 	//check depreciation 
		if (form.depMethod.selectedIndex == 0)
		{
			form.depMethod.focus();
			alert(a_jsMsg.error15.value);
			return false;
		}
	}
	//check employee
	if((form.empId.value == '' || form.empId.value == '0') && form.empName.value != '')
	{
		form.empName.focus();
		form.empName.select();
		alert(a_jsMsg.error19.value);
		return false;
	}
	if (form.salvage.value.length == 0 || !isValidNumber216(form.salvage))
	{
		form.salvage.focus();
		form.salvage.select();
		alert(a_jsMsg.error11.value);
		return false;
	}
	if (form.lifeTime.value.length == 0 || form.lifeTime.value*1 ==0 || !isValidNumber(form.lifeTime))
	{
		form.lifeTime.focus();
		form.lifeTime.select();
		alert(a_jsMsg.error16.value);
		return false;
	}
	if (form.totDepMnths.value.length != 0 && form.totDepMnths.value*1 !=0 )
	{
		if (form.totDepMnths.value*1 > form.lifeTime.value*1){
			form.lifeTime.focus();
			form.lifeTime.select();
			alert(a_jsMsg.error25.value);
			return false;
		}
	}
	if(form.level0.value == "")
	{
		form.levelText0.select();
		form.levelText0.focus();
		alert(a_jsMsg.error12.value);
		return false;
	}
	if(form.level3.value == "")
	{
		form.levelText3.select();
		form.levelText3.focus();
		alert(a_jsMsg.error13.value);
		return false;
	}
	if(form.level1.value == "")
	{
		form.levelText1.select();
		form.levelText1.focus();
		alert(a_jsMsg.error14.value);
		return false;
	}
	if(form.level2.value == "")
	{
		form.levelText2.select();
		form.levelText2.focus();
		alert(a_jsMsg.error17.value);
		return false;
	}
	return true;
}
/***************************************************************
 * open vendor class search popup window
***************************************************************/
function asset_class_search(form, clType, fname, fld1, fld2, contextPath)
{	
	var classType = eval("form." + clType);
	var className = eval("form." + fld2);
	if(classType.selectedIndex == 0) 
	{
		classType.focus();
		alert(a_jsMsg.error18.value);
		return;
	} 
	var url = contextPath + "/fa/Classification.do?_message=search&f_name=" + fname + 
			"&fld_1=" + fld1 + 
			"&fld_2=" + fld2 + 
			"&classTypeId=" + classType.options[classType.selectedIndex].value + 
	  		"&allowParent=Y&search=" + URLEncode(className.value);
 	openDialog(url, "Search Item Classification", "550","550", contextPath);
}
/***************************************************************
 * reset fields when change classType
***************************************************************/
function asset_class_change(form)
{	
	form.classId.value = "0";
	form.className.value = "";	
}
/***************************************************************
 * reset fields when change classType lookup page
***************************************************************/
function asset_lookup_change(form)
{	
	form.lcid.value = "0";
	form.lcnm.value = "";	
}
 /***********************************************
 * lookup submit validation
 ************************************************/
function asset_lookup(form) 
{
	if((form._message.value == 'new'))
		return true;

	//check that only one section is being searched forlookup
	//var ptf;
	var qtt;
	qtf = eval("form.ffdt");
	qtt = eval("form.ftdt");

	if ((form.lcid.value == "" || form.lcid.value == "0") && form.lcnm.value  != "")
	{
		form.lcnm.focus(); 
		form.lcnm.select();
		alert(a_jsMsg.error26.value);
		return false;
	} 
	if ((form.empCustId9.value == "" || form.empCustId9.value == " ©©© ©©© ©©©0©©©0©©©") && form.empCustIdText9.value  != "")
	{
		form.empCustIdText9.focus(); 
		form.empCustIdText9.select();
		alert(a_jsMsg.error27.value);
		return false;
	} 
	if (form.lfdt.value != "" && !isValidDate(form.lfdt))
	{
		form.lfdt.focus(); 
		form.lfdt.select();
		alert(a_jsMsg.error28.value);
		return false;
	} 
	if (form.ltdt.value != "" && !isValidDate(form.ltdt))
	{
		form.ltdt.focus(); 
		form.ltdt.select();
		alert(a_jsMsg.error28.value);
		return false;
	} 
    if (form.lfdt.value != "" && form.ltdt.value != "" && cmpDate(form.lfdt, form.ltdt) > 0)	
	{
		form.ltdt.focus();
		form.ltdt.select();
		alert(a_jsMsg.error29.value);
		return false;
	}
	if( form.ffdt.value != '' && !isValidNumber216(qtf))
	{
		form.ffdt.focus(); 
		form.ffdt.select(); 
		alert(a_jsMsg.error30.value);
		return false;  
	}
	if( form.ftdt.value != '' && !isValidNumber216(qtt))
	{
		form.ftdt.focus(); 
		form.ftdt.select(); 
		alert(a_jsMsg.error30.value);
		return false;  
	}
	
    if (form.ffdt.value != "" && form.ftdt.value != "" && (form.ffdt.value * 1 >  form.ftdt.value * 1))	
	{
		form.ftdt.focus();
		form.ftdt.select();
		alert(a_jsMsg.error31.value);
		return false;
	}
	if ((form.vendI.value == "" || form.vendI.value == "0") && form.vendN.value  != "")
	{
		form.vendN.focus(); 
		form.vendN.select();
		alert(a_jsMsg.error32.value);
		return false;
	} 
	if ((form.lemp.value == "" || form.lemp.value == "0") && form.lempnm.value  != "")
	{
		form.lempnm.focus(); 
		form.lempnm.select(); 
		alert(a_jsMsg.error33.value);
		return false;
	} 

	//0= Html, 1=Excel, 2=PDF
    if(form.formato[0].checked == true)
    {
    	return true;
    }	
    else//(form.formato[2].checked == true || form.formato[1].checked == true)
	{
		if(form.formato[1].checked == true)
		{
			var url = form.contextRoot.value + "/fa/Asset.do?_message=prtExcel" + 
							  "&presId=" + form.presId.options[form.presId.selectedIndex].value +
							  "&assName=" + form.assName.value + 
							  "&asTag=" + form.asTag.value + 
							  "&lfdt=" + form.lfdt.value + 
							  "&ltdt=" + form.ltdt.value + 
							  "&ffdt=" + form.ffdt.value + 
							  "&ftdt=" + form.ftdt.value + 
							  "&vendI=" + form.vendI.value + 
							  "&lst=" + form.lst.value + 
							  "&entType9=" + form.entType9.value + 
							  "&empCustId9=" + form.empCustId9.value + 
							  "&lcty=" + form.lcty.value + 
							  "&lcid=" + form.lcid.value + 
							  "&lemp=" + form.lemp.value ;
			
			openDialog(url, "Fixed Asset Report", "760", "700", form.contextRoot.value);
		}
		else
		{
			var url = form.contextRoot.value + "/fa/Asset.do?_message=pripdf" + 
							  "&presId=" + form.presId.options[form.presId.selectedIndex].value +
							  "&assName=" + form.assName.value + 
							  "&asTag=" + form.asTag.value + 
							  "&lfdt=" + form.lfdt.value + 
							  "&ltdt=" + form.ltdt.value + 
							  "&ffdt=" + form.ffdt.value + 
							  "&ftdt=" + form.ftdt.value + 
							  "&vendI=" + form.vendI.value + 
							  "&lst=" + form.lst.value + 
							  "&entType9=" + form.entType9.value + 
							  "&empCustId9=" + form.empCustId9.value + 
							  "&lcty=" + form.lcty.value + 
							  "&lcid=" + form.lcid.value + 
							  "&lemp=" + form.lemp.value ;
			
			openDialog(url, "Fixed Asset Report", "760", "700", form.contextRoot.value);
		}
		return false;
	}
}
//***************************************************
// validate depreciate all submit
//***************************************************
function depall_submit(form)
{
	if(form._message.value != 'depall')
		return true;

	if (form.depDate.value.length == 0 || !isValidDate(form.depDate))
	{
		form.depDate.focus();
		form.depDate.select();
		alert(a_jsMsg.error34.value);
		return false;
	}
	if (cmpDate(form.lastRunDate, form.depDate) >=0)
	{
		form.depDate.focus();
		form.depDate.select();
		alert(a_jsMsg.error35.value);
		return false;
	}
	//check comment
	if(form.comm.value == '')
	{
  		form.comm.focus();
  		form.comm.select();
		alert(a_jsMsg.error36.value);
  		return false;
	}
	return true;	
}
//***************************************************
// validate remove asset submit
//***************************************************
function remove_submit(form)
{
	if(form._message.value != 'remove')
		return true;

	if (form.removeDate.value.length == 0 || !isValidDate(form.removeDate))
	{
		form.removeDate.focus();
		form.removeDate.select();
		alert(a_jsMsg.error34.value);
		return false;
	}
	if (cmpDate(form.lastRunDate, form.removeDate) >0)
	{
		form.removeDate.focus();
		form.removeDate.select();
		alert(a_jsMsg.error37.value);
		return false;
	}
	if(form.depBefore.value == 'Y')
	{
		//check cost center
	  	if(form.empCustId0.value == " ©©© ©©© ©©©0©©©0©©©" && form.empCustIdText0.value != "")
	  	{
	        form.empCustIdText0.focus();
	        form.empCustIdText0.select();
			alert(a_jsMsg.error9.value);
			return false;
	  	}	  
	}
	//check comment
	if(form.comm.value == '')
	{
  		form.comm.focus();
  		form.comm.select();
		alert(a_jsMsg.error36.value);
  		return false;
	}
	return true;	
}

/***********************************************
* lookup submit validation
************************************************/
function asset_report(form) 
{
	var url = form.contextRoot.value + "/fa/Asset.do?_message=" + form._message.value + 
		  "&rptDate=" + form.rptDate.value;
			
	openDialog(url, "Depreciation Report", "760", "700", form.contextRoot.value);
	return false;
}