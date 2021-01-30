// Load google charts
google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawChart);

// Draw the chart and set the chart values
function drawChart() {
var data = google.visualization.arrayToDataTable([
['Task', 'Hours per Day'],
['Work', 8],
['Eat', 2],
['TV', 4],
['Gym', 2],
['Sleep', 8]
]);

// Optional; add a title and set the width and height of the chart
var options = {'title':'My Average Day', 'width':550, 'height':400};

// Display the chart inside the <div> element with id="piechart"
var chart = new google.visualization.PieChart(document.getElementById('piechart'));
chart.draw(data, options);
}

// 	var data = [
	// 		{x: "White", value: 223553265},
	// 		{x: "Black or African American", value: 38929319},
	// 		{x: "American Indian and Alaska Native", value: 2932248},
	// 		{x: "Asian", value: 14674252},
	// 		{x: "Native Hawaiian and Other Pacific Islander", value: 540013},
	// 		{x: "Some Other Race", value: 19107368},
	// 		{x: "Two or More Races", value: 9009073}
	// 	];

	// 	var chart = anychart.pie();

	// 	// set the chart title
	// 	chart.title("Population by Race for the United States: 2010 Census");

	// 	// add the data
	// 	chart.data(data);
	// 	// chart.legend().position("right");
	// //	chart.fill("aquastyle");
	
	// 	// // set items layout
	// 	// chart.legend().itemsLayout("vertical");
	// 	// display the chart in the container
	// 	chart.container('container1');
	// 	chart.draw();
	// 	var chart1 = anychart.pie();

	// 	// set the chart title
	// 	chart1.title("Population by Race for the United States: 2010 Census");

	// 	// add the data
	// 	chart1.data(data);
	// 	// chart1.legend().position("right");
	// 	// // set items layout
	// 	// chart1.legend().itemsLayout("vertical");
	// 	// display the chart1 in the container
	// 	chart1.radius("60%")
	// 	chart1.container('container2');
	// 	chart1.draw();
	// 	// var chart2 = anychart.pie();

	// 	// // set the chart title
	// 	// chart2.title("Population by Race for the United States: 2010 Census");

	// 	// // add the data
	// 	// chart2.data(data);
	// 	// // chart2.legend().position("right");
	// 	// // // set items layout
	// 	// // chart2.legend().itemsLayout("vertical");
	// 	// // display the chart2 in the container
	// 	// chart2.container('container3');
	// 	// chart2.draw();