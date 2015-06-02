<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<meta name="decorator" content="login"/>

<div class="row">
  	<div class="col-md-4 col-md-offset-4">
  	
  		<c:if test="${not empty errors}">
			<div class="alert alert-danger">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				<c:forEach items="${errors}" var="error">
					<b><fmt:message key="${error.category}"/></b> - <fmt:message key="${error.message}"/> <br/>
				</c:forEach>
			</div>
		</c:if>
  	
		<div class="login-panel panel panel-default">
		    <div class="panel-heading">
		        <h3 class="panel-title"><fmt:message key="signin"/></h3>
		    </div>
		    <div class="panel-body">
		        <form action="${linkTo[HomeController].login}" method="post" class="form-horizontal">
		            <fieldset>
		                <div class="form-group">
		                    <label class="sr-only" for="login"><fmt:message key="user.login"/></label>
							<input type="text" class="form-control" id="login" name="login" placeholder="<fmt:message key="user.login"/>"/>
		                </div>
		                <div class="form-group">
		                    <label class="sr-only" for="password"><fmt:message key="user.password"/></label>
							<input type="password" class="form-control" name="password" placeholder="<fmt:message key="user.password"/>" />
		                </div>
		                <!-- Change this to a button or input when using this as a form -->
		                <button type="submit" id="submit" class="btn btn-lg btn-success btn-block">
							<fmt:message key="access"/>
						</button>
		              </fieldset>
		          </form>
		      </div>
		  </div>
    </div>
</div>

<div class="row">
  	<div class="col-md-4 col-md-offset-4">
		<div class="panel panel-default">
		    <div class="panel-heading">
		        <h3 class="panel-title"><fmt:message key="signup"/></h3>
		    </div>
		    <div class="panel-body">
		        <form action="${linkTo[UsersController].add}" method="post" class="form-horizontal">
		            <fieldset>
		                <div class="form-group">
							<label class="sr-only" for="login"><fmt:message key="user.login"/></label>
							<input type="text" class="form-control" id="newname" name="user.name" value="${user.name}" placeholder="<fmt:message key="user.name"/>" />
						</div>
						<div class="form-group">
							<label class="sr-only" for="login"><fmt:message key="user.login"/></label>
							<input type="text" class="form-control" id="newlogin" name="user.login" value="${user.login}" placeholder="<fmt:message key="user.login"/>" />
						</div>
						<div class="form-group">
							<label class="sr-only" for="password"><fmt:message key="user.password"/></label>
							<input type="password" class="form-control" name="user.password" value="${user.password}" placeholder="<fmt:message key="user.password"/>" />
						</div>
		                <!-- Change this to a button or input when using this as a form -->
		                <button type="submit" id="submit" class="btn btn-lg btn-primary btn-block">
							<fmt:message key="create"/>
						</button>
		              </fieldset>
		          </form>
		      </div>
		  </div>
    </div>
</div>

