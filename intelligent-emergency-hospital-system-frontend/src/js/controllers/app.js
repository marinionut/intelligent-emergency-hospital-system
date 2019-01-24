var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('http://localhost:8081/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    //stompClient.connect("","",function (frame) {
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('ws://localhost:8081/topic/alerts', function (greeting) {
            alert("in subscribe1!");
            //showGreeting(JSON.parse(greeting.body).content);
            showGreeting("1");
        });
        stompClient.subscribe('/topic/alerts', function (greeting) {
            // bootbox.alert({
            //     message: "Alerta pentru:" + JSON.parse(greeting.body).name,
            //     callback: function () {
            //         console.log('Succes in rezolvarea alertei!');
            //     }
            // })
            alert(JSON.parse(greeting.body).name);
            showGreeting(JSON.parse(greeting.body).name);
            //showGreeting("2");
        });
        stompClient.subscribe('ws://localhost:8081/topic/alerts', function (greetings) {
            alert("in subscribe3!");
            // showGreeting(JSON.parse(greetings.body).content);
            showGreeting("3");
        });
        stompClient.subscribe('ws://localhost:8081/topic/test', function (test) {
            alert("in subscribe4!");
            // showGreeting(JSON.parse(greeting.body).content);
            showGreeting("4");
        });
        alert("connected");
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    alert($("#name").val());
    stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
    stompClient.send("/hello", {}, JSON.stringify({'name': $("#name").val()}));
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});