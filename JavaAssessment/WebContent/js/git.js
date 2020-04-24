$(document).ready(function() {
	$.ajax({
		url: "https://api.github.com/users"
	}).then(function(data) {
		//console.log(data);
		var table=$("#usersTable");
		var contents = "";
		$.each(data, function(idx) {
			contents += "<tr>";
			
			contents += "<td><div class='uname'>" + data[idx].login + "</div></td>";
			contents += "<td><div class='link'>" + data[idx].followers_url + "</div></td>";
			contents += "<td id='"+ data[idx].login +"'>" + 0 + "</td>";
			//contents += "<td><img src='" + data[idx].avatar_url + "' width=50 height=50/></td>";
			contents += "</tr>";
		})
		//console.log(contents);
		table.append(contents);
		$("#usersTable").on('click','.uname',function(){
	         // get the current row
	         var currentRow=$(this).closest("tr");
	         var uname = currentRow.find("td:eq(0)").text();
	         var link=currentRow.find("td:eq(1)").text(); // get current row 3rd TD
	         console.log(link);
	         $.ajax({
	        	 url: link
	         }).then(function(data) {
	        	 //console.log(data);
	        	 console.log("#" + uname);
	        	 var rid = "#" + uname;
	        	 var p = "";
	        	 $.each(data, function(idx) {
	        		 p += "<img src='" + data[idx].avatar_url + "' width=50 height=50/>";
	        	 })
	        	 $(rid).html(p);
	         })
	    });
	});
});