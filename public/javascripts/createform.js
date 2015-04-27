var route;
var id;

var append = function(secId,type,action,method,titleParam,contentParam,buttonMsg,needLink,manualRequest,postId)
{
	route = action;
	id = postId;
	alert(id);
	var existedForm = document.getElementById(type + postId);
	if(existedForm == null) {
		var form = document.createElement("form");
		form.setAttribute("class","ui form");
		form.setAttribute("id", type + postId);		
		if(!manualRequest) {
			form.setAttribute("action",action);	
			form.setAttribute("method",method);
		}
		if(needLink) {
			form.innerHTML = '<div class="field">\ ' + 
			' <input id="" placeholder="Page Link" type="text" name="">\ </div>\ ';
		}
		form.innerHTML +=
		' <div class="field">\ '+ 
		' <input id="title' + type +  postId + '" placeholder="Title" type="text" name=' + titleParam + '></input>\ ' + 
		' <textarea id="content' + type +  postId + '" name=' + contentParam + ' placeholder="Content"></textarea>\ </div>\ ' + 
		' <button id="draftbutton" class="ui blue button">Save Draft</button>\ ';
		
		if(manualRequest) {
			form.innerHTML += ' <button class="ui blue button" onclick="manuReq()"> ' + buttonMsg + '</button>';
		}
		
		document.getElementById(secId).appendChild(form);
		$('#' + type +  postId).hide();
		$('#' + type +  postId).show(600);
		
	}
	else {
		$('#' + type + postId).toggle(600);
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
	    		"bullist " +
	    		"numlist link image forecolor " +
	    		"backcolor emoticons",
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
	var nContent = tinyMCE.get("contentedit" + id).getContent();
	alert('Post id:' + id + ', ' + nTitle + ' ' + nContent);
	var date = new Date().toDateString();
	var xhr;
	if(window.XMLHttpRequest) {
		xhr = new XMLHttpRequest();
	}
	else {
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	}	
	xhr.open("POST",route,true);
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhr.send("idToEdit=" + id + "&nTitle=" + nTitle + "&nContent=" + nContent + "&date=" + date);
}