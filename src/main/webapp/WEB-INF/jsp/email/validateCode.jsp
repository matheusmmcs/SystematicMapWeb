<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<meta name="decorator" content="login"/>

<div class="row">
	<div class="col-md-4 col-md-offset-4">

		<c:if test="${not empty errors}">
			<div class="alert alert-danger">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<c:forEach items="${errors}" var="error">
					<b><fmt:message key="${error.category}" /></b> - <fmt:message key="${error.message}" />
					<br />
				</c:forEach>
			</div>
		</c:if>
		<c:if test="${not empty notice}">
				<div class="alert alert-success">
					<button type="button" class="close" data-dismiss="alert">&times;</button>
						<b>${notice}</b>					
				</div>
			</c:if>
		<div class="login-panel panel panel-default">
		    <div class="panel-heading">
		        <h3 class="panel-title"><fmt:message key="recovery.password"/></h3>
		    </div>
		    <div class="panel-body">
		        <form action="${linkTo[EmailController].newPassword}" method="post" class="form-horizontal">
		            <fieldset>
						<div class="form-group">
							<label class="sr-only" for="password"><fmt:message key="recovery.chance.password"/></label>
							<input type="password" class="form-control" id="password" name="password" placeholder="<fmt:message key="user.password"/>" />	
							<input type="hidden" class="form-control" id="code" name="code" value="${code}"/>
						</div>
						
						<div class="form-group">
							<label class="sr-only" for="repassword"><fmt:message key="recovery.chance.password"/></label>
							<input type="password" class="form-control" id="repassword" name="repassword" placeholder="<fmt:message key="recovery.repassword"/>" />
						</div>

		                <!-- Change this to a button or input when using this as a form -->
		                <button type="submit" id="submit" class="btn btn-lg btn-primary btn-block">
							<fmt:message key="salve"/>
						</button>
		              </fieldset>
		          </form>
		      </div>
		  </div>
    </div>
</div>