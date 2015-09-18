<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<meta name="decorator" content="login"/>

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
						<b><fmt:message key="${info.category}" /></b> - <fmt:message
							key="${info.message}" />
						<br />
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
		        <h3 class="panel-title"><fmt:message key="recovery.password"/></h3>
		    </div>
		    <div class="panel-body">
<%-- 		     --%>
		        <form action="${linkTo[EmailController].recoveryPassword}" method="post" class="form-horizontal" autocomplete="off">
		            <fieldset>
						<div class="form-group">
							<label class="sr-only" for="email"><fmt:message key="user.email"/></label>
							<input type="email" class="form-control" id="email" name="email" placeholder="<fmt:message key="user.email"/>" />
						</div>

		                <!-- Change this to a button or input when using this as a form -->
		                <button type="submit" id="submit" class="btn btn-lg btn-primary btn-block">
							<fmt:message key="send"/>
						</button>
		              </fieldset>
		          </form>
		      </div>
		  </div>
    </div>
</div>