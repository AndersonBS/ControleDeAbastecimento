$(document).ready(function () {
    //Mensagens
    $("#message").delay(2500).fadeOut(2000);
    if ($("#message").length) {
        $('#login_modal').modal('show');
    }
   
    //Logout
    $("#quit").click(function () {
        $("#quit_form").submit();
    });
    
    //Alteração de Registro
    $("#quit").click(function () {
        $("#quit_form").submit();
    });
    
    //Exclusão de Registro
    $("#quit").click(function () {
        $("#quit_form").submit();
    });
    
    $("#search_toggle").click(function () {
        $("#search_toggle").hide();
        $("#search").removeAttr('hidden');
    });
    
    $("#automovel").change(function () {
        $("#automovel_form").submit();
    });
});

//Verificação da Concordância com os Termos de Uso
$(function() {
    $('#register_terms').change( function() {
        if ($('#register_terms').prop('checked')) {
            $('#register_submit').removeAttr('disabled');
        } else {
            $('#register_submit').attr('disabled','disabled');
        }
    });
});

function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.onload = function (e) {
            $('#show_foto').attr('src', e.target.result);
        };
        reader.readAsDataURL(input.files[0]);
    }
};