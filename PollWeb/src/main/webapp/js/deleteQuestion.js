$(function() {

    $(".btn-danger").click(function(){
            console.log($(this).val()); // Cè UN BUG PERCHè GLI ID SONO UNICI...
            $.get($(this).val(), response => {
                $("#QuestionPage").html(response);
            });
        });


});

