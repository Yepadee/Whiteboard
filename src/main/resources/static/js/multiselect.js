$(document).ready(function() {
	/*enable multiselection for Helpers*/
    $('.myMultiselect-selectOptions').multiselect({
    	nonSelectedText: 'Please Select',
        includeSelectAllOption: true,
        enableFiltering: true,
        maxHeight: 300
    });
	
	/*enable multiselection for Helpers*/
    $('.select-user-group').multiselect({
    	nonSelectedText: 'Select Students',
        includeSelectAllOption: true,
        enableFiltering: true,
        maxHeight: 300
    });
    
	/*enable multiselection for Helpers*/
    $('#select-helper').multiselect({
    	nonSelectedText: 'Select Helpers',
        includeSelectAllOption: true,
        enableFiltering: true,
        maxHeight: 300
    });
    
    /*once project added or ubdated, pop up a message form*/
    $('#addProject').click(function(){
		$('#submitProject').modal('show');
	});
    
    /*enable multiselection for students (to be assigned to marker/s */
	$('.toMark').multiselect({
    	nonSelectedText: 'Add Users',
        includeSelectAllOption: true,
        enableFiltering: true,
        maxHeight: 300
    });
	
	/*for each student/user add attribute named 'users' in solo project*/
    $("#users .multiselect-container .checkbox>input").attr('name', 'users');
	
	/*for each student/user add attribute named 'users' in group project*/
	$("#groupUsers .multiselect-container .checkbox>input").attr('name', 'groupUsersToMark');
	
    /* In order to marker count in user assignment:
     * fill a list of array (of a value and number) of each input checked,
     * and increment the number of selection of each user/student,
     * then show the value.
     * */
	var usersSelectedArray= [];
    $.each($("input[name='groupUsersToMark']:checked"), function(){
    	$(this).addClass("userChecked");
    	var currentValue = $(this).val();
    	usersSelectedArray.push({value:currentValue,numberOfSelected:1});
    });
    
    var groupUsersSelectedArray= [];
    $.each($("input[name='groupUsersToMark']:checked"), function(){
    	$(this).addClass("userChecked");
    	var currentValue = $(this).val();
    	groupUsersSelectedArray.push({value:currentValue,numberOfSelected:1});
    });
    
    usersCheckArray=[];
    for(var i in usersSelectedArray) {
    	var checkboxValue = usersSelectedArray[i]["value"];    					
    	if(jQuery.inArray(checkboxValue, usersCheckArray) !== -1) {
    		var count=0;
    		var x=0;
    		for(var j in usersCheckArray) {
    			if(checkboxValue == usersCheckArray[j]){
    				usersSelectedArray[j]["numberOfSelected"]++;
    				x=usersSelectedArray[j]["numberOfSelected"];
    			}
    			count++;
	    	}
    		usersSelectedArray[count]["numberOfSelected"]=x;
    	}else{
		}
    	usersCheckArray.push(checkboxValue);
      }
    
    
    groupUsersCheckArray=[];
    for(var i in groupUsersSelectedArray) {
    	var checkboxValue = groupUsersSelectedArray[i]["value"];    					
    	if(jQuery.inArray(checkboxValue, groupUsersCheckArray) !== -1) {
    		var count=0;
    		var x=0;
    		for(var j in groupUsersCheckArray) {
    			if(checkboxValue == groupUsersCheckArray[j]){
    				groupUsersSelectedArray[j]["numberOfSelected"]++;
    				x=groupUsersSelectedArray[j]["numberOfSelected"];
    			}
    			count++;
	    	}
    		groupUsersSelectedArray[count]["numberOfSelected"]=x;
    	}else{
		}
    	groupUsersCheckArray.push(checkboxValue);
      }
    
	$("input[name='users']").each(function (c) {
		for(var key in usersSelectedArray){
    		if(usersSelectedArray[key]["value"] == $(this).val() && usersSelectedArray[key]["value"] != "multiselect-all"){
        	  	numberOfSelected = usersSelectedArray[key]["numberOfSelected"];
        	  	$(this).parent().append(' <small class="smaller selectedUser">(selected '+numberOfSelected+' times)</small>');
        	  	break;
        	}
		}
    });
	
	$("input[name='groupUsersToMark']").each(function (c) {
		for(var key in groupUsersSelectedArray){
    		if(groupUsersSelectedArray[key]["value"] == $(this).val() && groupUsersSelectedArray[key]["value"] != "multiselect-all"){
        	  	numberOfSelected = groupUsersSelectedArray[key]["numberOfSelected"];
        	  	$(this).parent().append(' <small class="smaller selectedUser">(selected '+numberOfSelected+' times)</small>');
        	  	break;
        	}
		}
    });
	
	/*///////////////////////////////////////////////////////////////////////////////////////*/	
	
	/*for each student/user add attribute named 'users'*/
	$("#user-group .multiselect-container .checkbox>input").attr('name', 'groupUsers');
	
	/* In order to marker count in user assignment:
     * fill a list of array (of a value and number) of each input checked,
     * and increment the number of selection of each user/student,
     * then show the value.
     * */
	var groupUsersArray= [];
    $.each($("input[name='groupUsers']:checked"), function(){
    	$(this).addClass("userChecked");
    	var currentValue = $(this).val();
    	groupUsersArray.push(currentValue);
    });
    
    // Colour the selected input to silver to show the user that it is selected before 
	$.each( groupUsersArray, function( key) {
    	$("input[name='groupUsers']").each(function () {
    	  if(groupUsersArray[key] == $(this).val()){
    		  if ($(this).prop('checked')!=true){ 
    			  $(this).parent().parent().parent().remove();
    		  }
    	  }
    	});
	});

});
