$(function() {

	$("#codeFilter").show();
	
});


var changeSelector = function(that) {
	var select = $(that);
    var selectMenu = select.parents('div.ui-selectonemenu');
    var value = $('label.ui-selectonemenu-label', selectMenu).text();
    
    $(".filter").hide();
    
    $("#"+value+"Filter").show();
}