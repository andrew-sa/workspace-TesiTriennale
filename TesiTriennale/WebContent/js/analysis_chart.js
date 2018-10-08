var xhttp;
var xhttpYears;
var xhttpRegion;

var arrayCollapsibleRegions = [];
var lastYear = (new Date).getFullYear();

var arrayCheckBoxs = [];
var copyArrayCheckBoxs = [];
var povertyAnalysisChart = new AnalysisChart();
var netMigrationAnalysisChart = new AnalysisChart();
var gdpPerCapitaAnalysisChart = new AnalysisChart();
var POVERTY = 'poverty';
var NET_MIGRATION = 'netmigration';
var GDP_PER_CAPITA = 'gdppercapita';
var dataTypes = [POVERTY, NET_MIGRATION, GDP_PER_CAPITA];
var currentDataType = POVERTY;

//var regionArrayCheckBoxs = [];

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

/* CHECK BOXS */
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
//	this.container = $('<label class="container"></label>');
//	this.input = $('<input class="region-checkbox" type="checkbox" onchange="checkBoxRegionHandler(this)">');
//	this.span = $('<span class="checkmark"></span>');
//	this.container.html(regionName);
//	this.input.attr("value", regionName);
//	this.container.append(this.input);
//	this.container.append(this.span);
	
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
//	    if (arrayData[0].length > 1)
//	    {
//	    	console.log("We");
//	    	google.charts.setOnLoadCallback(drawChart);
//	    }
	   });
	}
}

function CollapsibleRegion(regionName)
{
	this.checkbox = new RegionCheckBox(regionName);
//	regionArrayCheckBoxs.push(checkbox);
	this.title = $('<div class="wrapper-ragion-checkbox"></div>');
	this.button = $('<button class="collapsible"></button>');
	this.div = $('<div class="content"></div>');
	this.button.html(regionName);
	this.div.attr('id', regionName);
	
	this.title.append(this.checkbox.input);
	this.title.append(this.button);
//	this.wrapper.append(this.div);
	
	this.region = regionName;
}

//function drawInitialChart()
//{
//	var data = google.visualization.arrayToDataTable(arrayData);
//	
//	var options = {
//			title: 'Poverty index (expressed as a percentage)',
//			curveType: 'none',
//			legend: { position: 'none' },
//			colors: ['#4CAF50'],
//			backgroundColor: '#f1f1f1',
//			lineWidth: 4,
//			pointSize: 8,
//			interpolateNulls: true,
//			focusTarget: 'category',
//			crosshair: {
//				trigger: 'both',
//				orientation: 'horizontal',
//				color: '#333'
//			}
//		};
//
//	var chart = new google.visualization.LineChart(document.getElementById('analysis-chart'));
//
//	chart.draw(data, options);
//}

/* CHARTS */
function AnalysisChart()
{
	this.options = {
		curveType: 'none',
		legend: {
			position: 'right',
			textStyle: {
				fontSize: 15
			}
		},
		backgroundColor: '#f1f1f1',
		pointSize: 4,
		interpolateNulls: true,
		focusTarget: 'datum',
		theme: 'material',
		chartArea: {
			'width': '80%',
			'height': '90%'
		},
		explorer: {
            axis: 'horizontal',
            keepInBounds: true,
            maxZoomIn: 2.50,
            maxZoomOut: 0.75
		}
	};
	this.arrayData = [];
//	this.addCountryData = function(arrayCountryData) {
//		arrayData.push(arrayCountryData);
//	}
}

//function drawChart()
//{
//	$('#analysis-chart').empty();
//	
//	var data = google.visualization.arrayToDataTable(arrayData);
//	
//	if (typeChart == 'line')
//	{
//	var options = {
//			curveType: 'none',
//			legend: {
//				position: 'right',
//				textStyle: {
//					fontSize: 15
//				}
//			},
//			backgroundColor: '#f1f1f1',
//			pointSize: 4,
//			interpolateNulls: true,
//			focusTarget: 'datum',
//			theme: 'material',
//			chartArea: {
//				'width': '80%',
//				'height': '90%'
//			},
////			hAxis: {
////				title: 'Year'        
////			},
////			vAxis: {
////				title: 'Poverty'        
////			},
//			explorer: {
//	            axis: 'horizontal',
//	            keepInBounds: true,
//	            maxZoomIn: 2.50,
//	            maxZoomOut: 0.75
//			}
////			trendlines: {
////			    0: {
////			      type: 'linear',
////			      color: '#4CAF50',
////			      lineWidth: 10,
////			      pointsVisible: false,
////			      opacity: 0.4,
////			      showR2: false,
////			      visibleInLegend: false
////			    }
////			}
//		};
//
//	var chart = new google.visualization.LineChart(document.getElementById('analysis-chart'));
//
//	chart.draw(data, options);
//	}
//	else if (typeChart == 'bar')
//	{
//		var options = {
//				bars: 'vertical'
//		};
//
//		var chart = new google.charts.Bar(document.getElementById('analysis-chart'));
//
//		chart.draw(data, google.charts.Bar.convertOptions(options));
//	}
//	
////	var options = {
////			title: 'Poverty index (expressed as a percentage)',
////			curveType: 'none',
////			legend: { position: 'right' },
//////			colors: ['#4CAF50'],
////			backgroundColor: '#f1f1f1',
//////			lineWidth: 4,
////			pointsVisible: true,
////			pointSize: 4,
////			interpolateNulls: true,
////			focusTarget: 'datum',
////			hAxis: {
////                title: 'Year',         
////             },
////			vAxis: {
////	        	title: 'Poverty',        
////	        }
////		};
////	
////	var chart = new google.charts.Line(document.getElementById('analysis-chart'));
////
////    chart.draw(data, google.charts.Line.convertOptions(options));
//}

function drawChart()
{
	$('input[type="checkbox"]').prop('disabled', false);
	
	if (currentDataType == POVERTY)
	{
		$('#analysis-chart').empty();
		if (povertyAnalysisChart.arrayData[0].length == 1)
		{
//			$('#analysis-chart').empty();
			$('#analysis-chart').append('<p>No values available for selected country/ies</p>');
		}
		else
		{
			var data = google.visualization.arrayToDataTable(povertyAnalysisChart.arrayData);
			var chart = new google.visualization.LineChart(document.getElementById('analysis-chart'));
			chart.draw(data, povertyAnalysisChart.options);
		}
	}
	else if (currentDataType == NET_MIGRATION)
	{
		$('#analysis-chart').empty();
		if (netMigrationAnalysisChart.arrayData[0].length == 1)
		{
//			$('#analysis-chart').empty();
			$('#analysis-chart').append('<p>No values available for selected country/ies</p>');
		}
		else
		{
			var data = google.visualization.arrayToDataTable(netMigrationAnalysisChart.arrayData);
			var chart = new google.visualization.LineChart(document.getElementById('analysis-chart'));
			chart.draw(data, netMigrationAnalysisChart.options);
		}
	}
	else if (currentDataType == GDP_PER_CAPITA)
	{
		$('#analysis-chart').empty();
		if (gdpPerCapitaAnalysisChart.arrayData[0].length == 1)
		{
//			$('#analysis-chart').empty();
			$('#analysis-chart').append('<p>No values available for selected country/ies</p>');
		}
		else
		{
			var data = google.visualization.arrayToDataTable(gdpPerCapitaAnalysisChart.arrayData);
			var chart = new google.visualization.LineChart(document.getElementById('analysis-chart'));
			chart.draw(data, gdpPerCapitaAnalysisChart.options);
		}
	}
}

/* START of LOAD INITIAL VIEW */
function loadInitialChart()
{
	$('input[type="checkbox"]').prop('disabled', false);
	
	var queryString = location.search;
	console.log(queryString);
	if (queryString == '')
	{
		$('#analysis-chart').append('<p>Select country/ies to show on the chart from right menu</p>');
	}
	else
	{
		var regexCountry = /^\?show\=[A-Z]{2}$/;
		var regexRegion = /^\?show\=[A-Za-z]{3,}$/;
		if (queryString.match(regexCountry))
		{
			var countryCode = queryString.substring(6);
			console.log(countryCode)
//			var boxs = document.getElementById('countries-selector-panel').getElementsByTagName('input');
			var boxs =  document.getElementsByClassName('country-checkbox');
			console.log(boxs);
			var checkbox = null;
			var found = false;
			var i = 0;
			while (!found && i < boxs.length)
			{
				if (boxs[i].value == countryCode)
				{
					found = true;
					checkbox = boxs[i];
				}
				i++
			}
			if (null != checkbox)
			{
				checkbox.checked = true;
				checkBoxCountryHandler(checkbox);
			}
			else
			{
				$('#analysis-chart').append('<p>No values available for selected country/ies</p>');
			}
		}
		else if (queryString.match(regexRegion))
		{
			var region = queryString.substring(6);
			var boxs =  document.getElementsByClassName('region-checkbox');
			var checkbox = null;
			var found = false;
			var i = 0;
			while (!found && i < boxs.length)
			{
				if (boxs[i].value == region)
				{
					found = true;
					checkbox = boxs[i];
				}
				i++
			}
			if (null != checkbox)
			{
				checkbox.checked = true;
				checkBoxRegionHandler(checkbox);
			}
			else
			{
				$('#analysis-chart').append('<p>No values available for selected country/ies</p>');
			}
		}
		else
		{
			$('#analysis-chart').append('<p>Select country/ies to show on the chart from right menu</p>');
		}
	}
}

function showYears()
{
	if (xhttpYears.readyState == 4 && xhttpYears.status == 200)
	{
		var responseSTR = xhttpYears.responseText;
		if (isJSONString(responseSTR))
		{
			var responseOBJ = JSON.parse(responseSTR);
			
			povertyAnalysisChart.arrayData.push(['Year']);
			for (var i = responseOBJ.poverty; i <= lastYear; i++)
			{
				povertyAnalysisChart.arrayData.push([new Date(i, 11, 31)]);
			}
			
			netMigrationAnalysisChart.arrayData.push(['Year']);
			for (var i = responseOBJ.netmigration; i <= lastYear; i++)
			{
				netMigrationAnalysisChart.arrayData.push([new Date(i, 11, 31)]);
			}
			
			gdpPerCapitaAnalysisChart.arrayData.push(['Year']);
			for (var i = responseOBJ.gdppercapita; i <= lastYear; i++)
			{
				gdpPerCapitaAnalysisChart.arrayData.push([new Date(i, 11, 31)]);
			}
			
			loadInitialChart();
		}
		else
		{
			$('input[type="checkbox"]').prop('disabled', false);
		}
	}
}

function loadYears()
{
	xhttpYears = new XMLHttpRequest();
	xhttpYears.open("get", "load_first_year", true);
	xhttpYears.setRequestHeader("connection", "close");
	xhttpYears.onreadystatechange = showYears;
	xhttpYears.send(null);
}

function showCheckboxs()
{
	if (xhttp.readyState == 4 && xhttp.status == 200)
	{
		document.body.style.cursor = "auto";
		
		var resultSTR = xhttp.responseText;
		if (isJSONString(resultSTR))
		{
			var resultJSON = JSON.parse(resultSTR);
			var regions = resultJSON[0];
			var countries = resultJSON[1];
			for (var i = 0; i < regions.length; i++)
			{
				var collapsibleRegion = new CollapsibleRegion(regions[i]);
//				$('#countries-selector-panel').append(collapsibleRegion.button);
				$('#countries-selector-panel').append(collapsibleRegion.title);
				$('#countries-selector-panel').append(collapsibleRegion.div);
//				$('#countries-selector-panel').append(collapsibleRegion.div);
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
			
			loadYears();
		}	
	}
}

function loadCheckboxs()
{
	document.body.style.cursor = "wait";
	
	xhttp = new XMLHttpRequest();
	xhttp.open("get", "load_countries", true);
	xhttp.setRequestHeader("connection", "close");
	xhttp.onreadystatechange = showCheckboxs;
	xhttp.send(null);
}

function loadInitialView()
{
	loadCheckboxs();
}
/* END */


function loadDataChart()
{
	if (xhttp.readyState == 4 && xhttp.status == 200)
	{
		document.body.style.cursor = "auto";
		
		var responseSTR = xhttp.responseText;
		if (isJSONString(responseSTR))
		{
			var responseOBJ = JSON.parse(responseSTR);
			
			var name = responseOBJ.name;
			var poverty = responseOBJ.poverty;
			var netmigration = responseOBJ.netmigration;
			var gdppercapita = responseOBJ.gdppercapita;
			
			if (poverty.length > 0)
			{
	//			console.log(povertyAnalysisChart.arrayData);
				povertyAnalysisChart.arrayData[0].push(name);
				for (var i = 1; i < povertyAnalysisChart.arrayData.length; i++)
				{
					var value = null;
					var j = 0;
					while (value == null && j < poverty.length)
					{
						if (povertyAnalysisChart.arrayData[i][0].getFullYear() == poverty[j].year)
						{
							value = poverty[j].value;
							povertyAnalysisChart.arrayData[i].push(value);
							poverty.splice(j, 1);
						}
						j++;
					}
					if (value == null)
					{
						povertyAnalysisChart.arrayData[i].push(null);
					}
				}
			}
			if (netmigration.length > 0)
			{
				netMigrationAnalysisChart.arrayData[0].push(name);
				for (var i = 1; i < netMigrationAnalysisChart.arrayData.length; i++)
				{
					var value = null;
					var j = 0;
					while (value == null && j < netmigration.length)
					{
						if (netMigrationAnalysisChart.arrayData[i][0].getFullYear() == netmigration[j].year)
						{
							value = netmigration[j].value;
							netMigrationAnalysisChart.arrayData[i].push(value);
							netmigration.splice(j, 1);
						}
						j++;
					}
					if (value == null)
					{
						netMigrationAnalysisChart.arrayData[i].push(null);
					}
				}
			}
			if (gdppercapita.length > 0)
			{
				gdpPerCapitaAnalysisChart.arrayData[0].push(name);
				for (var i = 1; i < gdpPerCapitaAnalysisChart.arrayData.length; i++)
				{
					var value = null;
					var j = 0;
					while (value == null && j < gdppercapita.length)
					{
						if (gdpPerCapitaAnalysisChart.arrayData[i][0].getFullYear() == gdppercapita[j].year)
						{
							value = gdppercapita[j].value;
							gdpPerCapitaAnalysisChart.arrayData[i].push(value);
							gdppercapita.splice(j, 1);
						}
						j++;
					}
					if (value == null)
					{
						gdpPerCapitaAnalysisChart.arrayData[i].push(null);
					}
				}
			}
			
			document.getElementById('toast').className = '';
			drawChart();
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
//	$('input[type="checkbox"]').prop('disabled', true);
	document.getElementById('toast').innerHTML = '<i class="fa fa-circle-notch fa-spin"></i> Loading ' + clicked.value + ' data';
	document.getElementById('toast').className = 'show';
	
//	console.log(clicked.checked);
	xhttp = new XMLHttpRequest();
	xhttp.open("get", "load_country_data?" + "code=" + clicked.value + "&name=" + clicked.name, true);
	xhttp.setRequestHeader("connection", "close");
	xhttp.onreadystatechange = loadDataChart;
	xhttp.send(null);
}

function getPosition(countryName, dataType)
{
	var index = -1;
	var countries = null;
	if (dataType == POVERTY)
	{
		countries = povertyAnalysisChart.arrayData[0];
	}
	else if (dataType == NET_MIGRATION)
	{
		countries = netMigrationAnalysisChart.arrayData[0];
	}
	else if (dataType == GDP_PER_CAPITA)
	{
		countries = gdpPerCapitaAnalysisChart.arrayData[0];
	}
	
	if (null != countries)
	{
		var i = 1;
		while (index == -1 && i < countries.length)
		{
			if (countries[i] == countryName)
			{
				index = i;
			}
			i++;
		}
	}
	
	return index;
}

function removeCountryDataType(index, dataType)
{
	if (dataType == POVERTY)
	{
		for (var i = 0; i < povertyAnalysisChart.arrayData.length; i++)
		{
			/*The first parameter (0) defines the position where new elements should be added (spliced in).

			The second parameter (1) defines how many elements should be removed.

			The rest of the parameters are omitted. No new elements will be added.*/
			
			povertyAnalysisChart.arrayData[i].splice(index, 1);
		}
	}
	else if (dataType == NET_MIGRATION)
	{
		for (var i = 0; i < netMigrationAnalysisChart.arrayData.length; i++)
		{
			netMigrationAnalysisChart.arrayData[i].splice(index, 1);
		}
	}
	else if (dataType == GDP_PER_CAPITA)
	{
		for (var i = 0; i < gdpPerCapitaAnalysisChart.arrayData.length; i++)
		{
			gdpPerCapitaAnalysisChart.arrayData[i].splice(index, 1);
		}
	}
}

function removeCountryData(clicked)
{
//	$('input[type="checkbox"]').prop('disabled', true);
	
	var country = clicked.name;
//	var index = getPosition(country);
	console.log(dataTypes);
	console.log(dataTypes.length);
	clicked.parentElement.parentElement.previousElementSibling.children[0].checked = false;
	for (var i = 0; i < dataTypes.length; i++)
	{
		console.log('we');
		var index = getPosition(country, dataTypes[i]);
		if (index != -1)
		{
			removeCountryDataType(index, dataTypes[i]);
		}
	}
	
	$('input[type="checkbox"]').prop('disabled', false);
	
	if (currentDataType == POVERTY && povertyAnalysisChart.arrayData[0].length == 1)
	{
		$('#analysis-chart').empty();
		$('#analysis-chart').append('<p>Select country/ies to show on the chart from right menu</p>');
	}
	else
	{
		drawChart();
	}
	
	if (currentDataType == NET_MIGRATION && netMigrationAnalysisChart.arrayData[0].length == 1)
	{
		$('#analysis-chart').empty();
		$('#analysis-chart').append('<p>Select country/ies to show on the chart from right menu</p>');
	}
	else
	{
		drawChart();
	}
	
	if (currentDataType == GDP_PER_CAPITA && gdpPerCapitaAnalysisChart.arrayData[0].length == 1)
	{
		$('#analysis-chart').empty();
		$('#analysis-chart').append('<p>Select country/ies to show on the chart from right menu</p>');
	}
	else
	{
		drawChart();
	}
}

function checkBoxCountryHandler(clicked)
{
	$('input[type="checkbox"]').prop('disabled', true);
	
	console.log(clicked);
//	if (clicked.checked == true)
//	{
////		console.log('true');
//		loadCountryPovertyData(clicked);
//	}
//	else if (clicked.checked == false)
//	{
////		console.log('false');
//		removeCountryPovertyData(clicked);
//	}
	
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
//	console.log(index);
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
function loadRegionDataChart()
{
	if (xhttpRegion.readyState == 4 && xhttpRegion.status == 200)
	{
		document.body.style.cursor = "auto";
		
		var responseSTR = xhttpRegion.responseText;
		if (isJSONString(responseSTR))
		{
			var responseARRAY = JSON.parse(responseSTR);
			for (var pos = 0; pos < responseARRAY.length; pos++)
			{
				var name = responseARRAY[pos].name;
				var poverty = responseARRAY[pos].poverty;
				var netmigration = responseARRAY[pos].netmigration;
				var gdppercapita = responseARRAY[pos].gdppercapita;
				
				if (poverty.length > 0)
				{
		//			console.log(povertyAnalysisChart.arrayData);
					povertyAnalysisChart.arrayData[0].push(name);
					for (var i = 1; i < povertyAnalysisChart.arrayData.length; i++)
					{
						var value = null;
						var j = 0;
						while (value == null && j < poverty.length)
						{
							if (povertyAnalysisChart.arrayData[i][0].getFullYear() == poverty[j].year)
							{
								value = poverty[j].value;
								povertyAnalysisChart.arrayData[i].push(value);
								poverty.splice(j, 1);
							}
							j++;
						}
						if (value == null)
						{
							povertyAnalysisChart.arrayData[i].push(null);
						}
					}
				}
				if (netmigration.length > 0)
				{
					netMigrationAnalysisChart.arrayData[0].push(name);
					for (var i = 1; i < netMigrationAnalysisChart.arrayData.length; i++)
					{
						var value = null;
						var j = 0;
						while (value == null && j < netmigration.length)
						{
							if (netMigrationAnalysisChart.arrayData[i][0].getFullYear() == netmigration[j].year)
							{
								value = netmigration[j].value;
								netMigrationAnalysisChart.arrayData[i].push(value);
								netmigration.splice(j, 1);
							}
							j++;
						}
						if (value == null)
						{
							netMigrationAnalysisChart.arrayData[i].push(null);
						}
					}
				}
				if (gdppercapita.length > 0)
				{
					gdpPerCapitaAnalysisChart.arrayData[0].push(name);
					for (var i = 1; i < gdpPerCapitaAnalysisChart.arrayData.length; i++)
					{
						var value = null;
						var j = 0;
						while (value == null && j < gdppercapita.length)
						{
							if (gdpPerCapitaAnalysisChart.arrayData[i][0].getFullYear() == gdppercapita[j].year)
							{
								value = gdppercapita[j].value;
								gdpPerCapitaAnalysisChart.arrayData[i].push(value);
								gdppercapita.splice(j, 1);
							}
							j++;
						}
						if (value == null)
						{
							gdpPerCapitaAnalysisChart.arrayData[i].push(null);
						}
					}
				}
			}
			
			document.getElementById('toast').className = '';
			drawChart();
		}
		else
		{
			$('input[type="checkbox"]').prop('disabled', false);
			document.getElementById('toast').className = '';
		}
	}
}

function loadRegionData(queryString)
{
//	$('input[type="checkbox"]').prop('disabled', true);
	
	xhttpRegion = new XMLHttpRequest();
	xhttpRegion.open("get", "load_region_countries_data" + queryString, true);
	xhttpRegion.setRequestHeader("connection", "close");
	xhttpRegion.onreadystatechange = loadRegionDataChart;
	xhttpRegion.send(null);
}

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

function selectRegionDataToLoad(clicked)
{
	document.getElementById('toast').innerHTML = '<i class="fa fa-circle-notch fa-spin"></i> Loading ' + clicked.value + ' data';
	document.getElementById('toast').className = 'show';
	
	var queryString = '?code=';
	var region = clicked.value;
	var countries = null;
	if (null != document.getElementById(region))
	{
		countries = document.getElementById(region).getElementsByClassName('country-checkbox');
	}
	if (null != countries)
	{
		for (var i = 0; i < countries.length; i++)
		{
			if (countries[i].checked == false)
			{
				countries[i].checked = true;
				var index = getCopyArrayCheckBoxsPosition(countries[i].value)
				if (index >= 0)
				{
					copyArrayCheckBoxs[index].input[0].checked = true;
				}
				
				if (queryString == '?code=')
				{
					queryString = queryString.concat(countries[i].value);
				}
				else
				{
					queryString = queryString.concat('-' + countries[i].value);
				}
			}
		}
	}
//	console.log(queryString);
	loadRegionData(queryString);
}

function removeRegionData(clicked)
{
	var region = clicked.value;
	var countries = null;
	if (null != document.getElementById(region))
	{
		countries = document.getElementById(region).getElementsByClassName('country-checkbox');
	}
	if (null != countries)
	{
		for (var i = 0; i < countries.length; i++)
		{
			if (countries[i].checked == true)
			{
				countries[i].checked = false;
				var index = getCopyArrayCheckBoxsPosition(countries[i].value)
				if (index >= 0)
				{
					copyArrayCheckBoxs[index].input[0].checked = false;
				}
				
				var country = countries[i].name;
				for (var j = 0; j < dataTypes.length; j++)
				{
					var index = getPosition(country, dataTypes[j]);
					if (index != -1)
					{
						removeCountryDataType(index, dataTypes[j]);
					}
				}
			}
		}
		
		$('input[type="checkbox"]').prop('disabled', false);
		
		if (currentDataType == POVERTY && povertyAnalysisChart.arrayData[0].length == 1)
		{
			$('#analysis-chart').empty();
			$('#analysis-chart').append('<p>Select country/ies to show on the chart from right menu</p>');
		}
		else
		{
			drawChart();
		}
		
		if (currentDataType == NET_MIGRATION && netMigrationAnalysisChart.arrayData[0].length == 1)
		{
			$('#analysis-chart').empty();
			$('#analysis-chart').append('<p>Select country/ies to show on the chart from right menu</p>');
		}
		else
		{
			drawChart();
		}
		
		if (currentDataType == GDP_PER_CAPITA && gdpPerCapitaAnalysisChart.arrayData[0].length == 1)
		{
			$('#analysis-chart').empty();
			$('#analysis-chart').append('<p>Select country/ies to show on the chart from right menu</p>');
		}
		else
		{
			drawChart();
		}
	}
}

function checkBoxRegionHandler(clicked)
{
	$('input[type="checkbox"]').prop('disabled', true);
	
	if (clicked.checked == true)
	{
		selectRegionDataToLoad(clicked);
	}
	else if (clicked.checked == false)
	{
		removeRegionData(clicked);
	}
}


/* CHART OPTIONS */
function changeDataType(input)
{
	if (currentDataType != input.value)
	{
		currentDataType = input.value;
		drawChart();
	}
}

//function loadRegionPovertyData(clicked)
//{
//	document.body.style.cursor = "wait";
////	console.log(clicked.checked);
//	xhttp = new XMLHttpRequest();
//	xhttp.open("get", "load_region_poverty_data?" + "region=" + clicked.value, true);
//	xhttp.setRequestHeader("connection", "close");
//	xhttp.onreadystatechange = loadDataChart;
//	xhttp.send(null);
//}
//
//function removeRegionPovertyData(clicked)
//{
//	var region = clicked.value;
//	var index = getPosition(region);
//	if (index != -1)
//	{
//		for (var i = 0; i < arrayData.length; i++)
//		{
//			arrayData[i].splice(index, 1);
//		}
//	}
//	if (arrayData[0].length == 1)
//	{
//		$('#analysis-chart').empty();
//		$('#analysis-chart').append('<p>Select country/ies to show on the chart from right menu</p>');
//	}
//	else
//	{
//		drawChart();
//	}
//}
//
//function checkBoxRegionHandler(clicked)
//{
//	if (clicked.checked == true)
//	{
//		loadRegionPovertyData(clicked);
//	}
//	else if (clicked.checked == false)
//	{
//		removeRegionPovertyData(clicked);
//	}
//}

//function openNav()
//{
//	document.getElementById("mySidepanel").style.width = "23.2em";
//}
//
//function closeNav()
//{
//    document.getElementById("mySidepanel").style.width = "0";
//} 

function clearAll()
{
	currentDataType = POVERTY;
	
	for (var i = 0; i < arrayCheckBoxs.length; i++)
	{
		if (arrayCheckBoxs[i].input[0].checked == true)
		{
			var country = arrayCheckBoxs[i].input[0].name;
			for (var j = 0; j < dataTypes.length; j++)
			{
				var index = getPosition(country, dataTypes[j]);
				if (index != -1)
				{
					removeCountryDataType(index, dataTypes[j]);
				}
			}
		}
		arrayCheckBoxs[i].input[0].checked = false;
		copyArrayCheckBoxs[i].input[0].checked = false;
	}
	
	for (var i = 0; i < arrayCollapsibleRegions.length; i++)
	{
		arrayCollapsibleRegions[i].checkbox.input.prop('checked', false);
	}
	
	var dataTypesElements = document.getElementsByName('dataType');
	for (var i = 0; i <dataTypesElements.length; i++)
	{
		if (dataTypesElements[i].value == POVERTY)
		{
			dataTypesElements[i].checked = true;
		}
	}
	
	$('#analysis-chart').empty();
	$('#analysis-chart').append('<p>Select country/ies to show on the chart from right menu</p>');
}

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
	google.charts.load('current', {packages: ['corechart']});
}