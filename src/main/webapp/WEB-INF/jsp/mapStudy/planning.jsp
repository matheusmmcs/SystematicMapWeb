<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<ol class="breadcrumb u-margin-top">
  <li><a href="<c:url value="/" />"><fmt:message key="home"/></a></li>
  <li><a href="${linkTo[MapStudyController].show(map.id)}"><fmt:message key="mapstudy.details"/></a></li>
  <li class="active"><fmt:message key="mapstudy.planning"/></li>
</ol>

<h3 class="color-primary">
	<fmt:message key="mapstudy" />	- ${map.title}
	<a id="return" class="btn btn-default pull-right" href="${linkTo[MapStudyController].show(map.id)}"><fmt:message key="button.back"/></a>
</h3>

<div class="row">
<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.inclusion.criterias" /></b>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<h4>
					<fmt:message key="mapstudy.inclusion.criteria.add" />
				</h4>
				<form action="${linkTo[MapStudyController].addinclusion}"
					method="post">
					<input type="hidden" name="id" value="${map.id}" />
					<div class="form-group">
						<div class="col-lg-9 padding-left-none">
							<input type="text" class="form-control" id="incdescription"
								name="criteria.description"
								placeholder="<fmt:message key="mapstudy.inclusion.criteria"/>" />
						</div>
						<button type="submit" id="submit"
							class="btn btn-large btn-primary col-lg-3 float-right">
							<fmt:message key="add" />
						</button>
						<div class="clear-both"></div>
					</div>
				</form>

				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th><fmt:message key="mapstudy.criteria.description" /></th>
								<th><fmt:message key="remove" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="criteria" items="${map.inclusionCriterias}"
								varStatus="s">
								<tr>
									<td>${criteria.description}</td>
									<td class="text-center"><a class="btn btn-danger" href="${linkTo[MapStudyController].removeinclusioncriteriapage(map.id, criteria.id)}"><i
											class="glyphicon glyphicon-remove"></i></a></td>
								</tr>								
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<div class="panel panel-default">
			<div class="panel-heading">
				<b><fmt:message key="mapstudy.exclusion.criterias" /></b>
			</div>
			<!-- /.panel-heading -->
			<div class="panel-body">
				<h4>
					<fmt:message key="mapstudy.exclusion.criteria.add" />
				</h4>
				<form action="${linkTo[MapStudyController].addexclusion}"
					method="post">
					<input type="hidden" name="id" value="${map.id}" />
					<div class="form-group">
						<div class="col-lg-9 padding-left-none">
							<input type="text" class="form-control" id="excdescription"	name="criteria.description"
								placeholder="<fmt:message key="mapstudy.exclusion.criteria"/>" />
						</div>
						<button type="submit" id="submit" class="btn btn-large btn-primary col-lg-3 float-right">
							<fmt:message key="add" />
						</button>
						<div class="clear-both"></div>
					</div>
				</form>

				<div class="table-responsive">
					<table class="table table-striped table-bordered table-hover">
						<thead>
							<tr>
								<th><fmt:message key="mapstudy.criteria.description" /></th>
								<th><fmt:message key="remove" /></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="criteria" items="${map.exclusionCriterias}"
								varStatus="s">
								<tr>
									<td>${criteria.description}</td>
									<td class="text-center"><a class="btn btn-danger" href="${linkTo[MapStudyController].removeexclusioncriteriapage(map.id, criteria.id)}"><i
											class="glyphicon glyphicon-remove"></i></a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
