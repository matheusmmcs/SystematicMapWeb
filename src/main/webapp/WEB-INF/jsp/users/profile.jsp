<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="gravatar" uri="http://www.paalgyula.hu/schemas/tld/gravatar" %>


<h3 class="color-primary">
	<fmt:message key="welcome" /><b> ${userInfo.user.name }</b>
</h3>

<div class="row">
	<div class="col-lg-6">
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
