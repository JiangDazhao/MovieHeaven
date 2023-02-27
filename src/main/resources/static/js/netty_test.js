$(function () {
    var username = "";
    while (true) {
        username = prompt("Please input your name", "xxx");
        if (username.trim() === "") {
            alert("The username cannot be empty!")
        } else {
            $("#username").text(username);
            break;
        }
    }


    var ws = new WebSocket("ws://127.0.0.1:8083/movie/netty_test");
    ws.onopen = function () {
        console.log("Connected successfully.")
        sendActiveMsg(1);
    }
    ws.onmessage = function (evt) {
        showMessage(evt.data);
    }
    ws.onclose = function () {
        console.log("Connection Closed.")
    }

    ws.onerror = function () {
        console.log("Connection Failed!")
    }

    function showMessage(message) {
        var obj = JSON.parse(message);
        $("#msg_list").append(`<li class="active"}>
                                  <div class="main">
                                    <img class="avatar" width="30" height="30" src="/img/user.png">
                                    <div>
                                        <div class="user_name">${obj.send}</div>
                                        <div class="text">${obj.info}</div>
                                    </div>                       
                                   </div>
                              </li>`);
    }

    $('#my_test').bind({
        focus: function (event) {
            event.stopPropagation()
            $('#my_test').val('');
        },
        keydown: function (event) {
            event.stopPropagation()
            if (event.keyCode === 13) {
                if ($('#my_test').val().trim() === '') {
                    this.blur()
                    setTimeout(() => {
                        this.focus()
                    }, 1000)
                } else {
                    sendMsg();
                    this.blur()
                    setTimeout(() => {
                        this.focus()
                    })
                }
            }
        }
    });
    $('#send').on('click', function (event) {
        event.stopPropagation()
        if ($('#my_test').val().trim() === '') {
            alert("Message cannot be empty!")
        } else {
            sendMsg();
        }
    })

    function sendActiveMsg() {
        const send = {
            user_name: $("#username").text(),
            msg_id: Math.round(Math.random() * 10000),
            msg: "Here I am!",
            type: 0,
        }
        ws.send(JSON.stringify(send));
    }

    function sendMsg() {
        var message = $("#my_test").val();
        $("#msg_list").append(`<li>
                                  <div>
                                      <div>` + message + `</div>
                                  </div>
                              </li>`);
        $("#my_test").val('');

        // message = username + ":" + message;
        const send = {
            user_name: $("#username").text(),
            msg_id: Math.round(Math.random() * 10000),
            msg: message,
            type: 0,
        }
        ws.send(JSON.stringify(send));
    }
});