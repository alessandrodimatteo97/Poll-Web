$(function() {

    $(document).on('click',"button[name='delete']",function (e) {
            console.log($(this).val());
            $.get("Poll_Detail?deleteQuestion=delete&k="+$(this).val()+"&question="+$(this).attr("data-value"), response => {
                $("#QuestionPage").html(response);

            })
        });

});

