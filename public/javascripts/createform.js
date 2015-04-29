var route;
var id;
var pType;

var append = function(secId,type,action,method,titleParam,prevTitle,contentParam,prevContent,sButtonMsg,dBoxMsg,needLink,linkParam,prevLink,manualRequest,pId)
{
	route = action;
	id = pId;
	pType = type;
	var existedForm = document.getElementById(type + pId);
	if(existedForm == null) {
		var form = document.createElement("form");
		form.setAttribute("class","ui form");
		form.setAttribute("id", type + pId);		
		if(!manualRequest) {
			form.setAttribute("action",action);	
			form.setAttribute("method",method);
		}
		if(needLink) {
			form.innerHTML = '<div class="field">\ ' + 
			'<input id="" placeholder="Page Link" type="text" name="' + linkParam + '" value="' + prevLink + '"></input>\ </div>\ ';
		}
		form.innerHTML +=
		'<div class="field">\ '+ 
		'<input id="title' + type +  pId + '" placeholder="Title" type="text" name=' + titleParam + ' value="' + prevTitle + '"></input>\ ' + 
		'<textarea id="content' + type +  pId + '" name=' + contentParam + ' placeholder="Content">' + prevContent + '</textarea>\ </div>\ ';
		
		if(!needLink) {
			form.innerHTML += '<input id="draftcheckbox' + type +  pId + '" type="checkbox" name="toDraft" value="true">' + dBoxMsg + '</input><br><br>';
		}
		if(manualRequest) {
			form.innerHTML += '<button class="ui blue button" onclick="manuReq()">' + sButtonMsg + '</button>';
		}
		else {
			form.innerHTML += '<button id="submitbutton" class="ui blue button">' + sButtonMsg + '</button>';
		}
		
		document.getElementById(secId).appendChild(form);
		$('#' + type +  pId).hide();
		$('#' + type +  pId).show(600);		
	}
	else {
		$('#' + type + pId).toggle(600);
	}
	

	tinymce.init({
	    selector: "textarea",
	    theme: "modern",
	    plugins: [
	        "lists link image preview",
	        "searchreplace wordcount code fullscreen",
	        "insertdatetime table contextmenu",
	        "emoticons paste textcolor colorpicker textpattern"
	    ],
	    toolbar1: "insertfile undo redo styleselect bold italic " +
	    		"bullist numlist link image forecolor backcolor emoticons",
	    image_advtab: true,
	    templates: [
	        {title: 'Test template 1', content: 'Test 1'},
	        {title: 'Test template 2', content: 'Test 2'}
	    ]
	});
};

var manuReq = function() 
{
	var titleId = "titleedit" + id;
	var nTitle = $('#' + titleId).val();	
	var contentId = "contentedit" + id;
	var checkbox = "false";
	if(document.getElementById("draftcheckbox" + pType +  id).checked) {
		checkbox = "true";
	}
	//var nContent = $('#' + contentId).val();
	var nContent = tinyMCE.get("contentedit" + id).getContent();
	//alert('Post id:' + id + ', ' + nTitle + ' ' + nContent);
	var xhr;
	if(window.XMLHttpRequest) {
		xhr = new XMLHttpRequest();
	}
	else {
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	}	
	xhr.open("POST",route,true);
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhr.send("idToEdit=" + id + "&nTitle=" + nTitle + "&nContent=" + nContent + "&toDraft=" + checkbox);
	//alert("Content: " + nContent);
}