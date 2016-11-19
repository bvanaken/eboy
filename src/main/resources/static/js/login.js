var LOGIN_ENDPOINT = 'users/login';
var FB_APP_ID = '1070576466361642';

var messageBar;
var redirectUri;
var accountLinkingToken;

$(document).ready(function () {
    messageBar = $("#message").hide();

    redirectUri = getUrlParameter('redirect_uri');
    accountLinkingToken = getUrlParameter('account_linking_token');

    registerLoginButtonListener();
    registerFBLoginButtonListener();
});

function registerLoginButtonListener(){
    $("#loginButton").click(function(){
        var email = $("#emailField").val();
        var password = $("#passwordField").val();

        if(email == "" || password == ""){
            messageBar.show().text("Please fill in both fields.");

        } else {
            messageBar.hide();
            sendCredentials(email, password);
        }
    });
}

function registerFBLoginButtonListener(){
    $("#fbLoginButton").click(function(){
        facebookLogin();
    });
}

function sendCredentials(email, password){
    $.ajax({
        type: 'POST',
        url: LOGIN_ENDPOINT,
        data: '{"email": "' + email + '", "password": "' + password + '"}',
        success: function(data) {
            handleResponse(data);
        },
        error: function(){
            handleError();
        },
        contentType: "application/json",
        dataType: 'json'
    });
}

function handleResponse(data){

    if(typeof data == 'undefined'){
        handleWrongCredentials();
        return;
    }

    console.log('Id: ' + data.id);
    console.log('Email: ' + data.email);

    window.location = "https://www.facebook.com/messenger_platform/account_linking?" +
        "account_linking_token=" + accountLinkingToken +
        "&authorization_code=" + data.id;

}

function handleWrongCredentials(){
    messageBar.show().text("Wrong credentials. Please try again.");
}

function handleError(){
    messageBar.show().text("Couldn't log you in. Please try again.");
}

function facebookLogin(){

    // This will login a user with Facebook and return user's public data
    $.fblogin({
        fbId: FB_APP_ID,
        permissions: 'email'
    }).fail(function (error) {
        console.log('error callback', error);
        })
        .progress(function (data) {
            console.log('progress', data);
        })
        .done(function (data) {
            console.log('done everything', data);
            // $('.success-callback').html(JSON.stringify(data, null, '\t'));
        });
}

function getUrlParameter(param) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === param) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};