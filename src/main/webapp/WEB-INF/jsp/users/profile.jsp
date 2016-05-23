<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="gravatar" uri="http://www.paalgyula.hu/schemas/tld/gravatar" %>

<ol class="breadcrumb u-margin-top" style="margin-top: 0px;">
  <li><a href="<c:url value="/" />"><fmt:message key="home"/></a></li>
  <li class="active"><fmt:message key="user.profile"/></li>
</ol>

<h3 class="color-primary">
	${map.title} <a id="return" class="btn btn-default pull-right" href="<c:url value="/" />"><fmt:message key="button.back"/></a>
</h3>

<h3 class="color-primary">
	<fmt:message key="welcome" /><b> ${userInfo.user.name }</b>
</h3>

<hr/>

<div class="row">
	<div class="col-md-12">
	<div class="panel panel-default">
<!-- 			<div class="panel-heading"> -->
<%-- 				<b><fmt:message key="mapstudy.details" /></b> --%>
<!-- 			</div> -->
			<!-- /.panel-heading -->
			<div class="panel-body">
				<p>
					<img src="<gravatar:image email="${userInfo.user.email}" size="192"/>" alt="Gravatar" title="${userInfo.user.name }" class="img-responsive"/>
				<p>
					<strong> <fmt:message key="user.name" />: </strong> ${userInfo.user.name}
				<p>
				<p>
					<strong> <fmt:message key="user.email" />: </strong> ${userInfo.user.email}
				<p>
				
			</div>
		</div>
	</div>
</div>
