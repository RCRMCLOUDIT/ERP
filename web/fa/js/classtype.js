//***************************************************
// validate item classification type add/update
//***************************************************
function class_type_submit(form)
{
	if(form._message.value == 'list')
		return true;
		
	if(form.typeName.value.length == 0 || form.typeName.value.length > 50)
	{
		form.typeName.select();
		form.typeName.focus();
		alert("Please enter type name to continue.");
		return false;
	}
	if(form.maxLevel.value.length == 0)
	{
		form.maxLevel.select();
		form.maxLevel.focus();
		alert("Please enter maximum level to continue.");
		return false;	
	}
	return true;
}