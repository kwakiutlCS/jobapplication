$(function() {

	$(document).on( 'click', '.showExtra', function () {
		var elem = $(this)[0].nextSibling;
		
		if (elem.style.display === "block") {
			$(".extraInfo").hide();
		}
		else {
			$(".extraInfo").hide();
			elem.style.display = 'block';
		}
		return false;
	});
	
	
})