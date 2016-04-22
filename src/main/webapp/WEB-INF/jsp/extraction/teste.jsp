
<form action="" method="post">
	<label for="name">Nome:</label><input type="text" name="name" value="" id="name" />
	<div id="phones">
		<div id="phone_0">
			<input type="text" name="phones[][number]" value=""	id="phones[][number]" />
		</div>
	</div>
	<a href="javascript:addField('phones','phone','text','phones[][number]');">Adicionar telefone +</a> <br /><br />
</form>


<script type="text/javascript">
(function($){		
	$(document).ready(function(){

		function addField (objId,selectedId,typeField,nameIdField){
			console.log('add');
			divPai = $("#"+objId);
			time = new Date().getTime();
			selectedId = selectedId+"_"+time;
			fieldContent = "<div id='"+selectedId+"' >";
			fieldContent += "<input type='"+typeField+"' name='"+nameIdField+"' value='' id='"+nameIdField +"'>";
			fieldContent += "<a onclick='removeField("+"\""+selectedId+"\""+");'>Remover</a>";
			fieldContent += "</div>";
			divPai.append(fieldContent);
			console.log('add fim');
		};	

		function removeField(obj){
			console.log('remove');
			$(obj).remove();
		};
			
	});
});
</script>