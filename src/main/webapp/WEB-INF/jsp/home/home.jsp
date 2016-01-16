<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<meta name="decorator" content="home" />


<br></br>

<div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
  <!-- Indicators -->
  <ol class="carousel-indicators">
    <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
    <li data-target="#carousel-example-generic" data-slide-to="1"></li>
    <li data-target="#carousel-example-generic" data-slide-to="2"></li>
  </ol>

  <!-- Wrapper for slides -->
  <div class="carousel-inner" role="listbox">
    <div class="item active">
      <img src='<c:url value="/images/sampleslide.jpg"/>' alt="Texto 1" class="img-responsive center-block">
      <div class="carousel-caption">
      <h3>H3</h3>
      <p>Aqui se encontra o texto que fica dentro da imagem</p>
      </div>
    </div>
    
    <div class="item">
      <img src='<c:url value="/images/sampleslide.jpg"/>' alt="Texto 1" class="img-responsive center-block">
      <div class="carousel-caption">
        Aqui se encontra o texto que fica dentro da imagem
      </div>
    </div>
    
    <div class="item">
      <img src='<c:url value="/images/sampleslide.jpg"/>' alt="Texto 1" class="img-responsive center-block">
      <div class="carousel-caption">
        Aqui se encontra o texto que fica dentro da imagem
      </div>
    </div>
    
    Aqui se encontra o texto que fica fora das imagens
  </div>

  <!-- Controls -->
  <a class="left carousel-control" href="#carousel-example-generic" role="button" data-slide="prev">
    <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
    <span class="sr-only">Previous</span>
  </a>
  <a class="right carousel-control" href="#carousel-example-generic" role="button" data-slide="next">
    <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
    <span class="sr-only">Next</span>
  </a>
</div>