$(function() {

	$("#userFilter").show();
	
});


var changeSelector = function(that) {
	var select = $(that);
    var selectMenu = select.parents('div.ui-selectonemenu');
    var value = $('label.ui-selectonemenu-label', selectMenu).text();
    
    value = value.substring(0, value.length-1);
    
    $(".filter").hide();
    
    $("#"+value+"Filter").show();
}