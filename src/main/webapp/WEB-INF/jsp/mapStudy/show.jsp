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
	<div class="panel panel-default">
		<div class="panel-heading">
			<b><fmt:message key="mapstudy.details" /></b>
		</div>
		<!-- /.panel-heading -->
		<div class="panel-body">
			<p>
				<strong> <fmt:message key="mapstudy.title" />:
				</strong> ${map.title}
			<p>
			<p>
				<strong> <fmt:message key="mapstudy.description" />:
				</strong> ${map.description}
			<p>
			<p>
				<strong> <fmt:message key="mapstudy.evaluation.selection.rate" />:</strong>
					<div class="progress">
						<div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="${percentEvaluated}" aria-valuemin="0" aria-valuemax="100"
							style="min-width: 3em; width: ${percentEvaluated}%"> ${percentEvaluated}%
						</div>
					</div>
					<div class="clear-both"></div>
					<strong> <fmt:message key="mapstudy.extraction.rate" />:</strong>
					<div class="progress">
						<div class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="${percentExtracted}" aria-valuemin="0" aria-valuemax="100"
							style="min-width: 3em; width: ${percentExtracted}%"> ${percentExtracted}%
						</div>
					</div>
					<div class="clear-both"></div>

						<a class="btn btn-primary" href="${linkTo[MapStudyController].planning(map.id)}">
							<fmt:message key="mapstudy.planning"/>
						</a>
						<a class="btn btn-primary" href="${linkTo[MapStudyController].identification(map.id)}">
							<fmt:message key="mapstudy.searching"/>
						</a>
						<a class="btn btn-primary" href="${linkTo[MapStudyController].evaluate(map.id)}">
							<fmt:message key="mapstudy.screening" />
						</a>
						<a class="btn btn-primary" href="${linkTo[MapStudyController].showEvaluates(map.id)}">
							<fmt:message key="mapstudy.viewarticles" />
						</a>
						<a class="btn btn-primary" href="${linkTo[ExtractionController].extraction(map.id)}">
							<fmt:message key="mapstudy.extraction" />
						</a>
						<a class="btn btn-primary" href="${linkTo[ExtractionController].showExtractionEvaluates(map.id)}">
							<fmt:message key="mapstudy.viewextractions" />
						</a>
		</div>
	</div>
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
							<th>#</th>
							<th><fmt:message key="mapstudy.members" /></th>
							<th><fmt:message key="mapstudy.evaluations.percentconclusion" /></th>
							<th><fmt:message key="mapstudy.extraction.percentconclusion" /></th>
							<c:if test="${map.isCreator(userInfo.user)}">
								<th><fmt:message key="remove" /></th>
							</c:if>

						</tr>
					</thead>
					<tbody>
						<c:forEach items="${mapStudyUsers}" var="member" varStatus="s">
							<tr>
								<td>${s.index + 1}</td>
								<td>${member.key.name}</td>
								<td>${member.value.selection}</td>
								<td>${member.value.extraction}</td>
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
	<p>
	<p>
</div>