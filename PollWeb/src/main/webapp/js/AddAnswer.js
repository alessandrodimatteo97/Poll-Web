$(function() {
    function countRows() {
    var i = 0;
    $('.input-group input').each(function () {
        $(this).attr("placeholder", "Choice "+ ++i);
    });
}
$( "#sortable" ).sortable({
    stop: function (event, ui) {
        countRows(); // re-number rows after sorting
    }
});
$( "#sortable" ).disableSelection();
var addChoice = function(){
    //.attr('placeholder').match( numberPattern );
   let num = $('.input-group').length; 
   console.log(num);
   //co// how many "duplicatable" input fields we currently have
   
            var newNum  = Number(num) + 1;      // the numeric ID of the new input field being added

            // create the new element via clone(), and manipulate it's ID using newNum value
             var newElem = $('.input-group:last').clone();//.attr('id', 'input' + newNum);
             newElem.children("input").attr("placeholder", "Choice "+ newNum).val('');
            // insert the new element after the last "duplicatable" input field
            $('.input-group:last').after(newElem);//
            //ù
            //            e.preventDefault();
          //  e.preventDefault();

         //   $(".input-group:last div:last").replaceWith( "<div>" + newNum + "</td>" );
            // enable the "remove" button
         //   $('#btnDel').attr('disabled','');

};

$('#plus').click(function(){addChoice();});
 
 
$(' #Type').change(function(){
    
    
            console.log('perche non funzioni??????');

    if($(this).val() == 'single choice' || $(this).val() == 'multiple choice') $('#prova1').show();

    else {
        $('#prova1').hide();
    }
        if($('.input-group').length == 1 && $(this).val()=='multiple choice') addChoice();

});

$(document).on('click', '.cazzi', function(e) {
if($('.input-group').length == 1) return;
if($('.input-group').length == 2 && $('#Type option:selected').val()=='multiple choice') return;


    $(this).parent().parent().remove();
        countRows(); 

});

});




