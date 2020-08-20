"use strict";

var pageNumber;
var topicId = topicIdInputElement.value;


//create new topic
$.ajax({
    type: "GET",
    url: '/topics/findAll',
    dataType: 'json',
    async: true,
    success: createTopicsTable,
    error: warnAboutProblemsWithAjax
});

function createTopicsTable(topics) {
    console.log(topics);
    const table = document.getElementById('topics_list_table');
    table.innerHTML = '';
    $(table).append('<table class="forum-table"><tr>');
    $(table).append('<thead>');
    $(table).append('<tr>');
    $(table).append('<th>Id</th>');
    $(table).append('<th>Title</th>');
    $(table).append('</tr>');
    $(table).append('</thead>');


    for (var i = 0; i < topics.length; ++i) {
        var topic = topics[i];
        $(table).append('<tr>');
        $(table).append('<td>' + topic.id + '</td>');
        $(table).append('<td><a href="/messages/' + topic.id + '/">' + topic.title + '</a></td>');
        $(table).append('</tr>');
    }

    $(table).append('</table>');
}

//hide and show topics under the forum pages
function showDiscussions(catId){
	console.log("test");
	var html_id = "#cat_discussions_" + catId;
	if 	((html_id).css("display" == "block")){
		(html_id).css("display", "none");
	} else {
		(html_id.css("display", "block"));
	}
}

//generate new posts
$.ajax({type : "GET",
    url: '/messages/findTotalNumberOfPages',
    data: {topicId:topicId},
    dataType: 'json',
    async: true,
    success: initMessagePage,
    error: warnAboutProblemsWithAjax
});

function initMessagePage(totalNumberOfPages) {
    if (totalNumberOfPages > 0) {
       fillInformationOnMessagesPage(totalNumberOfPages - 1);
    } else {
       fillInformationOnMessagesPage(0);
    }
}

function fillInformationOnMessagesPage(newPageNumber){
    pageNumber = newPageNumber;

    $.ajax({type : "GET",
        url: '/messages/findTopicById',
        data: {topicId:topicId},
        dataType: 'json',
        async: true,
        success: createMessagesTitle,
        error: warnAboutProblemsWithAjax
    });

    $.ajax({type : "GET",
        url: '/messages/findForPage',
        data: {topicId:topicId, pageNumber:pageNumber},
        dataType: 'json',
        async: true,
        success: createMessagesTable,
        error: warnAboutProblemsWithAjax
    });

    $.ajax({type : "GET",
        url: '/messages/findTotalNumberOfPages',
        data: {topicId:topicId},
        dataType: 'json',
        async: true,
        success: createMessagePagesNavigator,
        error: warnAboutProblemsWithAjax
    });
}

function createMessagesTitle(topic){
    const title = document.getElementById('messages_title');
    title.innerHTML = '';
    $(title).append('<title>');
    $(title).append(topic.title);
    $(title).append(', page ');
    $(title).append(pageNumber + 1);
    $(title).append('</title>');
}

function createMessagesTable(messages){
    console.log(messages);
    const table = document.getElementById('messages_list_table');
    table.innerHTML = '';
    $(table).append('<table class="forum-table"><tr>');
    $(table).append('<thead>');
    $(table).append('<tr>');
    $(table).append('<th>User</th>');
    $(table).append('<th>Text</th>');
    $(table).append('<th>date</th>');
    $(table).append('</tr>');
    $(table).append('</thead>');

    for(var i = 0; i < messages.length; ++i){
        var message = messages[i];
        $(table).append('<tr>');
        $(table).append('<td>' + message.accountUsername + '</td>');
        $(table).append('<td>' + message.text + '</td>');
        $(table).append('<td>' + message.date + '</td>');
        $(table).append('</tr>');
    }

    $(table).append('</table>');
}

function createMessagePagesNavigator(totalNumberOfPages){
    const navigator = document.getElementById('messages_pages_navigator');
    navigator.innerHTML = '';
    if (pageNumber > 0) {
        $(navigator).append(constructButtonForPage(pageNumber-1));
    }
    $(navigator).append(constructButtonForPage(pageNumber));
    if (pageNumber < totalNumberOfPages - 1) {
        $(navigator).append(constructButtonForPage(pageNumber+1));
    }
}

function constructButtonForPage(newPageNumber) {
    return '<input type="button" onClick="fillInformationOnMessagesPage('+newPageNumber+')" ' +
        'value="to ' + (newPageNumber + 1) + '"/>'
}


//log in 
$('.form').find('input, textarea').on('keyup blur focus', function (e) {
  
	var $this = $(this);
		label = $this.prev('label');
   
		if (e.type === 'keyup') {
		  if ($this.val() === '') {
			label.removeClass('active highlight');
		  } else {
			label.addClass('active highlight');
		  }
	  } else if (e.type === 'blur') {
		  if( $this.val() === '' ) {
		  label.removeClass('active highlight'); 
		  } else {
		  label.removeClass('highlight');   
		  }   
	  } else if (e.type === 'focus') {
		
		if( $this.val() === '' ) {
		  label.removeClass('highlight'); 
		  } 
		else if( $this.val() !== '' ) {
		  label.addClass('highlight');
		  }
	  }
   
  });
   
  $('.tab a').on('click', function (e) {
	
	e.preventDefault();
	
	$(this).parent().addClass('active');
	$(this).parent().siblings().removeClass('active');
	
	target = $(this).attr('href');
   
	$('.tab-content > div').not(target).hide();
	
	$(target).fadeIn(600);
	
  });