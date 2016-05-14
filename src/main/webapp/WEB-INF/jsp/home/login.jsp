<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link href="<c:url value="/vendor/bootstrap/bootstrap-social.css"/>" rel="stylesheet" type="text/css">

<meta name="decorator" content="login" />

<div class="row">
	<div class="col-md-6 col-md-offset-1">
		<div class="login-panel panel panel-default" style="height: 260px; margin-top: 16%;">
			<div class="panel-heading">
				<h3 class="panel-title">
					<strong>TheEnd - Systematic Mapping Tool</strong>
				</h3>
			</div>
			<div class="panel-body text-justify">
				<p>Mapeamento Sistemático da Literatura (MSL) é um método científico capaz identificar, interpretar e sumarizar os trabalhos relevantes para determinada linha de
				pesquisa, área ou fenômeno de interesse de forma não tendenciosa e replicável [Kitchenham 2004]. Essa metodologia utiliza estudos primários relativos a uma
				Questão de Pesquisa (QP) com o objetivo específico de integrar/sintetizar as evidências relacionadas a essa questão.</p>
				<p>A TheEnd oferece um serviço Web capaz de auxiliar o planejamento, execução e sumarização dos resultados de um MSL, tornando a pesquisa mais ágil e replicável.</p>
			</div>
		</div>
	</div>
	<div class="col-md-4">
		<div class="login-panel panel panel-default" style="height: 260px;">
			<div class="panel-heading">
				<h3 class="panel-title">
					<fmt:message key="signin" />
				</h3>
			</div>
			<div class="panel-body">
				<form action="${linkTo[HomeController].login}" method="post" class="" id="formLogin">
					<fieldset>
						<div class="form-group">
							<label class="sr-only" for="login"><fmt:message	key="user.login" /></label> 
							<input type="text" class="form-control"	id="login" name="login"	placeholder="<fmt:message key="user.loginoremail"/>" />
						</div>
						<div class="form-group">
							<label class="sr-only" for="password"><fmt:message key="user.password" /></label> 
							<input type="password" class="form-control" name="password" placeholder="<fmt:message key="user.password"/>" />
						</div>
						<!-- Change this to a button or input when using this as a form -->
						<button type="submit" id="submit" class="btn btn-success btn-lg btn-block">
							<fmt:message key="access" />
						</button>
					</fieldset>
				</form>
		
			</div>
			<a class="btn btn-lg btn-link pull-lefth" href="<c:url value="/home/create"/>" style="text-align: left; margin-left: 14px;"><fmt:message key="signup" /></a>
			<a class="btn btn-lg btn-link pull-right" href="<c:url value="/home/recovery"/>" style="text-align: right; margin-right: 14px;"><fmt:message key="recovery.password" /></a>
		</div>		
	</div>
</div>


<!-- <div class="text-center"> -->
<!-- 	<a class="btn btn-lg btn-social btn-google-plus" href="#"><i class="fa fa-google"></i> Google</a>  -->
<!-- 	<a class="btn btn-lg btn-social btn-facebook" href="#"><i class="fa fa-facebook"></i> Facebook</a>	 -->
<!-- </div> -->
