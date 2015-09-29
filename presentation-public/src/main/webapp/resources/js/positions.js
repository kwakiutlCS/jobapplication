$(function() {

	$("#keywordFilter").show();
	
});


var changeSelector = function(that) {
	var select = $(that);
    var selectMenu = select.parents('div.ui-selectonemenu');
    var value = $('label.ui-selectonemenu-label', selectMenu).text();
    
    if (value === "technical area") value = "area";
    
    $(".filter").hide();
    
    $("#"+value+"Filter").show();
}