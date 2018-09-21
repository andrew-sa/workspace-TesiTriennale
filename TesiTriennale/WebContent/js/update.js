var xhttp;

function SourceTable()
{
	this.table = $('<table id="source-tb"><tbody></tbody></table>');
	this.table.append('<tr><th>Source</th><th>Updated</th></tr>');
	this.addSource = function(name, updated)
	{
		var row = $('<tr></tr>');
		var btn = $('<button class="source-btn" type="button"></button>');
		btn.attr('name', name);
		btn.attr('value', updated);
		btn.text(name);
		var state = $('<td></td>');
		if (updated)
		{
			state.html('<i class="fas fa-check fa-3x"></i>');
		}
		else
		{
			state.html('<i class="fas fa-times fa-3x"></i>');
		}
		row.append($('<td></td>.').append(btn));
		row.append(state);
		this.table.append(row);
	}
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
		queryString += sources[i].name + '=' + sources[i].value;
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
		}
		else
		{
			var updatesAvailable = false;
			var sourceTable = new SourceTable();
			for (var i = 0; i < responseARRAY.length; i++)
			{
				if (!responseARRAY[i].updated)
				{
					updatesAvailable = true;
					
				}
				sourceTable.addSource(responseARRAY[i].name, responseARRAY[i].updated);
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
		}
		document.body.style.cursor = "auto";
		document.getElementById('psw-btn').style.cursor = "auto";
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