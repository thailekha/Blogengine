$(document).ready(function() {
$("#link").hide();
	$("textarea").hide();
	$(".form").attr("action", "${_action}");
	$("#inputtitle").attr("name", "${_parameter}");
	
//	$(".form").hide();
//	$("#showbutton").click(function() {
//		$(".form").toggle(500);
//	});
	
	//$("#${_link}").show();
	$("#inputlink").attr("name", "${_linkparam}")
	$("#${_content}").show();
	$("#${_content}").attr("name", "${_contentparam}");
	
	$("#submitbutton").text("${_buttonmessage}");
	$("#submitbutton").attr("disabled",true);
	$("#inputtitle").keypress(function(){
		if($(this).val().length != 0) {
			$("#submitbutton").attr("disabled",false);
		}
		else {
			$("#submitbutton").attr("disabled",true);
		}
	});
});