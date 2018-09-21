var count = 0;

//var space = 15.4;
var first = '8.8em';
var second = '24.2em';
var third = '39.6em';

var btnVisibiled = [false, false, false];

function closeAccordionButtonContainer()
{
	if (count == 0)
	{
		$('#accordion-btn-container').hide();
	}
}

function openChartOptionsPanel()
{
	$('#chart-options-btn').hide();
	count--;
	closeAccordionButtonContainer();
	$('#chart-options-panel').show();
	
	btnVisibiled[0] = false;
	if (btnVisibiled[1] == true)
	{
		$('#countries-selector-btn').css('top', first);
		$('#regions-selector-btn').css('top', second);
	}
	else if (btnVisibiled[2] == true)
	{
		$('#regions-selector-btn').css('top', first);
	}
}

function closeChartOptionsPanel()
{
	$('#chart-options-btn').show();
	count++;
	$('#accordion-btn-container').show();
	$('#chart-options-panel').hide();
	
	btnVisibiled[0] = true;
	if (btnVisibiled[1] == true)
	{
		$('#countries-selector-btn').css('top', second);
		$('#regions-selector-btn').css('top', third);
	}
	else if (btnVisibiled[2] == true)
	{
		$('#regions-selector-btn').css('top', second);
	}
}

function openCountriesSelectorPanel()
{
	$('#countries-selector-btn').hide();
	count--;
	closeAccordionButtonContainer();
	$('#countries-selector-panel').show();
	
	btnVisibiled[1] = false;
	if (btnVisibiled[0] == true)
	{
		$('#regions-selector-btn').css('top', second);
	}
	else
	{
		$('#regions-selector-btn').css('top', first);
	}
}

function closeCountriesSelectorPanel()
{
	$('#countries-selector-btn').show();
	count++;
	$('#accordion-btn-container').show();
	$('#countries-selector-panel').hide();
	
	btnVisibiled[1] = true;
	if (btnVisibiled[0] == true)
	{
		$('#countries-selector-btn').css('top', second);
		$('#regions-selector-btn').css('top', third);
	}
	else
	{
		$('#countries-selector-btn').css('top', first);
		$('#regions-selector-btn').css('top', second);
	}
}

function openRegionsSelectorPanel()
{
	$('#regions-selector-btn').hide();
	count--;
	closeAccordionButtonContainer();
	$('#regions-selector-panel').show();
	
	btnVisibiled[2] = false;
}

function closeRegionsSelectorPanel()
{
	$('#regions-selector-btn').show();
	count++;
	$('#accordion-btn-container').show();
	$('#regions-selector-panel').hide();
	
	btnVisibiled[2] = true;
	if (btnVisibiled[0] == true && btnVisibiled[1] == true)
	{
		$('#regions-selector-btn').css('top', third);
	}
	else if (btnVisibiled[0] == true || btnVisibiled[1] == true)
	{
		$('#regions-selector-btn').css('top', second);
	}
	else
	{
		$('#regions-selector-btn').css('top', first);
	}
}