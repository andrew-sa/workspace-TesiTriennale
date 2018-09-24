$(document).ready(function() {
	var btn = $(".play-button");
	btn.click(function() {
		btn.toggleClass("paused");
		if (!animatedChart.hasNextYear() && document.getElementById('playButton').classList.contains("paused"))
		{
//			console.log(animatedChart.years[animatedChart.years.length - 1] - animatedChart.years[0]);
			document.getElementById('yearsRange').stepDown((animatedChart.years[animatedChart.years.length - 1] - animatedChart.years[0]));
			animatedChart.index = 0;
		}
		drawAnimatedChart();
	});
});

function showSelectedYear(rangeElement)
{
	var index = animatedChart.getYearIndex(rangeElement.value);
	document.getElementById('playButton').classList.remove("paused");
	if (index >= 0)
	{
		animatedChart.index = index;
		drawAnimatedChart();
	}
}

function showYear(labelElement)
{
	var year = labelElement.innerText;
	var currentYear = animatedChart.getCurrentYear();
	if (year > currentYear)
	{
		console.log(year - currentYear);
		document.getElementById('yearsRange').stepUp(year - currentYear);
		animatedChart.index = animatedChart.getYearIndex(year);
		drawAnimatedChart();
	}
	else if (year < currentYear)
	{
		console.log(currentYear - year);
		document.getElementById('yearsRange').stepDown(currentYear - year);
		animatedChart.index = animatedChart.getYearIndex(year);
		drawAnimatedChart();
	}
	else //if year == currentYear
	{
		// do nothing
	}
}