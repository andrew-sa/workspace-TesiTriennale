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