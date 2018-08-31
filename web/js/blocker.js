/**********************************************************
*
* How to implement:
*
*
* Simply include this .js file in whatever page you wish to
* block the right click function and cntrl-N and cntrl-R
*
**********************************************************/

///////////////////////////////////
function clickIE4()
{
	if (event.button==2)
	{
		return false;
	}
}

function clickNS4(e)
{
	if (document.layers||document.getElementById&&!document.all)
	{
		if (e.which==2||e.which==3)
		{
			return false;
		}
	}
}

if (document.layers)
{
	document.captureEvents(Event.MOUSEDOWN);
	document.onmousedown=clickNS4;
}
else if (document.all&&!document.getElementById)
{
	document.onmousedown=clickIE4;
}

document.oncontextmenu=new Function("return false");

document.onkeydown = function ()
{
	var event = window.event || getEvent(arguments.callee);
	if ((event.keyCode == 78) && (event.ctrlKey))
	{
		void(0);
		event.keyCode = 0;
		event.returnValue = false;
		event.cancelBubble = true;
		return false;
	}
	if ((event.keyCode == 82) && (event.ctrlKey))
	{
		void(0);
		event.keyCode = 0;
		event.returnValue = false;
		event.cancelBubble = true;
		return false;
	}
	if (event.keyCode == 116)
	{ 
		//F5
		void(0);
		event.keyCode = 0;
		event.returnValue = false;
		event.cancelBubble = true;
		return false;
	}
};