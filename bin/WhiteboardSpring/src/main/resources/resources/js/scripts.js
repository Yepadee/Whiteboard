$(document).ready(function(){
	$("#flip1").click(function(){$("#panel1").slideToggle("slow");});
	$("#flip2").click(function(){$("#panel2").slideToggle("slow");});
	$("#flip3").click(function(){$("#panel3").slideToggle("slow");});


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

	/* /////////Add Multiple Assignment///////// */
	var maxField = 10; //Input fields increment limitation
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

	//Once add button is clicked
	$(addButton).click(function(){
			//Check maximum number of input fields

	$(wrapper).append(fieldHTML); //Add field html
	});

	//Once remove button is clicked
	$(wrapper).on('click', '.removeAssignment', function(e){
			e.preventDefault(); //This method stops the default action of an element from happening.
			$(this).parent().parent().parent().remove(); //Remove field html
	});
	/* /////////End of Add Multiple Assignment///////// */

});
