<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="<c:url value="/vendor/bootstrap/bootstrap-social.css"/>" rel="stylesheet" type="text/css">

<meta name="decorator" content="login" />

<div class="row">
	<div class="col-md-4 col-md-offset-4">

			<c:if test="${not empty errors}">
				<div class="alert alert-danger">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<c:forEach items="${errors}" var="error">
						<b><fmt:message key="${error.category}" /></b> - <fmt:message
							key="${error.message}" />
						<br />
					</c:forEach>
				</div>
			</c:if>

			<c:if test="${not empty messages.info}">
				<div class="alert alert-info">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<c:forEach items="${messages.info}" var="info">
						<fmt:message key="${info.message}" /><br />
					</c:forEach>
				</div>
			</c:if>

			<c:if test="${not empty messages.warnings}">
				<div class="alert alert-warning">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
					<c:forEach items="${messages.warnings}" var="warning">
						<b><fmt:message key="${warning.category}" /></b> - <fmt:message
							key="${warning.message}" />
						<br />
					</c:forEach>
				</div>
			</c:if>

		<div class="login-panel panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">
					<fmt:message key="signin" />
				</h3>
			</div>
			<div class="panel-body">
				<form action="${linkTo[HomeController].login}" method="post" class="form-horizontal" id="formLogin">
					<fieldset>
						<div class="form-group">
							<label class="sr-only" for="login"><fmt:message	key="user.login" /></label> 
							<input type="text" class="form-control"	id="login" name="login"	placeholder="<fmt:message key="user.login"/>" />
						</div>
						<div class="form-group">
							<label class="sr-only" for="password"><fmt:message key="user.password" /></label> 
							<input type="password" class="form-control" name="password" placeholder="<fmt:message key="user.password"/>" />
						</div>
						<!-- Change this to a button or input when using this as a form -->
						<button type="submit" id="submit" class="btn btn-success btn-lg btn-block">
							<fmt:message key="access" />
						</button>
<!-- 						<a class="btn btn-primary btn-lg" -->
<%-- 							href="<c:url value="/home/create"/>"><fmt:message --%>
<%-- 								key="create" /></a> --%>
					</fieldset>
				</form>

			</div>
			<a class="btn btn-lg btn-link" href="<c:url value="/home/create"/>" style="text-align: left;"><fmt:message key="signup" /></a>
			<a class="btn btn-lg btn-link" href="<c:url value="/home/recovery"/>" style="text-align: right;"><fmt:message key="recovery.password" /></a>
		</div>
		<center>
				<a class="btn btn-lg btn-social btn-google-plus" href="#"><i class="fa fa-google"></i> Google</a> 
				<a class="btn btn-lg btn-social btn-facebook" href="#"><i class="fa fa-facebook"></i> Facebook</a>	
		</center>
	</div>
</div>


