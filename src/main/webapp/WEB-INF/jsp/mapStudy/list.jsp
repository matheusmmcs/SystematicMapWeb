<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty userInfo.user.mapStudys}">
<h3 class="color-primary"><fmt:message key="mapstudy.my"/></h3>

<table class="table table-striped table-bordered table-hover personalized-table-simple">
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
				<td>
				<a class="btn btn-primary" href="${linkTo[MapStudyController].show(map.id)}"><i class="glyphicon glyphicon-log-in"></i> <fmt:message key="open"/> </a>
				<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#excluirMapeamentoModal"><i class="glyphicon glyphicon-remove"></i> <fmt:message key="remove"/> </button>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</c:if>

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
			<div class="form-group pull-right">	
				<button type="submit" class="btn btn-primary"><fmt:message key="mapstudy.create"/></button>
			</div>	
		</form>
	</div>		
</div>

					<!-- Modal -->
<!-- <div class="modal fade" id="excluirMapeamentoModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"> -->
<!--   <div class="modal-dialog" role="document"> -->
<!--     <div class="modal-content"> -->
<!--       <div class="modal-header"> -->
<!--         <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button> -->
<%--         <h4 class="modal-title" id="myModalLabel"><fmt:message key="mapstudy.remove"/></h4> --%>
<!--       </div> -->
<!--       <div class="modal-body"> -->
<%--         <fmt:message key="mapstudy.remove.message"/> --%>
<!--       </div> -->
<!--       <div class="modal-footer"> -->
<%--         <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="no"/></button> --%>
<%--         <form action="${linkTo[MapStudyController].excluir}" method="post"> --%>
<%--         <input type="hidden" name="id" value="${map.id}" /> --%>
<%--         	<button type="button" class="btn btn-primary" ><fmt:message key="yes"/></button> --%>
<!--         </form> -->
<!--       </div> -->
<!--     </div> -->
<!--   </div> -->
<!-- </div>	 -->
