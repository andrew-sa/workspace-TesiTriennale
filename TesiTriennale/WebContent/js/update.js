var xhttp;

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

function SourceTable()
{
	this.table = $('<table id="source-tb"><tbody></tbody></table>');
	this.numDataTypes = 0;
	this.dataTypesSource = null;
	this.addDataTypes = function(dataTypes)
	{
		this.numDataTypes = dataTypes.length;
		var dataTypesSTR = '<th>SOURCE</th>';
		this.dataTypesSource = '';
		for (var i = 0; i < dataTypes.length; i++)
		{
			dataTypesSTR = dataTypesSTR.concat('<th name="' + dataTypes[i] + '">' + dataTypes[i] + '</th>');
			this.dataTypesSource = this.dataTypesSource.concat('<td name="' + dataTypes[i] + '"></td>');
		}
		this.table.append('<tr>' + dataTypesSTR + '</tr>');
	}
	this.addNames = function(names)
	{
//		var cells = '';
//		for (var i = 0; i < this.numDataTypes; i++)
//		{
//			cells = cells.concat('<td></td>')
//		}
		for (var i = 0; i < names.length; i++)
		{
			this.table.append('<tr id="' + names[i] + '"><td>' + names[i] + '</td>' + this.dataTypesSource + '</tr>');
		}
	}
	this.addSource = function(name, dataType, updated)
	{
		console.log(this.table);
		var row = this.table.children('tbody').children('#'.concat(name));
		var btn = $('<button class="source-btn" type="button"></button>');
		btn.attr('name', name);
		btn.attr('title', dataType);
		btn.attr('value', updated);
		if (updated)
		{
			btn.html('<i class="fas fa-check fa-3x"></i>');
		}
		else
		{
			btn.html('<i class="fas fa-times fa-3x"></i>');
		}
		row.children('td[name="' + dataType + '"]').append(btn);
	}
//	this.addSource = function(name, dataType, updated)
//	{
//		var row = $('#' + name);
//		var btn = $('<button class="source-btn" type="button"></button>');
//		btn.attr('name', name);
//		btn.attr('value', updated);
//		btn.text(name);
//		var state = $('<td></td>');
//		if (updated)
//		{
//			state.html('<i class="fas fa-check fa-3x"></i>');
//		}
//		else
//		{
//			state.html('<i class="fas fa-times fa-3x"></i>');
//		}
//		row.append($('<td></td>').append(btn));
//		row.append(state);
//		this.table.append(row);
//	}
}


function showModalUpdate()
{
	$('.msg').remove();
	$('#source-tb').remove();
	$('#update-form').hide();
	$('#insertion-form').hide();
	$('#deleting-form').hide();
	$('#password-form').show();
	window.onclick = function(event) {
		if (event.target == document.getElementById('modal-update'))
	    {
			document.getElementById('modal-update').style.display = "none";
	    }
	}
	document.getElementById('modal-update').style.display = "block";
}

function closeModalUpdate()
{
	document.getElementById('modal-update').style.display = "none";
}

function showUpdateResult()
{
	if (xhttp.readyState == 3)
	{
		$('#updating-msg').show();
		$('#updating-msg').empty();
		var response = xhttp.responseText;
		var msg = response.split('\n');
//		console.log(msg[msg.length - 2].trim());
		$('#updating-msg').html(msg[msg.length - 2].trim());
	}
	else if (xhttp.readyState == 4 && xhttp.status == 200)
	{
		$('#updating-msg').hide();
		$('#updating-msg').empty();
		var responseSTR = xhttp.responseText;
		var msg = responseSTR.split('\n');
		responseSTR = msg[msg.length - 2].trim();
		if (responseSTR == 'OK')
		{
			$('#update-btn').hide();
			$('#close-btn').show();
			$('#source-tb').remove();
			$('#update-form > .container').prepend('<div class="success-msg msg">UPDATE COMPLETED</div>');
		}
		else
		{
			$('#source-tb').before('<div class="error-msg msg">UPDATE FAILED. TRY AGAIN</div>');
		}
		document.body.style.cursor = "auto";
//		document.getElementById('update-btn').style.cursor = "auto";
		$('.source-btn').css('cursor', 'auto');
		$('#modal-update .pointer-btn').css('cursor', 'pointer');
	}
}

function checkUpdate(form)
{
	$('.msg').remove();
	var sources = form.getElementsByClassName('source-btn');
	var queryString = '?'
	for (var i = 0; i < sources.length; i++)
	{
		if (i > 0)
		{
			queryString += '&'
		}
		queryString += sources[i].name + sources[i].title + '=' + sources[i].value;
	}
//	console.log(queryString);
	document.body.style.cursor = "wait";
//	document.getElementById('update-btn').style.cursor = "wait";
	$('#modal-update .pointer-btn').css('cursor', 'wait');
	xhttp = new XMLHttpRequest();
	xhttp.open("get", "update_data" + queryString, true);
//	xhttp.open("get", "test_processing_response" + queryString, true);
	xhttp.setRequestHeader("connection", "close");
	xhttp.onreadystatechange = showUpdateResult;
	xhttp.send(null);
}

function showAction()
{
	if (xhttp.readyState == 4 && xhttp.status == 200)
	{
		var responseSTR = xhttp.responseText;
		var responseARRAY = JSON.parse(responseSTR);
		if (responseARRAY.length == 0)
		{
			$('#password-form > .container').prepend('<div class="error-msg msg">WRONG PASSWORD</div>');
			document.body.style.cursor = "auto";
//			document.getElementById('psw-btn').style.cursor = "pointer";
			$('#modal-update .pointer-btn').css('cursor', 'pointer');
		}
		else
		{
			var names = responseARRAY[0];
			var dataTypes = responseARRAY[1];
			var sources = responseARRAY[2];
			
			var sourceTable = new SourceTable();
			sourceTable.addDataTypes(dataTypes);
			sourceTable.addNames(names);
			var updatesAvailable = false;
			for (var i = 0; i < sources.length; i++)
			{
				if (!sources[i].updated)
				{
					updatesAvailable = true;
					
				}
				sourceTable.addSource(sources[i].name, sources[i].dataType, sources[i].updated);
			}
			if (updatesAvailable)
			{
				$('#update-btn').show();
				$('#close-btn').hide();
			}
			else
			{
				$('#update-btn').hide();
				$('#close-btn').show();
			}
			$('#update-form > .container').prepend(sourceTable.table);
			$('#password-form').hide();
			$('#update-form').show();
			
			document.body.style.cursor = "auto";
//			document.getElementById('psw-btn').style.cursor = "auto";
			$('#modal-update .pointer-btn').css('cursor', 'pointer');
		}
	}
}

function checkPassword(form)
{
	$('.msg').remove();
	var password = form.psw.value;
	
	document.body.style.cursor = "wait";
//	document.getElementById('psw-btn').style.cursor = "wait";
	$('#modal-update .pointer-btn').css('cursor', 'wait');
	xhttp = new XMLHttpRequest();
	xhttp.open("get", "admin_actions?" + "password=" + password, true);
	xhttp.setRequestHeader("connection", "close");
	xhttp.onreadystatechange = showAction;
	xhttp.send(null);
}

/* INSERTION FORM */
function Data()
{
	this.countryCode = null;
	this.dataType = null;
	this.year = null;
	this.value = null;
	
	this.setCountryCode = function(countryCode)
	{
		this.countryCode = countryCode;
	}
	
	this.setDataType = function(dataType)
	{
		this.dataType = dataType;
	}
	
	this.setYear = function(year)
	{
		this.year = year;
	}
	
	this.setValue = function(value)
	{
		this.value = value;
	}
}

function isEmptyField(field)
{
	if (field.value == null || field.value == "")
	{
		return true;
	}
	else
	{
		return false;
	}
}

function isCountryCode(str)
{
	var regex = /^[A-Z]{2}$/;
	if (str.toUpperCase().match(regex))
	{
		return true;
	}
	else
	{
		return false;
	}
}

function isDataType(str)
{
	var dataTypes = ['population', 'poverty', 'netmigration', 'gdppercapita'];
	for (var i = 0; i < dataTypes.length; i++)
	{
		if (str.toLowerCase() == dataTypes[i])
		{
			return true;
		}
	}
	return false;
}

function isYear(str)
{
	var currentYear = (new Date).getFullYear();
	if (str <= currentYear)
	{
		return true;
	}
	else
	{
		return false;
	}
}

function isValue(str)
{
	var regex = /^-?[0-9]+(\.[0-9]+)?$/;
	if (str.match(regex))
	{
		return true;
	}
	else
	{
		return false;
	}
}

function showSaveDataInsertedResponse()
{
//	var regex = /\n+/;
	if (xhttp.readyState == 3)
	{
		$('#insertion-msg').show();
		$('#insertion-msg').empty();
		var response = xhttp.responseText;
//		var msg = response.split(regex);
		var msg = response.split('<br>');
		$('#insertion-msg').html(msg[msg.length - 2].trim());
	}
	else if (xhttp.readyState == 4 && xhttp.status == 200)
	{
		$('#insertion-form button').prop('disabled', false);
//		$('#submit-insertion-btn').css('cursor', 'pointer');
//		$('#close-insertion-btn').css('cursor', 'pointer');
//		$('#insertion-log-container button').css('cursor', 'pointer');
		$('#modal-update .pointer-btn').css('cursor', 'pointer');
		
		$('#insertion-msg').hide();
		$('#insertion-msg').empty();
		
		$('#insertion-form-container').hide();
		$('#insertion-log-container').show();
		$('#insertion-log').empty();
		
		var response = xhttp.responseText;
		$('#insertion-log').html(response);
	}
}

function saveDataInserted(form)
{
	$('.msg').remove();
	$('#insertion-form button').prop('disabled', true);
	
	var tuplesToken = '\n';
	var attributesToken = ',';
	var numAttributes = 4;
	
	var tuples = null;
	var validTuples = [];
	
	var textarea = form.textarea;
	
	if (!isEmptyField(textarea))
	{
		var tuples = textarea.value.split(tuplesToken);
//		console.log(tuples);
		for (var i = 0; i < tuples.length; i++)
		{
			var data = new Data();
			var attributes = tuples[i].trim().split(attributesToken);
			if (attributes.length == numAttributes)
			{
				var validated = true;
				var j = 0;
				while (validated && j < attributes.length)
				{
					attributes[j] = attributes[j].trim();
					switch (j)
					{
						case 0:
							if (!isCountryCode(attributes[j]))
							{
								validated = false;
							}
							else
							{
								data.setCountryCode(attributes[j].toUpperCase());
							}
							break;
						case 1:
							if (!isDataType(attributes[j]))
							{
								validated = false;
							}
							else
							{
								data.setDataType(attributes[j].toLowerCase());
							}
							break;
						case 2:
							if (!isYear(attributes[j]))
							{
								validated = false;
							}
							else
							{
								data.setYear(attributes[j]);
							}
							break;
						case 3:
							if (!isValue(attributes[j]))
							{
								validated = false;
							}
							else
							{
								data.setValue(attributes[j]);
							}
							break;
						default:
							break;
					}
					j++;
				}
				if (validated)
				{
					validTuples.push(data);
				}
			}
		}
		
		if (validTuples.length > 0)
		{
//			$('#insertion-form button').css('cursor', 'wait');
			$('#modal-update .pointer-btn').css('cursor', 'wait');
			
			xhttp = new XMLHttpRequest();
			xhttp.open("post", "save_data_manual_insertion", true);
			xhttp.setRequestHeader("content-type", "application/x-www-form-urlencoded");
			xhttp.setRequestHeader("connection", "close");
			xhttp.onreadystatechange = showSaveDataInsertedResponse;
			console.log(JSON.stringify(validTuples));
			xhttp.send("data=" + encodeURIComponent(JSON.stringify(validTuples)));
		}
		else
		{
			$('#submit-insertion-btn').before('<div class="error-msg msg">INSERTED TUPLES ARE NOT IN THE CORRECT FORMAT</div>');
			
			$('#insertion-form button').prop('disabled', false);
//			$('#submit-insertion-btn').css('cursor', 'pointer');
//			$('#close-insertion-btn').css('cursor', 'pointer');
			$('#modal-update .pointer-btn').css('cursor', 'pointer');
		}
	}
	else
	{
		$('#submit-insertion-btn').before('<div class="error-msg msg">INSERT ONE OR MORE TUPLES</div>');
		
		$('#insertion-form button').prop('disabled', false);
//		$('#submit-insertion-btn').css('cursor', 'pointer');
//		$('#close-insertion-btn').css('cursor', 'pointer');
		$('#modal-update .pointer-btn').css('cursor', 'pointer');
	}
}

function showModalInsertion()
{
	$('.msg').remove();
	$('#source-tb').remove();
	$('#update-form').hide();
	$('#insertion-form').show();
	$('#insertion-form-container').show();
	$('#insertion-log-container').hide();
	$('#deleting-form').hide();
	$('#password-form').hide();
	
	$('#updating-msg').hide();
	$('#updating-msg').empty();
	$('#insertion-msg').hide();
	$('#insertion-msg').empty();
	$('#insertion-log').empty();
}

/* DELETING FORM */
function showDeleteResponse()
{
	if (xhttp.readyState == 4 && xhttp.status == 200)
	{
		$('.msg').remove();
		
		var response = xhttp.responseText;
		$('#deleting-log').empty();
		$('#deleting-log').html(response);
		
		$('#deleting-log').show();
		$('#deleting-form-container').hide();
		$('#deleting-log-container').show();
		
		document.body.style.cursor = "auto";
		$('#modal-update .pointer-btn').prop('disabled', false);
		$('#modal-update .pointer-btn').css('cursor', 'pointer');
	}
}

function deleteSelectData(form)
{
	$('.msg').remove();
	
	document.body.style.cursor = "wait";
	$('#modal-update .pointer-btn').prop('disabled', true);
	$('#modal-update .pointer-btn').css('cursor', 'wait');
	
	var checkboxs = form.getElementsByTagName('input');
	var toDelete = [];
//	console.log(checkboxs);
	for (var i = 0; i < checkboxs.length; i++)
	{
		if (checkboxs[i].checked == true)
		{
			var values = checkboxs[i].value.split('-');
			var data = new Data();
			data.setCountryCode(values[0]);
			data.setYear(values[1]);
			data.setDataType(values[2]);
			toDelete.push(data);
		}
	}
	
	if (toDelete.length > 0)
	{
		xhttp = new XMLHttpRequest();
		xhttp.open("post", "delete_inserted_data", true);
		xhttp.setRequestHeader("content-type", "application/x-www-form-urlencoded");
		xhttp.setRequestHeader("connection", "close");
		xhttp.onreadystatechange = showDeleteResponse;
		console.log(JSON.stringify(toDelete));
		xhttp.send("data=" + encodeURIComponent(JSON.stringify(toDelete)));
	}
	else
	{
		$('#submit-deleting-btn').before('<div class="error-msg msg">NO DATA SELECTED</div>');
		
		document.body.style.cursor = "auto";
		$('#modal-update .pointer-btn').prop('disabled', false);
		$('#modal-update .pointer-btn').css('cursor', 'pointer');
	}
}

function DeletingCheckBox(source, year, value, dataType)
{
	this.data = source + '-' + year + '-' + dataType;
	this.dataLabel = source + ', ' + dataType + ', ' + year + ', ' + value;
	this.html = $('<div class="single-data-container"></div>');
	this.html.append('<input type="checkbox" id="' + this.data + '" value="' + this.data + '"> <label for="' + this.data + '">' + this.dataLabel + '</label>')
}

function showInsertedData()
{
	if (xhttp.readyState == 4 && xhttp.status == 200)
	{
		$('#submit-deleting-btn').show();
		$('#refresh-deleting-btn').hide();
		
		var responseSTR = xhttp.responseText;
		if  (isJSONString(responseSTR))
		{
			$('#deleting-form  #checkbox-data-container').empty();
			
			var responseARRAY = JSON.parse(responseSTR);
			if (responseARRAY.length == 0)
			{
				$('#deleting-form  #checkbox-data-container').html('NO DATA INSERTED MANUALLY');
				
			}
			else
			{
				for (var i = 0; i < responseARRAY.length; i++)
				{
					var data = responseARRAY[i];
					var checkbox = new DeletingCheckBox(data.country, data.year, data.value, data.dataType);
					$('#deleting-form  #checkbox-data-container').append(checkbox.html);
				}
			}
		}
		else
		{
			$('#submit-deleting-btn').hide();
			$('#refresh-deleting-btn').show();
			
			$('#deleting-form  #checkbox-data-container').html('<div class="error-msg msg">ERROR DURING INTERACTION WITH DATABASE</div>');
		}
		
		$('#deleting-form  #checkbox-data-container').show();
		
		document.body.style.cursor = "auto";
		$('#modal-update .pointer-btn').prop('disabled', false);
		$('#modal-update .pointer-btn').css('cursor', 'pointer');
	}
}

function loadInsertedData()
{
	$('.msg').remove();
	
	document.body.style.cursor = "wait";
	$('#modal-update .pointer-btn').prop('disabled', true);
	$('#modal-update .pointer-btn').css('cursor', 'wait');
	
	xhttp = new XMLHttpRequest();
	xhttp.open("get", "load_data_inserted_manually", true);
	xhttp.setRequestHeader("connection", "close");
	xhttp.onreadystatechange = showInsertedData;
	xhttp.send(null);
}
function showModalDeleting()
{
	$('.msg').remove();
	$('#source-tb').remove();
	$('#update-form').hide();
	$('#insertion-form').hide();
	$('#insertion-form-container').show();
	$('#insertion-log-container').hide();
	$('#password-form').hide();
	
	$('#submit-deleting-btn').show();
	$('#refresh-deleting-btn').hide();
	$('#deleting-form-container').show();
	$('#deleting-log-container').hide();
	$('#deleting-form').show();
	
	$('#deleting-form  #checkbox-data-container').hide();
	$('#updating-msg').hide();
	$('#updating-msg').empty();
	$('#insertion-msg').hide();
	$('#insertion-msg').empty();
	$('#insertion-log').empty();
	$('#deleting-log').hide();
	$('#deleting-log').empty();
	
	loadInsertedData();
}