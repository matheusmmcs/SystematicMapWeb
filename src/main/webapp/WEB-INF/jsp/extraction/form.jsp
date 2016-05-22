<!-- Aqui vai ficar a parte onde o formulário dinâmico é criado -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	$(document).ready(function(){

		//Remove um campo
		var removeField = function (obj){
// 			console.log('remove');
			$(obj).remove();
		};

		// Adiciona questões
		addQuestion = function (divObj){
			time = new Date().getTime();
			fieldContent = '<div class="form-group group_question" id="group_question_'+time+'">' +
				'<div class="row">' +
					'<div class="col-md-6">' +
						'<label for="quest_'+time+'" class=""><fmt:message key="mapstudy.question.name" /></label> <input type="text"' +
							'class="form-control group_question_name" id="quest_'+time+'" name="questions[]"' +
							'placeholder="<fmt:message key="mapstudy.question.name"/>" />' +
					'</div>' +
					'<div class="col-md-3">' +
						'<label for="type_'+time+'" class=""><fmt:message key="mapstudy.question.type" /></label>' + 
						'<select class="form-control selectiontype group_question_type" name="types[]" id="type_'+time+'">' +
							'<c:forEach var="type" items="${questionTypes}">' +
								'<option value="${type}">${type}</option>' +
							'</c:forEach>' +
						'</select>' +
					'</div>' +
					'<div class="col-md-3">' +
						'<label class="" for="btnquestionremove_'+time+'">&nbsp;&nbsp;&nbsp;</label>' +
						'<a href="#" class="btn btn-danger buttonquestionremove form-control" id="btnquestionremove_'+time+'" idquest="group_question_'+time+'">' +
							'<i class="glyphicon glyphicon-remove"></i> <fmt:message key="mapstudy.question.remove" />' +
						'</a>' +
					'</div>' +
				'</div>' +
				'<hr />'	+		
			'</div>';			
			divObj.append(fieldContent);
		};

		var addAlternative = function(divObj, isFirst, questassociation){
			//var objId = "#group_question_" + myid;
			//console.log(divObj);
			//console.log(isFirst);
			//console.log(questassociation);
			time = new Date().getTime();
			var content = '<div class="form_group group_alternative" id="group_alternative_'+time+'">' +
			'<div class="col-md-6">' +
				'<label for="alternative_'+time+'" class="">' +
				'<fmt:message key="mapstudy.alternative.value" /></label> ' +
				'<input type="text" class="form-control group_alternative_name" id="alternative_'+time+'" name="alternatives[].value"' + 
				'placeholder="<fmt:message key="mapstudy.alternative.value"/>" questassociation="'+questassociation+'"/>' +
			'</div>';

			if (isFirst == true){
				content += '<div class="col-md-3">' +
				'<label class="" for="buttonalternativeadd_'+time+'">&nbsp;&nbsp;&nbsp;</label>' +
				'<a href="#" class="btn btn-success buttonalternativeadd form-control" id="buttonalternativeadd_'+time+'" questassociation="'+questassociation+'">' +  
					'<i class="glyphicon glyphicon-plus"></i> <fmt:message key="mapstudy.alternative.add" />' +
				'</a>' +
				'</div>' +
				'</div>';
			}else{
				content +=  '<div class="col-md-1">' +
				'<label class="" for="buttonalternativeremove_'+time+'">&nbsp;&nbsp;&nbsp;</label>' +
				'<a href="#" alternativeid="group_alternative_'+time+'" class="btn btn-danger buttonalternativeremove form-control" id="buttonalternativeremove_'+time+'">' +  
				'<i class="glyphicon glyphicon-remove"></i>' +
// 				<fmt:message key="mapstudy.alternative.remove" />
				'</a>' +
				'</div>' +
				'</div>';
			}	

			$(divObj).append(content);
		};

		// Adiciona questão
		$(document).on('click', '.buttonquestionadd', function(event){
			event.preventDefault();
// 			console.log('addquestion');

			divObj = $('#' + 'allquestions');
			addQuestion(divObj);				
		});

		// Remove questão
		$(document).on('click', '.buttonquestionremove', function(event){
			event.preventDefault();
			var id = $(this).attr('idquest');
// 			console.log('idquest: ', id);
			removeField($('#' + id));
		});

		// Adiciona ALternativa
		$(document).on('click', '.buttonalternativeadd', function(event){
			event.preventDefault();
// 			console.log('addalternative');
			var myid = $(this).attr('questassociation');
			//myid = myid.substring(myid.lastIndexOf('_') + 1);//, myid.length);
			addAlternative($('#group_question_' + myid), false, myid);
		});

		// Remove ALternativa
		$(document).on('click', '.buttonalternativeremove', function(event){
			event.preventDefault();
// 			console.log('addalternative');

			event.preventDefault();
			var id = $(this).attr('alternativeid');
// 			console.log('alternativeid: ', id);

			removeField($('#' + id));			
		});

		

		// Captura seleção de tipo
		$(document).on('change', '.selectiontype', function(){
// 			console.log('type: ', $(this).val());
			if ($(this).val() == 'SIMPLE'){
				// se existir alternatives remover todas
				var myid = $(this).attr('id');
				myid = myid.substring(myid.lastIndexOf('_') + 1, myid.length);
				var objId = "#group_question_" + myid;

				// assim ele vai pegar os group_alternative "filhos" de objId
				$(objId + " .group_alternative").each(function (index) {
					$(this).remove();
				});
				
				
			}else {
				// inserir alternative
				var myid = $(this).attr('id');
				myid = myid.substring(myid.lastIndexOf('_') + 1, myid.length);
				var objId = "#group_question_" + myid;

				if ($(objId).children('.group_alternative').length == 0){
					addAlternative($(objId), true, myid);
				}else{
// 					console.log('ja existe alternative');
				}
	
			}
		});


		//Salvar Formulário
		$(document).on('click', '.buttonextraction', function(event){
			event.preventDefault();
// 			console.log('buttonextraction');

			var questions = [];
			$('.group_question').each(function(idx, elem){
				var $elem = $(elem);
				var question = {};
				question.id = null;
				question.name = $elem.find('.group_question_name').val();
				question.type = $elem.find('.group_question_type').val();
				question.alternatives = [];

// 				console.log(question);
				
				$elem.find('.group_alternative').each(function(idx_a, elem_a) {
					var alternative = {};
					alternative.id = null;
					alternative.value = $(elem_a).find('.group_alternative_name').val();
					if (alternative.value != null && alternative.value != ""){
						question.alternatives.push(alternative);
// 						console.log(alternative);
					}					
				});

				if (question.name != null && question.name != ""){
					questions.push(question);
				}			

// 				console.log('tam: ' + questions.length);	
			});
			
			var address = "${linkTo[ExtractionController].formAjax}";
			var mapid = $('#mapid').val();
// 			var param = {};
			
// 			param.mapid = mapid;
// 			param.questions = questions;

			var questionVO = {
				"mapid" : mapid,
				"questions" : questions
			}; 

			param = { "questionVO" : questionVO};

// 			console.log('param: ', param);
// 			console.log('JSON: ', JSON.stringify(param));
// 			console.log('JQ' + jQuery.parseJSON(JSON.stringify(param)));

			$.ajax({
		        url: address,
		        dataType: 'json',
                contentType: 'application/json; charset=utf-8',
		        type: 'POST',
		        traditional: true,
				data: JSON.stringify(param),
		        success: function (data) {
// 			        console.log(data);
		        },
				error: function(e){
					console.error(e);
				}
			});			
						
		});
			
	});
</script>

<!-- Form Extraction -->

<div  id="divextraction">
	<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.form" /></b>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<h4>
					<fmt:message key="mapstudy.form.add" />
				</h4>
				<form class="form-horizontal" action="${linkTo[ExtractionController].formAjax}" method="post" id="formExtraction">
				<input type="hidden" name="mapid" id="mapid" value="${map.id}" />
				
<!-- 				Aqui fica as questões -->
<div id="allquestions">
		<div class="form-group group_question" id="group_question_inicial">
			<div class="row">
				<div class="col-md-6">
					<label for="quest_inicial" class=""><fmt:message key="mapstudy.question.name" /></label> 
					<input type="text" class="form-control group_question_name" id="quest_inicial" name="questions[]" placeholder="<fmt:message key="mapstudy.question.name"/>" />
				</div>
				<div class="col-md-3">
					<label for="type_inicial" class=""><fmt:message key="mapstudy.question.type" /></label> 
					<select class="form-control selectiontype group_question_type" name="types[]" id="type_inicial">
						<c:forEach var="type" items="${questionTypes}">
							<option value="${type}">${type}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-md-3">
					<label class="" for="buttonquestionadd">&nbsp;&nbsp;&nbsp;</label>
					<a href="#" class="btn btn-success buttonquestionadd form-control" id="buttonquestionadd">  <i class="glyphicon glyphicon-plus"></i> <fmt:message key="mapstudy.question.add" /></a>
				</div>
			</div>			
			<hr />
		</div>	
	
	<c:forEach var="question" items="${questions}" varStatus="q">
		<div class="form-group group_question" id="group_question_${q.index}">
			<div class="row">
				<div class="col-md-6">
					<label for="quest_${q.index}" class=""><fmt:message key="mapstudy.question.name" /></label> 
					<input type="text" class="form-control group_question_name" id="quest_${q.index}" name="questions[]" value="${question.name}" placeholder="<fmt:message key="mapstudy.question.name"/>" />
				</div>
				<div class="col-md-3">
					<label for="type_${q.index}" class=""><fmt:message key="mapstudy.question.type" /></label> 
					<select class="form-control selectiontype group_question_type" name="types[]" id="type_${q.index}">
					<option name="types[]" data-email="" selected="selected" value="${question.type}">${question.type}</option>	
						<c:forEach var="type" items="${questionTypes}">
							<option value="${type}">${type}</option>
						</c:forEach>
					</select>
				</div>

<%-- 				<c:if test="${q.index == 0}"> --%>
<!-- 					<div class="col-md-3"> -->
<!-- 						<label class="" for="buttonquestionadd">&nbsp;&nbsp;&nbsp;</label> -->
<%-- 						<a href="#" class="btn btn-success buttonquestionadd form-control" id="buttonquestionadd">  <i class="glyphicon glyphicon-plus"></i> <fmt:message key="mapstudy.question.add" /></a> --%>
<!-- 					</div> -->
<%-- 				</c:if> --%>
				<c:if test="${q.index != 0}">
					<div class="col-md-3">
						<label class="" for="btnquestionremove_${q.index}">&nbsp;&nbsp;&nbsp;</label>
						<a href="#" class="btn btn-danger buttonquestionremove form-control" id="btnquestionremove_${q.index}" idquest="group_question_${q.index}"> <i class="glyphicon glyphicon-remove"></i> <fmt:message key="mapstudy.question.remove" /> </a>
					</div>
				</c:if>

			</div>			
			<hr />
<!-- 					Alternative Inicio -->
			<c:if test="${question.type == 'LIST'}">
				<c:forEach var="alt" items="${question.alternatives}" varStatus="a">
					<div class="form_group group_alternative" id="group_alternative_${q.index}_${a.index}">
						<div class="col-md-6">
							<label for="alternative_${q.index}_${a.index}" class=""> <fmt:message key="mapstudy.alternative.value" /></label>
							<input type="text" class="form-control group_alternative_name" id="alternative_${q.index}_${a.index}" value="${alt.value}" name="alternatives[].value" placeholder="<fmt:message key="mapstudy.alternative.value"/>"questassociation="${q.index}"/>
						</div>
					<c:if test="${a.index == 0}">
						<div class="col-md-3">
							<label class="" for="buttonalternativeadd_${q.index}_${a.index}">&nbsp;&nbsp;&nbsp;</label>
							<a href="#" class="btn btn-success buttonalternativeadd form-control" id="buttonalternativeadd_${q.index}_${a.index}" questassociation="${q.index}"> <i class="glyphicon glyphicon-plus"></i> <fmt:message key="mapstudy.alternative.add" /></a>
						</div>
					</c:if>
					<c:if test="${a.index != 0}">
						<div class="col-md-1">
							<label class="" for="buttonalternativeremove_${q.index}_${a.index}">&nbsp;&nbsp;&nbsp;</label>
							<a href="#" alternativeid="group_alternative_${q.index}_${a.index}" class="btn btn-danger buttonalternativeremove form-control" id="buttonalternativeremove_${q.index}_${a.index}"> <i class="glyphicon glyphicon-remove"></i>	<!-- <fmt:message key="mapstudy.alternative.remove" />--></a>
						</div>
					</c:if>

					</div>
				</c:forEach>

			</c:if>

		</div>

	</c:forEach>
				
</div>
<!-- Fim das questões -->
				
				<div class="form-group">
						<button type="submit" id="buttonextraction" class="btn btn-large btn-primary buttonextraction">
							<fmt:message key="salve" />
						</button>
					</div>
					<div class="clear-both"></div>
				</form>
			</div>
		</div>
</div>