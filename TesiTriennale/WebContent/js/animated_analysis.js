var xhttpCheckBoxs;
var arrayCollapsibleRegions = [];
var arrayCheckBoxs = [];
var copyArrayCheckBoxs = [];

var xhttpChart;
var firstYear = '1995';
var lastYear = (new Date).getFullYear();
//var indexYear = 0;
var animatedChart = new AnimatedBubbleChart();
//var arrayData;
var selectedCountries = [];
//var animatedChartUser = new AnimatedBubbleChart();

function isJSONString(str)
{
	var regexJSONObject = /^\{.*\}$/;
	var regexJSONArray = /^\[.*\]$/;
	if (str.match(regexJSONObject) || str.match(regexJSONArray))
	{
		return true;
	}
	else
	{
		return false;
	}
}

/* CHECKBOXS COUNTRIES*/
function CheckBox(countryCode, countryName)
{
	this.container = $('<label class="container"></label>');
	this.input = $('<input class="country-checkbox" type="checkbox" onchange="checkBoxCountryHandler(this)">');
	this.span = $('<span class="checkmark"></span>');
	this.container.html(countryName);
	this.input.attr("name", countryName);
	this.input.attr("value", countryCode);
	this.container.append(this.input);
	this.container.append(this.span);
}

function RegionCheckBox(regionName)
{
	this.input = $('<input class="region-checkbox" type="checkbox" onchange="checkBoxRegionHandler(this)">');
	this.input.attr("value", regionName);
}

function addCollapsibleListener()
{
	var coll = document.getElementsByClassName("collapsible");
	for (var i = 0; i < coll.length; i++)
	{
		coll[i].addEventListener("click", function() {
			this.classList.toggle("active");
			this.parentElement.classList.toggle("active");
			var content = this.parentElement.nextElementSibling;
			if (content.style.maxHeight)
			{
				content.style.maxHeight = null;
			}
			else
			{
				content.style.maxHeight = content.scrollHeight + "px";
			}
	   });
	}
}

function CollapsibleRegion(regionName)
{
	this.checkbox = new RegionCheckBox(regionName);
	this.title = $('<div class="wrapper-ragion-checkbox"></div>');
	this.button = $('<button class="collapsible"></button>');
	this.div = $('<div class="content"></div>');
	this.button.html(regionName);
	this.div.attr('id', regionName);
	
	this.title.append(this.checkbox.input);
	this.title.append(this.button);
	
	this.region = regionName;
}

function showCheckboxs()
{
	if (xhttpCheckBoxs.readyState == 4 && xhttpCheckBoxs.status == 200)
	{
//		document.body.style.cursor = "auto";
		
		var resultSTR = xhttpCheckBoxs.responseText;
		if (isJSONString(resultSTR))
		{
			var resultJSON = JSON.parse(resultSTR);
			var regions = resultJSON[0];
			var countries = resultJSON[1];
			for (var i = 0; i < regions.length; i++)
			{
				var collapsibleRegion = new CollapsibleRegion(regions[i]);
	//			$('#countries-selector-panel').append(collapsibleRegion.button);
				$('#countries-selector-panel').append(collapsibleRegion.title);
				$('#countries-selector-panel').append(collapsibleRegion.div);
	//			$('#countries-selector-panel').append(collapsibleRegion.div);
				arrayCollapsibleRegions.push(collapsibleRegion);
			}
			$('#countries-selector-panel').append('<hr>');
			
			for (var i = 0; i < countries.length; i++)
			{
				var checkBox = new CheckBox(countries[i].code, countries[i].name);
				var copyCheckBox = new CheckBox(countries[i].code, countries[i].name);
				var found = false;
				var j = 0;
				while (found == false && j < arrayCollapsibleRegions.length)
				{
					if (countries[i].region == arrayCollapsibleRegions[j].region)
					{
						arrayCollapsibleRegions[j].div.append(checkBox.container);
						found = true;
					}
					j++
				}
				arrayCheckBoxs.push(checkBox);
				copyArrayCheckBoxs.push(copyCheckBox);
			}
			
			addCollapsibleListener();
			$('input[type="checkbox"]').prop('disabled', true);
		}
	}
}

function loadCheckboxs()
{
//	document.body.style.cursor = "wait";
	
	xhttpCheckBoxs = new XMLHttpRequest();
	xhttpCheckBoxs.open("get", "load_countries_animated_chart", true);
	xhttpCheckBoxs.setRequestHeader("connection", "close");
	xhttpCheckBoxs.onreadystatechange = showCheckboxs;
	xhttpCheckBoxs.send(null);
}

/* ANIMATED CHART (INITIAL) */
function AnimatedBubbleChart()
{
	this.options = {
		titlePosition: 'in',
		titleTextStyle: {
			color: '#4CAF50',
			fontName: 'Ariel',
			fontSize: 40,
			bold: true,
			italic: false
		},
		chartArea: {
			width: '90%',
			height: '85%'
		},
		legend: {
			position: 'none'
		},
		backgroundColor: '#f1f1f1',
//		interpolateNulls: true,
		theme: 'material',
		hAxis: {
			title: 'GDP per capita'
		},
		vAxis: {
			title: 'Poverty'
		},
		sizeAxis: {
			maxSize: 60,
			minSize: 10
		},
		explorer: {
//            axis: 'horizontal',
            keepInBounds: true,
            maxZoomIn: 2.50,
            maxZoomOut: 1
		}
//		animation: {
//			duration: 1000,
//	        easing: 'out'
//	    }
	};
	this.arrayYearsData = [];
	this.years = [];
	this.index = 0;
	
	this.resetArrayYearsData = function()
	{
		this.arrayYearsData = [];
	}
	
	this.resetYears = function()
	{
		this.years = [];
	}
	
	this.resetIndex = function()
	{
		this.index = 0;
	}
	
	this.addYearData = function(arrayData)
	{
		this.arrayYearsData.push(arrayData);
	}
	
	this.addYear = function(year)
	{
		this.years.push(year);
	}
	
	this.increaseIndex = function()
	{
		this.index += 1;
	}
	
	this.getCurrentYear = function()
	{
		return this.years[this.index];
	}
	
	this.setCurrentTitle = function()
	{
		this.options.title = this.getCurrentYear();
	}
	
	this.hasNextYear = function()
	{
		return (this.index < (this.years.length - 1));
	}
	
	this.getNextStep = function()
	{
		if (this.index == 0)
		{
			return 0;
		}
		else
		{
			return this.years[this.index] - this.years[this.index - 1];
		}
	}
	
	this.getYearIndex = function(year)
	{
		for (var i = 0; i < this.years.length; i++)
		{
			if (this.years[i] == year)
			{
				return i;
			}
		}
		return -1;
	}
//	this.data = null;
//	this.setData = function(arrayData) {
//		this.data = google.visualization.arrayToDataTable(arrayData);
//	}
}

function ArrayData()
{
	this.data = [['ID', 'GDP per capita', 'Poverty', 'Country', 'Population']];
	this.insertData = function(dataToInsert) {
		for (var i = 0; i < dataToInsert.length; i++)
		{
			var data = dataToInsert[i];
//			console.log([data.name, data.gdppercapita, data.poverty, data.population]);
			this.data.push([data.code, data.gdppercapita, data.poverty, data.name, data.population]);
		}
	}
}

function drawAnimatedChart()
{
	$('input[type="checkbox"]').prop('disabled', false);
//	console.log("Array: " + animatedChart.arrayYearsData);
	animatedChart.setCurrentTitle();
	
//	console.log(animatedChart.getNextStep());
	if (document.getElementById('yearsRange').value != animatedChart.getCurrentYear())
	{
		document.getElementById('yearsRange').stepUp(animatedChart.getNextStep());
	}
//	console.log(document.getElementById('yearsRange').value);
	
	var data = google.visualization.arrayToDataTable(animatedChart.arrayYearsData[animatedChart.index]);
	
	var chart = new google.visualization.BubbleChart(document.getElementById('animated-chart'));
	
	if (animatedChart.hasNextYear() && document.getElementById('playButton').classList.contains("paused"))
	{
		animatedChart.increaseIndex();
		setTimeout(drawAnimatedChart, 1000);
	}
	else if (!animatedChart.hasNextYear())
	{
		document.getElementById('playButton').classList.remove("paused");
	}
	
	chart.draw(data, animatedChart.options);
}

function getAxisTicks(min, max, range)
{
	var ticks = []
	while (min < max)
	{
		ticks.push(min);
		min += range;
	}
	ticks.push(min);
	return ticks;
}

//function getAxisTicks(min, max)
//{
//	var ticks = []
//	ticks.push(0);
//	ticks.push(min);
//	min *= 2;
//	while (min < max)
//	{
//		ticks.push(min);
//		min *= 2;
//	}
//	ticks.push(min);
//	return ticks;
//}

function showAnimatedChart()
{
	if (xhttpChart.readyState == 4 && xhttpChart.status == 200)
	{
//		document.body.style.cursor = "auto";
		
		var resultSTR = xhttpChart.responseText;
		if (isJSONString(resultSTR))
		{
			var resultJSON = JSON.parse(resultSTR);
			console.log(resultJSON);
			
			var boundaries = resultJSON.boundaries;
			var dataWrapper = resultJSON.data;
			
	//		animatedChart = new AnimatedBubbleChart();
			
			animatedChart.options.hAxis.minValue = boundaries.gdppercapita.min;
			animatedChart.options.hAxis.maxValue = boundaries.gdppercapita.max;
			animatedChart.options.vAxis.minValue = boundaries.poverty.min;
			animatedChart.options.vAxis.maxValue = boundaries.poverty.max;
			animatedChart.options.sizeAxis.minValue = boundaries.population.min;
			animatedChart.options.sizeAxis.maxValue = boundaries.population.max;
			
			animatedChart.options.hAxis.ticks = getAxisTicks(140, boundaries.gdppercapita.max, 20000);
			animatedChart.options.vAxis.ticks = getAxisTicks(0, boundaries.poverty.max, 15);
	//		animatedChart.options.hAxis.ticks = getAxisTicks(500, boundaries.gdppercapita.max);
	//		animatedChart.options.vAxis.ticks = getAxisTicks(10, boundaries.poverty.max);
			
			for (var i = firstYear; i <= lastYear; i++)
			{
				if (dataWrapper[i] != undefined)
				{
					var arrayData = new ArrayData();
					arrayData.insertData(dataWrapper[i]);
					animatedChart.addYearData(arrayData.data);
					animatedChart.addYear(i);
				}
				else
				{
					console.log(i);
				}
			}
			
			$('#yearsRange').attr('min', animatedChart.years[0]);
			$('#yearsRange').attr('max', animatedChart.years[animatedChart.years.length - 1]);
			$('#yearsRange').attr('value', animatedChart.getCurrentYear());
			
			for (var i = animatedChart.years[0]; i <= animatedChart.years[animatedChart.years.length - 1]; i++)
			{
				$('#yearsLabel').append('<span onclick="showYear(this)">' + i + '</span>');
			}
			
			document.getElementById('playButton').disabled = false;
			document.getElementById('yearsRange').disabled = false;
			
			document.getElementById('toast').className = '';
			
			console.log(arrayData.data);
	//		animatedChart.setData(arrayData.data);
			
			drawAnimatedChart();
		}
		else
		{
			$('input[type="checkbox"]').prop('disabled', false);
			document.getElementById('toast').className = '';
		}
			
	}
}

function loadAnimatedChart()
{
//	document.body.style.cursor = "wait";
	
	$('input[type="checkbox"]').prop('disabled', true);
	
	document.getElementById('toast').innerHTML = '<i class="fa fa-circle-notch fa-spin"></i> Loading Animated Chart';
	document.getElementById('toast').className = 'show';
	document.getElementById('playButton').disabled = true;
	document.getElementById('yearsRange').disabled = true;
	
	xhttpChart = new XMLHttpRequest();
	xhttpChart.open("get", "load_data_for_animated_chart", true);
	xhttpChart.setRequestHeader("connection", "close");
	xhttpChart.onreadystatechange = showAnimatedChart;
	xhttpChart.send(null);
}

/* ANIMATED CHART (USER) */
function showAnimatedChartUser()
{
	if (xhttpChart.readyState == 4 && xhttpChart.status == 200)
	{
//		document.body.style.cursor = "auto";
		animatedChart.resetArrayYearsData();
		animatedChart.resetYears();
		animatedChart.resetIndex();
		
		var resultSTR = xhttpChart.responseText;
		if (isJSONString(resultSTR))
		{
			var dataWrapper = JSON.parse(resultSTR);
			console.log(dataWrapper);
			
			for (var i = firstYear; i <= lastYear; i++)
			{
				if (dataWrapper[i] != undefined)
				{
					var arrayData = new ArrayData();
					arrayData.insertData(dataWrapper[i]);
					animatedChart.addYearData(arrayData.data);
					animatedChart.addYear(i);
				}
				else
				{
					console.log(i);
				}
			}
			
			$('#yearsRange').attr('min', animatedChart.years[0]);
			$('#yearsRange').attr('max', animatedChart.years[animatedChart.years.length - 1]);
			$('#yearsRange').attr('value', animatedChart.getCurrentYear());
			document.getElementById('yearsRange').stepDown(animatedChart.years.length);
			
			$('#yearsLabel').empty();
			for (var i = animatedChart.years[0]; i <= animatedChart.years[animatedChart.years.length - 1]; i++)
			{
				$('#yearsLabel').append('<span onclick="showYear(this)">' + i + '</span>');
			}
			
			document.getElementById('playButton').disabled = false;
			document.getElementById('yearsRange').disabled = false;
			
			document.getElementById('toast').className = '';
			
			console.log(arrayData.data);
//			animatedChart.setData(arrayData.data);
			
			drawAnimatedChart();
		}
		else
		{
			$('input[type="checkbox"]').prop('disabled', false);
			document.getElementById('toast').className = '';
		}
	}
}

function loadCountryData(clicked)
{
	$('input[type="checkbox"]').prop('disabled', true);
	
	document.getElementById('toast').innerHTML = '<i class="fa fa-circle-notch fa-spin"></i> Loading selected data';
	document.getElementById('toast').className = 'show';
	document.getElementById('playButton').disabled = true;
	document.getElementById('yearsRange').disabled = true;
	
	if (null != clicked)
	{
		selectedCountries.push(clicked.value);
	}
	var codes = '';
	for (var i = 0; i < selectedCountries.length; i++)
	{
		if (i != 0)
		{
			codes = codes.concat('-');
		}
		codes = codes.concat(selectedCountries[i]);
	}
	
	xhttpChart = new XMLHttpRequest();
	xhttpChart.open("get", "load_countries_animated_data?" + "codes=" + codes, true);
	xhttpChart.setRequestHeader("connection", "close");
	xhttpChart.onreadystatechange = showAnimatedChartUser;
	xhttpChart.send(null);
}

function removeCountryData(clicked)
{
	var countryCode = clicked.value;
	selectedCountries.splice(selectedCountries.indexOf(countryCode), 1);
	
	clicked.parentElement.parentElement.previousElementSibling.children[0].checked = false;
	
	if (selectedCountries.length == 0)
	{
		$('#animated-chart').empty();
		$('#animated-chart').append('<p>Select country/ies to show on the chart from right menu</p>');
		
		document.getElementById('playButton').disabled = true;
		document.getElementById('yearsRange').disabled = true;
		$('#yearsLabel').empty();
	}
	else
	{
		loadCountryData(null);
	}
}

function checkBoxCountryHandler(clicked)
{
	document.getElementById('playButton').classList.remove("paused");
	
	var countryCode = clicked.value;
	var found = false;
	var i = 0;
	var index = -1
	while (!found && i < arrayCheckBoxs.length)
	{
		if (arrayCheckBoxs[i].input[0].value == countryCode)
		{
			found = true;
			index = i;
		}
		i++;
	}
	if (index >= 0)
	{
		if (clicked.checked == true)
		{
			arrayCheckBoxs[index].input[0].checked = true;
			copyArrayCheckBoxs[index].input[0].checked = true;
			loadCountryData(clicked);
		}
		else if (clicked.checked == false)
		{
			arrayCheckBoxs[index].input[0].checked = false;
			copyArrayCheckBoxs[index].input[0].checked = false;
			removeCountryData(clicked);
		}
	}
}

/* ALL REGION'S COUNTRIES SELECTION */
function getCopyArrayCheckBoxsPosition(countryCode)
{
	var index = -1;
	var i = 0;
	var found = false;
	while (!found && i < copyArrayCheckBoxs.length)
	{
		if (copyArrayCheckBoxs[i].input[0].value == countryCode)
		{
			index = i;
			found = true;
		}
		i++;
	}
	return index;
}

function loadRegionData(countries)
{
	for (var i = 0; i < countries.length; i++)
	{
		if (countries[i].checked == false)
		{
			selectedCountries.push(countries[i].value);
			
			countries[i].checked = true;
			var index = getCopyArrayCheckBoxsPosition(countries[i].value)
			if (index >= 0)
			{
				copyArrayCheckBoxs[index].input[0].checked = true;
			}
		}
	}
	
	if (selectedCountries.length == 0)
	{
		$('#animated-chart').empty();
		$('#animated-chart').append('<p>Select country/ies to show on the chart from right menu</p>');
		
		document.getElementById('playButton').disabled = true;
		document.getElementById('yearsRange').disabled = true;
		$('#yearsLabel').empty();
	}
	else
	{
		loadCountryData(null);
	}
}

function removeRegionData(countries)
{
	for (var i = 0; i < countries.length; i++)
	{
		if (countries[i].checked == true)
		{
			selectedCountries.splice(selectedCountries.indexOf(countries[i].value), 1);
			
			countries[i].checked = false;
			var index = getCopyArrayCheckBoxsPosition(countries[i].value)
			if (index >= 0)
			{
				copyArrayCheckBoxs[index].input[0].checked = true;
			}
		}
	}
	
	if (selectedCountries.length == 0)
	{
		$('#animated-chart').empty();
		$('#animated-chart').append('<p>Select country/ies to show on the chart from right menu</p>');
		
		document.getElementById('playButton').disabled = true;
		document.getElementById('yearsRange').disabled = true;
		$('#yearsLabel').empty();
	}
	else
	{
		loadCountryData(null);
	}
}

function checkBoxRegionHandler(clicked)
{
	document.getElementById('playButton').classList.remove("paused");
	var region = clicked.value;
//	console.log(region);
	var countries = null;
	if (null != document.getElementById(region))
	{
		countries = document.getElementById(region).getElementsByClassName('country-checkbox');
//		console.log(countries);
	}
	if (null != countries)
	{
		if (clicked.checked == true)
		{
			loadRegionData(countries);
		}
		else if (clicked.checked == false)
		{
			removeRegionData(countries);
		}
	}
}

/* SEARCH BAR */
function searchCountry(input)
{
	$('#search-result-container').empty();
	if (input.value.length != 0)
	{
		var filter = input.value.toUpperCase();
//		console.log(filter);
		for (var i = 0; i < copyArrayCheckBoxs.length; i++)
		{
//			console.log(arrayCheckBoxs[i].input[0].name);
			if (copyArrayCheckBoxs[i].input[0].value.toUpperCase().indexOf(filter) > -1 || copyArrayCheckBoxs[i].input[0].name.toUpperCase().indexOf(filter) > -1)
			{
				$('#search-result-container').append(copyArrayCheckBoxs[i].container);
			}
		}
		if ($('#search-result-container').children().length == 0)
		{
			$('#search-result-container').append('<p class="search-msg">NO COUNTRY FOUND</p>')
		}
		$('#search-result-container').show();
	}
	else
	{
		$('#search-result-container').hide();
	}
}

/* LOAD CHARTS LIBRARY */
function loadGoogleChartsLibrary()
{
	google.charts.load('current', {'packages':['corechart']});
}