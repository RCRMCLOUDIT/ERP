/***************************************************************
 * validate item classificatin add/update
***************************************************************/
function class_submit(form)
{
	if(form._message.value == "list")
		return true;
		
	if(form.parentId.value == "0" && form.classTypeId.selectedIndex == 0)
	{
		form.classTypeId.focus();
		alert("Please select classification type to continue.");
		return false;
	}
	if(form.name.value.length == 0 || form.name.value.length > 50)
	{
		form.name.select();
		form.name.focus();
		alert("Please enter classification name to continue.");
		return false;
	}
	
	return true;
}

/***************************************************************
 * validate item class assign
***************************************************************/
function class_assign(form)
{	
	if(form.classTypeId.selectedIndex == 0)
	{
		form.classTypeId.focus();
		alert("Please select classification type to continue.");
		return false;
	}
	if(form.classId.value == "0" || form.classId.value.length == 0)
	{
		form.className.select();
		form.className.focus();
		alert("Please select valid classification to continue.");
		return false;
	}
	return true;
}
/***************************************************************
 * open vendor class search popup window
***************************************************************/
function class_search(form, contextPath)
{	
	if(form.classTypeId.selectedIndex == 0) 
	{
		alert("Please select classification type to continue.");
		return;
	} 

	var url = 
	  		contextPath + "/fa/Classification.do?_message=search&classTypeId=" + 
	  		form.classTypeId.options[form.classTypeId.selectedIndex].value + 
	  		"&search=" + URLEncode(form.className.value);

 	openDialog(url, "Search Item Classification", "500","500", contextPath);
}
/***************************************************************
 * reset fields when change classType
***************************************************************/
function class_change(form)
{	
	form.classId.value = "0";
	form.className.value = "";	
}