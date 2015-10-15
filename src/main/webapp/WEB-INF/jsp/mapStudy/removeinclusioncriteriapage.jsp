<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h3 class="color-primary"><fmt:message key="mapstudy.inclusion.criteria.remove"/> - ${criteria.description}</h3>

<div class="row">
	<div class="col-lg-12">
	
		<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.inclusion.criteria"/></b>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<p> 
					<strong>
						<fmt:message key="mapstudy.criteria.description"/>:
					</strong> ${criteria.description}
				<p>
				
				<p> 
					<strong>
						<fmt:message key="mapstudy.criteria.evaluations"/>:
					</strong> ${criteria.evaluations.size()}
				<p>
				
				<p>
					<strong>
						<fmt:message key="mapstudy.inclusion.criteria.remove"/>:
					</strong> 
					
					<form action="${linkTo[MapStudyController].removeinclusioncriteria}" method="post">
						<input type="hidden" name="studyMapId" value="${criteria.mapStudy.id}" />
						<input type="hidden" name="criteriaId" value="${criteria.id}" />
						<button type="submit" id="submit" class="btn btn-large btn-danger float-right">
							<fmt:message key="remove"/>
						</button>
						<div class="clear-both"></div>
					</form>
				<p>
			</div>
		</div>
		
		<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.criteria.evaluations"/></b>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<h4>
					<fmt:message key="mapstudy.criteria.evaluations.remove" />
				</h4>
				
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th><fmt:message key="id"/></th>
								<th><fmt:message key="user"/></th>
								<th><fmt:message key="mapstudy.article"/></th>
								<th><fmt:message key="mapstudy.inclusion.criterias" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${evaluationsRemoved}" var="ev" varStatus="s">
								<tr>
									<td>
										${ev.article.id}
									</td>
									<td>
										${ev.user.name}
									</td>
									<td>
										<a href="${linkTo[MapStudyController].evaluateArticle(criteria.mapStudy.id, ev.article.id) }">
											${ev.article.title}
										</a>										
									</td>
									<td>
										${ev.inclusionCriterias.size()}
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				
				<hr/>
				
				<h4>
					<fmt:message key="mapstudy.criteria.evaluations.contains" />
				</h4>
				
				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th><fmt:message key="id"/></th>
								<th><fmt:message key="user"/></th>
								<th><fmt:message key="mapstudy.article"/></th>
								<th><fmt:message key="mapstudy.inclusion.criterias" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${evaluationsImpacted}" var="ev" varStatus="s">
								<tr>
									<td>
										${ev.article.id}
									</td>
									<td>
										${ev.user.name}
									</td>
									<td>
										<a href="${linkTo[MapStudyController].evaluateArticle(criteria.mapStudy.id, ev.article.id) }">
											${ev.article.title}
										</a>										
									</td>
									<td>
										${ev.inclusionCriterias.size()}
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				
			</div>
		</div>
	</div>
</div>