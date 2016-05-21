<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
	(function($){
		$(document).ready(function(){
			$("#formAddMapStudy").validate({ 
                 rules: {
                	 'mapstudy.title': { 
                    	 required : true,
                    	 minlength : 3
                     },
                     'mapstudy.description': {
                         required: true,
                         minlength : 3
                     }
                  }, messages: {
                	  'mapstudy.title': {
                          required: '<fmt:message key="required" />',
                          minlength: '<fmt:message key="mapstudy.min.title" />'
                      },
                      'mapstudy.description': {
                          required: '<fmt:message key="required" />',
                          minlength: '<fmt:message key="mapstudy.min.description" />'
                      }
                  }
			});
		});
	})(jQuery);
</script>

<c:if test="${not empty mapStudys}">
<h3 class="color-primary"><fmt:message key="mapstudy.my"/></h3>

<table class="table table-striped table-bordered table-hover personalized-table-simple">
	<thead>
		<tr>
			<th style="width: 70%" class=""><fmt:message key="mapstudy.title"/></th>
			<th style="width: 30%" class="text-center"><fmt:message key="actions"/></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="map" items="${mapStudys}" varStatus="s">
			<tr>
				<td style="vertical-align: middle;"><a href="${linkTo[MapStudyController].show(map.id)}">${map.title}</a></td>
				<td class="text-center">
					<a class="btn btn-primary btn-sm listWidthButton" href="${linkTo[MapStudyController].show(map.id)}"><i class="glyphicon glyphicon-log-in"></i> <fmt:message key="open"/> </a>
					<c:if test="${map.isCreator(userInfo.user)}">
						<a class="btn btn-danger confirmation-modal btn-sm listWidthButton" data-conf-modal-body="<fmt:message key="mapstudy.excluir.message" />" href="${linkTo[MapStudyController].remove(map.id)}" ><i class="glyphicon glyphicon-remove"></i> <fmt:message key="remove"/> </a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</c:if>

<h3 class="color-primary"><fmt:message key="mapstudy.create"/></h3>


<div class="row">
	<div class="col-md-8">
		<div class="well">
			<form action="${linkTo[MapStudyController].add}" method="post" id="formAddMapStudy">
				<div class="form-group">
					<label for="title" class=""><fmt:message key="mapstudy.title"/></label>
					<input type="text" class="form-control" name="mapstudy.title" id="title" value="${mapstudy.title}"/>
				</div>
				<div class="form-group">
					<label for="description" class=""><fmt:message key="mapstudy.description"/></label>
					<textarea class="form-control" rows="3" name="mapstudy.description" id="description" value="${mapstudy.description}"></textarea>
				</div>
				<div class="form-group pull-right">	
					<button type="submit" class="btn btn-primary"><fmt:message key="mapstudy.create"/></button>
				</div>	
			</form>
		</div>
	</div>		
</div>