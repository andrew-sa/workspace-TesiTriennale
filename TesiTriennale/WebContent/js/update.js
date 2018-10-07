var xhttp;

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
	if (xhttp.readyState == 4 && xhttp.status == 200)
	{
		var responseSTR = xhttp.responseText;
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
		document.getElementById('update-btn').style.cursor = "auto";
		$('.source-btn').css('cursor', 'auto');
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
	document.getElementById('update-btn').style.cursor = "wait";
	$('.source-btn').css('cursor', 'wait');
	xhttp = new XMLHttpRequest();
	xhttp.open("get", "update_data" + queryString, true);
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
			document.getElementById('psw-btn').style.cursor = "pointer";
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
			document.getElementById('psw-btn').style.cursor = "auto";
		}
	}
}

function checkPassword(form)
{
	$('.msg').remove();
	var password = form.psw.value;
	
	document.body.style.cursor = "wait";
	document.getElementById('psw-btn').style.cursor = "wait";
	xhttp = new XMLHttpRequest();
	xhttp.open("get", "admin_actions?" + "password=" + password, true);
	xhttp.setRequestHeader("connection", "close");
	xhttp.onreadystatechange = showAction;
	xhttp.send(null);
}