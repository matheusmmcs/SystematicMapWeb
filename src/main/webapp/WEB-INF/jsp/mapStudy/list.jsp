<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h3 class="color-primary"><fmt:message key="mapstudy.my"/></h3>

<table class="table table-striped table-bordered table-hover">
	<thead>
		<tr>
			<th width="80%" class="text-center"><fmt:message key="mapstudy.title"/></th>
			<th width="20%" class="text-center"><fmt:message key="actions"/></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach var="map" items="${userInfo.user.mapStudys}" varStatus="s">
			<tr>
				<td><a href="${linkTo[MapStudyController].show(map.id)}">${map.title}</a></td>
				<td class="text-center"><a class="btn btn-primary" href="${linkTo[MapStudyController].show(map.id)}"><i class="glyphicon glyphicon-log-in"></i> <fmt:message key="mapstudy.open"/> </a></td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<h3 class="color-primary"><fmt:message key="mapstudy.create"/></h3>

<div class="well">
	<div class="row">
		<form action="${linkTo[MapStudyController].add}" method="post">
			<div class="form-group">
				<label for="title" class="control-label"><fmt:message key="mapstudy.title"/></label>
				<div class="">
					<input type="text" class="form-control" name="mapstudy.title" value="${mapstudy.title}"/>
				</div>	
			</div>
			<div class="form-group">
				<label for="description" class="control-label"><fmt:message key="mapstudy.description"/></label>
				<div class="">
					<input type="text" class="form-control" name="mapstudy.description" value="${mapstudy.description}"/>
				</div>	
			</div>
			<div class="form-group">
				<label for="type" class="control-label"><fmt:message key="mapstudy.members"/></label>
				<div>
					<c:forEach items="${users}" var="user" varStatus="u">  
	                    <input type="checkbox" name="members[${u.index}]" value="${user.id}"></input>  
	                    ${user.name}<br/>  
	                </c:forEach>
				</div>	
			</div>
			<div class="form-group pull-right">	
				<button type="submit" class="btn btn-primary"><fmt:message key="mapstudy.create"/></button>
			</div>	
		</form>
	</div>		
</div>