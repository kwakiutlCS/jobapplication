$(function() {

	$("#personalDetails").show();
	
	$("#passwordLink").click(function() {
		$(".accountPanel").hide();
		$("#passwordDetails").show();
		return false;
	});
	
	$("#personalLink").click(function() {
		$(".accountPanel").hide();
		$("#personalDetails").show();
		return false;
	});
	
});