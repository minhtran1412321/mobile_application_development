var express = require('express')
	, router = express.Router();
	
global.coordinates = [
	{
		id:1,
		name: 'Minh',
		hobby: 'Dev'
	}
];

router.get('/', function(req, res){
	var result = getDistanceFromLatLonInKm(req.query.lat1, req.query.lon1, req.query.lat2, req.query.lon2)

	return res.json({
		distance: result,
		error: false
	});
});

router.post('/', function(req, res){
	if(!req.body.name) {
		return res.json({
			message: 'username is missing',
			error: true
		});
	}
	global.students.push(req.body);
	return res.json({
		message: 'Success',
		error: false
	});
});

function getDistanceFromLatLonInKm(lat1,lon1,lat2,lon2) {
  var R = 6371; // Radius of the earth in km
  var dLat = deg2rad(lat2-lat1);  // deg2rad below
  var dLon = deg2rad(lon2-lon1); 
  var a = 
    Math.sin(dLat/2) * Math.sin(dLat/2) +
    Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * 
    Math.sin(dLon/2) * Math.sin(dLon/2)
    ; 
  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
  var d = R * c; // Distance in km
  return d;
}

function deg2rad(deg) {
  return deg * (Math.PI/180)
}

module.exports = router;