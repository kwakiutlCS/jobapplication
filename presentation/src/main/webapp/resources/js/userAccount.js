$(function() {

	$("#personalDetails").show();
	
	$("#passwordLink").click(function() {
		$(".accountPanel").hide();
		$("#passwordDetails").show();
		$("#globalMessages").html("");
		$(".ui-state-error").removeClass("ui-state-error");
		return false;
	});
	
	$("#personalLink").click(function() {
		$(".accountPanel").hide();
		$("#personalDetails").show();
		$("#globalMessages").html("");
		$(".ui-state-error").removeClass("ui-state-error");
		return false;
	});
	
	$("#extraLink").click(function() {
		$(".accountPanel").hide();
		$("#extraDetails").show();
		$("#globalMessages").html("");
		$(".ui-state-error").removeClass("ui-state-error");
		return false;
	});
});