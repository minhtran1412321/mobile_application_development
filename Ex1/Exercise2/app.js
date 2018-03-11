var express = require('express'); 
var app = express();
var bodyParser = require('body-parser'); 

app.use(bodyParser.json());  

app.use(require('./controllers'));

app.listen(process.env.PORT || 3000, function(){
	console.log('service is running');
});