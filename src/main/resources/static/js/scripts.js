$('#example-single').multiselect({
	nonSelectedText: 'Add Users',
    includeSelectAllOption: true,
    enableFiltering: true,
    maxHeight: 300
});


$(document).ready(function(){
	$("#flip1").click(function(){$("#panel1").slideToggle("slow");});
	$("#flip2").click(function(){$("#panel2").slideToggle("slow");});
	$("#flip3").click(function(){$("#panel3").slideToggle("slow");});

/*
	$('#forward').on('click',function(){
			var $selected = $('option:selected','#sourceSelect');
			$selected.each(function(i,e){
					console.log(e.value + e.innerHTML);
					$('#destinationSelect').append(
							$('<option />').val(e.value).html(e.innerHTML)
					);
			});
			$selected.remove();
	});

	$('#forward').on('click',function(){
			var $selected = $('option:selected','#sourceSelect');
			$selected.each(function(i,e){
					console.log(e.value + e.innerHTML);
					$('#destinationSelect').append(
							$('<option />').val(e.value).html(e.innerHTML)
					);
			});
			$selected.remove();
	});

	$('#back').on('click',function(){

			var $selected = $('option:selected','#destinationSelect');
			$selected.each(function(i,e){
					console.log(e.value + e.innerHTML);
					$('#sourceSelect').append(
							$('<option />').val(e.value).html(e.innerHTML)
					);
			});
			$selected.remove();
	});


	$('#selected').on('click',function(){
		var $selected = $('option:selected','#sourceSelect');
		$selected.each(function(i,e){
				console.log(e.value + e.innerHTML);
				$('#destinationSelect').append(
						$('<option />').val(e.value).html(e.innerHTML)
				);
		});
		$selected.remove();
	});


*/

	/*
	var seeMarker = $('.seeMarker'); //Add button selector
	var ppp = $('.ppp'); // where you want put a value in
	$(seeMarker).click(function(){
		var contentd = $('#marker').find(":selected").text();
		$(ppp).append(contentd);	
	});
	*/







	/* /////////Add Multiple Marker///////// */
	/*
	var addMarker = $('.add_button1'); //Add button selector
	var markerWrapper = $('.markerWrapper'); //Input field wrapper
	var fieldHTML1 = '<div class="markerWrapperAdded"><div class="form-group"><div class="col-sm-4 col-sm-push-3">'; //New input field html
	fieldHTML1+= '<label for="selectMarker">Select Marker:</label></br>';
	fieldHTML1+= '<input list="marker" name="markers" class="form-control datalist" id="selectMarker">';
	fieldHTML1+= '<datalist id="marker">';
	fieldHTML1+= '<option value="Marker1"> Marker name 1</option><option value="Marker2"> Marker name 2</option><option value="Marker3"> Marker name 3</option><option value="Marker4"> Marker name 4</option><option value="Marker5"> Marker name 5</option><option value="Marker6"> Marker name 6</option>';
	fieldHTML1+= '</datalist>';
	fieldHTML1+= '<input type="submit" value="Select" class="btn btn-black">';
	fieldHTML1+= '</div></div>';
	fieldHTML1+= '<div class="clearfix"></div>';
	fieldHTML1+= '<div class="form-group"><div class="col-sm-3 col-sm-push-3"><select name="stuOpt[]" multiple class="stuOpt">';
	fieldHTML1+= '<option value="oa17248">oa17248</option><option value="bs56489">bs56489</option><option value="fd17569">fd17569</option><option value="rd54698">rd54698</option><option value="lf56984">lf56984</option><option value="oa52793">oa52793</option><option value="go17546">go17546</option><option value="kj54897">kj54897</option>';
	fieldHTML1+= '</select>';
	fieldHTML1+= '</div></div>';
	fieldHTML1+='<div class="form-group"><div class="col-sm-2 col-sm-push-3"><button type="button"  class="btn btn-danger addService removeMarker"><span class="glyphicon glyphicon-minus"></span> Marker</button></div></div>';
	fieldHTML1+='</div>';
	*/
/*
	var fieldHTML1 = '<div class="clearfix"></div>'; //New input field html
	fieldHTML1 += '<div class="form-group"><div class="col-sm-3 col-sm-push-2"><select name="stuOpt[]" multiple class="stuOpt">';
	fieldHTML1 += '<option value="oa17248">oa17248</option>';
	fieldHTML1 += '<option value="bs56489">bs56489</option>';
	fieldHTML1 += '<option value="fd17569">fd17569</option><option value="rd54698">rd54698</option><option value="lf56984">lf56984</option><option value="oa52793">oa52793</option><option value="go17546">go17546</option><option value="kj54897">kj54897</option><option value="br17546">br17546</option><option value="qw54897">qw54897</option><option value="qw54894">qw54894</option>';
	fieldHTML1 += '<option value="qw54895">qw54895</option><option value="qw54896">qw54896</option><option value="qw54896">qw54896</option><option value="qw54896">qw54896</option><option value="qw54896">qw54896</option><option value="qw54896">qw54896</option><option value="qw54896">qw54896</option>';
	fieldHTML1 += '</select></div></div>';
*/

	//Once add button is clicked
	/*
	$(addMarker).click(function(){
		$(markerWrapper).append(fieldHTML1); //Add field html
		$('.stuOpt').multiselect({
				columns: 1,
				placeholder: 'Select Students',
				search: true,
				selectAll: true
		});
	});
	*/
	
	
	//Once remove button is clicked
	/*
	$(markerWrapper).on('click', '.removeMarker', function(e){
			e.preventDefault(); //This method stops the default action of an element from happening.
			$(this).parent().parent().parent().remove(); //Remove field html
	});
	*/
	
	/* /////////End of Add Multiple Markers///////// */

	/* /////////Add Multiple Assignment///////// */
	/*
	var addButton = $('.add_button'); //Add button selector
	var wrapper = $('.assignmentWrapper'); //Input field wrapper
	var fieldHTML = '<div class="assignment-background remove">'; //New input field html
	fieldHTML+= '<div class="form-group"><label class="control-label col-sm-2" for="assignmentName">Name:</label><div class="col-sm-3"><input type="text" class="form-control" id="assignmentName" placeholder="Assignment Name"  name="assignmentName[]"></div></div>';
	fieldHTML+= '<div class="form-group"><label  class="control-label col-sm-2" for="description">Description:</label><div class="col-sm-8"><textarea name="descriptionName[]" class="form-control" rows="3" id="description" placeholder="Add a description for the assignment"></textarea></div></div>';
	fieldHTML+= '<div class="form-group"><label class="control-label col-sm-2" for="deadline">Deadline:</label><div class="col-sm-2"><input class="form-control" type="datetime-local" name="deadline[]" id="deadline"></div></div>';
	fieldHTML+= '<div class="control-label col-sm-2" ><label for="deadline">Weight:</label></div>';
	fieldHTML+= '<div class="form-group"><div class="col-sm-2"><div class="input-group"><span class="input-group-addon">%</span><input type="number" class="form-control" name="weight[]" min="0" max="100"></div></div></div>';
	fieldHTML+= '<div class="clearfix"></div>';
	fieldHTML+='<div class="form-group"><div class="col-sm-2 col-sm-push-2"><button type="button" class="btn btn-danger addService removeAssignment">Remove</button></div></div>';
	fieldHTML+='</div>';
	*/

	//Once add button is clicked
	/*	
	$(addButton).click(function(){

	$(wrapper).append(fieldHTML); //Add field html
	});
	*/
	//Once remove button is clicked
	/*
	$(wrapper).on('click', '.removeAssignment', function(e){
			e.preventDefault(); //This method stops the default action of an element from happening.
			$(this).parent().parent().parent().remove(); //Remove field html
	});
	*/
	/* /////////End of Add Multiple Assignment///////// */
	/*
	$('.stuOpt').multiselect({
			columns: 1,
			placeholder: 'Select Students',
			search: true,
			selectAll: true,
			maxHeight: 400
	});
	*/
	
	/*/////////////////////////////*/

	/* Show content when click on the radioButton(Solo/Group) in new_project.html */
  /*$('input[type="radio"]').click(function(){
    var radiobutton_value = $(this).val();
    $(".project_selected").hide();
    var content= "#show"+radiobutton_value;
    $(content).show();
  });*/
	/* toggle the arrow 90 degree*/
	
	$( ".toggle" ).click( function() {
        $("#arrow").toggleClass('flip');
    });
	
	//$(".chosen-single span").text("Select");
	//$('#example-single').multiselect();
	$(".chosen").chosen();
});

