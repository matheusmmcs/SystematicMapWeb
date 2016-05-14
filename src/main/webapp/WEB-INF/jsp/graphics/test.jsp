<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator"	prefix="decorator"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Testes de graficos</title>


</head>
<body>
<script src="<c:url value="/vendor/highcharts/highcharts.js" />"></script>
<script src="<c:url value="/vendor/highcharts/exporting.js" />"></script>

<button class="btn btn-primary btnGraph">Avaliações</button>
<button class="btn btn-primary btnGraph2">Bases de Dados</button>
<button class="btn btn-primary btnGraph3">Artigos refinados</button>

<div class="graph" id="container"> </div><!--style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div> -->
<div class="graph" id="container2"></div><!--style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div> -->
<div class="graph" id="container3"></div><!--style="min-width: 310px; height: 400px; max-width: 600px; margin: 0 auto"></div> -->

Teste

<script type="text/javascript">
$(document).ready(function(){
		var mapid = 2;//$('#mapid').val();

		var graphView = function (sources, id){
			// Build the chart
	        $('#'+id).highcharts({
	            chart: {
	                plotBackgroundColor: null,
	                plotBorderWidth: null,
	                plotShadow: false,
	                type: 'pie'
	            },
	            title: {
	                text: sources.title
	            },
// 	            tooltip: {
// 	                //pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
// 	            	pointFormat: '<b>{point.percentage:.1f}%</b>'
// 	            },
tooltip: {
            headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
            pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.percent:.2f}%</b> of total<br/>'
        },
	            plotOptions: {
	            	pie: {
	                    allowPointSelect: true,
	                    cursor: 'pointer',
	                    dataLabels: {
	                        enabled: true,
	                        format: '{point.name}: {point.y}'
	                    },
	                    showInLegend: true
	                }
	            },
	            series: [{
	                name: sources.name,
	                colorByPoint: sources.colorByPoint,
	                data: sources.data,
	                drilldown: sources.data.drilldown
	            }]
	        });
		};

		$(document).on('click', '.btnGraph', function(event){
			$.getJSON('${linkTo[GraphicsController].articlesEvaluates(2)}', function(sources) {
				graphView(sources, 'container2');			
			});
		});

		$(document).on('click', '.btnGraph2', function(event){
			$.getJSON('${linkTo[GraphicsController].articlesSources(1)}', function(sources) {
				graphView(sources, 'container');			
			});
		});		

		$(document).on('click', '.btnGraph3', function(event){
			$.getJSON('${linkTo[GraphicsController].articlesRefine(1)}', function(sources) {
				graphView(sources, 'container3');			
			});
		});	
	})
</script>

</body>
</html>