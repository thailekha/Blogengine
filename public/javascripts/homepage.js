$('#blogssegment').hide();
		var showblogs = function() {
			$('#blogssegment').toggle(500);
		}
		
		var showothers = function() {
			$('#otherssegment').toggle(500);
		}
		
		var newsfeed = function() {
			alert("Got here");
			var xhr;
			if(window.XMLHttpRequest) {
				xhr = new XMLHttpRequest();
			}
			else {
				xhr = new ActiveXObject("Microsoft.XMLHTTP");
			}		
			xhr.open("GET","/newsfeed",true);
			xhr.send();
			xhr.onreadystatechange = function() {
				if(xhr.readyState==4) 
				{
					var jNews = JSON.parse(xhr.responseText);
					alert(jNews);
					var news = JSON.parse(jNews);
					alert(news);
					for(i = 0;i < news.length; i++) {
						var update = news[i];
						document.getElementById("newsfeedboard").innerHTML += "ID:" 
						+ update.updaterId + "<br> Updater: " + update.updaterName 
						+ "<br> Post ID: " + update.postId + "<br> Post Title: " 
						+ update.nTitle + "<br> Post Content: " + update.nContent 
						+ "<br> Date: " + update.date;
					}
				}
			}
		}