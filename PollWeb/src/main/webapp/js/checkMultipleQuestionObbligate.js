$(function() {
    $('form').on('click', '.required_group', function(){
            $('input.required_group').prop('required', $('input.required_group:checked').length === 0);
        });

});
