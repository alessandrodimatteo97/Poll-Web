
function activate_poll(event) {
    let idPoll = event.target.children[0].value;
    console.log(idPoll);
    $.ajax({
        url : 'admin',
        data : {
            poll_id : idPoll,
            to_do: 'activate'
        },
        success : function(responseText) {
            $('.my_polls').html(responseText);
        }
    });
}

function deactivate_poll(event) {
    let idPoll = event.target.children[0].value;
    console.log(idPoll);
    $.ajax({
        url : 'admin',
        data : {
            poll_id : idPoll,
            to_do: 'deactivate'
        },
        success : function(responseText) {
            $('.my_polls').html(responseText);
        }
    });
}

$(document).on("click", ".btn-secondary" , function(event) {
    let clicked = event.target.id;
    if(clicked === 'activate') {
        activate_poll(event);
    }
    if(clicked === 'deactivate') {
        deactivate_poll(event);
    }
});

