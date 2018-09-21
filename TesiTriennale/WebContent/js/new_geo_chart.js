var xhttp;

var geoChart;
var countryGeoChart;
var lineChartCountryInfo;

var selectedRegion = 'world';

function GeoChart()
{
	this.options = {
			backgroundColor: '#333',
			datalessRegionColor: '#f8bbd0',
			enableRegionInteractivity: true
	};
	this.data = null;
	this.setData = function(arrayData) {
		this.data = google.visualization.arrayToDataTable(arrayData);
	}
}

function CountryGeoChart()
{
	this.options = {
			backgroundColor: '#333',
			datalessRegionColor: '#f8bbd0',
			enableRegionInteractivity: true,
			tooltip: {
				isHtml: true
			}
	};
	this.data = null;
	this.setData = function(arrayData) {
		this.data = google.visualization.arrayToDataTable(arrayData);
	}
}

function LineChartCountryInfo()
{
	this.options = {
		title: 'Poverty index (expressed as a percentage)',
		curveType: 'none',
		legend: {
			position: 'none'
		},
		colors: ['#4CAF50'],
		backgroundColor: '#f1f1f1',
		lineWidth: 4,
		pointSize: 8,
		interpolateNulls: true,
		focusTarget: 'datum',
//		crosshair: {
//			trigger: 'both',
//			orientation: 'horizontal',
//			color: '#333'
//		}
	};
	this.dataView = null;
	this.setDataView = function(arrayData) {
		this.dataView = google.visualization.DataView(arrayData);
	}
}

//function drawLineChartCountryInfo()
//{
//	var chart = new google.visualization.LineChart(document.getElementById('analysis-chart'));
//	
//	chart.draw(lineChartCountryInfo.data, lineChartCountryInfo.options);
//}
//
//function showCountryInfo()
//{
//	if (xhttp.readyState == 4 && xhttp.status == 200)
//	{
//		document.body.style.cursor = "auto";
//		document.getElementById('geo-chart').style.cursor = "auto";
//		
//		var infoJSON = xhttp.responseText;
//		var info = JSON.parse(infoJSON);
//		
//		$('#countryModal > .modal-content > .modal-header > h2').html(info.wiki.name);
//		
//		$("#country-general > img").attr("width", "300px");
//		$("#country-general > img").attr("src", info.wiki.image);
//		$("#country-general > p").html(info.wiki.description);
//		
//		if (info.news.length > 0)
//		{
//			for (var i = 0; i < info.news.length; i++)
//			{
//				$('#country-news').append('<p><a href="' + info.news[i].url + '" target="_blank">' + info.news[i].title + '</a><span><b>' + info.news[i].info + '</b></span><span>' + info.news[i].description + '</span></p>')
//			}
//		}
//		else
//		{
//			$('#country-news').append('<p>N/A</p>');
//		}
//		
//		$('#countryModal').show();
//		
//		lineChartCountryInfo = new LineChartCountryInfo();
//		var values = info.poverty;
//		var arrayData = [['Year', 'Poverty']];
//		if (values.length > 0)
//		{
//			for (var i = 0; i < values.length; i++)
//			{
//				arrayData.push([values[i].year, values[i].value]);
//			}
//			lineChartCountryInfo.setData(arrayData);
//			drawLineChartCountryInfo();
////			google.charts.setOnLoadCallback(drawAnalysisChart);
//		}
//		else
//		{
//			$('#analysis-chart').html('N/A');
//		}
//	}
//}
//
//function loadCountryInfo()
//{	
//	$('#country-general > img').attr("src", "");
//	$('#country-general > p').empty();
//	$('#analysis-chart').empty();
//	$('#country-news > p').empty();
//	
//	document.body.style.cursor = "wait";
//	document.getElementById('geo-chart').style.cursor = "wait";
//	
//	xhttp = new XMLHttpRequest();
//	xhttp.open("get", "load_country_info?country=" + e.region, true);
//	xhttp.setRequestHeader("connection", "close");
//	xhttp.onreadystatechange = showCountryInfo;
//	xhttp.send(null);
//}

function showCountryGeoChart()
{
	if (xhttp.readyState == 4 && xhttp.status == 200)
	{
		document.body.style.cursor = "auto";
		document.getElementById('geo-chart').style.cursor = "wait";
		
		countryGeoChart = new CountryGeoChart();
		lineChartCountryInfo = new LineChartCountryInfo();
		
		var arrayDataGeo = [[]];
		var arrayDataLine = [['Year']];
		
		var responseSTR = xhttp.responseText;
		var responseOBJ = JSON.parse(responseSTR);
		
		var countryName = responseOBJ.name;
		var countryData = responseOBJ.data;
		
		/* DATA OF COUNTRYGEOCHART */
		arrayDataGeo[0].push(countryName);
		arrayDataGeo[0].push(countryData[countryData.length - 1]);
		
		/* DATA OF LINECHARTCOUNTRYINFO */
		arrayDataLine[0].push(countryName);
		for (var i = 0; i < countryData.length; i++)
		{
			arrayDataLine.push([countryData[i].year, countryData[i].value]);
		}
		
		countryGeoChart.setData(arrayDataGeo);
		lineChartCountryInfo.setDataView(arrayDataLine);
		
		
	}
}

function regionClickHandler(e)
{
	selectedRegion = e.region;
	
	document.body.style.cursor = "wait";
	document.getElementById('geo-chart').style.cursor = "wait";
	
	xhttp = new XMLHttpRequest();
	xhttp.open("get", "load_country_poverty_data?code=" + selectedRegion, true);
	xhttp.setRequestHeader("connection", "close");
	xhttp.onreadystatechange = showCountryGeoChart;
	xhttp.send(null);
}

function drawGeoChart()
{	
	document.body.style.cursor = "auto";
	document.getElementById('geo-chart').style.cursor = "auto";
	
	var chart = new google.visualization.GeoChart(document.getElementById('geo-chart'));
	
	geoChart.options.region = selectedRegion;
	
	chart.draw(geoChart.data, geoChart.options);
	
	google.visualization.events.addListener(chart, 'regionClick', regionClickHandler);
}

function showGeoChart()
{
	if (xhttp.readyState == 4 && xhttp.status == 200)
	{
			var arrayData = [['Country', 'Poverty']];
			var stringJSON = xhttp.responseText;
			var valuesCountries = JSON.parse(stringJSON);
			for (var i = 0; i < valuesCountries.length; i++)
			{
				arrayData.push([valuesCountries[i].country, valuesCountries[i].value]);
			}
			
			geoChart.setData(arrayData);
			
//			google.charts.setOnLoadCallback(drawGeoChart);
			drawGeoChart();
	}
}

function loadChart()
{
	document.body.style.cursor = "wait";
	document.getElementById('geo-chart').style.cursor = "wait";
	
	geoChart = new GeoChart();
	
	xhttp = new XMLHttpRequest();
	xhttp.open("get", "load_last_values", true);
	xhttp.setRequestHeader("connection", "close");
	xhttp.onreadystatechange = showGeoChart;
	xhttp.send(null);
}

function setRegion(clicked)
{
	if (selectedRegion != clicked.value)
	{
		selectedRegion = clicked.value;
		
		document.body.style.cursor = "wait";
		document.getElementById('geo-chart').style.cursor = "wait";
		$('geo-chart').empty();
		drawGeoChart();
	}
}

function loadChartsLibrary()
{
	google.charts.load('current', {
			'packages':['geochart', 'corechart'],
			// Note: you will need to get a mapsApiKey for your project.
			// See: https://developers.google.com/chart/interactive/docs/basic_load_libs#load-settings
			'mapsApiKey': 'AIzaSyD-9tSrke72PouQMnMX-a7eZSW0jkFMBWY'
			});
}

function addCollapsibleListener()
{
	var coll = document.getElementsByClassName("collapsible");
	for (var i = 0; i < coll.length; i++)
	{
		coll[i].addEventListener("click", function() {
	    this.classList.toggle("active");
	    var content = this.nextElementSibling;
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

function loadModalListener()
{
	$('span.close').click(function(){
		$('#countryModal').hide();
	});
	
	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
	    if (event.target == document.getElementById('countryModal'))
	    {
	    	document.getElementById('countryModal').style.display = "none";
	    }
	}
	
	addCollapsibleListener();
}