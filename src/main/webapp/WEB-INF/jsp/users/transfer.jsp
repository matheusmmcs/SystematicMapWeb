<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="gravatar" uri="http://www.paalgyula.hu/schemas/tld/gravatar" %>

<h3 class="color-primary">
	${mapStudy.title}
</h3>

<h3 class="color-primary">
	<fmt:message key="welcome" /><b> ${userInfo.user.name }</b>
</h3>

<hr/>

<div class="row">
	<div class="col-md-12">
	<div class="panel panel-default">
			<div class="panel-heading">
				<b>Transferência de Avaliações</b>
			</div>
			<div class="panel-body">
<!-- 				<div class="form-group"> -->
					<form action="${linkTo[UsersController].transferEvaluate }" method="post" >
					<div class="row">
						<input type="hidden" value="${mapStudy.id }" name="mapid">
						<div class="col-md-5">
							<label for="userIdOne" class="">Tranferir de: </label> 									
							<select class="form-control select-quest" name="userIdOne" id="userIdOne">
								<option selected value="-1">Selecione um usuário</option>
								<c:forEach var="q" items="${users}">
									<option value="${q.id}">${q.name}</option>
								</c:forEach>
							</select>
						</div>
						
						<div class="col-md-5">
							<label for="userIdTwo" class="">Para: </label> 
							<select class="form-control select-quest" name="userIdTwo" id="userIdTwo">
								<option selected value="-1">Selecione um usuário</option>
								<c:forEach var="q" items="${users}">
									<option value="${q.id}">${q.name}</option>
								</c:forEach>
							</select>
						</div>
					</div>		
					
					<div class="" style="padding: 8px;">
					</div>
					
					
					<div class="row">
					<div class="col-md-4">
					</div>
						<div class="col-md-4">
							<button type="submit" class="btn btn-primary btn-sm">Transferir</button>
						</div>
						<div class="col-md-4">
					</div>
					</div>				
													
					</form>
<!-- 				</div>				 -->
			</div>
		</div>
	</div>
</div>
