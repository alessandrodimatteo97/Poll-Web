$(function() {
    $('#addParticipant').click(function(){addParticipant();});

    function addParticipant(){
        //.attr('placeholder').match( numberPattern );

        // create the new element via clone(), and manipulate it's ID using newNum value
        var newElem = $('.input-group:last').clone();//.attr('id', 'input' + newNum);
        newElem.children("input").val('');
        // insert the new element after the last "duplicatable" input field
        $('.input-group:last').after(newElem);//
        //ù
        //            e.preventDefault();
        //  e.preventDefault();

        //   $(".input-group:last div:last").replaceWith( "<div>" + newNum + "</td>" );
        // enable the "remove" button
        //   $('#btnDel').attr('disabled','');

    };


    $(document).on('click', '.prof', function(e) {
        if($('.input-group').length == 1) return;


        $(this).parent().parent().remove();


    });
});

