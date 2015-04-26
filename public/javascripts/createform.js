var append = function(secId,formId,action,method,titleParam,contentParam,needLink,object)
{
	var existedForm = document.getElementById(formId);
	if(existedForm == null) {
		
		var form = document.createElement("form");
		form.setAttribute("class","ui form");
		form.setAttribute("id",formId);
		form.setAttribute("action",action);
		form.setAttribute("method",method);
		
		if(needLink) {
			form.innerHTML = '<div class="field">\ ' + 
			' <input id="" placeholder="Page Link" type="text" name="">\ </div>\ ';
		}
		
		form.innerHTML +=
		' <div class="field">\ '+ 
		' <input placeholder="" type="text" name=' + titleParam + '></input>\ ' + 
		' <textarea id="" name=' + contentParam + ' placeholder="Content"></textarea>\ </div>\ ' + 
		' <button id="submitbutton" class="ui blue button">Create Post</button>\ ' + 
		' <button id="draftbutton" class="ui blue button">Save Draft</button>\ '; 
		
		
		document.getElementById(secId).appendChild(form);
		$('#' + formId).hide();
		$('#' + formId).show(600);
	}
	else {
		$('#'+formId).toggle(600);
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

