var xhttp;

var geoChart;
var lineChartCountryInfo;

var selectedRegion = 'world';
var currentCountry;

var firstYear = '1960';
var lastYear = (new Date()).getFullYear();

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

/* LOAD CHARTS LIBRARY */
function loadGoogleChartsLibrary()
{
	google.charts.load('current', {
		'packages':['geochart', 'corechart'],
		// Note: you will need to get a mapsApiKey for your project.
		// See: https://developers.google.com/chart/interactive/docs/basic_load_libs#load-settings
		'mapsApiKey': 'AIzaSyD-9tSrke72PouQMnMX-a7eZSW0jkFMBWY'
		});
}

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
	this.options = null;
	this.setOptions = function(options) {
		this.options = options;
	}
//	this.options = {
////		title: 'Poverty index (expressed as a percentage)',
//		chartArea: {
//			width: '85%',
//			height: '85%'
//		},
//		curveType: 'none',
//		legend: {
//			position: 'top',
//			textStyle: {
//				fontSize: 20,
//				bold: true
//			}
//		},
////		colors: ['#4CAF50', "#555"],
//		backgroundColor: '#f1f1f1',
//		lineWidth: 4,
//		pointSize: 8,
//		interpolateNulls: true,
//		theme: 'material',
//		focusTarget: 'datum',
////		crosshair: {
////			trigger: 'both',
////			orientation: 'horizontal',
////			color: '#333'
////		},
//		series: {
//			0: {
//				targetAxisIndex: 0,
//				color: '#4CAF50'
//			},
//			1: {
//				targetAxisIndex: 1,
//				color: '#555'
//			}
//		},
//		vAxes: {
//			0: {
//				title: 'Poverty (expressed as a percentage)',
//				titleTextStyle: {
//					fontSize: 20
//				},
//				textStyle: {
//					fontSize: 15
//				},
//			},
//			1: {
//				title: 'Net migration',
//				titleTextStyle: {
//					fontSize: 20
//				},
//				textStyle: {
//					fontSize: 15
//				},
//				format: 'short'
//			}
//		}
//	};
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

//function clickHandler(e)
//{
//	console.log(e.targetID);
//}

function createArrayData(labels)
{
	var arrayData = [labels];
	for (var year = firstYear; year <= lastYear; year++)
	{
		arrayData.push([new Date(year, 0, 1)]);
	}
	return arrayData;
}

function fillArrayData(arrayData, dataToInsert)
{
	for (var i = 1; i < arrayData.length; i++)
	{
		var j = 0;
		var found = false;
		while (!found && j < dataToInsert.length)
		{
			if (arrayData[i][0].getFullYear() == dataToInsert[j].year)
			{
				found = true;
				arrayData[i].push(dataToInsert[j].value);
				dataToInsert.splice(j, 1);
			}
			j++;
		}
		if (!found)
		{
			arrayData[i].push(null);
		}
	}
	return arrayData;
}

function drawLineChartCountryInfo()
{
	var chart = new google.visualization.LineChart(document.getElementById('analysis-chart'));
	
	chart.draw(lineChartCountryInfo.data, lineChartCountryInfo.options);
	
//	google.visualization.events.addListener(chart, 'click', clickHandler);
}

function showCountryInfo()
{
	if (xhttp.readyState == 4 && xhttp.status == 200)
	{
//		document.body.style.cursor = "auto";
//		document.getElementById('geo-chart').style.cursor = "auto";
		document.getElementById('toast').className = '';
		
		var infoJSON = xhttp.responseText;
		if (isJSONString(infoJSON))
		{
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
			var poverty = info.poverty;
			var netMigration = info.netMigration;
			var gdpPerCapita = info.gdpPerCapita;
			
			if (poverty.length > 0 && netMigration.length > 0 && gdpPerCapita.length > 0)
			{
				var options = {
					chartArea: {
						width: '85%',
						height: '85%'
					},
					curveType: 'none',
					legend: {
						position: 'top',
						textStyle: {
							fontSize: 20,
							bold: true
						}
					},
					backgroundColor: '#f1f1f1',
					lineWidth: 4,
					pointSize: 8,
					interpolateNulls: true,
					theme: 'material',
					focusTarget: 'datum',
					series: {
						0: {
							targetAxisIndex: 0,
							color: '#4CAF50'
						},
						1: {
							targetAxisIndex: 1,
							color: '#777'
						},
						2: {
							targetAxisIndex: 2,
							color: '#111'
						}
					},
					vAxes: {
						0: {
							title: 'Poverty (expressed as a percentage)',
							titleTextStyle: {
								fontSize: 20
							},
							textStyle: {
								fontSize: 15
							},
						},
						1: {
							title: 'GDP per capita / Net migration',
							titleTextStyle: {
								fontSize: 20
							},
							textStyle: {
								fontSize: 15
							},
							format: 'short'
						},
						2: {
//							title: 'GDP per capita',
							textPosition: 'in',
							titleTextStyle: {
								fontSize: 20
							},
							textStyle: {
								fontSize: 15
							},
							format: 'short'
						}
					}
				};
				
//				var arrayData = [['Year', 'Poverty', 'Net migration']];
//				for (var year = firstYear; year <= lastYear; year++)
//				{
//					arrayData.push([year]);
//				}
				
				var labels = ['Year', 'Poverty', 'Net migration', 'GDP per capita'];
				var arrayData = createArrayData(labels);
				arrayData = fillArrayData(arrayData, poverty);
				arrayData = fillArrayData(arrayData, netMigration);
				arrayData = fillArrayData(arrayData, gdpPerCapita);
				
//				for (var i = 1; i < arrayData.length; i++)
//				{
//					var j = 0;
//					var found = false;
//					while (!found && j < poverty.length)
//					{
//						if (arrayData[i][0].getFullYear() == poverty[j].year)
//						{
//							found = true;
//							arrayData[i].push(poverty[j].value);
//							poverty.splice(j, 1);
//						}
//						j++;
//					}
//					if (!found)
//					{
//						arrayData[i].push(null);
//					}
//				}
//				
//				for (var i = 1; i < arrayData.length; i++)
//				{
//					var j = 0;
//					var found = false;
//					while (!found && j < netMigration.length)
//					{
//						if (arrayData[i][0].getFullYear() == netMigration[j].year)
//						{
//							found = true;
//							arrayData[i].push(netMigration[j].value);
//							netMigration.splice(j, 1);
//						}
//						j++;
//					}
//					if (!found)
//					{
//						arrayData[i].push(null);
//					}
//				}
//				
//				for (var i = 1; i < arrayData.length; i++)
//				{
//					var j = 0;
//					var found = false;
//					while (!found && j < gdpPerCapita.length)
//					{
//						if (arrayData[i][0].getFullYear() == gdpPerCapita[j].year)
//						{
//							found = true;
//							arrayData[i].push(gdpPerCapita[j].value);
//							gdpPerCapita.splice(j, 1);
//						}
//						j++;
//					}
//					if (!found)
//					{
//						arrayData[i].push(null);
//					}
//				}
				
				lineChartCountryInfo.setOptions(options);
				lineChartCountryInfo.setData(arrayData);
				
				$('#analysis-chart').after('<a id="compare-link" href="' + 'analysis.html?show=' + currentCountry + '"' + '>Compare with other countries </a>');
				$('#analysis-chart').css('height', '30em');
				drawLineChartCountryInfo();
//				google.charts.setOnLoadCallback(drawAnalysisChart);
			}
			else if (poverty.length > 0)
			{
				if (netMigration.length > 0)
				{
					var options = {
						chartArea: {
							width: '85%',
							height: '85%'
						},
						curveType: 'none',
						legend: {
							position: 'top',
							textStyle: {
								fontSize: 20,
								bold: true
							}
						},
						backgroundColor: '#f1f1f1',
						lineWidth: 4,
						pointSize: 8,
						interpolateNulls: true,
						theme: 'material',
						focusTarget: 'datum',
						series: {
							0: {
								targetAxisIndex: 0,
								color: '#4CAF50'
							},
							1: {
								targetAxisIndex: 1,
								color: '#777'
							}
						},
						vAxes: {
							0: {
								title: 'Poverty (expressed as a percentage)',
								titleTextStyle: {
									fontSize: 20
								},
								textStyle: {
									fontSize: 15
								},
							},
							1: {
								title: 'Net migration',
								titleTextStyle: {
									fontSize: 20
								},
								textStyle: {
									fontSize: 15
								},
								format: 'short'
							}
						}
					};
					
					var labels = ['Year', 'Poverty', 'Net migration'];
					var arrayData = createArrayData(labels);
					arrayData = fillArrayData(arrayData, poverty);
					arrayData = fillArrayData(arrayData, netMigration);
					
					lineChartCountryInfo.setOptions(options);
					lineChartCountryInfo.setData(arrayData);
				}
				else if (gdpPerCapita.length > 0)
				{
					var options = {
						chartArea: {
							width: '85%',
							height: '85%'
						},
						curveType: 'none',
						legend: {
							position: 'top',
							textStyle: {
								fontSize: 20,
								bold: true
							}
						},
						backgroundColor: '#f1f1f1',
						lineWidth: 4,
						pointSize: 8,
						interpolateNulls: true,
						theme: 'material',
						focusTarget: 'datum',
						series: {
							0: {
								targetAxisIndex: 0,
								color: '#4CAF50'
							},
							1: {
								targetAxisIndex: 1,
								color: '#777'
							}
						},
						vAxes: {
							0: {
								title: 'Poverty (expressed as a percentage)',
								titleTextStyle: {
									fontSize: 20
								},
								textStyle: {
									fontSize: 15
								},
							},
							1: {
								title: 'GDP per capita',
								titleTextStyle: {
									fontSize: 20
								},
								textStyle: {
									fontSize: 15
								},
								format: 'short'
							}
						}
					};
					
					var labels = ['Year', 'Poverty', 'GDP per capita'];
					var arrayData = createArrayData(labels);
					arrayData = fillArrayData(arrayData, poverty);
					arrayData = fillArrayData(arrayData, gdpPerCapita);
					
					lineChartCountryInfo.setOptions(options);
					lineChartCountryInfo.setData(arrayData);
				}
				else
				{
					var options = {
						chartArea: {
							width: '85%',
							height: '85%'
						},
						curveType: 'none',
						legend: {
							position: 'top',
							textStyle: {
								fontSize: 20,
								bold: true
							}
						},
						backgroundColor: '#f1f1f1',
						lineWidth: 4,
						pointSize: 8,
						interpolateNulls: true,
						theme: 'material',
						focusTarget: 'datum',
						series: {
							0: {
								targetAxisIndex: 0,
								color: '#4CAF50'
							}
						},
						vAxes: {
							0: {
								title: 'Poverty (expressed as a percentage)',
								titleTextStyle: {
									fontSize: 20
								},
								textStyle: {
									fontSize: 15
								}
							}
						}
					};
					
		//			var arrayData = [['Year', 'Poverty']];
		//			for (var i = 0; i < poverty.length; i++)
		//			{
		//				arrayData.push([poverty[i].year, poverty[i].value]);
		//			}
					
					var labels = ['Year', 'Poverty'];
					var arrayData = createArrayData(labels);
					arrayData = fillArrayData(arrayData, poverty);
					
					lineChartCountryInfo.setOptions(options);
					lineChartCountryInfo.setData(arrayData);
				}
			
				$('#analysis-chart').after('<a id="compare-link" href="' + 'analysis.html?show=' + currentCountry + '"' + '>Compare with other countries </a>');
				$('#analysis-chart').css('height', '30em');
				drawLineChartCountryInfo();
		//		google.charts.setOnLoadCallback(drawAnalysisChart);
			}
			else if (netMigration.length > 0)
			{
				if (gdpPerCapita.length > 0)
				{
					var options = {
						chartArea: {
							width: '85%',
							height: '85%'
						},
						curveType: 'none',
						legend: {
							position: 'top',
							textStyle: {
								fontSize: 20,
								bold: true
							}
						},
						backgroundColor: '#f1f1f1',
						lineWidth: 4,
						pointSize: 8,
						interpolateNulls: true,
						theme: 'material',
						focusTarget: 'datum',
						series: {
							0: {
								targetAxisIndex: 0,
								color: '#777'
							},
							1: {
								targetAxisIndex: 1,
								color: '#111'
							}
						},
						vAxes: {
							0: {
								title: 'Net migration',
								titleTextStyle: {
									fontSize: 20
								},
								textStyle: {
									fontSize: 15
								},
								format: 'short'
							},
							1: {
								title: 'GDP per capita',
								titleTextStyle: {
									fontSize: 20
								},
								textStyle: {
									fontSize: 15
								},
								format: 'short'
							}
						}
					};
					
					var labels = ['Year', 'Net migration', 'GDP per capita'];
					var arrayData = createArrayData(labels);
					arrayData = fillArrayData(arrayData, netMigration);
					arrayData = fillArrayData(arrayData, gdpPerCapita);
					
					lineChartCountryInfo.setOptions(options);
					lineChartCountryInfo.setData(arrayData);
				}
				else
				{
					var options = {
						chartArea: {
							width: '85%',
							height: '85%'
						},
						curveType: 'none',
						legend: {
							position: 'top',
							textStyle: {
								fontSize: 20,
								bold: true
							}
						},
						backgroundColor: '#f1f1f1',
						lineWidth: 4,
						pointSize: 8,
						interpolateNulls: true,
						theme: 'material',
						focusTarget: 'datum',
						series: {
							0: {
								targetAxisIndex: 0,
								color: '#555'
							}
						},
						vAxes: {
							0: {
								title: 'Net migration',
								titleTextStyle: {
									fontSize: 20
								},
								textStyle: {
									fontSize: 15
								},
								format: 'short'
							}
						}
					};
					
//					var arrayData = [['Year', 'Net migration']];
//					for (var i = 0; i < netMigration.length; i++)
//					{
//						arrayData.push([netMigration[i].year, netMigration[i].value]);
//					}
					
					var labels = ['Year', 'Net migration'];
					var arrayData = createArrayData(labels);
					arrayData = fillArrayData(arrayData, netMigration);
					
					lineChartCountryInfo.setOptions(options);
					lineChartCountryInfo.setData(arrayData);
				}
				
				$('#analysis-chart').after('<a id="compare-link" href="' + 'analysis.html?show=' + currentCountry + '"' + '>Compare with other countries </a>');
				$('#analysis-chart').css('height', '30em');
				drawLineChartCountryInfo();
//				google.charts.setOnLoadCallback(drawAnalysisChart);
			}
			else if (gdpPerCapita.length > 0)
			{
				var options = {
					chartArea: {
						width: '85%',
						height: '85%'
					},
					curveType: 'none',
					legend: {
						position: 'top',
						textStyle: {
							fontSize: 20,
							bold: true
						}
					},
					backgroundColor: '#f1f1f1',
					lineWidth: 4,
					pointSize: 8,
					interpolateNulls: true,
					theme: 'material',
					focusTarget: 'datum',
					series: {
						0: {
							targetAxisIndex: 0,
							color: '#111'
						}
					},
					vAxes: {
						0: {
							title: 'GDP per capita',
							titleTextStyle: {
								fontSize: 20
							},
							textStyle: {
								fontSize: 15
							},
							format: 'short'
						}
					}
				};
				
				var labels = ['Year', 'GDP per capita'];
				var arrayData = createArrayData(labels);
				arrayData = fillArrayData(arrayData, gdpPerCapita);
				
				lineChartCountryInfo.setOptions(options);
				lineChartCountryInfo.setData(arrayData);
				
				$('#analysis-chart').after('<a id="compare-link" href="' + 'analysis.html?show=' + currentCountry + '"' + '>Compare with other countries </a>');
				$('#analysis-chart').css('height', '30em');
				drawLineChartCountryInfo();
//				google.charts.setOnLoadCallback(drawAnalysisChart);
			}
			else
			{
				$('#analysis-chart').html('N/A');
			}
		}
	}
}

function regionClickHandler(e)
{	
	$('#country-general > img').attr("src", "");
	$('#country-general > p').empty();
	$('#analysis-chart').empty();
	$('#analysis-chart').css('height', 'auto');
	$('#compare-link').remove();
	$('#country-news > p').empty();
	currentCountry = e.region;
	
//	document.body.style.cursor = "wait";
//	document.getElementById('geo-chart').style.cursor = "wait";
	document.getElementById('toast').innerHTML = '<i class="fa fa-circle-notch fa-spin"></i> Loading ' + e.region + ' info';
	document.getElementById('toast').className = 'show';
	
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
		var stringJSON = xhttp.responseText;
		if (isJSONString(stringJSON))
		{
			var arrayData = [['Country', 'Poverty']];
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

function selectRadioBox()
{
	var radioboxs = $('input[type=radio]');
//	console.log(radioboxs);
	for (var i = 0; i < radioboxs.length; i++)
	{
		if (radioboxs[i].value == 'world')
		{
			radioboxs[i].checked = true;
		}
		else
		{
			radioboxs[i].checked = false;
		}
	}
	
	$('#analysis-link').attr('href', 'analysis.html');
}