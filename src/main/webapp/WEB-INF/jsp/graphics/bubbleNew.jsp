<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script src="<c:url value="/vendor/highcharts/highcharts.js" />"></script>
<script src="<c:url value="/vendor/highcharts/exporting.js" />"></script>
<script src="<c:url value="/vendor/dimplejs/d3.v3.min.js" />"></script>
<script src="<c:url value="/vendor/dimplejs/dimple.v2.1.6.min.js" />"></script>

<style>
svg {
	margin-left: auto;
	margin-right: auto;
	display: block;
}
</style>


<script type="text/javascript">
	$(document).ready(
			function() {
				var bubbleData = function(mapid, q1, q2) {
					var address = "${linkTo[GraphicsController].bubble}";
					var param = {
						"mapid" : mapid,
						"q1" : q1,
						"q2" : q2
					};
					$.ajax({
						url : address,
						type : 'GET',
						data : param,
						success : function(data) {
							/*var data = [
								{"q1": "Tipo", "q2": "Plataforma", "Tipo": "Framework", "Plataforma": "Mobile", "qnt": 3},
							    {"q1": "Tipo", "q2": "Plataforma", "Tipo": "Framework", "Plataforma": "Web", "qnt": 5},
							    {"q1": "Tipo", "q2": "Plataforma", "Tipo": "Framework", "Plataforma":"Desktop", "qnt": 6 },
							    {"q1": "Tipo", "q2": "Plataforma", "Tipo": "Ferramenta", "Plataforma": "Mobile", "qnt": 4},
							    {"q1": "Tipo", "q2": "Plataforma", "Tipo": "Ferramenta", "Plataforma": "Web", "qnt": 9},
							    {"q1": "Tipo", "q2": "Plataforma", "Tipo": "Ferramenta", "Plataforma":"Desktop", "qnt": 6 }];*/

							if (data && data.length > 0) {
								// 			        	console.log(data);
								data = JSON.parse(data);
								$('#bubbleGraph').html('');
								var svg = dimple.newSvg("#bubbleGraph", 800, 600);
								var myChart = new dimple.chart(svg, data);
								myChart.width = 1200;

								myChart.setBounds(95, 25, 475, 335)
								myChart.addCategoryAxis("x", data[0].q1);
								myChart.addCategoryAxis("y", data[0].q2);

								var z = myChart.addMeasureAxis("z", "qnt");
								var s = myChart.addSeries(data[0].q2,
										dimple.plot.bubble);
								s.aggregate = dimple.aggregateMethod.max;

								myChart.addLegend(240, 10, 330, 20, "right");
								myChart.draw();
							}
						},
						error : function(e) {
							console.error(e);
						}
					});
				};

// 				$(document).on('click', '.buttonbubblenewguia',
// 						function(event) {
// 							event.preventDefault();
// 							var q1 = $('#question_x').val();
// 							var q2 = $('#question_y').val();
// 							var mapid = $('#mapid').html();

// 							// 			console.log(q1, q2, mapid);		
// 							bubbleData(mapid, q1, q2);

// 							window.open("http://www.jqueryfaqs.com");
// 						});

				// 				$(document).on('click', '.buttonbubble', function(event) {
				// 					event.preventDefault();
				// 					var q1 = $('#question_x').val();
				// 					var q2 = $('#question_y').val();
				// 					var mapid = $('#mapid').html();

				// 					// 			console.log(q1, q2, mapid);		
				// 					bubbleData(mapid, q1, q2);
				// 				});

				bubbleData($('#mapid').val(), $('#q1').val(), $('#q2').val());
			});
</script>

<input type="hidden" class="form-control" name="mapid" id="mapid" value="${mapid}"/>
<input type="hidden" class="form-control" name="q1" id="q1" value="${q1}"/>
<input type="hidden" class="form-control" name="q2" id="q2" value="${q2}"/>

<div class="row">
	<div class="col-lg-12">
		<div id="bubbleGraph"></div>
	</div>
</div>