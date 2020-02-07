$(function() {

    $(document).on('click','.btn-danger',function (e) {
            console.log($(this).val());
            $.get("SummaryPoll?deleteQuestion=delete&k="+$(this).val()+"&qk="+$(this).attr("data-value"), response => {
                $("#QuestionPage").html(response);

            });
        });

});

