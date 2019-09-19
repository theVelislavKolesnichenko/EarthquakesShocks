$(function() {
debugger
    let user = {
        isAdmin: false,
    };
    user = getUserFromStorage();
     if (user === null) {
        redirectTo(homeurl);
    } else if(!user.isAdmin) {
        redirectTo(mapurl);
    }

    $('input[name="daterange"]').daterangepicker({
    }, function(start, end, label) {
    debugger
        let date1 = new Date(start);
        let date2 = new Date(end);
        let datediff = monthDiff(date1, date2);

        if(datediff < 3) {
            $('#startdate').val(start.format('YYYY-MM-DD'));
            $('#enddate').val(end.format('YYYY-MM-DD'));
        } else {
            alert('Choose dates between three months');
        }
        console.log("A new date selection was made: " + start.format('YYYY-MM-DD') + ' to ' + end.format('YYYY-MM-DD'));
    });
    $('#table').hide();
    $('#btnSearch').click(function(e) {
        e.preventDefault();
        search();
    });

    $('#btnReport').click(function(e) {
        e.preventDefault();
        report();
    });
});

function monthDiff(d1, d2) {
    let months;
    months = (d2.getFullYear() - d1.getFullYear()) * 12;
    months -= d1.getMonth();
    months += d2.getMonth();
    return months <= 0 ? 0 : months;
}

function drawTable(mydata){
    $('.bootstrap-table').remove();

    $('#table').clone().attr("id", "newTable").insertAfter("#table");
    $('#newTable').show();
    $('#newTable').bootstrapTable({
        data: mydata
    });
}

function report(){
debugger

 let search = $('#search').val();
 let startdate = null;
 let enddate = null;

 let date = $('#startdate').val();
 if(date !== "" && date !== null)
    startdate = new Date(date).toISOString();

 date = $('#startdate').val();
 if(date !== "" && date !== null)
    enddate = new Date($('#enddate').val()).toISOString();

 let postData = JSON.stringify({ "search" : search, "from" : startdate, "to" : enddate });

   $.ajax({
        type: "POST",
        dataType: 'json',
        async: false,
        crossDomain:true,
        crossOrigin:true,
        url: "http://localhost:8082/getcsvactivities",
        headers: {
            "Content-Type": "application/json",
            'Authorization': getUserFromStorage().token
        },
        data: postData,
        complete: function (xhr, ajaxOptions, thrownError) {
                    debugger
                        if (xhr.status == 200) {
                            download(xhr.responseText, "csv-report.csv", "csv");
                            track("registration");
                        }
                        else {
                          alert("No search results");
                        }
                    }
      });
}

function download(data, filename, type) {
debugger
    let file = new Blob([data], {type: type});
    if (window.navigator.msSaveOrOpenBlob) // IE10+
        window.navigator.msSaveOrOpenBlob(file, filename);
    else { // Others
        let a = document.createElement("a"),
                url = URL.createObjectURL(file);
        a.href = url;
        a.download = filename;
        document.body.appendChild(a);
        a.click();
        setTimeout(function() {
            document.body.removeChild(a);
            window.URL.revokeObjectURL(url);
        }, 0);
    }
}

function search(){
debugger

 let search = $('#search').val();
 let startdate = null;
 let enddate = null;

 let date = $('#startdate').val();
 if(date !== "" && date !== null)
    startdate = new Date(date).toISOString();

 date = $('#startdate').val();
 if(date !== "" && date !== null)
    enddate = new Date($('#enddate').val()).toISOString();

 let postData = JSON.stringify({ "search" : search, "from" : startdate, "to" : enddate });

   $.ajax({
        type: "POST",
        dataType: 'json',
        async: false,
        crossDomain:true,
        crossOrigin:true,
        url: "http://localhost:8082/getactivities",
        headers: {
            "Content-Type": "application/json",
            'Authorization': getUserFromStorage().token
        },
        data: postData,
        success: function (response) {
        debugger
                console.log(response);
                drawTable(response);
            },
            error: function (xhr, ajaxOptions, thrownError) {
            debugger
                alert("No search results");
            }
      });
}

function dateFormat(value, row, index) {
   return moment(value).format('DD.MM.YYYY HH:mm');
}