$(function() {

	$(document).on( 'click', '.showExtra', function () {
		$(".extraInfo").hide();
		var elem = $(this)[0].nextSibling;
		elem.style.display = 'block';
		return false;
	});
	
	
	var closeAll = function() {
		alert("closing");
	}
})