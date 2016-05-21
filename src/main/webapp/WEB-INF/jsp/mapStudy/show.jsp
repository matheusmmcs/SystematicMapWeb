<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
$(document).ready(function(){
	$('.progress-bar').each(function(idx){
		var percent = $(this).attr("style");
		$(this).attr('style', percent.replace(",", "."));
	});
}); 
</script>

<ol class="breadcrumb u-margin-top">
  <li><a href="<c:url value="/" />"><fmt:message key="home"/></a></li>
  <li class="active"><fmt:message key="mapstudy.details"/></li>
</ol>

<h3 class="color-primary">
	<fmt:message key="mapstudy" />
	- ${map.title}
	<a id="return" class="btn btn-default pull-right" href="<c:url value="/" />"><fmt:message key="button.back"/></a>
</h3>

<div class="row">
	<div class="col-md-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.details" /></b>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<p><strong><fmt:message key="mapstudy.title"/>:</strong> ${map.title}</p>
				<p><strong><fmt:message key="mapstudy.description"/>:</strong> ${map.description}</p>
				<div class="row">
					<div class="col-md-6">
						<p><strong><fmt:message key="mapstudy.evaluation.selection.rate" />:</strong></p>
						<div class="progress">
							<div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="${percentEvaluated}" aria-valuemin="0" aria-valuemax="100"
								style="min-width: 3em; width: ${percentEvaluated}%"> ${percentEvaluated}%
							</div>
						</div>
					</div>
					<div class="col-md-6">
						<p><strong><fmt:message key="mapstudy.extraction.rate" />:</strong></p>
						<div class="progress">
							<div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="${percentExtracted}" aria-valuemin="0" aria-valuemax="100"
								style="min-width: 3em; width: ${percentExtracted}%"> ${percentExtracted}%
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<a style="width: 24.5%;" class="btn btn-default" href="${linkTo[MapStudyController].planning(map.id)}">
							<span class="glyphicon glyphicon-pencil"></span>
							<fmt:message key="mapstudy.planning"/>
						</a>
						<a style="width: 24.5%;" class="btn btn-default" href="${linkTo[MapStudyController].identification(map.id)}">
							<span class="glyphicon glyphicon-search"></span>
							<fmt:message key="mapstudy.searching"/>
						</a>
						<a style="width: 24.5%;" class="btn btn-default" href="${linkTo[MapStudyController].evaluate(map.id)}">
							<span class="glyphicon glyphicon-ok"></span>
							<fmt:message key="mapstudy.screening" />
						</a>
						<a style="width: 24.5%;" class="btn btn-default" href="${linkTo[MapStudyController].showEvaluates(map.id)}">
							<span class="glyphicon glyphicon-list-alt"></span>
							<fmt:message key="mapstudy.viewarticles" />
						</a>
					</div>
				</div>
				<div class="row" style="margin-top: 5px;">
					<div class="col-md-12">
						<a style="width: 24.5%;" class="btn btn-default" href="${linkTo[ExtractionController].extraction(map.id)}">
							<span class="glyphicon glyphicon-cog"></span>
							<fmt:message key="mapstudy.extraction" />
						</a>
						<a style="width: 24.5%;" class="btn btn-default" href="${linkTo[ExtractionController].showExtractionEvaluates(map.id)}">
							<span class="glyphicon glyphicon-th-list"></span>
							<fmt:message key="mapstudy.viewextractions" />
						</a>
						<a style="width: 24.5%;" class="btn btn-default" href="${linkTo[MapStudyController].report(map.id)}">
							<span class="glyphicon glyphicon-tasks"></span>
							<fmt:message key="mapstudy.report" />
						</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="row">
	<div class="col-md-12">
		<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.members" /></b>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<h4>
					<fmt:message key="mapstudy.members.add" />
				</h4>
				<form action="${linkTo[MapStudyController].addmember}" method="post">
					<input type="hidden" name="id" value="${map.id}" />
	
					<div class="form-group">
						<div class="col-lg-9 padding-left-none">
							<select data-placeholder="<fmt:message key="mapstudy.members.choose" />" class="form-control select2" name="userId" tabindex="2">
								<c:forEach var="member" items="${mapStudyArentUsers}">
									<option value="${member.id}" data-email="${member.email}">${member.name}</option>
								</c:forEach>
							</select>
						</div>
						<button type="submit" id="submit"
							class="btn btn-large btn-primary col-lg-3 float-right">
							<fmt:message key="add" />
						</button>
						<div class="clear-both"></div>
					</div>
					<div class="checkbox">
						<label> <input type="checkbox" name="notify"> <fmt:message
								key="notify.member" />
						</label>
					</div>
				</form>
	
	
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th class="text-center">#</th>
								<th>Participante</th>
								<th class="text-center"><fmt:message key="mapstudy.evaluations.percentconclusion" /></th>
								<th class="text-center"><fmt:message key="mapstudy.extraction.percentconclusion" /></th>
								<c:if test="${map.isCreator(userInfo.user)}">
									<th class="text-center"><fmt:message key="remove" /></th>
								</c:if>
	
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${mapStudyUsers}" var="member" varStatus="s">
								<tr>
									<td class="text-center">${s.index + 1}</td>
									<td>${member.key.name}</td>
									<td class="text-center">${member.value.selection}</td>
									<td class="text-center">${member.value.extraction}</td>
									<c:if test="${map.isCreator(userInfo.user)}">
										<c:choose>
											<c:when test="${!map.isCreator(member.key)}">
												<td class="text-center"><a class="btn btn-danger confirmation-modal" data-conf-modal-body="<fmt:message key="member.excluir.message" />"
													href="${linkTo[MapStudyController].removemember(map.id, member.key.id)}"><i
														class="glyphicon glyphicon-remove"></i></a></td>
											</c:when>
											<c:otherwise>
												<td></td>
											</c:otherwise>
										</c:choose>
									</c:if>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</div>
