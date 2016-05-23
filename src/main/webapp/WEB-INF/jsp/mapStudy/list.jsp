<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<ol class="breadcrumb u-margin-top" style="margin-top: 0px;">
  <li><a href="<c:url value="/" />"><fmt:message key="home"/></a></li>
  <li class="active"><fmt:message key="mapstudy.short.list"/></li>
</ol>

<h3 class="color-primary" style="margin-top: 0px;"><fmt:message key="mapstudy.my"/></h3>
<hr/>

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