
$(function() {
debugger
    let user = {
        isAdmin: false,
    };
    user = getUserFromStorage();
    if (user !== null) {
        redirectTo(user.isAdmin ? statisticsurl : mapurl);
    }
});

/**
 * Registration form
 */
$('#signup').submit(function(e) {
debugger
    e.preventDefault();

    let username = $("input[id='username']").val();
    let password = $("input[id='password']").val();
    let confirmPassword = $("input[id='confirmPassword']").val();
    let name = $("input[id='name']").val();
    let email = $("input[id='email']").val();

    if (username.length < 3 || password.length < 6) {
        alert("Username must be at least 3 characters and password - at least 6. Be tricky!");
        return;
    }

    if(password != confirmPassword){
        alert("Invalid confirm password");
        return;
    }

	if (username && password) {
        $.ajax({
            url: 'http://localhost:8081/register',
            dataType: 'json',
            type: "post",
            async: false,
            headers: {
                "Content-Type": "application/json"
            },
            data: JSON.stringify({
                username: username,
                password: password,
                fullname: name,
                email: email
            }),
            complete: function (xhr, ajaxOptions, thrownError) {
                        debugger
                            if (xhr.status == 200) {
                                logIn(username, password);
                                track("registration");
                            }
                            else if (xhr.status == 400) {
                              alert("Sorry, account with the same name already exists.");
                            } else {
                              alert("An error during account creation. Please, try again.");
                            }
                        }
        });

	} else {
        alert("Please, fill all the fields.");
    }
});

function logIn(username, password) {
    $.ajax({
        url: 'http://localhost:8081/login',
        dataType: 'json',
        type: "post",
        async: false,
        headers: {
            "Content-Type": "application/json"
        },
        data: JSON.stringify({
            username: username,
            password: password,
        }),
        complete: function (xhr, ajaxOptions, thrownError) {
        debugger
            if (xhr.status == 200) {
              let obj = jQuery.parseJSON(xhr.responseText);
              let user = { token: obj.Authorization, isAdmin: obj.Role == 'ROLE_ADMIN', username: getCurrentUser(obj.Authorization)}
              setUserToStorage(user);
              redirectTo(user.isAdmin ? statisticsurl : mapurl);
            }
            else if (xhr.status == 400) {
              alert("Sorry, account with the same name already exists.");
            } else {
              alert("An error during account creation. Please, try again.");
            }
        }
    });
}

/**
 * LogIn form
 */
$('#signin').submit(function(e) {
debugger
    e.preventDefault();

    let username = $("input[id='loginUsername']").val();
    let password = $("input[id='loginPassword']").val();

    if (username.length < 3 || password.length < 6) {
        alert("Username must be at least 3 characters and password - at least 6. Be tricky!");
        return;
    }

	if (username && password) {
        logIn(username, password);
        track("login");
	} else {
        alert("Please, fill all the fields.");
    }
});