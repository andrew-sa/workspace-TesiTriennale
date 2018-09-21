var xhttp;

var geoChart;
var lineChartCountryInfo;

var selectedRegion = 'world';
var currentCountry;

//function drawAnalysisChart(values)
//{
//	var arrayData = [['Year', 'Poverty']];
//	for (var i = 0; i < values.length; i++)
//	{
//		arrayData.push([values[i].year, values[i].value]);
//	}
//	
//	var data = google.visualization.arrayToDataTable(arrayData);
//	
//	var options = {
//			title: 'Indice di poverta\' (espresso in percentuale)',
//			curveType: 'function',
//			legend: { position: 'bottom' },
//			interpolateNulls: true,
//			focusTarget: 'category'
//		};
//
//	var chart = new google.visualization.LineChart(document.getElementById('analysis-chart'));
//
//	chart.draw(data, options);
//}

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

function LineChartCountryInfo()
{
	this.options = {
		title: 'Poverty index (expressed as a percentage)',
		curveType: 'none',
		legend: { position: 'none' },
		colors: ['#4CAF50'],
		backgroundColor: '#f1f1f1',
		lineWidth: 4,
		pointSize: 8,
		interpolateNulls: true,
		focusTarget: 'datum',
		crosshair: {
			trigger: 'both',
			orientation: 'horizontal',
			color: '#333'
		}
	};
	this.data = null;
	this.setData = function(arrayData) {
		this.data = google.visualization.arrayToDataTable(arrayData);
	}
}

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
////		google.charts.load('current', {packages: ['corechart']});
//		google.charts.setOnLoadCallback(drawAnalysisChart);
//		
//		function drawAnalysisChart()
//		{
//			var values = info.poverty;
//			var arrayData = [['Year', 'Poverty']];
//			if (values.length > 0)
//			{
//				for (var i = 0; i < values.length; i++)
//				{
//					arrayData.push([values[i].year, values[i].value]);
//				}
//				
//				var data = google.visualization.arrayToDataTable(arrayData);
//				
//				var options = {
//						title: 'Poverty index (expressed as a percentage)',
//						curveType: 'none',
//						legend: { position: 'none' },
//						colors: ['#4CAF50'],
//						backgroundColor: '#f1f1f1',
//						lineWidth: 4,
//						pointSize: 8,
//						interpolateNulls: true,
//						focusTarget: 'datum',
////						trendlines: {
////						    0: {
////						      type: 'exponential',
////						      color: 'black',
////						      lineWidth: 20,
////						      pointsVisible: false,
////						      opacity: 0.4,
////						      showR2: false,
////						      visibleInLegend: false
////						    }
////						},
//						crosshair: {
//							trigger: 'both',
//							orientation: 'horizontal',
//							color: '#333'
//						}
//					};
//
//				var chart = new google.visualization.LineChart(document.getElementById('analysis-chart'));
//
//				chart.draw(data, options);
//			}
//			else
//			{
//				$('#analysis-chart').html('N/A');
//			}
//			
//		}
//		
//	}
//}

function drawLineChartCountryInfo()
{
	var chart = new google.visualization.LineChart(document.getElementById('analysis-chart'));
	
	chart.draw(lineChartCountryInfo.data, lineChartCountryInfo.options);
}

function showCountryInfo()
{
	if (xhttp.readyState == 4 && xhttp.status == 200)
	{
		document.body.style.cursor = "auto";
		document.getElementById('geo-chart').style.cursor = "auto";
		
		var infoJSON = xhttp.responseText;
		var info = JSON.parse(infoJSON);
		
		$('#countryModal > .modal-content > .modal-header > h2').html(info.wiki.name);
		
		$("#country-general > img").attr("width", "300px");
		$("#country-general > img").attr("src", info.wiki.image);
		$("#country-general > p").html(info.wiki.description);
		
		if (info.news.length > 0)
		{
			for (var i = 0; i < info.news.length; i++)
			{
				$('#country-news').append('<p><a href="' + info.news[i].url + '" target="_blank">' + info.news[i].title + '</a><span><b>' + info.news[i].info + '</b></span><span>' + info.news[i].description + '</span></p>')
			}
		}
		else
		{
			$('#country-news').append('<p>N/A</p>');
		}
		
		$('#countryModal').show();
		
		lineChartCountryInfo = new LineChartCountryInfo();
		var values = info.poverty;
		var arrayData = [['Year', 'Poverty']];
		if (values.length > 0)
		{
			for (var i = 0; i < values.length; i++)
			{
				arrayData.push([values[i].year, values[i].value]);
			}
			lineChartCountryInfo.setData(arrayData);
			
			$('#analysis-chart').after('<a id="compare-link" href="' + 'analysis.html?show=' + currentCountry + '"' + '>Compare with other countries </a>');
			
			drawLineChartCountryInfo();
//			google.charts.setOnLoadCallback(drawAnalysisChart);
		}
		else
		{
			$('#analysis-chart').html('N/A');
		}
	}
}

function regionClickHandler(e)
{	
	$('#country-general > img').attr("src", "");
	$('#country-general > p').empty();
	$('#analysis-chart').empty();
	$('#compare-link').remove();
	$('#country-news > p').empty();
	currentCountry = e.region;
	
	document.body.style.cursor = "wait";
	document.getElementById('geo-chart').style.cursor = "wait";
	
	xhttp = new XMLHttpRequest();
	xhttp.open("get", "load_country_info?country=" + e.region, true);
	xhttp.setRequestHeader("connection", "close");
	xhttp.onreadystatechange = showCountryInfo;
	xhttp.send(null);
}

//function readyHandler()
//{
//	var regions = document.getElementById('geo-chart').getElementsByTagName('path');
//	for (var i = 0; i < regions.length; i++)
//	{
//		console.log("we");
//		regions[i].style.cursor = "pointer";
//	}
//}

//function showGeoChart()
//{
//	if (xhttp.readyState == 4 && xhttp.status == 200)
//	{
//			document.body.style.cursor = "auto";
//
//			google.charts.setOnLoadCallback(drawRegionsMap);
//
//			function drawRegionsMap()
//			{		
//				var arrayData = [['Country', 'Poverty']];
//				var stringJSON = xhttp.responseText;
//				var valuesCountries = JSON.parse(stringJSON);
//				for (var i = 0; i < valuesCountries.length; i++)
//				{
//					arrayData.push([valuesCountries[i].country, valuesCountries[i].value]);
//				}
////				var data = google.visualization.arrayToDataTable([
////					['Country', 'Poverty'],
////					['BR', 2.4],
////					['Ghana', 32.4],
////					['Nepal', 26.6]
////				]);
//				
//				var data = google.visualization.arrayToDataTable(arrayData);
//
//				var options = {
//						backgroundColor: '#333',
//						datalessRegionColor: '#f8bbd0',
//						enableRegionInteractivity: true
//				};
//				
////				options.region = '150';
//
//				var chart = new google.visualization.GeoChart(document.getElementById('geo-chart'));
//				
//				chart.draw(data, options);
//				
//				google.visualization.events.addListener(chart, 'regionClick', regionClickHandler);
//				
////				google.visualization.events.addListener(chart, 'ready', readyHandler);
//				
////				google.visualization.events.addListener(chart, 'onmouseover', function() {
////					console.log("Overing");
////					document.getElementById('geo-chart').style.cursor = "pointer";
////				});
////				google.visualization.events.addListener(chart, 'onmouseout', function() {
////					document.getElementById('geo-chart').style.cursor = "auto";
////				});
//				
//				$('span.close').click(function(){
//					$('#countryModal').hide();
//				});
//				
//				// When the user clicks anywhere outside of the modal, close it
//				window.onclick = function(event) {
//				    if (event.target == document.getElementById('countryModal'))
//				    {
//				    	document.getElementById('countryModal').style.display = "none";
//				    }
//				}
//
//			}
//	}
//}

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
		
		$('#analysis-link').attr('href', 'analysis.html?show=' + clicked.parentElement.innerText);
		
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