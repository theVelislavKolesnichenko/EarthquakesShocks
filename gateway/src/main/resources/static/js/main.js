let mapurl = "map.html";
let statisticsurl = "statistics.html";
let homeurl = "/";

/*
*   User
*/
function setUserToStorage(user) {
	localStorage.setItem('user', JSON.stringify(user));
}

function getUserFromStorage() {
	return jQuery.parseJSON(localStorage.getItem('user'));
}

function removeUserFromStorage() {
    return localStorage.removeItem('user');
}

/*
*   Clear
*/
function clearStorage() {
    removeUserFromStorage();
}

function redirectTo(url) {
    window.location.href = url;
}

/* AJAX */
/**
 * Current user
 */
function getCurrentUser(token) {
debugger
	let user = null;

	if (token) {
		$.ajax({
			url: 'http://localhost:8081/getcurrontuser',
			dataType: 'json',
			type: 'get',
			headers: {'Authorization': token},
			async: false,
            complete: function (xhr, ajaxOptions, thrownError) {
            debugger
                if (xhr.status == 200) {
                  user = xhr.responseText;
                }
                else {
                  clearStorage();
                }
            }
		});
	}

	return user;
}

/*
*   Save Activity
*/
function track(event, additionalData){
debugger
    let user = getUserFromStorage();
    if(user != null) {
    let postData = {
                       "event": event,
                       "username": user.username,
                       "additionalData": additionalData
                   };

   $.ajax({
        type: "POST",
        dataType: 'json',
        url: "http://localhost:8082/saveactivity",
        crossDomain:true,
        crossOrigin:true,
        async: false,
        headers: {
            "Content-Type": "application/json",
            'Authorization': getUserFromStorage().token
        },
        data: JSON.stringify(postData),
        complete: function (xhr, ajaxOptions, thrownError) {
        debugger
            if (xhr.status == 200) {
              console.log(xhr.responseText);
            }
            else {
            }
        }
      });
  }
}

/**
 * Logout
 */
function logout() {
debugger
    track("logout");
    clearStorage();
    location.reload();
}

$(function() {
        $('#logout').click(function() {
            logout();
        });
});