$(function() {

	$("#recoverLink").click(function() {
		$(".loginForm").hide();
		$(".recoverForm").show();
		return false;
	});
	
	$("#loginLink").click(function() {
		$(".loginForm").show();
		$(".recoverForm").hide();
		return false;
	});
});