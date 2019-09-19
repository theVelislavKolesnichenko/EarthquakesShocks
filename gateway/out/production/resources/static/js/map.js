$(function() {
debugger
    let user = {
        isAdmin: false,
    };
    user = getUserFromStorage();
    if (user === null) {
        redirectTo(homeurl);
    }
    else if(!user.isAdmin) {
        $('#statistics').remove();
    }

    $('.dropdown-item').click(function(e) {
            e.preventDefault();
            $(this).parents(".dropdown").find('.btn').html($(this).text());
            $(this).parents(".dropdown").find('.btn').val($(this).data('value'));
        });
});

let map = null;
let infobox = null;

function GetMap() {
    map = new Microsoft.Maps.Map('#map', {});
    map.setOptions({ disableZooming: false, disableScrollWheelZoom: false, showZoomButtons: false });


    //Double click in to map
    Microsoft.Maps.Events.addHandler(map, 'dblclick', function (e) {
debugger
        e.handled = false;
        //e.preventDefault();
        debugger

        let shapeType = $('#btnDropdown').val();

        if(!shapeType) {
            alert('Please select search type');
            return;
        }

        clearMap();

        //Get Geo Coordinates
        let point = new Microsoft.Maps.Point(e.getX(), e.getY());
        let loc = e.target.tryPixelToLocation(point);
        let location = new Microsoft.Maps.Location(loc.latitude, loc.longitude);

        let postData = JSON.stringify({ "location": { "latitude": loc.latitude, "longitude": loc.longitude }, "shapeType": shapeType });

         $.ajax({
            type: "POST",
            dataType: 'json',
            async: false,
            url: "http://localhost:8083/getpolygon",
            crossDomain:true,
            crossOrigin:true,
            headers: {
                "Content-Type": "application/json",
                'Authorization': getUserFromStorage().token
            },
            data: postData,
            success: function (response) {
            debugger
                    console.log(response);
                    drowPolygon(response);

                    track("double click on map", postData);
                },
                error: function (xhr, ajaxOptions, thrownError) {
                debugger
                    alert("No result found");
                  }
          });
    });

}

function drowPolygon(location) {
debugger
    if(location === null || location.polygons === null || location.polygons.coordinates === null) {
        alert("No result found");
        return;
    }

    if(location !== null) {
        //Create an array of rings.

    for(i = 0; i < location.polygons.coordinates.length; i++) {
        let rings = location.polygons.coordinates[i];


        //Create a polygon
        let polygon = new Microsoft.Maps.Polygon(rings, {
            fillColor: Microsoft.Maps.Color.fromHex(location.polygonInfo.color), //'rgba(255, 0, 0, 0.5)',
            strokeColor: 'black',
            strokeThickness: 1
        });

        //Add the polygon to map
        map.entities.push(polygon);

        infobox = new Microsoft.Maps.Infobox(new Microsoft.Maps.Location(0, 0), { visible: false, autoAlignment: true })
        infobox.setMap(map);

        polygon.metadata = location.polygonInfo;

        //Hover in to polygon*
         Microsoft.Maps.Events.addHandler(polygon, 'mouseover', function (args) {
            displayInfoBox(infobox, args);
            track("mouseover on polygon", JSON.stringify({"location": args.location, "soil type": args.target.metadata.description}));

        });
        }
    }
}

function displayInfoBox(infobox, args) {//, polygon, polygonInfo) {

    let description = "";
    for(i = 0; i < args.target.metadata.description.length; i++) {
        description = description + args.target.metadata.description[i] + '<br />';
    }

    infobox.setOptions({
        location: args.location,
        title: args.target.metadata.title,
        description: description,
        visible: true
    });
}

//clear poligon
function clearMap() {
    if(map !== null) {
        if(infobox != null)
            infobox.setMap(null);

        for (let i = map.entities.getLength() - 1; i >= 0; i--) {
        let polygon = map.entities.get(i);
            if (polygon instanceof Microsoft.Maps.Polygon) {
                map.entities.removeAt(i);
            }
        }
    }
}
